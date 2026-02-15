package duck.exception;

/**
 * Storage Errors related to writing to / reading from file.
 */
public class StorageException extends DuckException {
    public StorageException(String errorMessage) {
        super(errorMessage);
    }
}
