package mealplanner.service

import mealplanner.controller.DataBaseManager
import mealplanner.model.Meal

/**
 * Meal Planner Service
 */
object MealPlanner {

    /**
     * Add a meal to the database
     * @param meal the meal to add
     */
    fun addMeal(meal: Meal) {
        DataBaseManager.init()
        // Insert Meal Data
        var insert = """
            INSERT INTO meals (category, meal)
            VALUES ('${meal.category}', '${meal.name}')
            returning meal_id
        """.trimIndent()
        val mealId = DataBaseManager.executeQuery(insert).getInt(1)
        // println(mealId)

        // Insert Meal Ingredients
        meal.ingredients.forEach {
            insert = """
                INSERT INTO ingredients (meal_id, ingredient)
                VALUES ($mealId, '${it}')
                returning ingredient_id
            """.trimIndent()
            DataBaseManager.executeQuery(insert)
        }
        DataBaseManager.close()
    }

    /**
     * Meals is Empty
     * @return true if there are no meals in the database
     */
    fun isEmpty(): Boolean {
        DataBaseManager.init()
        val select = """
            SELECT * FROM meals
        """.trimIndent()
        val result = DataBaseManager.executeQuery(select)
        return !result.next()
    }

    /**
     * Show all meals
     */
    fun showMeals() {
        val meals = getMeals()
        meals.forEach {
            println(it)
        }
    }

    /**
     * Get all meals
     * @return a list of meals
     */
    private fun getMeals(): List<Meal> {
        val meals = mutableListOf<Meal>()
        DataBaseManager.init()
        // Select Meals
        val selectMeals = """
                SELECT * FROM meals
            """.trimIndent()
        val resultMeals = DataBaseManager.executeQuery(selectMeals)

        while (resultMeals.next()) {
            val mealId = resultMeals.getInt("meal_id")
            val mealName = resultMeals.getString("meal")
            val mealCategory = resultMeals.getString("category")
            val ingredients = mutableListOf<String>()

            // Select Ingredients of a meal
            val selectIngredients = """
                    SELECT * FROM ingredients
                    WHERE meal_id = $mealId
                """.trimIndent()
            val resultIngredients = DataBaseManager.executeQuery(selectIngredients)
            while (resultIngredients.next()) {
                ingredients.add(resultIngredients.getString("ingredient"))
            }

            // add to list
            meals.add(Meal(name = mealName, category = mealCategory, ingredients = ingredients))
        }
        DataBaseManager.close()

        return meals
    }

    /**
     * Initialize the database of Meals
     */
    fun init() {
        // Init Data Base
        DataBaseManager.init()
        // Delete Tables
        // deleteTableMeals()
        // deleteTableIngredients()
        // Create Tables
        createTableMeals()
        createTableIngredients()
        DataBaseManager.close()
    }

    /**
     * Close the database
     */
    fun close() {
        // Close Data Base
        DataBaseManager.close()
    }

    /**
     * Create the table of Meals
     */
    private fun createTableMeals() {
        val meals = """
            CREATE TABLE IF NOT EXISTS meals (
                meal_id INTEGER PRIMARY KEY AUTOINCREMENT,
                category TEXT NOT NULL,
                meal TEXT NOT NULL
            );
        """.trimIndent()
        DataBaseManager.executeUpdate(meals)
    }

    /**
     * Create the table of Ingredients
     */
    private fun createTableIngredients() {
        val ingredients = """
            CREATE TABLE IF NOT EXISTS ingredients (
                ingredient_id INTEGER PRIMARY KEY AUTOINCREMENT,
                meal_id INTEGER NOT NULL references meals(meal_id),
                ingredient TEXT NOT NULL,
                FOREIGN KEY (meal_id) REFERENCES meals(meal_id)
            );
        """.trimIndent()
        DataBaseManager.executeUpdate(ingredients)
    }

    /**
     * Delete the table of Meals
     */
    private fun deleteTableIngredients() {
        val drop = """
            DROP TABLE IF EXISTS meals
        """.trimIndent()
        DataBaseManager.executeUpdate(drop)
    }

    /**
     * Delete the table of Ingredients
     */
    private fun deleteTableMeals() {
        val drop = """
            DROP TABLE IF EXISTS ingredients
        """.trimIndent()
        DataBaseManager.executeUpdate(drop)
    }
}