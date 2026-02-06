package duck.command;

import duck.Duck;
import duck.DuckException;
import duck.Item;
import duck.Parser;
import duck.Storage;
import duck.TaskList;
import duck.TaskType;
import duck.Ui;

public class TodoCommand extends Command{
    private int byDatetimePos;
    private int startDatetimePos;
    private int endDatetimePos;
    private String subCommand;

    public TodoCommand(int byDatetimePos, int startDatetimePos, int endDatetimePos, String subCommand){
        this.byDatetimePos = byDatetimePos;
        this.startDatetimePos = startDatetimePos;
        this.endDatetimePos = endDatetimePos;
        this.subCommand = subCommand;
    }

    /**
     * Command to add an item in list as a 'ToDo' item
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DuckException{
        if (this.byDatetimePos == -1 && this.startDatetimePos == -1 && this.endDatetimePos == -1) {
            tasks.addItem(new Item(this.subCommand));
            Item newItem = tasks.getItem(tasks.size()-1);
            storage.addToFile(newItem.toStringFile() + '\n');
        } else {  // error if (by), (start), (end) are in user input
            throw new DuckException("ERROR! Todo task should not have deadline, start or end date");
        }
    }
}
