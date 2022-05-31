from flask import request
from flask import current_app as app
from flasgger import swag_from
from src import db

@app.route("/recipes/", methods=["POST"])
@swag_from("../docs/recipes/recipes.yml")
def get_recipes():
    body = request.get_json()
    ingredients = body["ingredients"]
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
        # get recipes that only have ingredients from user's pantry
        query += "HAVING COUNT(*) = total_num_ingredients;"
        rows = cursor.execute(query, ingredients).fetchall() 
    else:
        # get recipes with one or more of the ingredients
        query += "ORDER BY (1.0 * COUNT(*) / total_num_ingredients) DESC;"
        rows = cursor.execute(query, ingredients).fetchall()

    recipes = []
    for row in rows:
        result = query_recipe_info(row[0])
        json = format_recipe_json(result)
        json["total_num_ingredients"] = row[1]
        json["num_ingredients_matched"] = row[2]
        recipes.append(json)

    return {"recipes" : recipes}, 200


@app.route("/recipes/<int:rid>/", methods=["GET"])
@swag_from("../docs/recipes/recipes_rid.yml")
def get_recipe_info(rid):
    result = query_recipe_info(rid)

    if result:
        json = format_recipe_json(result)
        return json, 200
    else:
        return "Invalid Recipe ID", 400

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