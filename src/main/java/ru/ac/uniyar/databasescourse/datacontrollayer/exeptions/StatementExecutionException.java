package ru.ac.uniyar.databasescourse.datacontrollayer.exeptions;

import java.sql.SQLException;

public class StatementExecutionException extends DataControlLayerException {
    public StatementExecutionException(SQLException ex) {
        super("Statement execution error:\n" + ex);
    }
}
