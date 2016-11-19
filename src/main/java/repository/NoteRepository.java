package repository;

import java.io.IOException;
import java.sql.*;


public class NoteRepository {
    private Connection conn;

    public NoteRepository(String jdbcUrl) throws SQLException {
        this.conn = DriverManager.getConnection(jdbcUrl);
    }

    // list people
    public ResultSet listNotes() throws SQLException {
        Statement stmt = conn.createStatement();
        return stmt.executeQuery("SELECT * FROM note");
    }

    // Creates a new Note and adds it to the list
    public void createNote(String text, int num) throws IOException, SQLException {
        // create a prepared statement

        PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO note" +
                        "(animal_id," +
                        "note_text) " +
                        "VALUES(?, ?)"
        );

        // set parameter values
        ps.setInt(1, num);
        ps.setString(2, text);

        // execute the query
        ps.executeUpdate();
    }
}





