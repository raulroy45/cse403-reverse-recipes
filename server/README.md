# API
Our API provides information about food recipes and ingredients. It can query for a recipe most suited for a group of ingredients that a user might have.

## API Endpoints

### **Get all recipes**
Returns recipe information involving a list of ingredients.
- URL:
    - /recipes
- Method:
    - <code>POST</code>
- URL Paramaters:
    - Optional.<br>
    <code>?filter=all</code>
- Data Parameters:
    - Required.<br>
    <code>ingredients={string list}</code>
- Success Response:
    - Code: 200
    - Content: JSON Object with the following form:<br>
    <code>{"image": &lt;string>,<br>
    "ingredients": &lt;string list>,<br>
    "instructions": &lt;string list>,<br>
    "link": &lt;string>,<br>
    "rid": &lt;integer>,<br>
    "title":&lt;string>,<br>
    "total_time": &lt;integer>,<br>
    "yields": &lt;integer>}</code>

### **Get all ingredients**
Get all the ingredients by category available for querying in the database.
- URL:
    - /ingredients
- Method:
    - <code>GET</code>
- URL Paramaters:
    - Optional<br>
    <code>?filter=used</code><br>
        - Gets all ingredients that have an associated recipe
- Data Parameters:
    - Not required.
- Success Response:
    - Code: 200
    - Content: JSON Object in the form:<br>
    <code>{"ingredients": &lt;list of JSON objects specifying "category" and "name">}</code>
        - For example: 
        <code>{"ingredients":<br>
        [{"category":"Baking goods","name":"baking powder"},<br>
        {"category":"Baking goods","name":"baking soda"}]}</code>

### **Get recipes by RID**
Get recipe information specified by an RID.
- URL:
    - /recipes/&lt;RID integer>
- Method:
    - <code>GET</code>
- URL Paramaters:
    - Not required
- Data Parameters:
    - Not required.
- Success Response:
    - Code: 200
    - Content: JSON Object with the following form:<br>
    <code>{"image": &lt;string>,<br>
    "ingredients": &lt;string list>,<br>
    "instructions": &lt;string list>,<br>
    "link": &lt;string>,<br>
    "rid": &lt;integer>,<br>
    "title":&lt;string>,<br>
    "total_time": &lt;integer>,<br>
    "yields": &lt;integer>}</code>
- Error Response:
    - Code: 400
    - Content: <code>{ error: "Invalid Recipe ID" }

    