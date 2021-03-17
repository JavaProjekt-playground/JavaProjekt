package Database;

import java.sql.SQLException;
import java.sql.Connection;

interface IInsertableTable {
    default boolean selfInsert(Connection conn, Object... extra) throws SQLException {
        return false;
    }
}
