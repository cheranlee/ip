package duck.command;
import java.io.IOException;

import duck.DuckException;
import duck.Storage;
import duck.TaskList;
import duck.Ui;



/**
 * Class created by Parser when user input = 'cheer'
 */
public class CheerCommand extends Command {

    /**
     * Shows Motivational Quote
     * @param tasks list of tasks
     * @param ui User Interface
     * @param storage Deals with storing information to hard disk
     * @throws DuckException Self-defined Exception Class which identifies Error
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DuckException {
        try {
            String cheerPhrase = storage.cheer();
            ui.showCheer(cheerPhrase);
        } catch (DuckException | IOException e) {
            ui.showError(e.getMessage());
        }
    }

}
