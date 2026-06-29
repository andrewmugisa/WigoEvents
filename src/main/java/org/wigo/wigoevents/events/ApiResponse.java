package org.wigo.wigoevents.events;

import lombok.Getter;

import lombok.Getter;

@Getter
public class ApiResponse {
    private final String message;

    public ApiResponse(String message) {
        this.message = message;
    }
}
