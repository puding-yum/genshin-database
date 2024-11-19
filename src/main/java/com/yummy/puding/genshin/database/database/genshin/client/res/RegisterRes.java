package com.yummy.puding.genshin.database.database.genshin.client.res;

public class RegisterRes {
    private String message;
    private String username;
    private String email;

    public RegisterRes() {}

    public RegisterRes(String message, String username, String email) {
        this.message = message;
        this.username = username;
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
