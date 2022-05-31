# CSE 403 - Reverse Recipes 🍳

## Project Idea: 

We aim to create an android app that allows users to select ingredients they have lying around the house and look through recipes that match up with the ingredients they inputed. We also aim to program the app to suggest alternative ingredients for the user if they don't have an ingredient for a recipe. 


## Navigating the Repo:

Our repository includes folders for the server (hosted on Azure) side in <code>./server/</code> along with the frontend (mobile application) side in <code>./app/</code>. There is also a <code>./reports/</code> folder that contains weekly status updates.


## Tools:
We use Java and Android Studio to create the frontend for our application and we used Python and SQL along with the services Azure SQL Database, Azure App Service to create the backend for our application. The server is hosted on <code>http://reverserecipes.azurewebsites.net</code> and is able to serve API requests. More details on all the served endpoints and how to use them can be found in <code>server/README.md</code> or http://reverserecipes.azurewebsites.net/apidocs/.

## Setup and Testing:

### Application-side:
- ### Setup
    1. Install Android Studio. Details on installation can be found [here](https://developer.android.com/studio).
	2. Open Android Studio and click the button labeled <code>Open</code>. Open the <code>app/</code> folder within our repository and click the button labeled <code>OK</code>.
	3. Create a virtual device using the instructions [here](https://developer.android.com/studio/run/managing-avds).
- ### Testing
    1. On your terminal, navigate to the folder <code>app/</code> within our repository and run the command <code>./gradlew test</code>. This runs all the application unit tests.

### Server-side:
- ### Setup
    1. Make sure that you have an ODBC software installed on your system. We are using Microsoft ODBC Driver 17 for SQL Server. Installation instructions can be found below:<br>
        - Windows:
            - [(x64)](https://go.microsoft.com/fwlink/?linkid=2187214)   
            - [(x86)](https://go.microsoft.com/fwlink/?linkid=2187215)<br>
        - MacOS:
        [Instructions](https://docs.microsoft.com/en-us/sql/connect/odbc/linux-mac/install-microsoft-odbc-driver-sql-server-macos?view=sql-server-ver15#17)<br>
        - Linux:
        [Instructions](https://docs.microsoft.com/en-us/sql/connect/odbc/linux-mac/installing-the-microsoft-odbc-driver-for-sql-server?view=sql-server-ver15#17)
        
        **NOTE :** If for any reason you are unable to download Microsoft ODBC Driver 17 for SQL Server onto your machine, you can download any other ODBC drivers (eg. unixodbc on MacOS/Linux), but you would not be able to run some tests locally.
  
    2. Obtain conda by installing Miniconda or Anaconda. Details on installation can be found [here](https://docs.conda.io/projects/conda/en/latest/user-guide/install/index.html).
    3. On your Anaconda Prompt (on Windows) or your terminal (on Linux, MacOX) navigate to the folder <code>server/</code> within our repository and run the command <code>conda env create -f environment.yml</code>.
    4. Now you can activate your conda environment required to run the backend server code by running the command <code>conda activate rrenv</code> and deactivate the environment by running the command <code>conda deactivate</code>.
- ### Testing
    1. While running the <code>rrenv</code> environment, run the command <code>pytest</code>. This will run all the tests in the <code>server/tests/</code> folder.

## Deploy and Run
### Application-side:
- ### Build and Run (with Gradle and an Android device)
    1. On your terminal, navigate to the folder <code>app/</code> within our repository and run the command <code>./gradlew build</code>. This outputs <code>app-release-unsigned.apk</code> in the folder <code>app/app/build/outputs/apk/release</code> and <code>app-debug.apk</code> in the folder <code>app/app/build/outputs/apk/debug</code>.
	2. Follow the instructions [here](https://www.thecustomdroid.com/how-to-install-apk-on-android/) to install the desired APK on your Android device.
- ### Build and Run (with Android Studio)
    1. In Android Studio with our project open, press the play button labeled <code>Run</code>.

### Server-side:
1. After making your changes, you can make a pull request and push your changes to the main branch of the repository, which will trigger our CI/CD pipeline to test and deploy the changes to our hosted server.

## Operation Use Cases for the Beta Release:
- Users can go to their pantry and search for ingredients by scrolling through an ingredient list in the search section.
- Users can update their pantry by adding and removing ingredients by clicking (or re-clicking) on an ingredient name in the search section.
- Users can view all the added ingredients in the Pantry section.
- Users can look for the recipes most suitable for the ingredients that they have in their pantry by clicking the *Find Recipes* button in the Pantry section.
- Recipes outputed to the users come in order - with the recipes that match the most ingredients to make that recipe is outputed first, the next most is outputed second, and so on.
- Users can click on a recipe which will take them to the website containing the recipe.
