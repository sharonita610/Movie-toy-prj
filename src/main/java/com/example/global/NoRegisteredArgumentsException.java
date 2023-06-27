package com.example.global;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NoRegisteredArgumentsException extends RuntimeException{

    public NoRegisteredArgumentsException(String message) {
        super(message);
    }

}
