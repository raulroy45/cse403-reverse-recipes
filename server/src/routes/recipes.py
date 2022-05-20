from flask import request
from flask import current_app as app
from flasgger import swag_from
from src import db

# recipe?get=all
@app.route("/recipes/", methods=["POST"])
@swag_from("../docs/recipes/recipes.yml")
def get_recipes():
    body = request.get_json()
    ingredients = body["ingredients"]
    cursor = db.cursor()

    # Parameterize the sql statement
    placeholder= "?"
    placeholders= ", ".join(placeholder * len(ingredients))

    # Check for the "filter" query parameter
    filter = request.args.get("filter", type=str)
    if filter == "all":
        # get recipes with all ingredients
        query = "SELECT DISTINCT rid FROM Ingredient_to_Recipe " \
                "WHERE name IN ({}) " \
                "GROUP BY rid HAVING COUNT(*) = ?;".format(placeholders)
        params = ingredients + [len(ingredients)]
        rows = cursor.execute(query, params).fetchall()
        
        recipes = []
        for row in rows:
            result = query_recipe_info(row[0])
            json = format_recipe_json(result)
            recipes.append(json)
    else:
        # get recipes with one or more of the ingredients
        query = "SELECT DISTINCT rid, COUNT(*) AS num_ingredients_matched " \
                "FROM Ingredient_to_Recipe " \
                "WHERE name IN ({}) " \
                "GROUP BY rid ORDER BY num_ingredients_matched DESC;".format(placeholders)
        
        rows = cursor.execute(query, ingredients).fetchall()

        recipes = []
        for row in rows:
            result = query_recipe_info(row[0])
            json = format_recipe_json(result)
            json["num_ingredients_matched"] = row[1]
            recipes.append(json)

    return {"recipes" : recipes}, 200

@app.route("/recipes/<rid>/", methods=["GET"])
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