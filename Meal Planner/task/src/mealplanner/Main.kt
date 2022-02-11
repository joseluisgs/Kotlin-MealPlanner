package mealplanner

import mealplanner.model.Meal
import mealplanner.model.PlanItem
import mealplanner.service.MealPlanner
import java.io.File

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
    when (readMenuOption()) {
        "add" -> {
            addMeal()
            mainMenu()
        }
        "show" -> {
            showMeals()
            mainMenu()
        }
        "plan" -> {
            planMeal()
            mainMenu()
        }
        "save" -> {
            saveShoppingList()
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
    val validOptions = listOf("add", "show", "plan", "save", "exit")
    do {
        println("What would you like to do (add, show, plan, save, exit)?")
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
    MealPlanner.saveMeal(Meal(category, name, ingredients))
    println("The meal has been added!")

}

/**
 * Read meal ingredients
 * @return list of ingredients
 */
private fun readMealIngredients(): List<String> {
    var ingredients: List<String>
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
        category = readln().lowercase()
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
    lateinit var category: String
    val validCategories = listOf("breakfast", "lunch", "dinner")
    do {
        println("Which category do you want to print (breakfast, lunch, dinner)?")
        category = readln().lowercase()
        if (!validCategories.contains(category)) {
            println("Wrong meal category! Choose from: breakfast, lunch, dinner.")
        }
    } while (!validCategories.contains(category))

    if (MealPlanner.isEmptyCategory(category)) {
        println("No meals found.")
    } else {
        val meals = MealPlanner.getMeals(category, false)
        println("Category: $category")
        meals.forEach {
            println(it)
        }
    }
}

/**
 * Plan meals for the week
 */
fun planMeal() {
    val days = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
    val categories = listOf("Breakfast", "Lunch", "Dinner")
    val planning = mutableListOf<PlanItem>()

    // Input
    days.forEach { day ->
        println(day)
        categories.forEach { category ->
            // Get meal for category
            val meals = MealPlanner.getMeals(category.lowercase(), true)
            meals.forEach { meal ->
                println(meal.name)
            }
            println("Choose the ${category.lowercase()} for $day from the list above:")
            // Choose the meal
            var meal: Meal?
            do {
                val mealName = readln().trim()
                meal = meals.find { it.name == mealName }
                if (meal == null) {
                    println("This meal doesn’t exist. Choose a meal from the list above.")
                }
            } while (meal == null)
            // Add to planning
            planning.add(PlanItem(day, category, meal))
        }
        println("Yeah! We planned the meals for $day.")
        println()
    }
    // Delete old planning
    MealPlanner.deletePlanning()
    // save to database
    MealPlanner.savePlan(planning)

    // Output
    days.forEach { day ->
        println(day)
        categories.forEach { category ->
            val planItem = planning.find { it.day == day && it.category == category }
            println("${planItem?.category}: ${planItem?.meal?.name}")
        }
        println()
    }
}

/**
 * Save saveShopping List
 */
fun saveShoppingList() {
    // Check if a planning exists
    if (MealPlanner.isEmptyPlan()) {
        println("Unable to save. Plan your meals first.")
    } else {
        val shoppingList = MealPlanner.getShoppingList()
        var filename: String
        do {
            println("Input a filename:")
            filename = readln().trim()
        } while (filename.isEmpty())

        val file = File(filename)
        file.writeText(shoppingList.joinToString("\n"))
        println("Saved!")
    }
}



