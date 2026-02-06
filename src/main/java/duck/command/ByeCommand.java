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
 * Class created by Parser when user input = 'Bye'
 */
public class ByeCommand extends Command{

    /**
     * Print Bye Message
     * @param tasks list of all tasks
     * @param ui User Interface
     * @param storage Deals with storing to hard disk
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage){
        ui.showBye();
    }

    /**
     * Breaks out of while loop in Duck Class
     * @return boolean [only true when bye is called]
     */
    @Override
    public boolean isExit(){
        return true;
    }
}
