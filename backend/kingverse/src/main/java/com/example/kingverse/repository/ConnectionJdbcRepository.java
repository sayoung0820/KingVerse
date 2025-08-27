package com.example.kingverse.repository;

import com.example.kingverse.model.ConnectionView;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ConnectionJdbcRepository {

    private final JdbcTemplate jdbc;

    public ConnectionJdbcRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private static final RowMapper<ConnectionView> ROW_MAPPER = new RowMapper<>() {
        @Override
        public ConnectionView mapRow(ResultSet rs, int rowNum) throws SQLException {
            ConnectionView v = new ConnectionView();
            // ints: read, then null-check via wasNull()
            int cid = rs.getInt("connectionId");
            v.setConnectionId(rs.wasNull() ? null : cid);

            int sid = rs.getInt("subjectId");
            v.setSubjectId(rs.wasNull() ? null : sid);

            int oid = rs.getInt("objectId");
            v.setObjectId(rs.wasNull() ? null : oid);

            v.setObjectName(rs.getString("objectName"));
            v.setConnectionType(rs.getString("connectionType"));
            v.setBookTitle(rs.getString("bookTitle"));
            v.setNote(rs.getString("note"));
            return v;
        }
    };

    public List<ConnectionView> findOutgoing(Integer subjectId) {
        String sql = """
            SELECT
              cc.connection_id   AS connectionId,
              cc.subject_id      AS subjectId,
              cc.object_id       AS objectId,
              o.name             AS objectName,
              cc.connection_type AS connectionType,
              b.title            AS bookTitle,
              cc.note            AS note
            FROM character_connection cc
            JOIN character_entity o   ON o.character_id = cc.object_id
            LEFT JOIN book b          ON b.book_id = cc.book_context_id
            WHERE cc.subject_id = ?
            ORDER BY o.name
        """;
        return jdbc.query(sql, ROW_MAPPER, subjectId);
    }

    public List<ConnectionView> findIncoming(Integer objectId) {
        String sql = """
            SELECT
              cc.connection_id   AS connectionId,
              cc.subject_id      AS subjectId,
              cc.object_id       AS objectId,
              s.name             AS objectName,
              cc.connection_type AS connectionType,
              b.title            AS bookTitle,
              cc.note            AS note
            FROM character_connection cc
            JOIN character_entity s   ON s.character_id = cc.subject_id
            LEFT JOIN book b          ON b.book_id = cc.book_context_id
            WHERE cc.object_id = ?
            ORDER BY s.name
        """;
        return jdbc.query(sql, ROW_MAPPER, objectId);
    }
}
