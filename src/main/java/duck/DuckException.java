package duck;

import duck.command.ByeCommand;
import duck.command.Command;
import duck.command.DeadlineCommand;
import duck.command.DeleteCommand;
import duck.command.EventCommand;
import duck.command.ListCommand;
import duck.command.MarkUnmarkCommand;
import duck.command.TodoCommand;

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