package duck.command;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import duck.exception.DuckException;
import duck.exception.ParserException;
import duck.storage.Storage;
import duck.tasks.Item;
import duck.tasks.TaskList;
import duck.userinteraction.Ui;

/**
 * Class created by Parser when user input = 'deadline'.
 */
public class DeadlineCommand extends Command {
    private static final String INVALID_FORMAT = "DateTime format should be dd-MM-yyyy HH:mm";
    private int byDatetimePos;
    private int startDatetimePos;
    private int endDatetimePos;
    private String argument;

    /**
     * Constructor Class for DeadlineCommand.
     *
     * @param byDatetimePos Index of 'by' keyword [not equal to -1 for deadline task].
     * @param startDatetimePos Index of 'start' keyword [should be equal to -1 for deadline task].
     * @param endDatetimePos Index of 'end' keyword [should be equal to -1 for deadline task].
     * @param argument User input without 'deadline' keyword
     */
    public DeadlineCommand(int byDatetimePos, int startDatetimePos, int endDatetimePos, String argument) {
        this.byDatetimePos = byDatetimePos;
        this.startDatetimePos = startDatetimePos;
        this.endDatetimePos = endDatetimePos;
        this.argument = argument;
    }

    @SuppressWarnings("checkstyle:Regexp")
    private Item generateDeadlineItem(String description, String datetime) throws DuckException {
        String[] splitString = datetime.split("\\s");

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        LocalDate date = null;
        LocalTime time = null;

        if (splitString.length > 2) {
            throw new DuckException(INVALID_FORMAT);
        }

        assert splitString.length > 0 : "datetime missing, error not caught previously!";

        try {
            for (String part : splitString) {
                if (part.contains("-")) {
                    date = LocalDate.parse(part.trim(), dateFormatter);
                } else if (part.contains(":")) {
                    time = LocalTime.parse(part.trim(), timeFormatter);
                } else {
                    throw new DuckException(INVALID_FORMAT);
                }
            }
        } catch (DateTimeParseException formatError) {
            throw new DuckException(INVALID_FORMAT);
        }
        return new Item(description, date, time);
    }

    /**
     * Add an item in tasks (TaskList) as a 'Deadline' item.
     * Throws Exception if unable to create new Item to store in tasklist.
     *
     * @param tasks List of tasks.
     * @param ui User Interface.
     * @param storage Deals with storing information to hard disk.
     * @throws DuckException Self-defined Exception Class which identifies Error
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DuckException {
        if (byDatetimePos != -1 && startDatetimePos == -1 && endDatetimePos == -1) {
            String byDatetime = this.argument.substring(byDatetimePos + 2);
            String description = this.argument.substring(0, byDatetimePos);
            if (description.isBlank() || byDatetime.isBlank()) { // check if by or description field are blank
                throw new ParserException("Description / Deadline of Task cannot be empty");
            } else {
                Item newItem = this.generateDeadlineItem(description.trim(), byDatetime.trim());
                String response = tasks.addItem(newItem);
                storage.addToFile(newItem.toStringFile() + '\n');
                this.setDuckResponse(ui.showOperationOutput(response));
                this.setCommandType(CommandType.Deadline);
            }
        } else { // error if (by) does not appear of if (start) or (end) are in user input
            throw new ParserException("Deadline task must have a Deadline (keyword: by). "
                    + "It also should not have a start or end date (keywords: start, end).");
        }
    }
}
