package duck.command;

import duck.Duck;
import duck.DuckException;
import duck.Item;
import duck.Parser;
import duck.Storage;
import duck.TaskList;
import duck.TaskType;
import duck.Ui;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Class created by Parser when user input = 'event'
 */
public class EventCommand extends Command{
    private int byDatetimePos;
    private int startDatetimePos;
    private int endDatetimePos;
    private String subCommand;

    /**
     * Constructor class for EventCommand
     * @param byDatetimePos index of 'by' keyword [equal to -1 for event task]
     * @param startDatetimePos index of 'start' keyword [should not be equal to -1 for event task]
     * @param endDatetimePos index of 'end' keyword [should not be equal to -1 for event task]
     * @param subCommand user input without 'event' keyword
     */
    public EventCommand(int byDatetimePos, int startDatetimePos, int endDatetimePos, String subCommand){
        this.byDatetimePos = byDatetimePos;
        this.startDatetimePos = startDatetimePos;
        this.endDatetimePos = endDatetimePos;
        this.subCommand = subCommand;
    }

    /**
     * Helper function to determine if date input is of a valid format (dd-MM-yyyy)
     * @param input date input
     * @return boolean
     */
    public boolean isValidDate(String input){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        try {
            LocalDate.parse(input, formatter);
            return true;
        } catch (DateTimeParseException dateFormatError) {
            return false;
        }
    }

    /**
     * Helper function to determine if time input is of a valid format (HH:mm)
     * @param input time input
     * @return boolean
     */
    public boolean isValidTime(String input){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        try {
            LocalTime.parse(input, formatter);
            return true;
        } catch (DateTimeParseException timeFormatError) {
            return false;
        }
    }


    /** Function to instantiate new task entry if task is an Event
     * e.g. In 'event school start 24-02-2026 08:00 end 24-02-2025 13:00',
     * @param description 'school'
     * @param datetimeOne '24-02-2026 08:00'
     * @param datetimeTwo '24-02-2025 13:00'
     * @return Item
     */
    public Item generateEventItem(String description, String datetimeOne, String datetimeTwo){
        String[] splitString = datetimeOne.split("\\s");
        LocalDate dateOne = null;
        LocalTime timeOne = null;
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        if (splitString.length == 2) {
            if (this.isValidDate(splitString[0]) && this.isValidTime(splitString[1])) {
                dateOne = LocalDate.parse(splitString[0].trim(), dateFormatter);
                timeOne = LocalTime.parse(splitString[1].trim(), timeFormatter);
            } else if (this.isValidDate(splitString[1]) && this.isValidTime(splitString[0])) {
                dateOne = LocalDate.parse(splitString[1].trim(), dateFormatter);
                timeOne = LocalTime.parse(splitString[0].trim(), timeFormatter);
            } else {
                throw new IllegalArgumentException("Error! DateTime format should be dd-MM-yyyy HH:mm");
            }
        } else if (splitString.length == 1) {
            if (this.isValidDate(splitString[0])) {
                dateOne = LocalDate.parse(splitString[0].trim(), dateFormatter);
            } else if (this.isValidTime(splitString[0])) {
                timeOne = LocalTime.parse(splitString[0].trim(),timeFormatter);
            } else {
                throw new IllegalArgumentException("Error! DateTime format should be dd-MM-yyyy HH:mm");
            }
        } else {
            throw new IllegalArgumentException("Error! DateTime format should be dd-MM-yyyy HH:mm");
        }
            String[] splitStringTwo = datetimeTwo.split("\\s");
        LocalDate dateTwo = null;
        LocalTime timeTwo = null;
        if (splitStringTwo.length == 2) {
            if (this.isValidDate(splitStringTwo[0]) && this.isValidTime(splitStringTwo[1])) {
                dateTwo = LocalDate.parse(splitStringTwo[0].trim(), dateFormatter);
                timeTwo = LocalTime.parse(splitStringTwo[1].trim(), timeFormatter);
            } else if (this.isValidDate(splitStringTwo[1]) && this.isValidTime(splitStringTwo[0])) {
                dateTwo = LocalDate.parse(splitStringTwo[1].trim(), dateFormatter);
                timeTwo = LocalTime.parse(splitStringTwo[0].trim(), timeFormatter);
            } else {
                throw new IllegalArgumentException("Error! DateTime format should be dd-MM-yyyy HH:mm");
            }
        } else if (splitStringTwo.length == 1) {
            if (this.isValidDate(splitStringTwo[0])) {
                dateTwo = LocalDate.parse(splitStringTwo[0].trim(), dateFormatter);
            } else if (this.isValidTime(splitStringTwo[0])) {
                timeTwo = LocalTime.parse(splitStringTwo[0].trim(), timeFormatter);
            } else {
                throw new IllegalArgumentException("Error! DateTime format should be dd-MM-yyyy HH:mm");
            }
        } else {
            throw new IllegalArgumentException("Error! DateTime format should be dd-MM-yyyy HH:mm");
        }
            if (dateOne == null || dateTwo == null) {
            dateOne = (dateTwo == null) ? dateOne : dateTwo;
            dateTwo = (dateOne == null) ? dateTwo : dateOne;
        }
        if (dateOne != null && dateTwo != null) {
            if (dateOne.isAfter(dateTwo)) {
                throw new IllegalArgumentException("Error! Start Date cannot be after End Date");
            } else if (dateOne.isEqual(dateTwo)) {
                if (timeOne == null || timeTwo == null) {
                    throw new IllegalArgumentException("Error! Time Info Missing");
                } else {
                    if (timeOne.isAfter(timeTwo)) {
                        throw new IllegalArgumentException("Error! Start Time cannot be after End Time");
                    }
                }
            }
        } else if (dateOne == null && dateTwo == null) {
            if (timeOne.isAfter(timeTwo)) {
                throw new IllegalArgumentException("Error! Start Time cannot be after End Time");
            }
        }
        return new Item(description, dateOne, timeOne, dateTwo, timeTwo);
    }

    /**
     * Add an Item in list as an 'event' item
     * Throws exception if unable to create new Item to store in tasklist
     * After Parser returns a command, use command.execute() to run this function
     * @param tasks list of tasks
     * @param ui User Interface
     * @param storage Deals with storing information to hard disk
     * @throws DuckException Self-defined Exception Class which identifies Error
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DuckException{
        if (byDatetimePos == -1 && startDatetimePos != -1 && endDatetimePos != -1) {
            String startDatetime = this.subCommand.substring(startDatetimePos + 5, endDatetimePos - 1);
            String endDatetime = this.subCommand.substring(endDatetimePos + 3);
            String description = this.subCommand.substring(0, startDatetimePos);
            if (startDatetime.isBlank() || endDatetime.isBlank() || description.isBlank()) {   // check if description / start / end field are blank
                System.out.println("ERROR! Description / End / Start cannot be empty");
            } else {
                try {
                    String result = tasks.addItem(generateEventItem(description.trim(), startDatetime.trim(), endDatetime.trim()));
                    Item newItem = tasks.getItem(tasks.size() - 1);
                    storage.addToFile(newItem.toStringFile() + '\n');
                    ui.showOperationOutput(result);
                } catch (IllegalArgumentException datetimeException) {
                    if (datetimeException.getMessage().contains("format")) {
                        throw new DuckException("Error! DateTime format should be dd-MM-yyyy HH:mm");
                    } else if (datetimeException.getMessage().contains("Date cannot be after")) {
                        throw new DuckException("Error! Start Date cannot be after End Date");
                    } else if (datetimeException.getMessage().contains("Missing")) {
                        throw new DuckException("Error! Time Info Missing");
                    } else if (datetimeException.getMessage().contains("Time cannot be after")) {
                        throw new DuckException("Error! Start Time cannot be after End Time");
                    }
                }
            }
        } else { // error if (by) appears or if (start) or (end) are not in the user input
            throw new DuckException("ERROR! Event task must have a start and end date (keywords: start, end). It also should not have a deadline");
        }
    }
}