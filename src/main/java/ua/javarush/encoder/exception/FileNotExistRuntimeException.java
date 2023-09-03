package ua.javarush.encoder.exception;

public class FileNotExistRuntimeException extends RuntimeException {

    public FileNotExistRuntimeException (String message) {
        super (message);
    }
}
