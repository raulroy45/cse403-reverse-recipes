import pyodbc
from flask import Flask
from flasgger import Swagger
from src.docs.swagger import template

server = 'reverserecipes.database.windows.net'
database = 'rrecipesDB'
username = 'azureuser'
password = 'xgD72UEc4GH5SLw'   
driver = '{ODBC Driver 17 for SQL Server}'

db = pyodbc.connect('DRIVER='+driver+';SERVER=tcp:'+server+';PORT=1433;DATABASE='+database+';UID='+username+';PWD='+ password)

def create_app():
    app = Flask(__name__)
    swagger = Swagger(app, template=template)

    with app.app_context():
        # Include our Routes
        from src.routes import ingredients
        from src.routes import recipes
        return app
