# CSE 403 - Reverse Recipes

## Project Idea: 

We aim to create an android app that allows users to select ingredients they have lying around the house and look through recipes that match up with the ingredients they inputed. We also aim to program the app to suggest alternative ingredients for the user if they don't have an ingredient for a recipe. 


## Navigating the Repo:

Our repository includes folders for the server (hosted on Azure) side in <code>./server/</code> along with the frontend (mobile application) side in <code>./app/</code>. There is also a <code>./reports/</code> folder that contains weekly status updates.


## Tools:
We use Java and Android Studio to create the frontend for our application and we used Python and SQL along with the services Azure SQL Database, Azure App Service to create the backend for our application. The server is hosted on <code>http://reverserecipes.azurewebsites.net</code> and is able to serve API requests. More details on all the served endpoints and how to use them can be found in <code>server/README.md</code>.

## Setup and Testing:

### Application-side:
- ### Build (with Gradle)
    1. On your terminal, navigate to the folder <code>app/</code> within our repository and run the command <code>./gradlew build</code>. This outputs <code>app-release-unsigned.apk</code> in the folder <code>app/app/build/outputs/apk/release</code> and <code>app-debug.apk</code> in the folder <code>app/app/build/outputs/apk/debug</code>.
- ### Testing
    1. On your terminal, navigate to the folder <code>app/</code> within our repository and run the command <code>./gradlew test</code>. This runs all the application unit tests.

### Server-side:
- ### Setup
    1. Obtain conda by installing Miniconda or Anaconda. Details on installation can be found [here](https://docs.conda.io/projects/conda/en/latest/user-guide/install/index.html).
    2. On your Anaconda Prompt (on Windows) or your terminal (on Linux, MacOX) navigate to the folder <code>server/</code> within our repository and run the command <code>conda env create -f environment.yml</code>.
    3. Now you can activate your conda environment required to run the backend server code by running the command <code>conda activate rrenv</code> and deactivate the environment by running the command <code>conda deactivate</code>.
- ### Testing
    1. While running the <code>rrenv</code> environment, run the command <code>pytest</code>. This will run all the tests in the <code>server/tests/</code> folder.

## Deploy and Run
1. After making your changes, you can make a pull request and push your changes to the main branch of the repository, which will trigger our CI/CD pipeline to test and deploy the changes to our hosted server.

## Operation Use Cases for the Beta Release:
- Users can go to their pantry and search for ingredients by scrolling through an ingredient list in the search section.
- Users can update their pantry by adding and removing ingredients by clicking (or re-clicking) on an ingredient name in the search section.
- Users can view all the added ingredients in the Pantry section.
- Users can look for the recipes most suitable for the ingredients that they have in their pantry by clicking the *Find Recipes* button in the Pantry section.
- Recipes outputed to the users come in order - with the recipes that match the most ingredients to make that recipe is outputed first, the next most is outputed second, and so on.
- Users can click on a recipe which will take them to the website containing the recipe.
