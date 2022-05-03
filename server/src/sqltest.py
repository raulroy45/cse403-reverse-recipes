import pyodbc
server = 'rrecipes.database.windows.net'
database = 'ReverseRecipes'
username = 'azureuser'
password = 'xgD72UEc4GH5SLw'   
driver = '{ODBC Driver 18 for SQL Server}'

with pyodbc.connect('DRIVER='+driver+';SERVER=tcp:'+server+';PORT=1433;DATABASE='+database+';UID='+username+';PWD='+ password) as conn:
    with conn.cursor() as cursor:
        cursor.execute("SELECT TOP 3 name, collation_name FROM sys.databases")
        row = cursor.fetchone()
        while row:
            print (str(row[0]) + " " + str(row[1]))
            row = cursor.fetchone()