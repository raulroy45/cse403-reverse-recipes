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

# def test_get_recipes_no_filter_no_results():
#     response = send_post_request_no_filter({ "ingredients" : [""]})
#     res = json.loads(response.data.decode('utf-8'))
#     assert response.status_code == 200
#     assert "recipes" in res.keys() and isinstance(res.get("recipes"), list)
#     assert len(res.get("recipes")) == 0

# def test_get_recipes_no_filter_one_result():
#     response = send_post_request_no_filter({ "ingredients" : ["spaghetti"]})
#     res = json.loads(response.data.decode('utf-8'))
#     assert response.status_code == 200
#     assert "recipes" in res.keys() and isinstance(res.get("recipes"), list)
#     assert len(res.get("recipes")) == 1

# def test_get_recipes_no_filter_multiple_results():
#     response = send_post_request_no_filter({ "ingredients" : ["spaghetti", "butter", "cheese", "olive oil"]})
#     res = json.loads(response.data.decode('utf-8'))
#     assert response.status_code == 200
#     assert "recipes" in res.keys() and isinstance(res.get("recipes"), list)
#     assert len(res.get("recipes")) > 1
#     recipes = res.get("recipes")
#     match_counts = []

#     for rec in recipes:
#         assert "num_ingredients_matched" in rec.keys() and isinstance(rec.get("num_ingredients_matched"), int)
#         assert rec.get("num_ingredients_matched") > 0
#         match_counts.append(rec["num_ingredients_matched"])

#     assert all(match_counts[i] >= match_counts[i + 1] for i in range(len(match_counts) - 1))

@pytest.mark.skip(reason="Helper function")
def send_post_request_no_filter(data):
    response = app.test_client().post("/recipes/", json=data)
    return response
    

        
    
        