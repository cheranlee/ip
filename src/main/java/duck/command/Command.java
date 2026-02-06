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
 * Boiler Plate Code for all Command Classes
 */
public abstract class Command {

    /**
     * Called in main Duck file after Parser returns a command
     * Executes the Command
     * @param tasklist list of tasks
     * @param ui User Interface
     * @param storage Deals with storing information to hard disk
     * @throws DuckException Self-defined Exception Class which identifies Error
     */
    public abstract void execute(TaskList tasklist, Ui ui, Storage storage)
        throws DuckException;

    /**
     * Breaks out of while loop in Duck Class
     * @return boolean [false by default]
     */
    public boolean isExit(){
        return false;
    }
}
