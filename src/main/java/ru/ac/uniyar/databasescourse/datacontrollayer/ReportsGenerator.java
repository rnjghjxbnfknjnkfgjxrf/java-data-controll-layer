package ru.ac.uniyar.databasescourse.datacontrollayer;

import ru.ac.uniyar.databasescourse.datacontrollayer.DataBase;
import ru.ac.uniyar.databasescourse.datacontrollayer.entity.Report;
import ru.ac.uniyar.databasescourse.datacontrollayer.exeptions.StatementExecutionException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ReportsGenerator {
    private final DataBase db;
    public ReportsGenerator(DataBase db) {
        this.db = db;
    }

    public Report getStudentsWithMinMaxScore() throws StatementExecutionException{
        Report rep = new Report();
        try {
            Statement st =  db.getStatement();
            ResultSet rs = st.executeQuery(
                    "SELECT students.name, MIN(solutions.score), MAX(solutions.score) FROM solutions " +
                    "JOIN students ON solutions.studentId = students.id "+
                    "GROUP BY students.name;");
            while (rs.next()) {
                rep.discribe.add(String.format("student: %s, MIN: %s, MAX: %s",
                        rs.getString(1),
                        rs.getString(2),
                        rs.getString(3)));
            }
        } catch (SQLException ex) {
            throw new StatementExecutionException(ex);
        }
        return rep;
    }
}
