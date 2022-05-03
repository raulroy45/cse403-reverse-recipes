import pytest
from src.app import app
import json

def test():
    response = app.test_client().get("/get-recipes?ingredients=onion,tomato")
    res = json.loads(response.data.decode('utf-8')).get("ingredients")
    assert res[0] == "onion"
    assert res[1] == "tomato"
    assert response.status_code == 200

