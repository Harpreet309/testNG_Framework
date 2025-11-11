package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Listeners(listener.TestListener.class)
public class StudentDBTest extends BaseTest {

    /**
     * Step 1: Create Students table
     */
    @Test(priority = 0)
    public void setupDatabase() throws SQLException {
        test.info("Creating 'Students' table if not exists...");
        try (Connection conn = database.getConnection();
             Statement stmt = conn.createStatement()) {

            String createTableSQL = "CREATE TABLE IF NOT EXISTS Students (" +
                    "ID INT PRIMARY KEY, " +
                    "Name VARCHAR(50), " +
                    "Score INT, " +
                    "Address VARCHAR(100))";

            stmt.executeUpdate(createTableSQL);
            test.pass("✅ 'Students' table created successfully.");

        } catch (Exception e) {
            test.fail("Failed to create 'Students' table: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Step 2: Insert sample student data
     */
    @Test(priority = 1, dependsOnMethods = "setupDatabase")
    public void insertData() throws SQLException {
        test.info("Inserting sample data into 'Students' table...");
        try (Connection conn = database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(
                     "INSERT INTO Students (ID, Name, Score, Address) VALUES (?, ?, ?, ?)")) {

            pstmt.setInt(1, 1);
            pstmt.setString(2, "John Doe");
            pstmt.setInt(3, 85);
            pstmt.setString(4, "123 Main St");

            int rows = pstmt.executeUpdate();
            Assert.assertEquals(rows, 1, "Insert should affect exactly 1 row.");

            test.pass("✅ Data inserted successfully into 'Students' table.");

        } catch (Exception e) {
            test.fail("Failed to insert data: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Step 3: Fetch and validate data
     */
    @Test(priority = 2, dependsOnMethods = "insertData")
    public void fetchAndValidateData() throws SQLException {
        test.info("Fetching and validating student record...");
        try (Connection conn = database.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Students WHERE ID = 1")) {

            Assert.assertTrue(rs.next(), "Record should exist in table.");
            Assert.assertEquals(rs.getString("Name"), "John Doe");
            Assert.assertEquals(rs.getInt("Score"), 85);
            Assert.assertEquals(rs.getString("Address"), "123 Main St");

            test.pass("✅ Data validation successful. Record values match expected data.");

        } catch (Exception e) {
            test.fail("Data validation failed: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Step 4: Cleanup - Drop the Students table
     */
    @Test(priority = 3, dependsOnMethods = "fetchAndValidateData")
    public void cleanup() throws SQLException {
        test.info("Dropping 'Students' table...");
        try (Connection conn = database.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate("DROP TABLE IF EXISTS Students");
            test.pass("'Students' table dropped successfully.");

        } catch (Exception e) {
            test.fail("Failed to drop table: " + e.getMessage());
            throw e;
        }
    }
}