from flask import Flask, request
from flask_restful import Resource, Api, reqparse

app = Flask(__name__)
api = Api(app)

class Recipes(Resource):
    def get(self):
        req = request.args.get("ingredients", type=str).split(",")
        return 200

api.add_resource(Recipes, '/get-recipe')

if __name__ == "__main__":
    app.run()