package duck;

/**
 * Custom Exception
 * All exceptions raised in Command Classes are classified as Duck Exception
 * Allows Duck Class to only have to catch DuckException even though there are multiple types of 'errors'
 */
public class DuckException extends Exception {
    public DuckException(String errorMessage) {
        super(errorMessage);
    }
}
