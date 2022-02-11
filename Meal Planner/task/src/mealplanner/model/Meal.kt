package mealplanner.model

/**
 * POKO for a meal.
 */
data class Meal(val id: Int, val category: String, val name: String, val ingredients: List<String>) {
    constructor(category: String, name: String, ingredients: List<String>) : this(-1, category, name, ingredients)

    /**
     * Returns a string representation of the meal.
     */
    override fun toString(): String {
        return "Name: $name\nIngredients:\n${ingredients.joinToString("\n")}\n"
    }
}
