package ru.ac.uniyar.databasescourse.DataControlLayer;

import ru.ac.uniyar.databasescourse.DataControlLayer.Entity.Reviewer;
import ru.ac.uniyar.databasescourse.DataControlLayer.Exeptions.DataControlLayerException;
import ru.ac.uniyar.databasescourse.DataControlLayer.Exeptions.StatementCreationException;
import ru.ac.uniyar.databasescourse.DataControlLayer.Exeptions.StatementExecutionException;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class ReviewersRepository {
    private final Connection con;

    public ReviewersRepository(Connection con) {
        this.con = con;
    }

    public void createTable() throws DataControlLayerException {
        try (Statement statement = con.createStatement()){
            try {
                statement.executeQuery(
                        "CREATE TABLE IF NOT EXISTS reviewers (" +
                                "id INT PRIMARY KEY," +
                                "surname VARCHAR(30) NOT NULL," +
                                "department VARCHAR(127) NOT NULL)" +
                                "CHARACTER SET \'utf8\'" +
                                "COLLATE \'utf8_unicode_ci\';");
            }catch (SQLException ex) {
                throw new StatementExecutionException(ex);
            }
        } catch (SQLException ex) {
            throw  new StatementCreationException(ex);
        }
    }

    public HashSet<Reviewer> loadData() throws DataControlLayerException {
        HashSet<Reviewer> reviewers = new HashSet<>();
        try (Statement statement = con.createStatement()) {
            try (ResultSet rs = statement.executeQuery("SELECT id, surname, department FROM reviewers;")) {
                while (rs.next()) {
                    reviewers.add(new Reviewer(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3)
                    ));
                }
            } catch (SQLException ex) {
                throw new StatementExecutionException(ex);
            }
        } catch (SQLException ex) {
            throw new StatementCreationException(ex);
        }
        return reviewers;
    }
    public void addReviewers(HashSet<Reviewer> reviewersSet) throws DataControlLayerException {
        try (PreparedStatement reviewersInsert = con.prepareStatement(
                "INSERT INTO reviewers (id, surname, department)" +
                        "VALUES(?, ?, ?);")) {
            try {
                for (Reviewer reviewer: reviewersSet) {
                    reviewersInsert.setInt(1, reviewer.getId());
                    reviewersInsert.setString(2, reviewer.getSurname());
                    reviewersInsert.setString(3, reviewer.getDepartment());
                    reviewersInsert.executeUpdate();
                }
            } catch (SQLException ex) {
                throw new StatementExecutionException(ex);
            }
        }catch (SQLException ex) {
            throw new StatementCreationException(ex);
        }
    }
}
