package duck.command;

import duck.Duck;
import duck.DuckException;
import duck.Item;
import duck.Parser;
import duck.Storage;
import duck.TaskList;
import duck.TaskType;
import duck.Ui;

import java.util.Objects;

/**
 * Class created by Parser when user input = 'find'
 */
public class FindCommand extends Command {
    private String word;

    /**
     * Constructor Class for FindCommand
     * @param word String e.g. X in 'find X'
     */
    public FindCommand(String word){
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
          throw new DuckException("Error! No Records Found");
      } else {
          ui.showWord(result);
      }
    }
}
