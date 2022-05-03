import time, os
from selenium import webdriver
from webdriver_manager.chrome import ChromeDriverManager
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.common.by import By
from selenium.common.exceptions import NoSuchElementException
from selenium.common.exceptions import WebDriverException

# link scraper for https://tasty.co/topic/

# Open Chrome browser
service = Service(executable_path=ChromeDriverManager().install())
options = webdriver.ChromeOptions()
options.add_argument('--ignore-certificate-errors')
options.add_argument('--ignore-ssl-errors')
driver = webdriver.Chrome(service=service, options=options)

def scrape_tasty_topic(topic):
    # Navigate to url
    driver.get("https://tasty.co/topic/" + topic)

    while True:
        # Scroll to "Show more" button and click it
        try:
            # Center the "Show more" button into view
            button = driver.find_element(By.CLASS_NAME, "show-more-button")
            desired_y = (button.size['height'] / 2) + button.location['y']
            window_h = driver.execute_script('return window.innerHeight')
            window_y = driver.execute_script('return window.pageYOffset')
            current_y = (window_h / 2) + window_y
            scroll_y_by = desired_y - current_y

            driver.execute_script("window.scrollBy(0, arguments[0]);", scroll_y_by)
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
    
    return recipe_links


topics = ["breakfast", "lunch", "dinner", "desserts", "snacks"]

script_dir = os.path.dirname(__file__) #<-- absolute dir the script is in
rel_path = "links/tasty_links.txt"
abs_file_path = os.path.join(script_dir, rel_path)

# Write links to text file
with open(abs_file_path, "w") as output:
    for topic in topics:
        recipe_links = scrape_tasty_topic(topic)
        for link in recipe_links:
            output.write(link + "\n")
    
    output.close()
