package duck.command;

import duck.exception.StorageException;
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
    public void execute(TaskList tasks, Ui ui, Storage storage) throws StorageException {
        String newFileContent = tasks.sort();
        storage.rewriteFile(newFileContent);
        String output = tasks.toString();
        this.setDuckResponse(ui.showOperationOutput(output));
        this.setCommandType(CommandType.List);
    }

}
