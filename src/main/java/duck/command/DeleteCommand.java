package duck.command;

import duck.exception.DuckException;
import duck.storage.Storage;
import duck.tasks.TaskList;
import duck.userinteraction.Ui;

/**
 * Class created by Parser when user input = 'delete'.
 */
public class DeleteCommand extends Command {

    private String fullCommand;

    /**
     * Constructor class for DeleteCommand.
     *
     * @param fullCommand e.g. delete 5.
     */
    public DeleteCommand(String fullCommand) {
        this.fullCommand = fullCommand;
    }

    /**
     * Deletes task (Item) from tasks (TaskList).
     *
     * @param tasks List of tasks.
     * @param ui User Interface.
     * @param storage Deals with storing information to hard disk.
     * @throws DuckException Self-defined Exception Class which identifies Error.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DuckException {
        try {
            int index = Integer.parseInt(this.fullCommand);
            index = index - 1;
            if (index < tasks.size() && index >= 0) {
                String response = tasks.deleteItem(index);
                storage.deleteFromFile(index);
                this.setDuckResponse(ui.showOperationOutput(response));
                this.setCommandType(CommandType.Delete);
            } else {
                throw new DuckException("Item Number out of range");
            }
        } catch (NumberFormatException ex3) {
            throw new DuckException("Index not a valid number");
        }
    }
}
