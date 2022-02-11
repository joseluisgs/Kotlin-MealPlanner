package mealplanner.model

/**
 * POKO for shopping items.
 */
data class ShoppingItem(val name: String, val quantity: Int) {
    override fun toString(): String {
        return if (quantity > 1) "$name x$quantity" else name
    }
}