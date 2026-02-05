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
    private int by_datetime_pos;
    private int start_datetime_pos;
    private int end_datetime_pos;
    private String subCommand;

    public TodoCommand(int by_datetime_pos, int start_datetime_pos, int end_datetime_pos, String subCommand){
        this.by_datetime_pos = by_datetime_pos;
        this.start_datetime_pos = start_datetime_pos;
        this.end_datetime_pos = end_datetime_pos;
        this.subCommand = subCommand;
    }

    /**
     * Command to add an item in list as a 'ToDo' item
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DuckException{
        if (this.by_datetime_pos == -1 && this.start_datetime_pos == -1 && this.end_datetime_pos == -1) {
            tasks.addItem(new Item(this.subCommand));
            Item newItem = tasks.getItem(tasks.size()-1);
            storage.addToFile(newItem.toStringFile() + '\n');
        } else {  // error if (by), (start), (end) are in user input
            throw new DuckException("ERROR! Todo task should not have deadline, start or end date");
        }
    }
}
