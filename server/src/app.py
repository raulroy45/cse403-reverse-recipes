import pyodbc
from flask import Flask, request
app = Flask(__name__)

# Sets up the database connection and returns the connection
def setup_db():
    server = 'reverserecipes.database.windows.net'
    database = 'rrecipesDB'
    username = 'azureuser'
    password = 'xgD72UEc4GH5SLw'   
    driver = '{ODBC Driver 18 for SQL Server}'

    db = pyodbc.connect('DRIVER='+driver+';SERVER=tcp:'+server+';PORT=1433;DATABASE='+database+';UID='+username+';PWD='+ password)
    return db

db = setup_db()

# recipe?get=all
@app.route("/recipes/", methods=["POST"])
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
def get_ingredients():
    return fetch_all_ingredients(db), 200

@app.route("/recipes/<rid>/", methods=["GET"])
def get_recipe_info(rid):
    result = query_recipe_info(rid)

    if result:
        json = format_recipe_json(result)
    
    return json, 200

# Helper method to query the database for recipe information given an rid
def query_recipe_info(rid):
    cursor = db.cursor()
    query = "SELECT * FROM Recipe WHERE rid = ?;"
    result = cursor.execute(query, rid).fetchone()
    return result

# Helper method to format JSON containing one recipe information
def format_recipe_json(data):
    instructions = data[6].split("\n")

    return {
                "rid": data[0],
                "link": data[1],
                "title": "" if data[2] is None else data[2],
                "total_time": 0 if data[3] is None else data[3],
                "yields": 0 if data[4] is None else data[4],
                "ingredients": data[5],
                "instructions": instructions,
                "image": "" if data[7] is None else data[7],
            }

# Return all ingredients in the database as a dictionary
def fetch_all_ingredients(db):
    cursor = db.cursor()

    rows = cursor.execute("SELECT * FROM Ingredient ORDER BY category").fetchall()
    ingredients = []
    for row in rows:
        ingredients.append({"name": row[0], "category": row[1]})
    
    return {"ingredients": ingredients}


if __name__ == "__main__":
    app.run()