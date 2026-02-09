package duck.command;

import duck.Storage;
import duck.TaskList;
import duck.Ui;

/**
 * Class created by Parser when user input = 'list'
 */
public class ListCommand extends Command {
    private String output;

    /**
     * Print List using toString method in TaskList
     * @param tasks list of tasks
     * @param ui User Interface
     * @param storage Deals with storing information to hard disk
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        this.setString(ui.showList(tasks.toString()));
        this.setCommandType(CommandType.List);
    }

}
