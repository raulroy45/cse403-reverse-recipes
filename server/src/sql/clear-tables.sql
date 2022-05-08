DELETE FROM Ingredient_to_Recipe;

DELETE FROM Recipe;
-- Reset identity id
DBCC CHECKIDENT (Recipe, RESEED, 0)

DELETE FROM Ingredient;

