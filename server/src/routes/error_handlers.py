from flask import current_app as app
from src.routes import HTTP_400_BAD_REQUEST, HTTP_404_NOT_FOUND, HTTP_500_INTERNAL_SERVER_ERROR

@app.errorhandler(HTTP_400_BAD_REQUEST)
def handle_400_error(error):
    return { "message": error.description }, HTTP_400_BAD_REQUEST

@app.errorhandler(HTTP_404_NOT_FOUND)
def handle_404_error(error):
    return { "message": error.description }, HTTP_404_NOT_FOUND

@app.errorhandler(HTTP_500_INTERNAL_SERVER_ERROR)
def handle_500_error(error):
    return { "message": "Something went wrong with our servers 😭✌️" }, HTTP_500_INTERNAL_SERVER_ERROR