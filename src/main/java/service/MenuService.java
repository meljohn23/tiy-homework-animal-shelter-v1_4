package service;

import entity.Animal;
import entity.AnimalNotes;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class MenuService {
    public static final int CREATE_ANIMAL = 1;
    public static final int MANAGE_ANIMAL = 2;
    public static final int MANAGE_ANIMAL_TYPES = 3;
    public static final int EXIT_PROGRAM = 4;

    AnimalsService service;

    public MenuService(AnimalsService service) {
        this.service = service;
    }


    public int promptForMainMenuSelection() {
        System.out.println("\n-- Main Menu --\n" +
                "\n" +
                "1) Add an animal\n" +
                "2) Manage an animal\n" +
                "3) Manage animal types\n" +
                "4) Quit\n");

        return waitForInt("Please choose an option: ");
    }

    public int waitForInt(String prompt) {

        System.out.print(prompt);

        Scanner reader = new Scanner(System.in);

        Integer aNum = null;

        while (aNum == null) {

            if (reader.hasNextInt()) {
                aNum = reader.nextInt();
                System.out.println();
            } else { // if user inputs random text...
                String discardInput = reader.nextLine();
                System.out.println("\nError: Sorry, that isn't a valid option.");
                System.out.print("\n" + prompt);
            }
        }
        return aNum;
    }

    public void promptForAnimalData() throws IOException, SQLException {
        String name, species, breed, description;

        System.out.println("-- Create an Animal --");
        System.out.println("\nPlease answer the following questions.");

        System.out.print("\nAnimal Name: ");
        name = retrieveAnimalInfoFromUser("Name");

        System.out.print("Type (ex. Cat, Dog, Bird): ");
        species = retrieveAnimalInfoFromUser("Species");
        while (validateTypeInput(species) == false) {
            System.out.println("\nError: Please choose from an existing animal type.\n");
            System.out.println("Animal Types:");
            System.out.println("--------------");
            for (String type : service.getTypeService().listTypes()) {
                System.out.println(type);
            }
            System.out.println("--------------");
            System.out.print("\nType: ");
            species = retrieveAnimalInfoFromUser("Species");

        }

        System.out.print("Breed (optional): ");
        breed = retrieveAnimalInfoFromUser("Breed (optional)");

        System.out.print("Description: ");
        description = retrieveAnimalInfoFromUser("Description");


        int speciesID = service.getTypeService().getTypeID(species);

        System.out.println("\nSuccess: The animal has been created!");
        //create an entity.Animal instance and add the new animal to an ArrayList
        service.createAnimal(name, speciesID, breed, description, generateUniqueID(), service.getNoteService().listNotes());

    }

    public int generateUniqueID() {
        Random random = new Random();
        int x = random.nextInt(1000);
        for (Animal animal : service.listAnimals()) {
            if (animal.getID() == x) {
                x = random.nextInt(1000);
            }

        }

        return x;
    }

    public boolean isInteger(String userInput) {
        boolean isAnInt = false;
        try {
            Integer.parseInt(userInput);

            isAnInt = true;
        } catch (NumberFormatException ex) {
            // the user did not input a valid integer!!!
        }

        return isAnInt;
    }

    public String retrieveAnimalInfoFromUser(String animalProperty) {

        Scanner reader = new Scanner(System.in);
        String readString = reader.nextLine();
        Boolean flag = true;

        while (flag) {

            if (animalProperty.equals("Breed (optional)")) // Since this is optional, don't need to check for enter key
            {
                if (isInteger(readString)) {
                    System.out.printf("\nError: Please enter a non-numeric value for this field.\n");
                    System.out.printf("\n%s: ", animalProperty);
                    readString = reader.next();
                } else {
                    flag = false; // exit loop
                }
            } else if ((readString.isEmpty())) //checks to see if user pressed enter
            {
                System.out.printf("\nError: The %s field is required. Please try again.\n", animalProperty);
                System.out.printf("\n%s: ", animalProperty);
                readString = reader.next();
            } else if (isInteger(readString)) {
                System.out.printf("Error: Please enter a non-numeric value for this field: ");
                readString = reader.next();
            } else {
                flag = false; // exit loop
            }
        }
        return readString; // return userInput
    }

    public String validateAndSetUserInputForAnimal(String animalProperty, String currentAnimalProperty) {
        Scanner reader = new Scanner(System.in);
        String readString = reader.nextLine();
        Boolean flag = true;

        while (flag) {

            if (isInteger(readString)) //checks to see if user entered a number instead of text
            {
                System.out.printf("Error: Please enter a non-numeric value for this field.");
                System.out.printf("\n%s: ", animalProperty);
                readString = reader.next();
            } else if (readString.isEmpty()) {
                // keeps the current value. do nothing here
                return currentAnimalProperty;
            } else {
                return readString;
            }
        }
        return readString;
    }

    public boolean validateTypeInput(String input) throws SQLException {
        for (String type : service.getTypeService().listTypes()) {
            if (type.toLowerCase().equals(input.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public boolean confirmExit() {
        Scanner reader = new Scanner(System.in);
        System.out.print("Are you sure you want to quit? (Yes/No): ");
        String readString = reader.nextLine();

        if (readString.equalsIgnoreCase("yes")) {
            System.out.println("\nGoodbye!");
            return false;
        } else if (readString.equalsIgnoreCase("no")) {
            return true;
        } else {
            System.out.println("\nError: Sorry, that isn't a valid option. Must type \"Yes\" or \"No\"");
            System.out.println("Please review the menu options below and select an option again.");
            return true;
        }
    }

    public boolean confirmDelete(int index) throws SQLException {
        System.out.print("\nAre you sure you want to delete this animal? (Yes/No): ");

        Scanner reader = new Scanner(System.in);
        String readString = reader.nextLine();

        if (readString.equalsIgnoreCase("yes")) {
            service.removeAnAnimal(index);
            System.out.println("\nSuccess: The animal has been deleted!");
            return true;
        } else if (readString.equalsIgnoreCase("no")) {
            return true;
        } else {
            System.out.println("\nError: Sorry, that isn't a valid option. Must type \"Yes\" or \"No\".");
            System.out.println("Please review the menu options below and select an option again.");
            return true;
        }
    }

    public boolean manageAnimal() throws SQLException, IOException {
        System.out.println("-- Manage an Animal --");
        System.out.println("\nSearch for Animals:\n");
        System.out.println("1) Type");
        System.out.println("2) Name");
        System.out.println("3) ID");
        System.out.println("4) All animals");

        Scanner reader = new Scanner(System.in);

        int userSelection = waitForInt("\nHow do you wish to search?: ");

        ArrayList<Animal> animals;

        ArrayList<Integer> animalIDs = new ArrayList<>();

        switch (userSelection) {
            case 1:
                System.out.print("Animal Type (Cat, Dog, Bird, etc.): ");
                String input = reader.nextLine();
                while (validateTypeInput(input) == false) {
                    System.out.println("\nError: Please choose from the list of animal types on file.\n");
                    System.out.println("Animal Types:");
                    System.out.println("--------------");
                    for (String type : service.getTypeService().listTypes()) {
                        System.out.println(type);
                    }
                    System.out.println("--------------");
                    System.out.print("\nType: ");
                    input = retrieveAnimalInfoFromUser("Type");
                }
                boolean inList = false;
                for (Animal animal : service.listAnimals()) {
                    String type = service.getTypeService().getType(animal.getSpecies());
                    type.toLowerCase();
                    if (type.equalsIgnoreCase(input)) {
                        inList = true;
                    }
                }
                if (inList == false) {
                    System.out.println("\nThere are currently no animals of this type on file.");
                    return true;
                } else {
                    System.out.println("\n-- List of Animals --");
                    animals = service.getAnimalsByType(service.getTypeService().getTypeID(input));
                    System.out.println("\nID\t| Name\t\t\t| Type");
                    System.out.println("--------------------------------------------");
                    for (Animal animal : animals) {
                        animalIDs.add(animal.getID());
                        animal.toAString();
                    }

                }
                return animalDetails(animalIDs);
            case 2:
                System.out.print("Animal name: ");
                String name = reader.nextLine();
                animals = service.getAnimalsByName(name);
                if (animals.size() == 0) {
                    System.out.println("Sorry! There are no animals named " + name);
                    System.out.println("Returning you to the main menu....");
                    return true;
                } else {
                    System.out.println("\n-- List of Animals --");
                    System.out.println("\nID\t| Name\t\t\t| Type");
                    System.out.println("--------------------------------------------");
                    animalIDs.clear();
                    for (Animal animal : animals) {
                        animalIDs.add(animal.getID());
                        animal.toAString();
                    }
                }
                return animalDetails(animalIDs);
            case 3:
                System.out.print("Animal ID: ");
                int num = reader.nextInt();
                Animal animal = service.getAnimalById(num);
                if (animal.getID() == -1) {
                    return true;
                }
                animals = service.listAnimals();
                animalIDs.clear();
                for (Animal anAnimal : animals) {
                    animalIDs.add(anAnimal.getID());
                }
                System.out.println("\n-- Animal Details --\n");
                System.out.println("Name: " + animal.getName());
                System.out.println("Species: " + service.getTypeService().getType(animal.getSpecies()));
                System.out.println("Breed: " + animal.getBreed());
                System.out.println("Description: " + animal.getDescription());
                System.out.println("Notes: ");
                for (AnimalNotes note : animal.getNotes()) {

                    if (note.getID() == animal.getID())
                        System.out.println("\t" + note.getDate() + " - " + note.getText());
                }
                break;
            case 4:
                System.out.println("-- List of Animals --");
                System.out.println("\nID\t| Name\t\t\t| Type");
                System.out.println("--------------------------------------------");
                animals = service.listAnimals();
                animalIDs.clear();
                for (Animal anAnimal : animals) {
                    animalIDs.add(anAnimal.getID());
                    anAnimal.toAString();
                }
                return animalDetails(animalIDs);
            default:
                System.out.println("Wrong input! Please choose a valid option (1-4):");
                this.manageAnimal();
                break;
        }

        return true;
    }

    public boolean animalDetails(ArrayList<Integer> animalIDs) throws SQLException, IOException {
        System.out.print("\nWhich animal do you want to manage? ");
        Scanner reader = new Scanner(System.in);
        int num = reader.nextInt();
        boolean isInList = false;
        for (Integer x : animalIDs) {
            if (x == num) {
                isInList = true;
            }
        }

        while (isInList == false) {
            System.out.println("\nSorry! This is not a valid ID.");
            System.out.print("Please choose an ID from the list of animals above: ");
            num = reader.nextInt();
            for (Integer x : animalIDs) {
                if (x == num) {
                    isInList = true;
                }
            }
        }
        Animal animal = service.getAnimalById(num);

        if (animal.getID() != -1) {
            System.out.println("\n-- Animal Details --\n");
            System.out.println("Name: " + animal.getName());
            System.out.println("Species: " + service.getTypeService().getType(animal.getSpecies()));
            System.out.println("Breed: " + animal.getBreed());
            System.out.println("Description: " + animal.getDescription());
            System.out.println("Notes: ");
            for (AnimalNotes note : animal.getNotes()) {

                if (note.getID() == animal.getID())
                    System.out.println("\t" + note.getDate() + " - " + note.getText());
            }
        }
        return prompt(animal, num);
    }


    public boolean prompt(Animal animal, int num) throws IOException, SQLException {
        System.out.println("\nPlease select an option: \n");
        System.out.println("1) Edit animal");
        System.out.println("2) Delete animal");
        System.out.println("3) Add note");
        System.out.print("4) Return to main menu\n");


        System.out.print("\nWhat do you want to do? ");
        Scanner reader = new Scanner(System.in);
        int selection = reader.nextInt();
        switch (selection) {
            case 1:
                this.editAnimal(animal);
                break;
            case 2:
                return deleteAnimal(num);
            case 3:
                this.addNote(animal);
                return true;
            case 4:
                return true;
            default:
                System.out.println("Not a valid option. Returning you to the main menu...");
                break;
        }
        return true;
    }

    public void addNote(Animal animal) throws IOException, SQLException {
        System.out.println("-- Add a note --\n");

        Scanner reader = new Scanner(System.in);

        System.out.print("Note Text: ");
        String noteText = reader.nextLine();

        // add note
        service.getNoteService().createNote(noteText, animal.getID());

        System.out.println("\nYour note was added!");
    }

    public void manageAnimalTypes() throws IOException, SQLException {
        Scanner reader = new Scanner(System.in);
        System.out.println("1) Add an animal type");
        System.out.println("2) View all types");
        System.out.print("\nPlease select option 1 or 2: ");
        int response = reader.nextInt();

        switch (response) {
            case 1:
                System.out.print("\nWhat animal type would you like to add? : ");
                reader.nextLine();
                String type = reader.nextLine();
                service.getTypeService().createType(type.toLowerCase());
                System.out.println("\nSuccess! The animal type has been created.");
                break;
            case 2:
                ArrayList<String> a = service.getTypeService().listTypes();
                System.out.println("\nList of all animal types:");
                System.out.println("---------------");
                for (String aType : a) {
                    System.out.println(aType);
                }
                break;
            default:
                System.out.println("Invald input. Please select either 1 or 2");
                break;
        }
    }

    public void editAnimal(Animal animal) throws SQLException, IOException {
        String userInput;
        System.out.println("\nPlease answer the following questions. Press enter to keep the current value.");
        System.out.printf("\nAnimal Name [%s]: ", animal.getName());
        userInput = validateAndSetUserInputForAnimal("Enter non-numeric Animal Name: ", animal.getName());
        service.modifyAnimal("name", userInput, animal);

        System.out.printf("Species [%s]: ", service.getTypeService().getType(animal.getSpecies()));
        userInput = validateAndSetUserInputForAnimal("Enter non-numeric Species: ", service.getTypeService().getType(animal.getSpecies()));
        while (service.getTypeService().getTypeID(userInput) == -1) {
            System.out.println("Animal Types:");
            System.out.println("--------------");
            for (String type : service.getTypeService().listTypes()) {
                System.out.println(type);
            }
            System.out.println("--------------");
            System.out.print("\nType: ");
            userInput = validateAndSetUserInputForAnimal("Enter non-numeric Species: ", service.getTypeService().getType(animal.getSpecies()));
        }
        service.modifyAnimal("species", userInput, animal);

        System.out.printf("Breed (optional) [%s]: ", animal.getBreed());
        userInput = validateAndSetUserInputForAnimal("Enter non-numeric Breed (optional): ", animal.getBreed());
        service.modifyAnimal("breed", userInput, animal);

        System.out.printf("Description [%s]: ", animal.getDescription());
        userInput = validateAndSetUserInputForAnimal("Enter non-numeric Description: ", animal.getDescription());
        service.modifyAnimal("description", userInput, animal);

        System.out.println("\nSuccess: The animal has been updated!\n");

        System.out.println("Name: " + animal.getName());
        System.out.println("Species: " + service.getTypeService().getType(animal.getSpecies()));
        System.out.println("Breed: " + animal.getBreed());
        System.out.println("Description: " + animal.getDescription());
    }


    public boolean deleteAnimal(int userSelection) throws SQLException {
        Animal animal = service.getAnimalById(userSelection);

        if (animal.getID() != -1) {
            System.out.println("\nName: " + service.getAnimalById(userSelection).getName());
            System.out.println("Species: " + service.getAnimalById(userSelection).getSpecies());
            System.out.println("Breed: " + service.getAnimalById(userSelection).getBreed());
            System.out.println("Description: " + service.getAnimalById(userSelection).getDescription());
            return confirmDelete(userSelection);
        } else {
            System.out.printf("You haven't added enough animals! You currently have %s animals in the list.\n", service.listAnimals().size());
            System.out.println("\nReturning you to the main menu so you can add more animals (or select another option...");
            return true;
        }
    }
}

