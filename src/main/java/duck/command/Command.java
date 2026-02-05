package duck.command;

import duck.Duck;
import duck.DuckException;
import duck.Item;
import duck.Parser;
import duck.Storage;
import duck.TaskList;
import duck.TaskType;
import duck.Ui;

public abstract class Command {
    public abstract void execute(TaskList tasklist, Ui ui, Storage storage)
        throws DuckException;

    public boolean isExit(){
        return false;
    }
}
