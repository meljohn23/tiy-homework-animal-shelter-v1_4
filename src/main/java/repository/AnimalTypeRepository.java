package repository;
import java.io.IOException;
import java.sql.*;


public class AnimalTypeRepository {

    private Connection conn;

    public AnimalTypeRepository(String jdbcUrl) throws SQLException {
        this.conn = DriverManager.getConnection(jdbcUrl);
    }

    // list people
    public ResultSet listTypes() throws SQLException {
        Statement stmt = conn.createStatement();
        return stmt.executeQuery("SELECT * FROM animal_type");
    }

    // This method adds a new animal type to the database
    public void createType(String text) throws IOException, SQLException {
        // create a prepared statement
        PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO animal_type" +
                        "(species) " +
                        "VALUES(?)"
        );

        // set parameter values
        ps.setString(1, text);

        // execute the query
        ps.executeUpdate();
    }

    public int getTypeID(String text) throws SQLException {
        // create a prepared statement
        PreparedStatement ps = conn.prepareStatement(
                        "SELECT animal_type_id " +
                                "FROM animal_type " +
                        "WHERE species = ?"
        );

        // set parameter values
        ps.setString(1, text.toLowerCase());

        // execute the query
        ps.execute();
        ResultSet resultSet = ps.getResultSet();
        while(resultSet.next()){
            return resultSet.getInt("animal_type_id");}

            System.out.println("\nError: Please choose from an existing animal type.");
            return -1;

    }

    public String getType(int id) throws SQLException {
        // create a prepared statement
        PreparedStatement ps = conn.prepareStatement(
                "SELECT species " +
                        "FROM animal_type " +
                        "WHERE animal_type_id = ?"
        );

        // set parameter values
        ps.setInt(1, id);

        // execute the query
        ps.execute();
        ResultSet resultSet = ps.getResultSet();
        resultSet.next();
        return resultSet.getString("species");
    }
}
