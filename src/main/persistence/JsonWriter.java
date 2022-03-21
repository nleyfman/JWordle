package persistence;

import model.Statistics;
import org.json.JSONObject;
import ui.GameController;
import ui.GameControllerPrev;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

// Code reference: JsonSerializationDemo from Phase 2 example file
// Represents a writer that writes JSON representation of game state to file
public class JsonWriter {
    private static final int TAB = 4;
    private PrintWriter writer;
    private String destination;

    // EFFECTS: constructs writer to write to destination file
    public JsonWriter(String destination) {
        this.destination = destination;
    }

    // MODIFIES: this
    // EFFECTS: opens writer; throws FileNotFoundException if destination file cannot
    // be opened for writing
    public void open() throws FileNotFoundException {
        writer = new PrintWriter(new File(destination));
    }

    // MODIFIES: this
    // EFFECTS: writes JSON representation of game state to file
    public void write(GameController gc, Statistics stats) {
        JSONObject json = new JSONObject();
        json.put("gameController", gc.toJson());
        json.put("stats", stats.toJson());
        saveToFile(json.toString(TAB));
    }

    // MODIFIES: this
    // EFFECTS: closes writer
    public void close() {
        writer.close();
    }

    // MODIFIES: this
    // EFFECTS: writes string to file
    private void saveToFile(String json) {
        writer.print(json);
    }

}
