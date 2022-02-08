package mealplanner

import mealplanner.model.Meal
import mealplanner.service.MealPlanner

/**
 * Main entry point for the application.
 */
fun main() {
    // init Data Base
    MealPlanner.init()
    mainMenu()
}

/**
 * Main menu
 */
fun mainMenu() {
    val option = readMenuOption()

    when (option) {
        "add" -> {
            addMeal()
            mainMenu()
        }
        "show" -> {
            showMeals()
            mainMenu()
        }
        "exit" -> {
            MealPlanner.close()
            println("Bye!")
        }
    }
}

/**
 * Read menu option
 * @return menu option
 */
private fun readMenuOption(): String {
    lateinit var option: String
    val validOptions = listOf("add", "show", "exit")
    do {
        println("What would you like to do (add, show, exit)?")
        option = readln()
    } while (!validOptions.contains(option))
    return option
}

/**
 * Add meal
 */
fun addMeal() {
    // Category
    val category: String = readMealCategory()
    // Name
    val name: String = readMealName()
    // Ingredients
    val ingredients = readMealIngredients()

    // Add meal
    MealPlanner.addMeal(Meal(category, name, ingredients))
    println("The meal has been added!")

}

/**
 * Read meal ingredients
 * @return list of ingredients
 */
private fun readMealIngredients(): List<String> {
    var ingredients = listOf<String>()
    do {
        var ok = true
        println("Input the ingredients:")
        ingredients = readln().split(",").map { it.trim() }
        ingredients.forEach {
            val regex = """[^a-zA-Z\s]""".toRegex()
            val matchResult = regex.find(it)
            if (matchResult != null || it.isEmpty()) {
                println("Wrong format. Use letters only!")
                ok = false
            }
        }
    } while (!ok)
    return ingredients
}

/**
 * Read meal name
 * @return meal name
 */
private fun readMealName(): String {
    lateinit var name: String
    do {
        var ok = true
        println("Input the meal's name:")
        name = readln().trim()
        val regex = """[^a-zA-Z\s]""".toRegex()
        val matchResult = regex.find(name)
        if (matchResult != null || name.isEmpty()) {
            println("Wrong format. Use letters only!")
            ok = false
        }
    } while (!ok)
    return name
}

/**
 * Read meal category
 * @return meal category
 */
private fun readMealCategory(): String {
    lateinit var category: String
    val validCategories = listOf("breakfast", "lunch", "dinner")
    do {
        println("Which meal do you want to add (breakfast, lunch, dinner)?")
        category = readln()
        if (!validCategories.contains(category)) {
            println("Wrong meal category! Choose from: breakfast, lunch, dinner.")
        }
    } while (!validCategories.contains(category))
    return category
}

/**
 * Show meals
 */
fun showMeals() {
    if (MealPlanner.isEmpty()) {
        println("No meals saved. Add a meal first.")
    } else {
        MealPlanner.showMeals()
    }
}
