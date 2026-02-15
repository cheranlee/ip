package duck.exception;

/**
 * Syntax Errors when user is calling commands (E.g. Missing keywords such as 'by', 'start' or 'end').
 */
public class ParserException extends DuckException {
    public ParserException(String errorMessage) {
        super(errorMessage);
    }
}
