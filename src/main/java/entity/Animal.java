package entity;

import service.TypeService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;


public class Animal {
    private String name = "";
    private int species = -1;
    private String breed = "";
    private String description = "";
    private int ID;
    private ArrayList<AnimalNotes> notes;
    private TypeService t;

    public Animal(String name, int species, String breed, String description, int ID, ArrayList<AnimalNotes> notes, TypeService t) {
        this.name = name;
        this.species = species;
        this.breed = breed;
        this.description = description;
        this.ID = ID;
        this.notes = notes;
        this.t = t;
    }

    public Animal() {
        this.ID = -1;
    }


    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public ArrayList<AnimalNotes> getNotes() {
        return notes;
    }

    public void setNotes(ArrayList<AnimalNotes> notes) {
        this.notes = notes;
    }

    public String getBreed() {
        return breed;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getSpecies() {
        return species;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSpecies(int species) {
        this.species = species;
    }

    public String toString() {
        String a = null;
        try {
            a = "" + this.getID() + "\t| " + this.getName() + "\t\t| " + t.getType(this.getSpecies());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return a;
    }

    public void toAString() throws IOException, SQLException {
        int a = this.getID();
        String b = this.getName();
        String c = t.getType(this.getSpecies());

        System.out.format("%-4d" + "| %-14s" + "| %-1s", a, b, c);
        System.out.println();

    }

}
