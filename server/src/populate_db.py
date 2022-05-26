
from recipe_scrapers import scrape_me
from ingreedypy import Ingreedy
import pyodbc
from google.cloud import language
import os
import re
from textblob import TextBlob
import csv

server = 'reverserecipes.database.windows.net'
database = 'rrecipesDB'
username = 'azureuser'
password = 'xgD72UEc4GH5SLw'   
driver = '{ODBC Driver 17 for SQL Server}'

conn = pyodbc.connect('DRIVER='+driver+';SERVER=tcp:'+server+';PORT=1433;DATABASE='+database+';UID='+username+';PWD='+ password)
cursor = conn.cursor()

script_dir = os.path.dirname(__file__) #<-- absolute dir the script is in
rel_path = "./key.json"
abs_file_path = os.path.join(script_dir, rel_path)
client = language.LanguageServiceClient.from_service_account_json(abs_file_path)

exclude = ["water", "salt", "black pepper", "optional", "ice"]

GET_INGREDIENT_EXACT = "SELECT name from Ingredient WHERE name = ?"

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
    cursor.execute("SELECT rid FROM Recipe WHERE link LIKE '' + ? + ''", link)
    if cursor.fetchone():
        print(link + " already exists")
        return

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
        parsed_ingredients = [(Ingreedy().parse(ingr))["ingredient"] for ingr in ingredients]
        entities = analyze_recipe_entities(parsed_ingredients)
        matched_ingredients = confirm_ingredient_mappings(link, entities, ingredients)
        ingredients = "\n".join(ingredients)
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

    if matched_ingredients:
        cursor.execute(INSERT_RECIPE, link, title, time, yields, ingredients, instructions, image)
        cursor.execute("SELECT @@IDENTITY")
        rid = cursor.fetchone()[0]
        conn.commit()

        for ingr in matched_ingredients:
            add_mapping(ingr, rid)

        print("rid", rid, "added")
    

def process_ingredients(file):
    with open(file, "r") as csv_file:
        # Skip first row of column names
        next(csv_file)
        csv_reader = csv.reader(csv_file, delimiter=',')
        for row in csv_reader:
            name = row[0]
            category = row[1]
            add_ingredient(name, category)


def process_links(file, start, num):
    with open(file, "r") as links_file:
        for i in range(start):
            next(links_file)
        count = 0  # REMOVE LATER
        for link in links_file:
            if count == num:
                break
            add_recipe(link.strip())
            count += 1


# Parses a list of ingredients, identifying entities using Google's NLP API.
# Returns a set of entities.
def analyze_recipe_entities(ingredients):
    document = language.Document(content=str(ingredients), type_=language.Document.Type.PLAIN_TEXT)
    response = client.analyze_entities(document=document)

    entities = set()
    for entity in response.entities:
        if entity.type_.name in ("OTHER", "CONSUMER_GOOD"):
            entities.add(entity.name)
            print("=" * 80)
            results = dict(
                name=entity.name,
                type=entity.type_.name,
            )
            for k, v in results.items():
                print(f"{k:15}: {v}")
    
    return entities


# Checks whether the given entity exists in the Reverse Recipe database
# as an ingredient. Returns the ingredient from the database
# if there is a match, otherwise returns None.
def match_entity_to_ingredient(entity):
    cursor = conn.cursor()
    cursor.execute(GET_INGREDIENT_EXACT, entity)
    row = cursor.fetchone()
    if row:
        print(row[0])
        return row[0]

    blob = TextBlob(entity)
    singulars = [word.singularize() for word in blob.words]
    print(singulars)
    
    # Check entire entity for exact match
    cursor.execute(GET_INGREDIENT_EXACT, " ".join(singulars).strip())
    row = cursor.fetchone()
    if row:
        print(row[0])
        return row[0]
    else:
        return None


def confirm_ingredient_mappings(link, entities, ingredients):
    matches = []
    num_excluded = 0
    no_matches = []
    num_excluded = 0
    for entity in entities:
        if exclude_ingredient(entity):
            num_excluded += 1
        else:
            match = match_entity_to_ingredient(entity)
            if match:
                matches.append(match)
            else:
                no_matches.append(entity)
                print(entity, "did not match")

    if (len(matches) + num_excluded) == len(ingredients):
        return matches
    elif (len(matches) / len(ingredients)) >= 0.75 :
        log_incomplete_recipe(link)
        for no_match in no_matches:
            log_no_match_ingredient(no_match)
        return None
    else:
        return None


def exclude_ingredient(ingredient):
    for excl in exclude:
        if re.search(r"\b" + excl + r"\b", ingredient):
            print("exclude {}".format(excl))
            return True
    return False


def log_no_match_ingredient(ingredient):
    with open("no_match_ingredients.txt", "a+") as f:
        f.write(ingredient + ",,\n")


def log_incomplete_recipe(link):
    with open("incomplete_recipes.txt", "a+") as f:
        f.write(link + "\n")


if __name__ == "__main__":
    script_dir = os.path.dirname(__file__) #<-- absolute dir the script is in
    # rel_path = "./ingredients.csv"
    # abs_file_path = os.path.join(script_dir, rel_path)
    # process_ingredients(abs_file_path)

    # rel_path = "./test_links.txt"
    # abs_file_path = os.path.join(script_dir, rel_path)
    # process_links(abs_file_path)

    rel_path = "./scrapers/website-crawlers/links/tasty_links.txt"
    abs_file_path = os.path.join(script_dir, rel_path)
    process_links(abs_file_path, 200, 100)




   






