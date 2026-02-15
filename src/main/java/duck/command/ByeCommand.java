package duck.command;

import duck.storage.Storage;
import duck.tasks.TaskList;
import duck.userinteraction.Ui;

/**
 * Class created by Parser when user input = 'Bye'.
 */
public class ByeCommand extends Command {

    /**
     * Print Bye Message.
     *
     * @param tasks List of Tasks.
     * @param ui User Interface.
     * @param storage Deals with storing information to hard disk.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) {
        this.setDuckResponse(ui.showBye());
        this.setCommandType(CommandType.Bye);
    }

    /**
     * Breaks out of While loop in Duck Class.
     *
     * @return boolean true <= bye is called.
     */
    @Override
    public boolean isExit() {
        return true;
    }
}
