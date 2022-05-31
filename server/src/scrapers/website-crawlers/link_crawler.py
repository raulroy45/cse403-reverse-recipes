import os
from bs4 import BeautifulSoup
import requests
from recipe_scrapers import scrape_me

all_sitemap_urls = [
    # "https://tasty.co/sitemaps/tasty/sitemap.xml",
    "https://www.allrecipes.com/sitemaps/recipe/1/sitemap.xml",
    "https://www.allrecipes.com/sitemaps/recipe/2/sitemap.xml",
    "https://www.allrecipes.com/sitemaps/recipe/3/sitemap.xml"
]

def scrape_recipes(URL):
    scraped_links = set()
    page = requests.get(URL)
    soup = BeautifulSoup(page.content, "lxml")
    for link in soup.find_all('loc'):
        link = str(link).replace("<loc>", "").replace("</loc>", "")
        scraped_links.add(link)
    return scraped_links

if __name__=='__main__':
    script_dir = os.path.dirname(__file__) #<-- absolute dir the script is in
    rel_path = "links/all_recipes.txt"
    abs_file_path = os.path.join(script_dir, rel_path)

    # Write recipe links to txt file
    with open(abs_file_path, "w") as output: 
        for sitemap_url in all_sitemap_urls:
            recipe_links = scrape_recipes(sitemap_url)
            for link in recipe_links:
                try:
                    scraper = scrape_me(link)
                except KeyboardInterrupt:
                    print("ERROR: Invoked a keyboard interrupt")
                    break
                except:
                    print("Could not scrape link: {}".format(link))
                    continue
                
                if (len(scraper.ingredients()) == 0):
                    print("Invalid recipe site: {}".format(link))
                else:
                    output.write(link + "\n")
            else:
                continue
            break
    output.close()



