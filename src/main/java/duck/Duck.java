package duck;

import duck.command.Command;
import duck.command.CommandType;
import duck.exception.DuckException;
import duck.exception.StorageException;
import duck.storage.Storage;
import duck.tasks.TaskList;
import duck.userinteraction.Parser;
import duck.userinteraction.Ui;

/**
 * Main Class.
 */
public class Duck {

    private Storage storage;
    private TaskList tasks;
    private Ui ui;
    private Parser parser;
    private CommandType commandType;

    /**
     * Constructor for Duck Class.
     * Initialises ui, storage, parser and tasklist (loads old data in hard disk using storage object).
     * If no old data, creates empty tasklist.
     *
     * @param filePath HOME Directory src/main/java
     */
    public Duck(String filePath) {
        ui = new Ui();
        try {
            storage = new Storage(filePath);
        } catch (StorageException storageError) {
            ui.showError(storageError);
        }
        parser = new Parser();
        try {
            tasks = new TaskList(storage.load()); // load prev tasks
        } catch (StorageException noPrevRecordError) {
            ui.showLoadingError();
            tasks = new TaskList();
        } catch (DuckException markError) {
            ui.showError(markError);
        }
    }

    /**
     * Generates a response for the user's chat message.
     *
     * @return String Formatted String response from Duck.
     */
    public String getResponse(String input) {
        try {
            Command command = parser.parse(input);
            command.execute(tasks, ui, storage);
            this.setCommandType(command.getCommandType());
            return command.getDuckResponse();
        } catch (DuckException error) {
            this.setCommandType(CommandType.NoCommand);
            return ui.showError(error);
        }
    }

    public CommandType getCommandType() {
        return this.commandType;
    }

    public void setCommandType(CommandType commandType) {
        this.commandType = commandType;
    }
}

