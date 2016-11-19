package repository;

import entity.Animal;

import java.io.IOException;
import java.sql.*;

public class AnimalRepository {

    private Connection conn;

    public AnimalRepository(String jdbcUrl) throws SQLException {
        this.conn = DriverManager.getConnection(jdbcUrl);
    }

    // This method returns a set of all animals
    public ResultSet listAnimal() throws SQLException {
        Statement stmt = conn.createStatement();
        return stmt.executeQuery("SELECT * FROM animal");
    }

    // Removes an animal from the database
    public void removeAnimal(int animalID) throws SQLException {

        // create a prepared statement
        PreparedStatement ps = conn.prepareStatement(

                "DELETE FROM animal " +
                        "WHERE animal_id = ?"
        );

        // set parameter values
        ps.setInt(1, animalID);

        // execute the query
        ps.executeUpdate();

    }

    // Creates a new Animal and adds it to the list
    public void createAnimal(Animal anAnimal) throws IOException, SQLException {
        // create a prepared statement
        PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO animal" +
                        "(animal_id, " +
                        "animal_name, " +
                        "animal_type_id, " +
                        "breed, " +
                        "description) " +
                        "VALUES(?,?,?,?,?)"
        );

        // set parameter values
        ps.setInt(1, anAnimal.getID());
        ps.setString(2, anAnimal.getName());
        ps.setInt(3, anAnimal.getSpecies());
        ps.setString(4, anAnimal.getBreed());
        ps.setString(5, anAnimal.getDescription());

        // execute the query
        ps.executeUpdate();
    }

    public ResultSet getResultSet() throws SQLException {

        ResultSet resultSet = this.listAnimal();
        return resultSet;
    }


    public void modifyAnimal(String property, String newInput, Animal anAnimal, int typeID) throws IOException, SQLException {

        switch (property) {
            case "name":
                anAnimal.setName(newInput);
                break;
            case "species":
                anAnimal.setSpecies(typeID);
                break;
            case "breed":
                anAnimal.setBreed(newInput);
                break;
            case "description":
                anAnimal.setDescription(newInput);
                break;
        }

        PreparedStatement ps = conn.prepareStatement(
                "UPDATE animal SET animal_name = ?," +
                        "animal_type_id = ?," +
                        "breed = ?," +
                        "description = ?" +
                        "WHERE animal_id = ?"
        );

        // set parameter values
        ps.setString(1, anAnimal.getName());
        ps.setInt(2, anAnimal.getSpecies());
        ps.setString(3, anAnimal.getBreed());
        ps.setString(4, anAnimal.getDescription());
        ps.setInt(5, anAnimal.getID());

        // execute the query
        ps.executeUpdate();

    }
}
