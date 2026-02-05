package duck.command;

import duck.Duck;
import duck.DuckException;
import duck.Item;
import duck.Parser;
import duck.Storage;
import duck.TaskList;
import duck.TaskType;
import duck.Ui;

public class ByeCommand extends Command{

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage){
        ui.showBye();
    }

    @Override
    public boolean isExit(){
        return true;
    }
}
