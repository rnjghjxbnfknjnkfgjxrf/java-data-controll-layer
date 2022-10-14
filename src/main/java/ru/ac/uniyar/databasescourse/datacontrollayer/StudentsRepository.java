package ru.ac.uniyar.databasescourse.datacontrollayer;

import ru.ac.uniyar.databasescourse.datacontrollayer.entity.Student;
import ru.ac.uniyar.databasescourse.datacontrollayer.exeptions.DataControlLayerException;
import ru.ac.uniyar.databasescourse.datacontrollayer.exeptions.StatementCreationException;
import ru.ac.uniyar.databasescourse.datacontrollayer.exeptions.StatementExecutionException;

import java.sql.*;
import java.util.HashSet;

class StudentsRepository {
    private final Connection con;

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

    public HashSet<Student> loadData() throws DataControlLayerException {
        HashSet<Student> students = new HashSet<>();
        try (Statement statement = con.createStatement()) {
            try (ResultSet rs = statement.executeQuery("SELECT id, name, surname FROM students;")){
                while (rs.next()) {
                    students.add(new Student(
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
        return students;
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
}
