package com.example.nestedintervalwithfarey.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class FareyTreeException extends RuntimeException {
    public FareyTreeException(String message) {
        super(message);
    }
}
