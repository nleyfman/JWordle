package persistence;

import org.json.JSONObject;

// Code reference: JsonSerializationDemo
public interface Writable {
    // EFFECTS: returns this as a JSON object
    JSONObject toJson();
}
