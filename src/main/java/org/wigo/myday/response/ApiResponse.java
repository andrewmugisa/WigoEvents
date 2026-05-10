// ApiResponse.java  — standard envelope for success messages
package org.wigo.myday.response;

import lombok.Getter;

@Getter
public class ApiResponse {
    private final String message;

    public ApiResponse(String message) {
        this.message = message;
    }
}