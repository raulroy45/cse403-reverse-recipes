from flask import request, abort
from flask import current_app as app
from flasgger import swag_from
from src.routes import HTTP_200_OK, HTTP_400_BAD_REQUEST, HTTP_500_INTERNAL_SERVER_ERROR
from src import db

# Gets a list of ingredients and its category.
@app.route("/ingredients/", methods=["GET"])
@swag_from("../docs/ingredients.yml")
def get_ingredients():
    try:
        filter = request.args.get("filter", type=str)
        cursor = db.cursor()
        if filter == "used":
            # Get all ingredients that have an associated recipe
            query = "SELECT DISTINCT i.name, i.category " \
                    "FROM Ingredient AS i, Ingredient_to_Recipe AS itr " \
                    "WHERE i.name = itr.name " \
                    "ORDER by i.category;"
            rows = cursor.execute(query).fetchall()
            return format_ingredients_json(rows), HTTP_200_OK
        elif filter is None:
            # Get all ingredients
            rows = cursor.execute("SELECT * FROM Ingredient ORDER BY category").fetchall()
            return format_ingredients_json(rows), HTTP_200_OK
        else:
            abort(HTTP_400_BAD_REQUEST, "Invalid Request. If the request includes" \
                                        " query parameters, use valid key-value pairs" \
                                        " as described in the apidocs.")
    except:
        abort(HTTP_500_INTERNAL_SERVER_ERROR)

# Helper method to format JSON containing ingredients
def format_ingredients_json(data):
    ingredients = []
    for ingr in data:
        ingredients.append({"name": ingr[0], "category": ingr[1]})
    
    return {"ingredients": ingredients}
