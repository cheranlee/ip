package duck.command;

import duck.storage.Storage;
import duck.tasks.TaskList;
import duck.userinteraction.Ui;

/**
 * Class created by Parser when user input = 'list'.
 */
public class ListCommand extends Command {

    /**
     * Print tasks (TaskList) using toString method in TaskList.
     *
     * @param tasks List of tasks.
     * @param ui User Interface.
     * @param storage Deals with storing information to hard disk.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        this.setDuckResponse(ui.showOperationOutput(tasks.toString()));
        this.setCommandType(CommandType.List);
    }

}
