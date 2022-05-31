import pyodbc
from flask import request, abort
from flask import current_app as app
from flasgger import swag_from
from src.routes import HTTP_200_OK, HTTP_400_BAD_REQUEST, HTTP_404_NOT_FOUND, HTTP_500_INTERNAL_SERVER_ERROR
from src import db

# Gets recipes based on a list of ingredients provided in the body of the POST request.
@app.route("/recipes/", methods=["POST"])
@swag_from("../docs/recipes.yml")
def get_recipes():
    try:
        body = request.get_json()
        if "ingredients" not in body.keys():
            abort(HTTP_400_BAD_REQUEST, "Invalid request. Missing key named 'ingredients'.")
        
        ingredients = body["ingredients"]
        if not isinstance(ingredients, list) or (isinstance(ingredients, list) and len(ingredients) == 0):
            abort(HTTP_400_BAD_REQUEST, "Invalid request. Value of 'ingredients' must be a non-empty array.")

        cursor = db.cursor()

        # Parameterize the sql statement
        placeholder= "?"
        placeholders= ", ".join(placeholder * len(ingredients))

        query = "WITH res AS (SELECT DISTINCT rid, COUNT(*) AS total_num_ingredients " \
                            "FROM Ingredient_to_Recipe " \
                            "GROUP BY rid)" \
                "SELECT itr.rid, total_num_ingredients, COUNT(*) AS num_ingredients_matched " \
                "FROM Ingredient_to_Recipe AS itr, res " \
                "WHERE itr.rid = res.rid AND name IN ({}) " \
                "GROUP BY itr.rid, total_num_ingredients ".format(placeholders)

        # Check for the "filter" query parameter
        filter = request.args.get("filter", type=str)
        if filter == "all":
            # Get recipes that only have ingredients from user's pantry
            query += "HAVING COUNT(*) = total_num_ingredients;"
            rows = cursor.execute(query, ingredients).fetchall() 
        elif filter is None:
            # Get recipes with one or more of the ingredients
            query += "ORDER BY (1.0 * COUNT(*) / total_num_ingredients) DESC;"
            rows = cursor.execute(query, ingredients).fetchall()
        else:
            abort(HTTP_400_BAD_REQUEST, "Invalid request. If the request includes" \
                                        " query parameters, use valid key-value pairs" \
                                        " as described in the apidocs.")

        recipes = []
        for row in rows:
            result = query_recipe_info(row[0])
            json = format_recipe_json(result)
            json["total_num_ingredients"] = row[1]
            json["num_ingredients_matched"] = row[2]
            recipes.append(json)

        return {"recipes" : recipes}, HTTP_200_OK
    except pyodbc.Error:
        abort(HTTP_500_INTERNAL_SERVER_ERROR)


# Given an rid, returns recipe information associated with that rid.
@app.route("/recipes/<int:rid>/", methods=["GET"])
@swag_from("../docs/recipes_rid.yml")
def get_recipe_info(rid):
    try:
        result = query_recipe_info(rid)

        if result:  # valid rid
            json = format_recipe_json(result)
            return json, HTTP_200_OK
        else:   # invalid rid
            abort(HTTP_404_NOT_FOUND, "{} is an invalid recipe ID (rid)".format(rid))
    except pyodbc.Error:
        abort(HTTP_500_INTERNAL_SERVER_ERROR)


# Helper method to query the database for recipe information given an rid
def query_recipe_info(rid):
    cursor = db.cursor()
    query = "SELECT * FROM Recipe WHERE rid = ?;"
    result = cursor.execute(query, rid).fetchone()
    return result if result is not None else None


# Helper method to format JSON containing one recipe information
def format_recipe_json(data):
    ingredients = data[5].split("\n")
    instructions = data[6].split("\n")

    return {
                "rid": data[0],
                "link": data[1],
                "title": "" if data[2] is None else data[2],
                "total_time": 0 if data[3] is None else data[3],
                "yields": 0 if data[4] is None else data[4],
                "ingredients": ingredients,
                "instructions": instructions,
                "image": "" if data[7] is None else data[7],
            }