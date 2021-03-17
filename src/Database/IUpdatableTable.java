package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.sql.Statement;

public interface IUpdatableTable {
    Statement getUpdateCommand(Connection conn);
}
