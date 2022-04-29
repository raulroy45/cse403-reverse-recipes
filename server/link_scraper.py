import time
from selenium import webdriver
from webdriver_manager.chrome import ChromeDriverManager
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.common.by import By
from selenium.common.exceptions import NoSuchElementException
from selenium.common.exceptions import WebDriverException

# link scraper for https://tasty.co/topic/dinner

# Open Chrome browser
service = Service(executable_path=ChromeDriverManager().install())
options = webdriver.ChromeOptions()
options.add_argument('--ignore-certificate-errors')
options.add_argument('--ignore-ssl-errors')
driver = webdriver.Chrome(service=service, options=options)

# Navigate to url
driver.get("https://tasty.co/topic/dinner")

# Infinite scroll 
# Get scroll height after first time page load
last_height = driver.execute_script("return document.body.scrollHeight")
while True:
    # Scroll down to bottom
    driver.execute_script("window.scrollTo(0, document.body.scrollHeight);")
    # Wait to load page
    time.sleep(2)
    # Calculate new scroll height and compare with last scroll height
    new_height = driver.execute_script("return document.body.scrollHeight")
    if new_height == last_height:
        break
    last_height = new_height
    # Click "Show more" button
    try:
        button = driver.find_element(By.CLASS_NAME, "show-more-button")
        button.click()
        time.sleep(2)
    except (NoSuchElementException, WebDriverException) as e:
        break

# Get the first "main" element
result = driver.find_element(By.TAG_NAME, "main")

# Get all "a" tags, containing hyperlinks to recipe
links = result.find_elements(By.TAG_NAME, "a")

# Get list of recipe links
recipe_links = []

for link in links:
    href = link.get_attribute("href")
    # Don't include if it's a compilation of recipes article
    if "/recipe" in href:
        recipe_links.append(href)

# Write links to text file
with open("tasty_links.txt", "w") as output:
  for link in recipe_links:
    output.write(link + "\n")