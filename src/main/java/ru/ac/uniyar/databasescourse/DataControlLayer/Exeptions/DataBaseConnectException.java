package ru.ac.uniyar.databasescourse.DataControlLayer.Exeptions;

import java.sql.SQLException;

public class DataBaseConnectException extends DataControlLayerException {
    public DataBaseConnectException(SQLException ex) {
        super("Can't create connection:\n" + ex);
    }
}
