package duck.command;

import duck.Duck;
import duck.DuckException;
import duck.Item;
import duck.Parser;
import duck.Storage;
import duck.TaskList;
import duck.TaskType;
import duck.Ui;

import java.util.List;

/**
 * Class created by Parser when user input = 'mark' or 'unmark'
 */
public class MarkUnmarkCommand extends Command{

    private String fullCommand;
    private Boolean mark;

    /**
     * Constructor Class for MarkUnmarkCommand
     * @param fullCommand e.g. mark 3
     */
    public MarkUnmarkCommand(String fullCommand, Boolean unmark){
        this.fullCommand = fullCommand;
        this.mark = !unmark;
    }

    /**
     * Mark Item as Done / Mark Item as Not Done
     * Throws Exception if:
     *  1) User inputs mark X but X has been marked (and vice versa)
     *  2) X is more than the number of tasks
     *  3) X is not a valid number (e.g. -3)
     * @param tasks list of tasks
     * @param ui User Interface
     * @param storage Deals with storing information to hard disk
     * @throws DuckException Self-defined Exception Class which identifies Error
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DuckException{
        try {
            int index = Integer.parseInt(this.fullCommand);
            index = index - 1;
            if (index < tasks.size() && index >= 0) {
                try {
                    System.out.println(this.fullCommand);
                    List<String> returnArray = tasks.markUnmarkItem(this.mark, index);
                    String editedItemString = returnArray.get(1);
                    storage.editFile(index, editedItemString);
                    ui.showOperationOutput(returnArray.get(0));
                } catch (IllegalArgumentException ex) {
                    if (ex.getMessage().contains("Not")) {
                        throw new DuckException("\tItem already marked as not done!");
                    } else {
                        throw new DuckException("\tItem already marked as done!");
                    }
                }
            } else {
                throw new DuckException("ERROR! Item Number out of range");
            }
        } catch (NumberFormatException ex2) {
            throw new DuckException("ERROR! Index not a valid number");
        }
    }
}
