import pyodbc
from flask import Flask, request, abort
from flasgger import Swagger, swag_from

server = 'reverserecipes.database.windows.net'
database = 'rrecipesDB'
username = 'azureuser'
password = 'xgD72UEc4GH5SLw'   
driver = '{ODBC Driver 17 for SQL Server}'

db = pyodbc.connect('DRIVER='+driver+';SERVER=tcp:'+server+';PORT=1433;DATABASE='+database+';UID='+username+';PWD='+ password)

HTTP_200_OK = 200
HTTP_400_BAD_REQUEST = 400
HTTP_404_NOT_FOUND = 404
HTTP_500_INTERNAL_SERVER_ERROR = 500

app = Flask(__name__)
swagger = Swagger(app, template_file="./docs/template.yml")

# Gets recipes based on a list of ingredients provided in the body of the POST request.
@app.route("/recipes/", methods=["POST"])
@swag_from("./docs/recipes.yml")
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
            query += "HAVING COUNT(*) = total_num_ingredients ORDER BY itr.rid;"
            rows = cursor.execute(query, ingredients).fetchall() 
        elif filter is None:
            # Get recipes with one or more of the ingredients
            query += "ORDER BY itr.rid;"
            rows = cursor.execute(query, ingredients).fetchall()
        else:
            abort(HTTP_400_BAD_REQUEST, "Invalid request. If the request includes" \
                                        " query parameters, use valid key-value pairs" \
                                        " as described in the apidocs.")

        recipes = []
        rids = []

        for row in rows:
            rids.append(row[0])

        recipes_info = query_recipes_info(rids)

        for i in range(len(recipes_info)):
            json = format_recipe_json(recipes_info[i])
            json["total_num_ingredients"] = rows[i][1]
            json["num_ingredients_matched"] = rows[i][2]
            recipes.append(json)

        recipes.sort(key=lambda r: r["num_ingredients_matched"] / r["total_num_ingredients"], reverse=True)

        return { "recipes" : recipes }, HTTP_200_OK
    except pyodbc.Error:
        abort(HTTP_500_INTERNAL_SERVER_ERROR)


# Given an rid, returns recipe information associated with that rid.
@app.route("/recipes/<int:rid>/", methods=["GET"])
@swag_from("./docs/recipes_rid.yml")
def get_recipe_info(rid):
    try:
        result = query_recipes_info(rid)

        if result:  # valid rid
            json = format_recipe_json(result)
            return json, HTTP_200_OK
        else:   # invalid rid
            abort(HTTP_404_NOT_FOUND, "{} is an invalid recipe ID (rid)".format(rid))
    except pyodbc.Error:
        abort(HTTP_500_INTERNAL_SERVER_ERROR)


# Gets a list of ingredients and its category.
@app.route("/ingredients/", methods=["GET"])
@swag_from("./docs/ingredients.yml")
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
            abort(HTTP_400_BAD_REQUEST, "Invalid request. If the request includes" \
                                        " query parameters, use valid key-value pairs" \
                                        " as described in the apidocs.")
    except pyodbc.Error:
        abort(HTTP_500_INTERNAL_SERVER_ERROR)

# Helper method to format JSON containing ingredients
def format_ingredients_json(data):
    ingredients = []
    for ingr in data:
        ingredients.append({"name": ingr[0], "category": ingr[1]})
    
    return {"ingredients": ingredients}


# Helper method to query the database for recipe information given a single rid or an array of rids
def query_recipes_info(rids):
    cursor = db.cursor()
    
    if isinstance(rids, list):
        placeholder= "?"
        placeholders= ", ".join(placeholder * len(rids))
        query = "SELECT * FROM Recipe WHERE rid IN ({}) ORDER BY Recipe.rid;".format(placeholders)
        result = cursor.execute(query, rids).fetchall()
    else:
        query = "SELECT * FROM Recipe WHERE rid = ?;"
        result = cursor.execute(query, rids).fetchone()

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


# Error handlers
@app.errorhandler(HTTP_400_BAD_REQUEST)
def handle_400_error(error):
    return { "message": error.description }, HTTP_400_BAD_REQUEST

@app.errorhandler(HTTP_404_NOT_FOUND)
def handle_404_error(error):
    return { "message": error.description }, HTTP_404_NOT_FOUND

@app.errorhandler(HTTP_500_INTERNAL_SERVER_ERROR)
def handle_500_error(error):
    return { "message": "Something went wrong with our servers 😭✌️" }, HTTP_500_INTERNAL_SERVER_ERROR


if __name__ == "__main__":
    app.run()
