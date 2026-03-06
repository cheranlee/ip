package duck.command;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import duck.exception.DuckException;
import duck.storage.Storage;
import duck.tasks.TaskList;
import duck.userinteraction.Ui;


/**
 * Class created by Parser when user input = 'date'.
 */
public class DateSearchCommand extends Command {
    private String dateString;

    /**
     * Constructor Class for DateCommand.
     *
     * @param date String in DD-MM-YYYY format
     */
    public DateSearchCommand(String date) {
        this.dateString = date;
    }

    private LocalDate generateDate(String dateString) throws DuckException {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate date = null;
        try {
            date = LocalDate.parse(dateString.trim(), dateFormatter);
        } catch (DateTimeParseException formatError) {
            throw new DuckException("DateTime format should be dd-MM-yyyy");
        }
        return date;
    }

    /**
     * Store Date String as a LocalDate Object in Java.
     * Find Tasks that fall on that Date.
     * Find Tasks that fall before that Date.
     *
     * @param tasks List of tasks.
     * @param ui User Interface.
     * @param storage Deals with storing information to hard disk.
     * @throws DuckException Exception, such as missing date or invalid date format.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DuckException {
        LocalDate date = generateDate(this.dateString);
        List<String> tasksOnDate = tasks.findOnDate(date);
        List<String> tasksBefDate = tasks.findBefDate(date);
        this.setDuckResponse(ui.showDateSearch(date, tasksOnDate, tasksBefDate));
        this.setCommandType(CommandType.Find);
    }
}
