package duck.command;

import duck.Storage;
import duck.TaskList;
import duck.Ui;

/**
 * Class created by Parser when user input = 'Help'
 */
public class HelpCommand extends Command {
    private String output;

    /**
     * Print Help Message
     * @param tasks   list of tasks
     * @param ui      User Interface
     * @param storage Deals with storing information to hard disk
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        this.setString(ui.printInfo());
        this.setCommandType(CommandType.Help);
    }
}
