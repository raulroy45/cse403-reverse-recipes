import csv
import os
from recipe_scrapers import scrape_me
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
# recipe with the given rid doesn not exist in the database
def get_recipe(rid):
    cursor.execute(GET_RECIPE, rid)
    row = cursor.fetchone()
    if row:
        return row 
    return None

def add_recipe(link):
    try:
        scraper = scrape_me(link)
    except:
        print("Could not scrape link: {}".format(link))
        return

    try:
        title = scraper.title()
    except Exception as e:
        print(e)
        title = None

    try:
        time = scraper.total_time()
    except:
        time = None

    try:
        yields = scraper.yields().split(" ")
        if len(yields) > 1:
            yields = yields[0]
        yields = int(yields)
    except:
        yields = None
    
    try:
        ingredients = scraper.ingredients()
    except:
        ingredients = None
    
    try:
        instructions = scraper.instructions()
    except:
        instructions = None
    
    try:
        image = scraper.image()
    except:
        image = None

    cursor.execute(INSERT_RECIPE, link, title, time, yields, str(ingredients), instructions, image)
    conn.commit()
    

def process_ingredients(file):
    with open(file, "r") as csv_file:
        # Skip first row of column names
        next(csv_file)
        csv_reader = csv.reader(csv_file, delimiter=',')
        for row in csv_reader:
            name = row[0]
            category = row[1]
            add_ingredient(name, category)


def process_links(file):
    with open(file, "r") as links_file:
        for link in links_file:
            add_recipe(link.strip())

if __name__ == "__main__":
    script_dir = os.path.dirname(__file__) #<-- absolute dir the script is in
    # rel_path = "./ingredients.csv"
    # abs_file_path = os.path.join(script_dir, rel_path)
    # process_ingredients(abs_file_path)

    rel_path = "./test_links.txt"
    abs_file_path = os.path.join(script_dir, rel_path)
    process_links(abs_file_path)
    




   






