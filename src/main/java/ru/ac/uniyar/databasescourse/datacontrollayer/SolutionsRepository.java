package ru.ac.uniyar.databasescourse.datacontrollayer;

import ru.ac.uniyar.databasescourse.datacontrollayer.entity.Solution;
import ru.ac.uniyar.databasescourse.datacontrollayer.exeptions.DataControlLayerException;
import ru.ac.uniyar.databasescourse.datacontrollayer.exeptions.StatementCreationException;
import ru.ac.uniyar.databasescourse.datacontrollayer.exeptions.StatementExecutionException;

import java.sql.*;
import java.util.HashSet;

public class SolutionsRepository {
    private final Connection con;
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

    public HashSet<Solution> loadData() throws DataControlLayerException {
        HashSet<Solution> solutions = new HashSet<>();
        try (Statement statement = con.createStatement()) {
            try (ResultSet rs = statement.executeQuery(
                    "SELECT id, score, hasPassed, studentId, reviewerId FROM solutions;")) {
                while (rs.next()) {
                    solutions.add(new Solution(
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
        return solutions;
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
}
