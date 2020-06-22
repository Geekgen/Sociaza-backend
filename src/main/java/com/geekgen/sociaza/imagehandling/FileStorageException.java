package com.geekgen.sociaza.imagehandling;

public class FileStorageException extends RuntimeException {
    /**
     *
     */
    private static final long serialVersionUID = 2267372877489617840L;

    public FileStorageException(String message) {
        super(message);
    }

    public FileStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}


