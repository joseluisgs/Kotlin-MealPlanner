package mealplanner.service

import mealplanner.controller.DataBaseManager
import mealplanner.model.Meal
import mealplanner.model.PlanItem
import mealplanner.model.ShoppingItem

/**
 * Meal Planner Service
 */
object MealPlanner {

    /**
     * Add a meal to the database
     * @param meal the meal to add
     */
    fun saveMeal(meal: Meal) {
        // Insert Meal Data
        var insert = """
            INSERT INTO meals (category, meal)
            VALUES ('${meal.category}', '${meal.name}')
            returning meal_id
        """.trimIndent()
        DataBaseManager.open()
        val mealId = DataBaseManager.executeQuery(insert).getInt(1)
        DataBaseManager.close()
        // println(mealId)

        // Insert Meal Ingredients
        meal.ingredients.forEach {
            insert = """
                INSERT INTO ingredients (meal_id, ingredient)
                VALUES ($mealId, '${it}')
                returning ingredient_id
            """.trimIndent()
            DataBaseManager.open()
            DataBaseManager.executeQuery(insert)
            DataBaseManager.close()
        }
    }

    /**
     * Meals is Empty
     * @return true if there are no meals in the database
     */
    fun isEmptyCategory(category: String): Boolean {
        val select = """
            SELECT * FROM meals WHERE category = '$category'
        """.trimIndent()
        DataBaseManager.open()
        val result = DataBaseManager.executeQuery(select)
        val isEmpty = !result.next()
        DataBaseManager.close()
        return isEmpty
    }


    /**
     * Get all meals from a category
     * @param category the category to get meals from
     * @return a list of meals
     */
    fun getMeals(category: String, order: Boolean = false): List<Meal> {
        val meals = mutableListOf<Meal>()
        // Select Meals
        val selectMeals = if (order) {
            """
                SELECT * FROM meals WHERE category = '$category'
                ORDER BY meal ASC
            """.trimIndent()
        } else {
            """
                SELECT * FROM meals WHERE category = '$category'
            """.trimIndent()
        }
        DataBaseManager.open()
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
            meals.add(Meal(id = mealId, name = mealName, category = mealCategory, ingredients = ingredients))
        }
        DataBaseManager.close()
        return meals
    }

    /**
     * Save a week plan to the database
     * @param plan the plan to save
     */
    fun savePlan(plan: List<PlanItem>) {
        DataBaseManager.open()
        plan.forEach {
            val insert = """
                INSERT INTO plan (day, category, meal_id)
                VALUES ('${it.day}', '${it.category}', '${it.meal.id}')
                returning plan_id
            """.trimIndent()

            DataBaseManager.executeQuery(insert)
        }
        DataBaseManager.close()
    }

    /**
     * Dele the plan from the database
     */
    fun deletePlanning() {
        DataBaseManager.open()
        val delete = """
            DELETE FROM plan
        """.trimIndent()
        DataBaseManager.executeUpdate(delete)
        DataBaseManager.close()
    }

    /**
     * Get if exist a plan
     */
    fun isEmptyPlan(): Boolean {
        val select = """
            SELECT * FROM plan
        """.trimIndent()
        DataBaseManager.open()
        val result = DataBaseManager.executeQuery(select)
        val isEmpty = !result.next()
        DataBaseManager.close()
        return isEmpty
    }

    fun getShoppingList(): List<ShoppingItem> {
        val shoppingList = mutableListOf<ShoppingItem>()
        val select = """
            SELECT ingredients.ingredient, COUNT(ingredients.ingredient) as quantity
            FROM ingredients, PLAN
            WHERE plan.meal_id = ingredients.meal_id
            GROUP BY ingredients.ingredient;
        """.trimIndent()
        DataBaseManager.open()
        val result = DataBaseManager.executeQuery(select)
        while (result.next()) {
            val ingredient = result.getString("ingredient")
            val quantity = result.getInt("quantity")
            shoppingList.add(ShoppingItem(ingredient, quantity))
        }
        DataBaseManager.close()
        return shoppingList
    }


    /**
     * Initialize the database of Meals
     */
    fun init() {
        // Init Data Base
        DataBaseManager.open()
        // Delete Tables
        // deleteTableMeals()
        // deleteTableIngredients()
        // deleteTablePlan()
        // Create Tables
        createTableMeals()
        createTableIngredients()
        createTablePlan()
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
                meal_id INTEGER NOT NULL REFERENCES meals(meal_id),
                ingredient TEXT NOT NULL
            );
        """.trimIndent()
        DataBaseManager.executeUpdate(ingredients)
    }

    /**
     * Create the table of Plannings
     */
    private fun createTablePlan() {
        val plannings = """
            CREATE TABLE IF NOT EXISTS plan (
                plan_id INTEGER PRIMARY KEY AUTOINCREMENT,
                day TEXT NOT NULL,
                category TEXT NOT NULL,
                meal_id	INTEGER NOT NULL REFERENCES meals(meal_id)
            ); 
        """.trimIndent()
        DataBaseManager.executeUpdate(plannings)
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

    /**
     * Delete the table of Plannings
     */
    private fun deleteTablePlan() {
        val drop = """
            DROP TABLE IF EXISTS plan
        """.trimIndent()
        DataBaseManager.executeUpdate(drop)
    }
}