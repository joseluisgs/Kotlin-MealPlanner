package mealplanner.model

/**
 * POKO for a meal.
 */
data class Meal(val category: String, val name: String, val ingredients: List<String>) {
    /**
     * Returns a string representation of the meal.
     */
    override fun toString(): String {
        return "Name: $name\nIngredients:\n${ingredients.joinToString("\n")}\n"
    }
}
