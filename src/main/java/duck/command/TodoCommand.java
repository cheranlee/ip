package duck.command;

import duck.exception.DuckException;
import duck.exception.ParserException;
import duck.storage.Storage;
import duck.tasks.Item;
import duck.tasks.TaskList;
import duck.userinteraction.Ui;

/**
 * Class created by Parser when user input = 'todo'.
 */
public class TodoCommand extends Command {
    private int byDatetimePos;
    private int startDatetimePos;
    private int endDatetimePos;
    private String argument;

    /**
     * Constructor Class for TodoCommand.
     *
     * @param byDatetimePos Index of 'by' keyword [should be equal to -1 for deadline task].
     * @param startDatetimePos Index of 'start' keyword [should be equal to -1 for deadline task].
     * @param endDatetimePos Index of 'end' keyword [should be equal to -1 for deadline task].
     * @param argument Iser input without 'todo' keyword.
     */
    public TodoCommand(int byDatetimePos, int startDatetimePos, int endDatetimePos, String argument) {
        this.byDatetimePos = byDatetimePos;
        this.startDatetimePos = startDatetimePos;
        this.endDatetimePos = endDatetimePos;
        this.argument = argument;
    }

    /**
     * Add an item in tasks (TaskList) as a 'ToDo' item.
     * Throws exception if unable to create new Item to store in tasklist
     *
     * @param tasks List of tasks.
     * @param ui User Interface.
     * @param storage Deals with storing information to hard disk.
     * @throws DuckException Self-defined Exception Class which identifies Error.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DuckException {
        System.out.println(this.argument);
        if (this.byDatetimePos == -1 && this.startDatetimePos == -1 && this.endDatetimePos == -1) {
            String result = tasks.addItem(new Item(this.argument));
            Item newItem = tasks.getItem(tasks.size() - 1);
            storage.addToFile(newItem.toStringFile());
            this.setDuckResponse(ui.showOperationOutput(result));
            this.setCommandType(CommandType.Todo);
        } else { // error if (by), (start), (end) are in user input
            throw new ParserException("Todo task should not have deadline, start or end date "
                    + "(keywords: by, start, end).");
        }
    }
}
