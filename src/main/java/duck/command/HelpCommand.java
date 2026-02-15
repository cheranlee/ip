package duck.command;

import duck.storage.Storage;
import duck.tasks.TaskList;
import duck.userinteraction.Ui;

/**
 * Class created by Parser when user input = 'Help'.
 */
public class HelpCommand extends Command {

    /**
     * Print Help Message.
     *
     * @param tasks List of tasks.
     * @param ui User Interface.
     * @param storage Deals with storing information to hard disk.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        this.setDuckResponse(ui.printInfo());
        this.setCommandType(CommandType.Help);
    }
}
