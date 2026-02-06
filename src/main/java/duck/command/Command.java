package duck.command;

import duck.DuckException;
import duck.Storage;
import duck.TaskList;
import duck.Ui;

public abstract class Command {
    public abstract void execute(TaskList tasks, Ui ui, Storage storage)
        throws DuckException;

    public boolean isExit(){
        return false;
    }
}
