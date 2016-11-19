package entity;

public class AnimalNotes {
    private int ID = 0;  // animal ID - foreign key
    private int noteID = 0;
    private String text = "";
    private String date = "";

    public AnimalNotes(int ID, String text, String date, int noteID) {
        this.ID = ID;
        this.text = text;
        this.date = date;
        this.noteID = noteID;
    }

    public int getNoteID() {
        return noteID;
    }

    public void setNoteID(int noteID) {
        this.noteID = noteID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String toString() {
        return String.format("%s %s",
                this.text,
                this.date);
    }
}
