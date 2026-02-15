package duck.command;

import duck.exception.DuckException;
import duck.exception.ParserException;
import duck.exception.StorageException;
import duck.storage.Storage;
import duck.tasks.TaskList;
import duck.userinteraction.Ui;

/**
 * Boiler Plate Code for all Command Classes.
 */
public abstract class Command {
    private String duckResponse;
    private CommandType commandType;

    /**
     * Executes the Command.
     *
     * @param tasks List of tasks.
     * @param ui User Interface.
     * @param storage Deals with storing information to hard disk.
     * @throws DuckException Self-defined Exception Class which identifies Error.
     */
    public abstract void execute(TaskList tasks, Ui ui, Storage storage)
            throws DuckException, StorageException, ParserException;

    /**
     * Breaks out of while loop in Duck Class.
     *
     * @return boolean false <= default.
     */
    public boolean isExit() {
        return false;
    }

    /**
     * Return output of Command as a String
     *
     * @return String output to be displayed in JavaFX
     */
    public String getDuckResponse() {
        return this.duckResponse;
    }

    public void setDuckResponse(String message) {
        this.duckResponse = message;
    }

    public void setCommandType(CommandType commandType) {
        this.commandType = commandType;
    }

    public CommandType getCommandType() {
        return this.commandType;
    }
}
