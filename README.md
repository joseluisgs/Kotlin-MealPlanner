# Kotlin Meal Planner
Proyecto de evaluación para el título de Kotlin Developer en Jetbrains Academy. Consiste en el manejo de un organizador de comidas usando una base de datos SQL.  

[![Kotlin](https://img.shields.io/badge/Code-Kotlin-blueviolet)](https://kotlinlang.org/)
[![LISENCE](https://img.shields.io/badge/Lisence-MIT-green)]()
![GitHub](https://img.shields.io/github/last-commit/joseluisgs/Kotlin-MealPlanner)


![imagen](https://www.adesso-mobile.de/wp-content/uploads/2021/02/kotlin-einfu%CC%88hrung.jpg)

- [Kotlin Meal Planner](#kotlin-meal-planner)
  - [Acerca de](#acerca-de)
  - [Enunciado](#enunciado)
    - [Parte 1](#parte-1)
      - [Description](#description)
    - [Parte 2](#parte-2)
      - [Description](#description-1)
    - [Parte 3](#parte-3)
      - [Description](#description-2)
    - [Parte 4](#parte-4)
      - [Description](#description-3)
    - [Parte 5](#parte-5)
      - [Description](#description-4)
    - [Parte 6](#parte-6)
      - [Description](#description-5)
  - [Autor](#autor)
    - [Contacto](#contacto)
  - [Licencia](#licencia)

## Acerca de
Este proyecto de la academia Jetbrains fue realizado con el fin de evaluar la capacidad de desarrollo de Kotlin. Se trata de un organizador de comida usando bases de datos relacionales.

Every day, people face a lot of difficult choices: for example, what to prepare for breakfast, lunch, and dinner? Are the necessary ingredients in stock? With the Meal Planner, this can be quick and painless! You can make a database of categorized meals and set the menu for the week. This app will also help create and store shopping lists based on the meals so that no ingredient is missing.

## Enunciado

### Parte 1
#### Description
Let's start with something simple. Write a program that can store meals and their properties. Prompt users about the category of a meal (breakfast, lunch, or dinner), name of a meal, and necessary ingredients. The program should print that information with the meal properties in the correct format. In this stage, you don't need to validate user input.

Objectives
To complete this stage, your program should:

Ask about the meal category with the following message: Which meal do you want to add (breakfast, lunch, dinner)?;
Ask about the name of the meal with the message Input the meal's name:;
Inquire about the necessary ingredients with the message Input the ingredients:. The input contains ingredients in one line separated by commas. The output displays each ingredient on a new line (see Examples);

### Parte 2
#### Description
One meal is not going to get you far! Let's create the main menu to add several meals and display their properties. For this, we need to add a few commands:
- Add starts the meal input process and prompts users for the meal properties;
- Show prints the list of saved meals with their names, categories, and ingredients;
- After executing add or show, the program should ask what users want to do next;
- The program must run until users input exit — the command that terminates the program.

In this stage, your program should also check the user input. What if users enter something wrong?
- If the input meal category is something other than breakfast, lunch, or dinner, print Wrong meal category! Choose from: breakfast, lunch, dinner. and prompt users for input;
- If the meal's name or ingredients have a wrong format (for example, there are numbers or non-alphabet characters; ingredients are blank, and so on), print Wrong format. Use letters only! and prompt users for input.

Objectives
To complete this stage, the program must comply with the following requirements:
1. Create an infinite loop of your program that can be terminated with the exit command only;
2. Prompt users to choose an operation with the message What would you like to do (add, show, exit)?
3. After the command has been processed, ask for another operation;
4. Make sure that the input and output formats are correct;
5. If users want to add a meal, follow the sequence from the previous stage. Don't forget to validate input as explained above. Output The meal has been added! before proceeding;
6. If users want to show the meals when no meals have been added, print No meals saved. Add a meal first. If there are meals that can be dislayed, print them in the order they've been added, following the format from Stage 1;
7. Print Bye! and end the program once the exit command is entered;
8. If users fail to input a valid command, print the following message again: What would you like to do (add, show, exit)?

### Parte 3
#### Description
At this point, when we close our program, we lose all our stored meals! Let's improve our planner and connect a database to retrieve all meals after a restart.

To connect a database to our project, we can use Java DataBase Connectivity (JDBC), an API for database-independent connectivity between programs and various databases. This standard ensures the same methods for connecting, updating, querying, and results handling, regardless of the database you employ. However, the choice of the database affects the SQL syntax, available data types, and supported features.

In this project, we will refer to SQLite. It is a very popular and straightforward solution that contains great capabilities. Android, for example, comes with SQLite by default. An SQLite is a single-file database. Although, there may be some differences between different SQL databases. You can find more information on the SQLite datatypes and syntax on the official SQLite website: Datatypes In SQLite Version 3 and SQL As Understood By SQLite.

In this stage, we're going to store meal data in database tables. When the program restarts, the saved data should be available in the program.

To use SQLite in Kotlin, you need to import the sqlite-jdbc library into your project. For the Meal Planner, this is already done. You can find an example of this library in the sqlite-jdbc repository (Github). The same code is applicable in Kotlin, although, with reduced functionality; namely, without the try-catch-finally expressions:

Mind that the nested resultset requires different statement instances.

The jdbc:sqlite:sample.db string includes three strings divided by semicolons. The first one is the database interface, the second is the database, and the third is the path and the name of your database.

It's a good idea to use DB Browser for SQLite — a nice open-source GUI tool for browsing SQLite database files. You can inspect the tables you've created and the data in your database. Moreover, you can try out SQL commands there.

If you are connected to the database file when you check your code, it may lead to issues.

Objectives
1. Your program should create and sequentially connect to a database file named meals.db. When you start your program, this file will be created inside the project root path. In our tests, it is different;
2. Create two tables. Name the first one as meals with three columns: category (text), meal (text), and meal_id (integer). Name the second table ingredients; it must contain three columns: ingredient (text), ingredient_id (integer), and meal_id (integer). Meal_id in both tables must match!
3. Read all data in the tables, so their contents are available before a show operation is requested;
4. When users add a new meal, store it in your database.
   
There are no changes in the input/output structure in this stage.

### Parte 4
#### Description
Let's improve the navigation in the program and make it more user-friendly! In this stage, we will enhance the show command. The program will ask users to specify the meal category with the following message: Which category do you want to print (breakfast, lunch, dinner)? After this, the program will search through the database and print only the chosen category. If the requested category is empty, the program should show an appropriate message.

Objectives
When users input show, your program should:

Ask users for the meal category;
- Search through the database for meals from the chosen category;
- Print Category: <category>. For each meal, print Meal's name: <meal name>, followed by the specific meal ingredients list, each on a new line. The meals and ingredients should be printed in the same order they've been added;
- If the input category doesn't exist, print Wrong meal category! Choose from: breakfast, lunch, dinner.;
- If there're no meals in the category, print No meals found.

### Parte 5
#### Description
A solid person plans for the week ahead! Let's help our users plan their meals for the entire week. In this stage, we will add a new command — plan. We also need to change the main menu accordingly. From now on, it should read as What would you like to do (add, show, plan, exit)?

When users input plan, the program should print the first day of the week, Monday, and print the list of all breakfasts stored in the database in alphabetical order. After this, the program should ask users to pick a meal with the following message: Choose the breakfast for Monday from the list above:

After users input a meal option, the program should verify it. If it's not stored in the database, print This meal doesn’t exist. Choose a meal from the list above. If the input is correct, move on to the next category — lunch and then to dinner. Once the meals for three categories are picked, print Yeah! We planned the meals for Monday. Repeat these steps for other weekdays. In the end, print the whole plan for the week.

Save the plan to the database. For this purpose, create a new table named plan when the program starts. This table contains the meal option, meal category, and meal_id. The third column must match the meal_id columns of the other two tables. You are free to choose how to implement the fields in this table. If a new plan is created, delete the old plan.

Objectives
- Create a table in the database named plan;
- Add the plan option to the menu;
- When users choose the plan option:
  - Print Monday;
  - Print the meal names of the breakfast category in alphabetical order;
  - Prompt Choose the breakfast for Monday from the list above:
  - Once users input a meal, print the meal names of the lunch category in alphabetical order;
  - Prompt Choose the lunch for Monday from the list above:
  - Once users input a meal, print the meal names of the dinner category in alphabetical order;
  - Prompt Choose the dinner for Monday from the list above:
  - Once users input a meal, print Yeah! We planned the meals for Monday.
  - If a meal option isn't in the provided list, print This meal doesn’t exist. Choose a meal from the list above.;
  - Print a blank line and repeat for the rest of the week;
  - Once the plan for the week is drawn, print it. The plan print format is as follows:
```
Monday
Breakfast: [meal's name]
Lunch: [meal's name]
Dinner: [meal's name]

Tuesday etc.
```
- Save the plan data in the plan table. Overwrite the old plan every time a new plan is created.

### Parte 6
#### Description
Meal planning is only half of the job: we also need to be sure that we have all ingredients! In this stage, the program should generate a shopping list that contains the required ingredients for meals. If several meals require one ingredient, put it in the list only once in the following format: ingredient xN, where N is how many times we need this ingredient.

Finally, let's add the option of saving the list as a file. Add the save command to the menu. Save the shopping list to a file and print an appropriate message. Don't forget to ask users for the file name.

Objectives
- Add the save option to the menu. This is how the first message should look in this stage: What would you like to do (add, show, plan, save, exit)?
- When users choose plan:
  - The save option is available only after users have made the plan for the week. If the plan isn't ready, print Unable to save. Plan your meals first. and go back to the menu;
  - Ask users about a filename with the message: Input a filename:
  - When the list has been saved, print Saved!

- The format of the shopping list is as follows:
```
eggs
tomato x3
beef
broccoli
salmon
chicken x2
```
- The ingredients can be stored in any order.

## Autor

Codificado con :sparkling_heart: por [José Luis González Sánchez](https://twitter.com/joseluisgonsan)

[![Twitter](https://img.shields.io/twitter/follow/joseluisgonsan?style=social)](https://twitter.com/joseluisgonsan)
[![GitHub](https://img.shields.io/github/followers/joseluisgs?style=social)](https://github.com/joseluisgs)

### Contacto
<p>
  Cualquier cosa que necesites házmelo saber por si puedo ayudarte 💬.
</p>
<p>
    <a href="https://twitter.com/joseluisgonsan" target="_blank">
        <img src="https://i.imgur.com/U4Uiaef.png" 
    height="30">
    </a> &nbsp;&nbsp;
    <a href="https://github.com/joseluisgs" target="_blank">
        <img src="https://distreau.com/github.svg" 
    height="30">
    </a> &nbsp;&nbsp;
    <a href="https://www.linkedin.com/in/joseluisgonsan" target="_blank">
        <img src="https://upload.wikimedia.org/wikipedia/commons/thumb/c/ca/LinkedIn_logo_initials.png/768px-LinkedIn_logo_initials.png" 
    height="30">
    </a>  &nbsp;&nbsp;
    <a href="https://joseluisgs.github.io/" target="_blank">
        <img src="https://joseluisgs.github.io/favicon.png" 
    height="30">
    </a>
</p>


## Licencia

Este proyecto está licenciado bajo licencia **MIT**, si desea saber más, visite el fichero [LICENSE](./LICENSE) para su uso docente y educativo.