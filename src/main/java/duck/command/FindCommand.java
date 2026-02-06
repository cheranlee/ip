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

public class FindCommand extends Command {
    private String word;

    public FindCommand(String word){
        this.word = word;
    }

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
