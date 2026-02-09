package duck.command;

import java.util.Objects;

import duck.DuckException;
import duck.Storage;
import duck.TaskList;
import duck.Ui;

/**
 * Class created by Parser when user input = 'find'
 */
public class FindCommand extends Command {
    private String word;
    private String output;

    /**
     * Constructor Class for FindCommand
     * @param word String e.g. X in 'find X'
     */
    public FindCommand(String word) {
        this.word = word;
    }

    /**
     * Checks if output from findWord method in tasklist is empty
     * Throws Exception if empty, else redirects to ui class for printing
     * @param tasks list of tasks
     * @param ui User Interface
     * @param storage Deals with storing information to hard disk
     * @throws DuckException Self-defined Exception Class which identifies Error
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DuckException {
        String result = tasks.findWord(this.word);
        if (Objects.equals(result, "")) {
            throw new DuckException("No Records Found");
        } else {
            this.setString(ui.showWord(result));
            this.setCommandType(CommandType.Find);
        }
    }
}
