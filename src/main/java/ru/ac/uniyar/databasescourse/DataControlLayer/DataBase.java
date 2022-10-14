package ru.ac.uniyar.databasescourse.DataControlLayer;

import ru.ac.uniyar.databasescourse.DataControlLayer.Entity.Reviewer;
import ru.ac.uniyar.databasescourse.DataControlLayer.Entity.Solution;
import ru.ac.uniyar.databasescourse.DataControlLayer.Entity.Student;
import ru.ac.uniyar.databasescourse.DataControlLayer.Exeptions.DataBaseConnectException;
import ru.ac.uniyar.databasescourse.DataControlLayer.Exeptions.DataControlLayerException;
import ru.ac.uniyar.databasescourse.DataControlLayer.Exeptions.StatementCreationException;
import ru.ac.uniyar.databasescourse.DataControlLayer.Exeptions.StatementExecutionException;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;

public class DataBase {
    private static final String URL = String.format("jdbc:mariadb://%s", System.getenv("MARIADB_HOST"));
    private static final String user = System.getenv("MARIADB_USER");
    private static final String password = System.getenv("MARIADB_PASSWORD");
    private static final String useQuery = "USE ViacheslavMihailovDB;";
    private final Connection connection;
    private final Statement statement;
    private final StudentsRepository studentsRepository;
    private final ReviewersRepository reviewersRepository;
    private final SolutionsRepository solutionsRepository;
    public DataBase() throws DataControlLayerException {
        try {
            this.connection = createConnection();
            this.studentsRepository = new StudentsRepository(connection);
            this.reviewersRepository = new ReviewersRepository(connection);
            this.solutionsRepository = new SolutionsRepository(connection);
            try {
                this.statement = this.connection.createStatement();
                try {
                    statement.executeQuery(useQuery);
                } catch (SQLException ex) {
                    throw new StatementExecutionException(ex);
                }
            } catch (SQLException ex) {
                throw new StatementCreationException(ex);
            }
        } catch (SQLException ex) {
            throw new DataBaseConnectException(ex);
        }
    }

    private static Connection createConnection() throws SQLException {
        return DriverManager.getConnection(URL, user, password);
    }

    public HashSet<Student> getStudents() throws DataControlLayerException {
        return this.studentsRepository.loadData();
    }
    public void addStudents(HashSet<Student> students) throws DataControlLayerException {
        this.studentsRepository.addStudents(students);
    }
    public void createStudentsTable() throws DataControlLayerException {
        this.studentsRepository.createTable();
    }

    public void createReviewersTable() throws DataControlLayerException {
        this.reviewersRepository.createTable();
    }
    public void addReviewers(HashSet<Reviewer> reviewers) throws DataControlLayerException {
        this.reviewersRepository.addReviewers(reviewers);
    }
    public HashSet<Reviewer> getReviewers() throws DataControlLayerException {
        return this.reviewersRepository.loadData();
    }

    public void createSolutionsTable() throws DataControlLayerException {
        this.solutionsRepository.createTable();
    }
    public void addSolutions(HashSet<Solution> solutions) throws DataControlLayerException {
        this.solutionsRepository.addSolutions(solutions);
    }
    public HashSet<Solution> getSolutions() throws DataControlLayerException {
        return this.solutionsRepository.loadData();
    }


}
