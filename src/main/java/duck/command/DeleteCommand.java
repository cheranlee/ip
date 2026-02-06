package duck.command;

import duck.Duck;
import duck.DuckException;
import duck.Item;
import duck.Parser;
import duck.Storage;
import duck.TaskList;
import duck.TaskType;
import duck.Ui;

/**
 * Class created by Parser when user input = 'delete'
 */
public class DeleteCommand extends Command{

    private String fullCommand;

    /**
     * Constructor class for DeleteCommand
     * @param fullCommand e.g. delete 5
     */
    public DeleteCommand(String fullCommand){
        this.fullCommand = fullCommand;
    }

    /**
     *
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
                tasks.deleteItem(index);
                storage.deleteFromFile(index);
            } else {
                throw new DuckException("ERROR! Item Number out of range");
            }
        } catch (NumberFormatException ex3) {
            throw new DuckException("ERROR! Index not a valid number");
        }
    }
}
