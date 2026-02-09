package duck.command;

import duck.DuckException;
import duck.Item;
import duck.Storage;
import duck.TaskList;
import duck.Ui;

/**
 * Class created by Parser when user input = 'todo'
 */
public class TodoCommand extends Command {
    private int byDatetimePos;
    private int startDatetimePos;
    private int endDatetimePos;
    private String subCommand;
    private String output;

    /**
     * Constructor Class for TodoCommand
     * @param byDatetimePos index of 'by' keyword [should be equal to -1 for deadline task]
     * @param startDatetimePos index of 'start' keyword [should be equal to -1 for deadline task]
     * @param endDatetimePos index of 'end' keyword [should be equal to -1 for deadline task]
     * @param subCommand user input without 'todo' keyword
     */
    public TodoCommand(int byDatetimePos, int startDatetimePos, int endDatetimePos, String subCommand) {
        this.byDatetimePos = byDatetimePos;
        this.startDatetimePos = startDatetimePos;
        this.endDatetimePos = endDatetimePos;
        this.subCommand = subCommand;
    }

    /**
     * Add Item in list as a 'todo' item
     * @param tasks list of tasks
     * @param ui User Interface
     * @param storage Deals with storing information to hard disk
     * @throws DuckException Self-defined Exception Class which identifies Error
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DuckException {
        System.out.println(this.subCommand);
        if (this.byDatetimePos == -1 && this.startDatetimePos == -1 && this.endDatetimePos == -1) {
            String result = tasks.addItem(new Item(this.subCommand));
            Item newItem = tasks.getItem(tasks.size() - 1);
            storage.addToFile(newItem.toStringFile() + '\n');
            this.setString(ui.showOperationOutput(result));
            this.setCommandType(CommandType.Todo);
        } else { // error if (by), (start), (end) are in user input
            throw new DuckException("Todo task should not have deadline, start or end date");
        }
    }
}
