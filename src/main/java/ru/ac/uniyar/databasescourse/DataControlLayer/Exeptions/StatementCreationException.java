package ru.ac.uniyar.databasescourse.DataControlLayer.Exeptions;

import java.sql.SQLException;

public class StatementCreationException extends DataControlLayerException {
    public StatementCreationException(SQLException ex) {
        super("Can't create statement:\n" + ex);
    }
}
