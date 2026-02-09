package duck.command;

import duck.Storage;
import duck.TaskList;
import duck.Ui;

/**
 * Class created by Parser when user input = 'list'
 */
public class ListCommand extends Command {

    /**
     * Print List using toString method in TaskList
     * @param tasks list of tasks
     * @param ui User Interface
     * @param storage Deals with storing information to hard disk
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        ui.showList(tasks.toString());
    }

}
