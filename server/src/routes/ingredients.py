from flask import request
from flask import current_app as app
from flasgger import swag_from
from src import db

# Gets a list of ingredients and its category.
@app.route("/ingredients/", methods=["GET"])
@swag_from("../docs/ingredients.yml")
def get_ingredients():
    filter = request.args.get("filter", type=str)
    cursor = db.cursor()
    if filter == "used":
        # Get all ingredients that have an associated recipe
        query = "SELECT DISTINCT i.name, i.category " \
                "FROM Ingredient AS i, Ingredient_to_Recipe AS itr " \
                "WHERE i.name = itr.name " \
                "ORDER by i.category;"
        rows = cursor.execute(query).fetchall()
        return format_ingredients_json(rows), 200
    else :
        # Get all ingredients
        rows = cursor.execute("SELECT * FROM Ingredient ORDER BY category").fetchall()
        return format_ingredients_json(rows), 200


# Helper method to format JSON containing ingredients
def format_ingredients_json(data):
    ingredients = []
    for ingr in data:
        ingredients.append({"name": ingr[0], "category": ingr[1]})
    
    return {"ingredients": ingredients}