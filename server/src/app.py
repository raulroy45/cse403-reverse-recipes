from flask import Flask, request
from src.database import setup_db
from src.database import fetch_all_ingredients

app = Flask(__name__)
db = setup_db()

@app.route("/get-recipes", methods=['GET'])
def get_recipes():
    req = request.args.get("ingredients", type=str).split(",")
    return {'ingredients' : req}, 200

@app.route("/ingredients", methods=['GET'])
def get_ingredients():
    return fetch_all_ingredients(db), 200

if __name__ == "__main__":
    app.run()