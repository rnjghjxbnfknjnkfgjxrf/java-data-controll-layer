package ru.ac.uniyar.databasescourse.DataControlLayer;

import ru.ac.uniyar.databasescourse.DataControlLayer.Entity.Solution;
import ru.ac.uniyar.databasescourse.DataControlLayer.Exeptions.DataControlLayerException;
import ru.ac.uniyar.databasescourse.DataControlLayer.Exeptions.StatementCreationException;
import ru.ac.uniyar.databasescourse.DataControlLayer.Exeptions.StatementExecutionException;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class SolutionsRepository {
    private final Connection con;
    private HashMap<Integer, Solution> solutions = new HashMap<>();

    public SolutionsRepository(Connection con) {
        this.con = con;
    }

    public void createTable() throws DataControlLayerException {
        try (Statement statement = con.createStatement()) {
            try {
                statement.executeQuery(
                        "CREATE TABLE IF NOT EXISTS solutions (" +
                                "id INT PRIMARY KEY," +
                                "score FLOAT," +
                                "hasPassed TINYINT(1)," +
                                "studentId INT NOT NULL," +
                                "reviewerId INT," +
                                "FOREIGN KEY (studentId) REFERENCES students(id) ON DELETE CASCADE ON UPDATE CASCADE," +
                                "FOREIGN KEY (reviewerId) REFERENCES reviewers(id) ON DELETE SET NULL ON UPDATE CASCADE)" +
                                "CHARACTER SET \'utf8\'" +
                                "COLLATE \'utf8_unicode_ci\';"
                );
            }catch (SQLException ex) {
                throw new StatementExecutionException(ex);
            }
        } catch (SQLException ex) {
            throw new StatementCreationException(ex);
        }
    }

    public void loadData() throws DataControlLayerException {
        try (Statement statement = con.createStatement()) {
            try (ResultSet rs = statement.executeQuery(
                    "SELECT id, score, hasPassed, studentId, reviewerId FROM solutions;")) {
                while (rs.next()) {
                    solutions.put(rs.getInt(1), new Solution(
                            rs.getInt(1),
                            rs.getFloat(2),
                            rs.getBoolean(3),
                            rs.getInt(4),
                            rs.getInt(5)
                    ));
                }
            }catch (SQLException ex) {
                throw new StatementExecutionException(ex);
            }
        } catch (SQLException ex) {
            throw new StatementCreationException(ex);
        }
    }

    public void addSolutions(HashSet<Solution> solutionsSet) throws DataControlLayerException {
        try (PreparedStatement solutionsInsert = con.prepareStatement(
                "INSERT INTO solutions (id, score, hasPassed, studentId, reviewerId)" +
                        "VALUES (?, ?, ?, ?, ?)")) {
            try {
                for (Solution solution: solutionsSet) {
                    solutionsInsert.setInt(1, solution.getId());
                    solutionsInsert.setFloat(2, solution.getScore());
                    solutionsInsert.setBoolean(3, solution.getHasPassed());
                    solutionsInsert.setInt(4, solution.getStudentId());
                    solutionsInsert.setInt(5, solution.getReviewerId());
                    solutionsInsert.executeUpdate();
                }
            } catch (SQLException ex) {
                throw new StatementExecutionException(ex);
            }
        } catch (SQLException ex) {
            throw new StatementCreationException(ex);
        }
    }

    public ArrayList<Solution> getSolutions() {
        return new ArrayList<>(solutions.values());
    }
}
