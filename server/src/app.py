from flask import Flask, request
app = Flask(__name__)

@app.route("/get-recipes", methods=['GET'])
def get_recipes():
    req = request.args.get("ingredients", type=str).split(",")
    return {'ingredients' : req}, 200

if __name__ == "__main__":
    app.run()