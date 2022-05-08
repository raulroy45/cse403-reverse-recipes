import pyodbc

# Sets up the database connection and returns the connection
def setup_db():
    server = 'reverserecipes.database.windows.net'
    database = 'rrecipesDB'
    username = 'azureuser'
    password = 'xgD72UEc4GH5SLw'   
    driver = '{ODBC Driver 18 for SQL Server}'

    db = pyodbc.connect('DRIVER='+driver+';SERVER=tcp:'+server+';PORT=1433;DATABASE='+database+';UID='+username+';PWD='+ password)
    return db

# Return all ingredients in the database as a dictionary
def fetch_all_ingredients(db):
    cursor = db.cursor()

    rows = cursor.execute("SELECT * FROM Ingredient").fetchall()
    ingredients = []
    for row in rows:
        ingredients.append({"name": row[0], "category": row[1]})
    
    return {"ingredients": ingredients}



