import org.hyperskill.hstest.dynamic.DynamicTest
import org.hyperskill.hstest.stage.StageTest
import org.hyperskill.hstest.testcase.CheckResult
import org.hyperskill.hstest.testing.TestedProgram
import java.io.File
import java.sql.DatabaseMetaData
import java.sql.DriverManager

class MealPlannerTest : StageTest<Any>() {

    @DynamicTest(order = 1)
    fun normalExe6Test(): CheckResult {
        try {
            val dbFile = File("meals.db")
            if (dbFile.exists()) dbFile.delete()
        } catch (e: Exception) {
            return CheckResult(false, "An exception was thrown, while trying to delete a database file.")
        }

        val co = CheckOutput()
        if (!co.start("What would you like to do (add, show, exit)?"))
            return CheckResult(
                false,
                "Your program should ask the user about the required action: \"(add, show, exit)?\""
            )

        val dbUrl = "jdbc:sqlite:meals.db"
        val tables = listOf(
            dbTable(
                "ingredients",
                listOf(Pair("ingredient", "text"), Pair("ingredient_id", "integer"), Pair("meal_id", "integer"))
            ),
            dbTable("meals", listOf(Pair("category", "text"), Pair("meal", "text"), Pair("meal_id", "integer")))
        )

        val (res, errorStr) = checkTableSchema(dbUrl, tables)
        if (!res) return CheckResult(false, errorStr)

        if (!co.input("exit", "Bye!"))
            return CheckResult(false, "Your output should contain \"Bye!\"")

        if (!co.programIsFinished())
            return CheckResult(false, "The application didn't exit.")

        return CheckResult.correct()
    }

    @DynamicTest(order = 2)
    fun normalExe7Test(): CheckResult {
        try {
            val dbFile = File("meals.db")
            if (dbFile.exists()) dbFile.delete()
        } catch (e: Exception) {
            return CheckResult(false, "An exception was thrown, while trying to delete a database file.")
        }

        try {
            val co = CheckOutput()
            if (!co.start("What would you like to do (add, show, exit)?"))
                return CheckResult(
                    false,
                    "Your program should ask the user about the required action: \"(add, show, exit)?\""
                )

            if (!co.input("add", "Which meal do you want to add (breakfast, lunch, dinner)?"))
                return CheckResult(
                    false,
                    "Your program should ask the user about meal category: \"(breakfast, lunch, dinner)?\""
                )

            if (!co.input("lunch", "Input the meal's name:"))
                return CheckResult(false, "Your output should contain \"Input the meal's name:\"")

            if (!co.input("sushi", "Input the ingredients:"))
                return CheckResult(false, "Your output should contain \"Input the ingredients:\"")

            if (!co.input("salmon, rice, avocado", "The meal has been added!"))
                return CheckResult(false, "Your output should contain \"The meal has been added!\"")

            if (!co.inputNext("What would you like to do (add, show, exit)?"))
                return CheckResult(
                    false,
                    "Your program should ask the user about the required action: \"(add, show, exit)?\""
                )

            if (!co.input(
                    "show", "Category: lunch", "Name: sushi", "Ingredients:",
                    "salmon", "rice", "avocado"
                )
            )
                return CheckResult(false, "Wrong \"show\" output for a saved meal.")

            if (!co.input("exit", "Bye!"))
                return CheckResult(false, "Your output should contain \"Bye!\"")

            if (!co.programIsFinished())
                return CheckResult(false, "The application didn't exit.")
        } catch (e: Exception) {
            return CheckResult(false, "An exception was thrown while testing - ${e.message}")
        }

        return CheckResult.correct()
    }

    @DynamicTest(order = 3)
    fun normalExe8Test(): CheckResult {
        try {
            val dbFile = File("meals.db")
            if (!dbFile.exists()) return CheckResult(false, "The meals.db database file doesn't exist.")
        } catch (e: Exception) {
            return CheckResult(false, "An exception was thrown, while trying to check a database file.")
        }
        try {
            val co = CheckOutput()
            if (!co.start("What would you like to do (add, show, exit)?"))
                return CheckResult(
                    false,
                    "Your program should ask the user about the required action: \"(add, show, exit)?\""
                )

            if (!co.input(
                    "show", "Category: lunch", "Name: sushi", "Ingredients:",
                    "salmon", "rice", "avocado"
                )
            )
                return CheckResult(false, "Wrong \"show\" output for a saved meal.")

            if (!co.input("exit", "Bye!"))
                return CheckResult(false, "Your output should contain \"Bye!\"")

            if (!co.programIsFinished())
                return CheckResult(false, "The application didn't exit.")
        } catch (e: Exception) {
            return CheckResult(false, "An exception was thrown while testing - ${e.message}")
        }

        return CheckResult.correct()
    }

    @DynamicTest(order = 4)
    fun normalExeNew01Test(): CheckResult {
        try {
            val dbFile = File("meals.db")
            if (dbFile.exists()) dbFile.delete()
        } catch (e: Exception) {
            return CheckResult(false, "An exception was thrown, while trying to delete a database file.")
        }

        try {
            val co = CheckOutput()
            if (!co.start("What would you like to do (add, show, exit)?"))
                return CheckResult(
                    false,
                    "Your program should ask the user about the required action: \"(add, show, exit)?\""
                )

            if (!co.input("add", "Which meal do you want to add (breakfast, lunch, dinner)?"))
                return CheckResult(
                    false,
                    "Your program should ask the user about meal category: \"(breakfast, lunch, dinner)?\""
                )

            if (!co.input("lunch", "Input the meal's name:"))
                return CheckResult(false, "Your output should contain \"Input the meal's name:\"")

            if (!co.input("sushi", "Input the ingredients:"))
                return CheckResult(false, "Your output should contain \"Input the ingredients:\"")

            if (!co.input("salmon, rice, avocado", "The meal has been added!"))
                return CheckResult(false, "Your output should contain \"The meal has been added!\"")

            if (!co.inputNext("What would you like to do (add, show, exit)?"))
                return CheckResult(
                    false,
                    "Your program should ask the user about the required action: \"(add, show, exit)?\""
                )

            if (!co.input("add", "Which meal do you want to add (breakfast, lunch, dinner)?"))
                return CheckResult(
                    false,
                    "Your program should ask the user about meal category: \"(breakfast, lunch, dinner)?\""
                )

            if (!co.input("breakfast", "Input the meal's name:"))
                return CheckResult(false, "Your output should contain \"Input the meal's name:\"")

            if (!co.input("english", "Input the ingredients:"))
                return CheckResult(false, "Your output should contain \"Input the ingredients:\"")

            if (!co.input("sausages, eggs", "The meal has been added!"))
                return CheckResult(false, "Your output should contain \"The meal has been added!\"")

            if (!co.inputNext("What would you like to do (add, show, exit)?"))
                return CheckResult(
                    false,
                    "Your program should ask the user about the required action: \"(add, show, exit)?\""
                )

            if (!co.input("add", "Which meal do you want to add (breakfast, lunch, dinner)?"))
                return CheckResult(
                    false,
                    "Your program should ask the user about meal category: \"(breakfast, lunch, dinner)?\""
                )

            if (!co.input("dinner", "Input the meal's name:"))
                return CheckResult(false, "Your output should contain \"Input the meal's name:\"")

            if (!co.input("meatballs", "Input the ingredients:"))
                return CheckResult(false, "Your output should contain \"Input the ingredients:\"")

            if (!co.input("meat, bread, salt, pepper, egg", "The meal has been added!"))
                return CheckResult(false, "Your output should contain \"The meal has been added!\"")

            if (!co.inputNext("What would you like to do (add, show, exit)?"))
                return CheckResult(
                    false,
                    "Your program should ask the user about the required action: \"(add, show, exit)?\""
                )

            if (!co.input(
                    "show", "Category: lunch", "Name: sushi", "Ingredients:",
                    "salmon", "rice", "avocado"
                )
            )
                return CheckResult(false, "Wrong \"show\" output for the lunch meal.")

            if (!co.inputNext(
                    "Category: breakfast", "Name: english", "Ingredients:",
                    "sausages", "eggs"
                )
            )
                return CheckResult(false, "Wrong \"show\" output for the breakfast meal.")

            if (!co.inputNext(
                    "Category: dinner", "Name: meatballs", "Ingredients:",
                    "meat", "bread", "salt", "pepper", "egg"
                )
            )
                return CheckResult(false, "Wrong \"show\" output for the dinner meal.")

            if (!co.inputNext("What would you like to do (add, show, exit)?"))
                return CheckResult(
                    false,
                    "Your program should ask the user about the required action: \"(add, show, exit)?\""
                )

            if (!co.input("exit", "Bye!"))
                return CheckResult(false, "Your output should contain \"Bye!\"")

            if (!co.programIsFinished())
                return CheckResult(false, "The application didn't exit.")
        } catch (e: Exception) {
            return CheckResult(false, "An exception was thrown while testing - ${e.message}")
        }

        return CheckResult.correct()
    }

    @DynamicTest(order = 5)
    fun normalExeNew02Test(): CheckResult {
        try {
            val dbFile = File("meals.db")
            if (!dbFile.exists()) return CheckResult(false, "The meals.db database file doesn't exist.")
        } catch (e: Exception) {
            return CheckResult(false, "An exception was thrown, while trying to check a database file.")
        }
        try {
            val co = CheckOutput()
            if (!co.start("What would you like to do (add, show, exit)?"))
                return CheckResult(
                    false,
                    "Your program should ask the user about the required action: \"(add, show, exit)?\""
                )

            if (!co.input(
                    "show", "Category: lunch", "Name: sushi", "Ingredients:",
                    "salmon", "rice", "avocado"
                )
            )
                return CheckResult(false, "Wrong \"show\" output for a saved meal.")

            if (!co.input("exit", "Bye!"))
                return CheckResult(false, "Your output should contain \"Bye!\"")

            if (!co.programIsFinished())
                return CheckResult(false, "The application didn't exit.")
        } catch (e: Exception) {
            return CheckResult(false, "An exception was thrown while testing - ${e.message}")
        }

        return CheckResult.correct()
    }

}

class dbTable(val name: String, val columnNameType: List<Pair<String, String>>)

fun checkTableSchema(dbUrl: String, tables: List<dbTable>): Pair<Boolean, String> {
    try {
        val connection = DriverManager.getConnection(dbUrl)
        val meta: DatabaseMetaData = connection.metaData

        for (table in tables) {
            var tableMeta = meta.getTables(null, null, table.name, null)
            if (!tableMeta.next() || table.name.lowercase() != tableMeta.getString("TABLE_NAME").lowercase())
                return Pair(false, "The table \"${table.name}\" doesn't exist.")

            var columns = meta.getColumns(null, null, table.name, null)
            val columnsData = mutableListOf<Pair<String, String>>()
            while (columns.next()) {
                val column = Pair(
                    columns.getString("COLUMN_NAME").lowercase(),
                    columns.getString("TYPE_NAME").lowercase()
                )
                columnsData.add(column)
            }

            for (c in table.columnNameType) {
                if (c !in columnsData) {
                    if (c.first !in columnsData.map { it.first })
                        return Pair(false, "The column \"${c.first}\" of the table \"${table.name}\" doesn't exist.")
                    return Pair(false, "The column \"${c.first}\" of the table \"${table.name}\" is of the wrong type.")
                }
            }
        }

        connection.close()
    } catch (e: Exception) {
        return Pair(false, "An exception was thrown, while trying to check the database schema - ${e.message}")
    }

    return Pair(true, "")
}

class CheckOutput {
    private var main: TestedProgram = TestedProgram()
    private var position = 0
    private var caseInsensitive = true
    private var trimOutput = true
    private val arguments = mutableListOf<String>()
    private var isStarted = false
    private var lastOutput = ""

    private fun checkOutput(outputString: String, vararg checkStr: String): Boolean {
        var searchPosition = position
        for (cStr in checkStr) {
            val str = if (caseInsensitive) cStr.lowercase() else cStr
            val findPosition = outputString.indexOf(str, searchPosition)
            if (findPosition == -1) return false
            if (outputString.substring(searchPosition until findPosition).isNotBlank()) return false
            searchPosition = findPosition + str.length
        }
        position = searchPosition
        return true
    }

    fun start(vararg checkStr: String): Boolean {
        return if (!isStarted) {
            var outputString = main.start(*arguments.toTypedArray())
            lastOutput = outputString
            if (trimOutput) outputString = outputString.trim()
            if (caseInsensitive) outputString = outputString.lowercase()
            isStarted = true
            checkOutput(outputString, *checkStr)
        } else false
    }

    fun stop() {
        main.stop()
    }

    fun input(input: String, vararg checkStr: String): Boolean {
        if (main.isFinished) return false
        var outputString = main.execute(input)
        lastOutput = outputString
        if (trimOutput) outputString = outputString.trim()
        if (caseInsensitive) outputString = outputString.lowercase()
        position = 0
        return checkOutput(outputString, *checkStr)
    }

    fun inputNext(vararg checkStr: String): Boolean {
        var outputString = lastOutput
        if (trimOutput) outputString = outputString.trim()
        if (caseInsensitive) outputString = outputString.lowercase()
        return checkOutput(outputString, *checkStr)
    }

    fun getNextOutput(input: String): String {
        if (main.isFinished) return ""
        val outputString = main.execute(input)
        lastOutput = outputString
        position = 0
        return outputString
    }

    fun getLastOutput(): String {
        return lastOutput
    }

    fun programIsFinished(): Boolean = main.isFinished
    fun setArguments(vararg args: String) {
        arguments.addAll(args.toMutableList())
    }

    fun setCaseSensitivity(caseInsensitive: Boolean) {
        this.caseInsensitive = caseInsensitive
    }

    fun setOutputTrim(trimOutput: Boolean) {
        this.trimOutput = trimOutput
    }
}


