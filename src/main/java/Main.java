import repository.AnimalRepository;
import repository.AnimalTypeRepository;
import repository.NoteRepository;
import service.AnimalsService;
import service.MenuService;
import service.NoteService;
import service.TypeService;

import java.io.IOException;
import java.sql.SQLException;



public class Main {
    public static void main(String[] args) throws SQLException, IOException {

        String jdbcUrl = "jdbc:postgresql://localhost:5432/animals";
        NoteRepository noteRepo = new NoteRepository(jdbcUrl);
        NoteService noteService = new NoteService(noteRepo);

        AnimalTypeRepository animalTypeRepository = new AnimalTypeRepository(jdbcUrl);
        TypeService typeService = new TypeService(animalTypeRepository);

        AnimalRepository animalRepository = new AnimalRepository(jdbcUrl);

        MenuService menu = new MenuService(new AnimalsService(animalRepository, noteService, typeService));

        boolean repeatMenuOptions = true;

        do {
            int userSelection = menu.promptForMainMenuSelection(); // returns user selection

            if (userSelection >= 1 && userSelection <= 4)  // check to see if a valid option was entered
            {
                switch (userSelection) {

                    case MenuService.CREATE_ANIMAL:
                        menu.promptForAnimalData();
                        break;

                    case MenuService.MANAGE_ANIMAL:
                        repeatMenuOptions = menu.manageAnimal();
                        break;

                    case MenuService.MANAGE_ANIMAL_TYPES:
                        menu.manageAnimalTypes();
                        break;

                    case MenuService.EXIT_PROGRAM:
                        repeatMenuOptions = menu.confirmExit();
                        break;
                }

            } else {
                System.out.println("Error: Sorry, that isn't a valid option.");
            }

        } while (repeatMenuOptions);
    }
}
