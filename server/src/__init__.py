from flask import Flask
from flasgger import Swagger
from src.docs.swagger import template

def create_app():
    app = Flask(__name__)
    swagger = Swagger(app, template=template)

    with app.app_context():
        # Include our Routes
        from src.routes import ingredients
        from src.routes import recipes
        return app

    
