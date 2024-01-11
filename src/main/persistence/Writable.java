package main.persistence;

import org.json.JSONObject;

// Represents a JSON Object writer
public interface Writable {
    // EFFECTS: returns this as JSON object
    JSONObject toJson();
}

