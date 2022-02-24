package sqlHelper;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface SqlTransaction<T> {
    T execute(PreparedStatement st) throws SQLException;
}