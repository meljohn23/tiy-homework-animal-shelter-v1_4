package service;

import repository.AnimalTypeRepository;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class TypeService {

    private AnimalTypeRepository animalTypeRepository;

    public TypeService(AnimalTypeRepository animalTypeRepository) {
        this.animalTypeRepository = animalTypeRepository;
    }

    public ArrayList<String> listTypes() throws SQLException {
        ArrayList<String> types = new ArrayList<>();

        ResultSet resultSet = this.animalTypeRepository.listTypes();
        while (resultSet.next()) {
            types.add(resultSet.getString("species"));
        }

        return types;
    }

    public void createType(String text) throws IOException, SQLException {
        animalTypeRepository.createType(text);
    }

    public int getTypeID(String text) throws IOException, SQLException {
        return animalTypeRepository.getTypeID(text);
    }

    public String getType(int id) throws IOException, SQLException {
        return animalTypeRepository.getType(id);
    }
}
