package com.example.global;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DuplicateIdException extends RuntimeException{

    public DuplicateIdException(String message) {
        super(message);
    }

}
