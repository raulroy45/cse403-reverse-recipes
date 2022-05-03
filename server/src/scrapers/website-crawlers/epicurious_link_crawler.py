import os
from selenium import webdriver
from webdriver_manager.chrome import ChromeDriverManager
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.common.by import By

service = Service(executable_path=ChromeDriverManager().install())

# Open Chrome browser
driver = webdriver.Chrome(service=service)

# Get all the recipe links for one page on Epicurious
def scrape_epi(page_num):
    # Navigate to url
    driver.get("https://www.epicurious.com/search?content=recipe&page=" + str(page_num))

    # Get first section element on the page
    results = driver.find_element(By.TAG_NAME, "section")

    # Get all elements with that contain links to recipes
    links = results.find_elements(By.CSS_SELECTOR, "article.recipe-content-card > a.view-complete-item")

    recipe_links = [link.get_attribute("href") for link in links]
    return recipe_links


script_dir = os.path.dirname(__file__) #<-- absolute dir the script is in
rel_path = "links/epi_links.txt"
abs_file_path = os.path.join(script_dir, rel_path)

# Write recipe links to txt file
with open(abs_file_path, "w") as output:
    for i in range(1, 501):
        recipe_links = scrape_epi(i)
        for link in recipe_links:
            output.write(link + "\n")
  
    output.close()