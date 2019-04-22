package com.metronom.tictactoe2.models;

public enum PlayRuntimeStatus {
    
	ON("Game is on.."),
    OVER("Game over!");

    private final String status;

    private PlayRuntimeStatus(String message) {
        this.status = message;
    }

    public String getStatus() {
        return status;
    }
}