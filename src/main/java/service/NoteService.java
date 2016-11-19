package service;

import entity.AnimalNotes;
import repository.NoteRepository;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class NoteService {
    private NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public void createNote(String text, int num) throws IOException, SQLException {
        noteRepository.createNote(text, num);
    }

    public ArrayList<AnimalNotes> listNotes() {
        ArrayList<AnimalNotes> notes = new ArrayList<>();

        try {
            ResultSet resultSet = this.noteRepository.listNotes();

            while (resultSet.next()) {
                AnimalNotes aNote = new AnimalNotes(
                        resultSet.getInt("animal_id"),
                        resultSet.getString("note_text"),
                        resultSet.getString("date_time"),
                        resultSet.getInt("note_id")

                );

                notes.add(aNote);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return notes;
    }

}

