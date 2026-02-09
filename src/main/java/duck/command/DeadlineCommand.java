package duck.command;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import duck.DuckException;
import duck.Item;
import duck.Storage;
import duck.TaskList;
import duck.Ui;

/**
 * Class created by Parser when user input = 'deadline'
 */
public class DeadlineCommand extends Command {
    private int byDatetimePos;
    private int startDatetimePos;
    private int endDatetimePos;
    private String subCommand;

    /**
     * Constructor Class for DeadlineCommand
     * @param byDatetimePos index of 'by' keyword [not equal to -1 for deadline task]
     * @param startDatetimePos index of 'start' keyword [should be equal to -1 for deadline task]
     * @param endDatetimePos index of 'end' keyword [should be equal to -1 for deadline task]
     * @param subCommand user input without 'deadline' keyword
     */
    public DeadlineCommand(int byDatetimePos, int startDatetimePos, int endDatetimePos, String subCommand) {
        this.byDatetimePos = byDatetimePos;
        this.startDatetimePos = startDatetimePos;
        this.endDatetimePos = endDatetimePos;
        this.subCommand = subCommand;
    }

    /**
     * helper function to determine if date input is of a valid format (dd-MM-yyyy)
     * @param input date input
     * @return boolean
     */
    public boolean isValidDate(String input) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        try {
            LocalDate.parse(input, formatter);
            return true;
        } catch (DateTimeParseException dateFormatError) {
            return false;
        }
    }

    /**
     * helper function to determine if time input is of a valid format (HH:mm)
     * @param input time input
     * @return boolean
     */
    public boolean isValidTime(String input) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        try {
            LocalTime.parse(input, formatter);
            return true;
        } catch (DateTimeParseException timeFormatError) {
            return false;
        }
    }

    /**
     * Instantiate new task entry if task is a Deadline
     * @param description 'return book' in 'deadline return book by 24-02-2026 03:00'
     * @param datetime '24-02-2026 03:00' in 'deadline return book by 24-02-2026 03:00'
     * @return Item
     */
    public Item generateDeadlineItem(String description, String datetime) {
        String[] splitString = datetime.split("\\s");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalDate date = null;
        LocalTime time = null;
        if (splitString.length == 2) {
            if (this.isValidDate(splitString[0]) && this.isValidTime(splitString[1])) {
                date = LocalDate.parse(splitString[0].trim(), dateFormatter);
                time = LocalTime.parse(splitString[1].trim(), timeFormatter);
            } else if (this.isValidDate(splitString[1]) && this.isValidTime(splitString[0])) {
                date = LocalDate.parse(splitString[1].trim(), dateFormatter);
                time = LocalTime.parse(splitString[0].trim(), timeFormatter);
            } else {
                throw new IllegalArgumentException("Error! DateTime format should be dd-MM-yyyy HH:mm");
            }
        } else if (splitString.length == 1) {
            if (this.isValidDate(splitString[0])) {
                date = LocalDate.parse(splitString[0].trim(), dateFormatter);
            } else if (this.isValidTime(splitString[0])) {
                time = LocalTime.parse(splitString[0].trim(), timeFormatter);
            } else {
                throw new IllegalArgumentException("Error! DateTime format should be dd-MM-yyyy HH:mm");
            }
        } else {
            throw new IllegalArgumentException("Error! DateTime format should be dd-MM-yyyy HH:mm");
        }

        return new Item(description, date, time);
    }

    /**
     * Add an item in list as a 'Deadline' item
     * Throws Exception if unable to create new Item to store in tasklist
     * After Parser returns a command, use command.execute() to run this function
     * @param tasks list of tasks
     * @param ui User Interface
     * @param storage Deals with storing information to hard disk
     * @throws DuckException Self-defined Exception Class which identifies Error
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DuckException {
        if (byDatetimePos != -1 && startDatetimePos == -1 && endDatetimePos == -1) {
            String byDatetime = this.subCommand.substring(byDatetimePos + 2);
            String description = this.subCommand.substring(0, byDatetimePos);
            if (description.isBlank() || byDatetime.isBlank()) { // check if by or description field are blank
                throw new DuckException("ERROR! Description / Deadline of Task cannot be empty");
            } else {
                try {
                    String result = tasks.addItem(this.generateDeadlineItem(description.trim(), byDatetime.trim()));
                    Item newItem = tasks.getItem(tasks.size() - 1);
                    storage.addToFile(newItem.toStringFile() + '\n');
                    ui.showOperationOutput(result);
                } catch (IllegalArgumentException wrongFormat) {
                    throw new DuckException("Error! DateTime format should be dd-MM-yyyy HH:mm");
                }
            }
        } else { // error if (by) does not appear of if (start) or (end) are in user input
            throw new DuckException("ERROR! Deadline task must have a Deadline (keyword: by). "
                    + "It also should not have a start or end date");
        }
    }
}
