import pytest
from src.app import app
import json

def test_all_ingredients():
    response = app.test_client().get("/ingredients/")
    assert response.status_code == 200
    res = json.loads(response.data.decode('utf-8')).get("ingredients")
    assert type(res) is list
    for ingr in res:
        assert "name" in ingr.keys()
        assert "category" in ingr.keys()
