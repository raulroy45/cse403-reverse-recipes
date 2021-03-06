-- Ingedient information
CREATE TABLE Ingredient (
    name VARCHAR(255) PRIMARY KEY,
    category VARCHAR(255)
);

-- Holds recipe information
CREATE TABLE Recipe (
    rid INT IDENTITY(1,1) PRIMARY KEY,
    link TEXT,
    title TEXT,
    total_time INT,
    yields INT,
    ingredients TEXT,
    instructions TEXT,
    image TEXT
);

-- Mapping of ingredient name to recipe id
CREATE TABLE Ingredient_to_Recipe (
    rid INT,
    name VARCHAR(255),
    PRIMARY KEY (rid, name),
    FOREIGN KEY (rid) REFERENCES Recipe(rid),
    FOREIGN KEY (name) REFERENCES Ingredient(name)
);