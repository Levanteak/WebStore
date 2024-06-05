package com.example.webstore.response;


import org.json.JSONObject;

public class UserCreationResponse {
    private int error;
    private boolean change;
    private String description;

    public UserCreationResponse(int error, boolean change, String description) {
        this.error = error;
        this.change = change;
        this.description = description;
    }


    public String generateResponse() {
        JSONObject response = new JSONObject();
        response.put("error", error);
        response.put("change", change);
        response.put("description", description);
        return response.toString();
    }
}