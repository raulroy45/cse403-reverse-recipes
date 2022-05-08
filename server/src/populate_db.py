import pyodbc
server = 'reverserecipes.database.windows.net'
database = 'rrecipesDB'
username = 'azureuser'
password = 'xgD72UEc4GH5SLw'   
driver = '{ODBC Driver 18 for SQL Server}'

conn = pyodbc.connect('DRIVER='+driver+';SERVER=tcp:'+server+';PORT=1433;DATABASE='+database+';UID='+username+';PWD='+ password)
cursor = conn.cursor()

INSERT_INGREDIENT = "INSERT INTO Ingredient (name, category) VALUES (?, ?);"
GET_INGREDIENT = "SELECT * FROM Ingredient WHERE LOWER(name) = LOWER(?);"

INSERT_MAPPING = "INSERT INTO Ingredient_to_Recipe (name, rid) VALUES (?, ?);"
GET_MAPPING = "SELECT * FROM Ingredient_to_Recipe WHERE name = ? AND rid = ?;"

INSERT_RECIPE = "INSERT INTO Recipe " \
    "(link, title, total_time, yields, ingredients, instructions, image) " \
    "VALUES (?,?,?,?,?,?,?);"

# Add ingredient name to the database with the given category
def add_ingredient(name, category):
    name = name.lower()
    # Check if ingredient already exists in database
    cursor.execute(GET_INGREDIENT, name)
    if cursor.fetchone():
        print("Ingredient {} already exists".format(name))
        return
    cursor.execute(INSERT_INGREDIENT, name, category)
    conn.commit()    

add_ingredient("onion", "Vegetable")
add_ingredient("chicken", "Meat")
add_ingredient("egg", "Dairy")




