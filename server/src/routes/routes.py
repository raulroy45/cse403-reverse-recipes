from flask import request
from flask import current_app as app
from flasgger import swag_from
from . import db

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

@app.route("/ingredients/", methods=["GET"])
@swag_from("../docs/ingredients/ingredients.yml")
def get_ingredients():
    filter = request.args.get("filter", type=str)
    cursor = db.cursor()
    if filter == "used":
        # get all ingredients that have an associated recipe
        query = "SELECT DISTINCT i.name, i.category " \
                "FROM Ingredient AS i, Ingredient_to_Recipe AS itr " \
                "WHERE i.name = itr.name " \
                "ORDER by i.category;"
        rows = cursor.execute(query).fetchall()
        return format_ingredients_json(rows), 200
    else :
        # get all ingredients
        rows = cursor.execute("SELECT * FROM Ingredient ORDER BY category").fetchall()
        return format_ingredients_json(rows), 200

# Helper method to format JSON containing ingredients
def format_ingredients_json(data):
    ingredients = []
    for ingr in data:
        ingredients.append({"name": ingr[0], "category": ingr[1]})
    
    return {"ingredients": ingredients}
