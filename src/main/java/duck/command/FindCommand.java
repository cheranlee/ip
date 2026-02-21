package duck.command;

import java.util.List;
import java.util.Objects;

import duck.exception.DuckException;
import duck.storage.Storage;
import duck.tasks.TaskList;
import duck.userinteraction.Ui;

/**
 * Class created by Parser when user input = 'find'.
 */
public class FindCommand extends Command {
    private String word;

    /**
     * Constructor Class for FindCommand.
     *
     * @param word String e.g. X in 'find X'.
     */
    public FindCommand(String word) {
        this.word = word;
    }

    /**
     * Finds Word in tasks (TaskList).
     *
     * @param tasks List of tasks.
     * @param ui User Interface.
     * @param storage Deals with storing information to hard disk.
     * @throws DuckException Self-defined Exception Class which identifies Error.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DuckException {
        List<String> result = tasks.findWord(this.word);
        if (Objects.equals(result, "")) {
            throw new DuckException("No Records Found");
        } else {
            this.setDuckResponse(ui.showWord(result));
            this.setCommandType(CommandType.Find);
        }
    }
}
