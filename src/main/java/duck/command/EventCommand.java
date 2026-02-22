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
 * Class created by Parser when user input = 'event'.
 */
public class EventCommand extends Command {
    private static final String INVALID_FORMAT = "DateTime format should be dd-MM-yyyy HH:mm";
    private int byDatetimePos;
    private int startDatetimePos;
    private int endDatetimePos;
    private String argument;

    /**
     * Constructor class for EventCommand.
     *
     * @param byDatetimePos Index of 'by' keyword [equal to -1 for event task].
     * @param startDatetimePos Index of 'start' keyword [should not be equal to -1 for event task].
     * @param endDatetimePos Index of 'end' keyword [should not be equal to -1 for event task].
     * @param argument User input without 'event' keyword
     */
    public EventCommand(int byDatetimePos, int startDatetimePos, int endDatetimePos, String argument) {
        this.byDatetimePos = byDatetimePos;
        this.startDatetimePos = startDatetimePos;
        this.endDatetimePos = endDatetimePos;
        this.argument = argument;
    }

    private record ParsedDateTime(LocalDate date, LocalTime time) {
    }

    /**
     * Parse Datetime String.
     *
     * @param datetime String representing Date and Time.
     * @return Record with LocalDate and LocalTime.
     * @throws DuckException throws exception.
     */
    private ParsedDateTime parseDateTime(String datetime) throws DuckException {
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

        return new ParsedDateTime(date, time);
    }


    /**
     * Instantiate new task entry if task is an Event.
     * Error Handling: Checks if End Datetime > Start Datetime.
     *
     * e.g. In 'event school start 24-02-2026 08:00 end 24-02-2025 13:00'
     * @param description 'school'
     * @param datetimeOne '24-02-2026 08:00'
     * @param datetimeTwo '24-02-2025 13:00'
     * @return Item
     */
    private Item generateEventItem(String description, String datetimeOne, String datetimeTwo) throws DuckException {
        ParsedDateTime start = parseDateTime(datetimeOne);
        ParsedDateTime end = parseDateTime(datetimeTwo);
        LocalDate startDate = start.date;
        LocalTime startTime = start.time;
        LocalDate endDate = end.date;
        LocalTime endTime = end.time;

        if (startDate == null || endDate == null) {
            startDate = (endDate == null) ? startDate : endDate;
            endDate = (startDate == null) ? endDate : startDate;
        }
        if (startDate != null && endDate != null) {
            if (startDate.isAfter(endDate)) {
                throw new DuckException("Start Date cannot be after End Date");
            } else if (startDate.isEqual(endDate)) {
                if (startTime == null || endTime == null) {
                    throw new DuckException("Time Info Missing");
                } else if (startTime.isAfter(endTime)) {
                    throw new DuckException("Start Time cannot be after End Time");
                }
            }
        } else {
            if (startTime.isAfter(endTime)) {
                throw new DuckException("Start Time cannot be after End Time");
            }
        }
        return new Item(description, startDate, startTime, endDate, endTime);
    }

    /**
     * Add an item in tasks (TaskList) as an 'Event' item.
     * Throws exception if unable to create new Item to store in tasklist
     *
     * @param tasks List of tasks.
     * @param ui User Interface.
     * @param storage Deals with storing information to hard disk.
     * @throws DuckException Self-defined Exception Class which identifies Error.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DuckException {
        if (byDatetimePos == -1 && startDatetimePos != -1 && endDatetimePos != -1) {
            String startDatetime = this.argument.substring(startDatetimePos + 5, endDatetimePos - 1);
            String endDatetime = this.argument.substring(endDatetimePos + 3);
            String description = this.argument.substring(0, startDatetimePos);
            // check if description / start / end field are blank
            if (startDatetime.isBlank() || endDatetime.isBlank() || description.isBlank()) {
                throw new ParserException("Description / End / Start cannot be empty");
            } else {
                Item newItem = this.generateEventItem(description.trim(), startDatetime.trim(), endDatetime.trim());
                String response = tasks.addItem(newItem);
                storage.addToFile(newItem.toStringFile());
                this.setDuckResponse(ui.showOperationOutput(response));
                this.setCommandType(CommandType.Event);
            }
        } else { // error if (by) appears or if (start) or (end) are not in the user input
            throw new ParserException("Event task must have a start and end date (keywords: start, end). "
                    + "It also should not have a deadline (keyword: by).");
        }
    }
}
