from flask import Flask, request
from src.database import setup_db
from src.database import fetch_all_ingredients

app = Flask(__name__)
db = setup_db()

# recipe?get=all
@app.route("/recipes", methods=["POST"])
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

        rid_list = []
        for row in rows:
            rid_list.append(row[0])
    else:
        # get recipes with one or more of the ingredients
        query = "SELECT DISTINCT rid, COUNT(*) AS num_ingredients_matched " \
                "FROM Ingredient_to_Recipe " \
                "WHERE name IN ({}) " \
                "GROUP BY rid ORDER BY num_ingredients_matched DESC;".format(placeholders)
        
        rows = cursor.execute(query, ingredients).fetchall()

        rid_list = []
        for row in rows:
            rid_list.append(row[0])

    return {'rids' : rid_list}, 200

@app.route("/ingredients", methods=["GET"])
def get_ingredients():
    return fetch_all_ingredients(db), 200

@app.route("/recipes/<rid>", methods=["GET"])
def get_recipe_information(rid):
    cursor = db.cursor()
    query = "SELECT * FROM Recipe WHERE rid = ?;"
    result = cursor.execute(query, rid).fetchone()

    if result:
        json = format_recipe_json(result)
    
    return json, 200

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


if __name__ == "__main__":
    app.run()