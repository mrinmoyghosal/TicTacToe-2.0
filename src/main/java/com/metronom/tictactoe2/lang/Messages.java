package com.metronom.tictactoe2.lang;

import java.util.ResourceBundle;

public class Messages {
    private static final Messages instance = new Messages();

    private ResourceBundle bundle;

    private Messages() {
        bundle = ResourceBundle.getBundle("language");
    }

    public static Messages getInstance() {
        return instance;
    }

    public String get(MessageKey key) {
        return bundle.getString(key.name());
    }
}