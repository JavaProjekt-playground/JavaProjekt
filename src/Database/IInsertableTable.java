package Database;

import java.sql.Statement;
import java.sql.Connection;

public interface IInsertableTable {
    Statement getInsertCommand(Connection conn);
}
