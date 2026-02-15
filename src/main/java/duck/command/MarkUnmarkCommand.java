package duck.command;

import java.util.List;

import duck.exception.DuckException;
import duck.storage.Storage;
import duck.tasks.TaskList;
import duck.userinteraction.Ui;


/**
 * Class created by Parser when user input = 'mark' or 'unmark'.
 */
public class MarkUnmarkCommand extends Command {

    private String fullCommand;
    private Boolean mark;

    /**
     * Constructor Class for MarkUnmarkCommand.
     *
     * @param fullCommand e.g. mark 3.
     * @param unmark true <= mark as done; false <= mark as not done.
     */
    public MarkUnmarkCommand(String fullCommand, Boolean unmark) {
        this.fullCommand = fullCommand;
        this.mark = !unmark;
    }

    /**
     * Mark Item as Done / Mark Item as Not Done
     *
     * Throws Exception if:
     *  1) User inputs mark X but X has been marked (and vice versa)
     *  2) X is more than the number of tasks
     *  3) X is not a valid number (e.g. -3)
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
                List<String> returnArray = tasks.markUnmarkItem(this.mark, index);
                String editedItemString = returnArray.get(1);
                storage.editFile(index, editedItemString);
                this.setDuckResponse(ui.showOperationOutput(returnArray.get(0)));
                this.setCommandType(CommandType.MarkUnmark);
            } else {
                throw new DuckException("Item Number out of range");
            }
        } catch (NumberFormatException ex2) {
            throw new DuckException("Index not a valid number");
        }
    }
}
