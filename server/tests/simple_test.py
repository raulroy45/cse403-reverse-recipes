import pytest
from src.app import app
import json

def test_get_all_ingredients():
    response = app.test_client().get("/ingredients/")
    assert response.status_code == 200
    res = json.loads(response.data.decode('utf-8')).get("ingredients")
    assert isinstance(res, list)
    for ingr in res:
        assert "name" in ingr.keys()
        assert "category" in ingr.keys()


def test_get_recipe_by_rid():
    response = app.test_client().get("/recipes/1/")
    assert response.status_code == 200
    res = json.loads(response.data.decode('utf-8'))
    assert "image" in res.keys() and isinstance(res.get("image"), str)
    assert "ingredients" in res.keys() and isinstance(res.get("ingredients"), list)
    assert "instructions" in res.keys() and isinstance(res.get("instructions"), list)
    assert "link" in res.keys() and isinstance(res.get("link"), str)
    assert "rid" in res.keys() and isinstance(res.get("rid"), int)
    assert "title" in res.keys() and isinstance(res.get("title"), str)
    assert "total_time" in res.keys() and isinstance(res.get("total_time"), int)
    assert "yields" in res.keys() and isinstance(res.get("yields"), int)
