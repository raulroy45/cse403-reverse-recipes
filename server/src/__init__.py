# import pyodbc
# from flask import Flask
# from flasgger import Swagger

# server = 'reverserecipes.database.windows.net'
# database = 'rrecipesDB'
# username = 'azureuser'
# password = 'xgD72UEc4GH5SLw'   
# driver = '{ODBC Driver 17 for SQL Server}'

# db = pyodbc.connect('DRIVER='+driver+';SERVER=tcp:'+server+';PORT=1433;DATABASE='+database+';UID='+username+';PWD='+ password)

# def create_app():
#     app = Flask(__name__)
#     swagger = Swagger(app, template_file="./docs/template.yml")

#     with app.app_context():
#         # Include our routes
#         from .routes import ingredients
#         from .routes import recipes
#         from .routes import error_handlers
#         return app