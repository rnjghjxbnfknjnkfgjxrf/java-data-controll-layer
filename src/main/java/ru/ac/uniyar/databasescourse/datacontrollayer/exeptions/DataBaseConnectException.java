package ru.ac.uniyar.databasescourse.datacontrollayer.exeptions;

import java.sql.SQLException;

public class DataBaseConnectException extends DataControlLayerException {
    public DataBaseConnectException(SQLException ex) {
        super("Can't create connection:\n" + ex);
    }
}
