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
GET_RECIPE = "SELECT * FROM Recipe WHERE rid = ?;"

# Add ingredient name to the database with the given category
def add_ingredient(name, category):
    name = name.lower()
    # Check if ingredient already exists in database
    if get_ingredient(name):
        print("Ingredient {} already exists".format(name))
        return
    cursor.execute(INSERT_INGREDIENT, name, category)
    conn.commit()

# Add mapping of ingredient name to recipe id to the database
def add_mapping(name, rid):
    name = name.lower()
    # Make sure ingredient exists in db
    if not get_ingredient(name):
        print("No such ingredient {}".format(name))
        return

    # Make sure recipe exists in db
    if not get_recipe(rid):
        print("No such recipe id {}".format(rid))
        return

    # Check if mapping already exists in database
    cursor.execute(GET_MAPPING, name, rid)
    if cursor.fetchone():
        print("Mapping ({}, {}) already exists".format(name, rid))
        return

    cursor.execute(INSERT_MAPPING, name, rid)
    conn.commit()

# Returns ingredient information given its name, returns None if
# ingredient does not exist in the database
def get_ingredient(name):
    cursor.execute(GET_INGREDIENT, name)
    row = cursor.fetchone()
    if row:
        return True
    return False

# Returns recipe information given its rid, returns None if
# a recipe with the given rid doesn not exist in the database
def get_recipe(rid):
    cursor.execute(GET_RECIPE, rid)
    row = cursor.fetchone()
    if row:
        return row 
    return None
    

if __name__ == "__main__":
    add_ingredient("onion", "Vegetable")
    add_ingredient("chicken", "Meat")
    add_ingredient("egg", "Dairy")
    add_mapping("chicken", 1)

