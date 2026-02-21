package duck.command;
import java.io.IOException;

import duck.exception.DuckException;
import duck.storage.Storage;
import duck.tasks.TaskList;
import duck.userinteraction.Ui;

/**
 * Class created by Parser when user input = 'cheer'.
 */
public class CheerCommand extends Command {

    /**
     * Shows Motivational Quote.
     *
     * @param tasks List of tasks.
     * @param ui User Interface.
     * @param storage Deals with storing information to hard disk.
     * @throws DuckException Self-defined Exception Class which identifies Error.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DuckException {
        try {
            String cheerPhrase = storage.cheer();
            this.setDuckResponse(ui.showOperationOutput(cheerPhrase));
            this.setCommandType(CommandType.Cheer);
        } catch (IOException fileError) {
            throw new DuckException("Unable to Open File!");
        }
    }
}
