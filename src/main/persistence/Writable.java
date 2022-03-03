package persistence;

import org.json.JSONObject;

// Code reference: JsonSerializationDemo from Phase 2 example file
public interface Writable {
    // EFFECTS: returns this as a JSON object
    JSONObject toJson();
}
