/**
 * Error Detection
 * Note: Refer to input.txt in text-ui-test folder to see examples of the following errors
 * 1. Commands are not case sensitive (ie List will be recognised as list)
 * 2. Command not recognised (list/todo/deadline/...)
 * 3. Missing / Wrong keyword for the wrong command (e.g. using start/end for deadline)
 * 4. Missing description / date (for deadline / start & end)
 * 5. Mark/Unmark/Delete item number out of range (task index does not exist in list)
 * 6. Mark/Unmark/Delete invalid task number (e.g. mark 2a, mark   , mark -1)
 * 7. Mark task that has been marked (& vice versa)
 * 8. Date and time format not valid
 * 9. For event, Start Date after End Date
 */

// TAKES IN DD-MM-YYYY prints out MMM dd YYYY

package duck;

import duck.command.Command;
import duck.command.CommandType;

/**
 * Main Class
 */
public class Duck {
    private static String home = System.getProperty("user.dir");

    private Storage storage;
    private TaskList tasks;
    private Ui ui;
    private Parser parser;
    private CommandType commandType;

    /**
     * Constructor for Duck Class
     * Initialises ui, storage, parser and tasklist (loads old data in hard disk using storage object)
     * If no old data, creates empty tasklist
     * @param filePath Home Directory src/main/java
     */
    public Duck(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        parser = new Parser(ui);
        try {
            tasks = new TaskList(storage.load()); // load prev tasks
        } catch (DuckException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        try {
            Command c = parser.parse(input);
            c.execute(tasks, ui, storage);
            this.setCommandType(c.getCommandType());
            return c.getString();
        } catch (DuckException e) {
            return "Error: " + e.getMessage();
        }
    }

    public CommandType getCommandType() {
        return this.commandType;
    }

    public void setCommandType(CommandType commandType) {
        this.commandType = commandType;
    }
}

