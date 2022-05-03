# cse403-reverse-recipes

Project Idea: 

We aim to create an android app that allows users to select ingredients they have lying around the house and look through recipes that match up with the ingredients they inputed. We also aim to program the app to suggest alternative ingredients for the user if they don't have an ingredient for a recipe. 


Navigating the Repo:

Our repository includes folders for the server side of the app along with the frontend (App) side of the application. There is also a status reports folder that contains weekly status updates.


Tools:
We have hosted our SQL database on Microsoft Azure, with the server name <code>reverserecipes.database.windows.net</code>.

Setup:
To be able to develop on the server side, you need to download conda. Instructions can be found here: https://docs.conda.io/projects/conda/en/latest/user-guide/install/windows.html

First of all, you need to run <code>conda env create -f environment.yml</code> from the server/ folder. Now you have required packages to be able to develop. You can activate the conda environment by typing <code>conda activate rrenv</code> and deactivate it when done using by <code>conda deactivate</code>.

Furthermore, we used Azure SQL database to store required data. To edit the database, you would need some specific login credientials. Contact the repository's owner for more details.
