package Database;

import java.sql.Connection;
import java.sql.SQLException;

interface IUpdatableTable {
    default boolean selfUpdate(Connection conn, Object... extra) throws SQLException {
        return false;
    }
}
