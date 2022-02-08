package mealplanner.controller

import java.sql.DriverManager
import java.sql.ResultSet

/**
 * This class is used to connect to the database and execute queries.
 */
object DataBaseManager {
    private lateinit var connection: java.sql.Connection
    private lateinit var statement: java.sql.Statement

    /**
     * This method is used to connect to the database.
     */
    fun init() {
        connection = DriverManager.getConnection("jdbc:sqlite:meals.db")
    }

    /**
     * This method is used to execute Update Query.
     * @param query The query to execute.
     * @return The result of the query.
     */
    fun executeUpdate(sql: String) {
        statement = connection.createStatement()
        statement.execute(sql)
    }

    /**
     * This method is used to execute a Query.
     * @param query The query to execute.
     * @return The result of the query.
     */
    fun executeQuery(sql: String): ResultSet {
        statement = connection.createStatement()
        return statement.executeQuery(sql)
    }

    /**
     * This method is used to close the connection to the database.
     */
    fun close() {
        statement.close()
        connection.close()
    }
}