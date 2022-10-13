package ru.ac.uniyar.databasescourse.DataControlLayer.Exeptions;

import java.sql.SQLException;

public class StatementExecutionException extends DataControlLayerException {
    public StatementExecutionException(SQLException ex) {
        super("Statement execution error:\n" + ex);
    }
}
