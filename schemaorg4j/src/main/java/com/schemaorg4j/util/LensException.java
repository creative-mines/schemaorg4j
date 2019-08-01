package com.schemaorg4j.util;

public class LensException extends RuntimeException {

    public LensException(String message, Exception originalException) {
        super(message, originalException);
    }
}
