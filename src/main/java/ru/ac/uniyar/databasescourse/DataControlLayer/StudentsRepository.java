package ru.ac.uniyar.databasescourse.DataControlLayer;

import ru.ac.uniyar.databasescourse.DataControlLayer.Entity.Student;
import ru.ac.uniyar.databasescourse.DataControlLayer.Exeptions.DataControlLayerException;
import ru.ac.uniyar.databasescourse.DataControlLayer.Exeptions.StatementCreationException;
import ru.ac.uniyar.databasescourse.DataControlLayer.Exeptions.StatementExecutionException;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

class StudentsRepository {
    private final Connection con;
    private HashMap<Integer, Student> students = new HashMap<>();

    public StudentsRepository(Connection con) {
        this.con = con;
    }

    public void createTable() throws DataControlLayerException {
        try (Statement statement = con.createStatement()) {
            try {
                statement.executeQuery(
                        "CREATE TABLE IF NOT EXISTS students (" +
                                "id INT PRIMARY KEY," +
                                "name VARCHAR(30) NOT NULL," +
                                "surname VARCHAR(30) NOT NULL)" +
                                "CHARACTER SET \'utf8\'" +
                                "COLLATE \'utf8_unicode_ci\';");
            }catch (SQLException ex) {
                throw new StatementExecutionException(ex);
            }
        }catch (SQLException ex) {
            throw new StatementCreationException(ex);
        }
    }

    public void loadData() throws DataControlLayerException {
        try (Statement statement = con.createStatement()) {
            try (ResultSet rs = statement.executeQuery("SELECT id, name, surname FROM students;")){
                while (rs.next()) {
                    students.put(rs.getInt(1), new Student(
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
    }

    public void addStudents(HashSet<Student> studentsSet) throws DataControlLayerException {
        try (PreparedStatement studentInsert = con.prepareStatement(
                "INSERT INTO students (id, name, surname)" +
                "VALUES (?, ?, ?);")) {
            try {
                for (Student student: studentsSet) {
                    studentInsert.setInt(1, student.getId());
                    studentInsert.setString(2, student.getName());
                    studentInsert.setString(3, student.getSurname());
                    studentInsert.executeUpdate();
                }
            } catch (SQLException ex) {
                throw new StatementExecutionException(ex);
            }
        } catch (SQLException ex) {
            throw new StatementCreationException(ex);
        }
    }

    public ArrayList<Student> getStudents(){
        return new ArrayList<>(students.values());
    }
}
