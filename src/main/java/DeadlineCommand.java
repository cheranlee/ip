import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DeadlineCommand extends Command{
    private int by_datetime_pos;
    private int start_datetime_pos;
    private int end_datetime_pos;
    private String subCommand;

    public DeadlineCommand(int by_datetime_pos, int start_datetime_pos, int end_datetime_pos, String subCommand){
        this.by_datetime_pos = by_datetime_pos;
        this.start_datetime_pos = start_datetime_pos;
        this.end_datetime_pos = end_datetime_pos;
        this.subCommand = subCommand;
    }

    /**
     * helper function to determine if date input is of a valid format (dd-MM-yyyy)
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
     * helper function to determine if time input is of a valid format (HH:mm)
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

    /**
     * Function to instantiate new task entry if task is a Deadline
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
        }
        return new Item(description, date, time);
    }


    /**
     * Helper function to add an item in list as a 'Deadline' item
     * Called by main function when user inputs deadline keyword
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DuckException{
        if (by_datetime_pos != -1 && start_datetime_pos == -1 && end_datetime_pos == -1) {
            String by_datetime = this.subCommand.substring(by_datetime_pos + 2);
            String description = this.subCommand.substring(0, by_datetime_pos);
            if (description.isBlank() || by_datetime.isBlank()) {  // check if by or description field are blank
                throw new DuckException("ERROR! Description / Deadline of Task cannot be empty");
            } else {
                try {
                    tasks.addItem(this.generateDeadlineItem(description.trim(), by_datetime.trim()));
                    Item newItem = tasks.getItem(tasks.size() - 1);
                    storage.addToFile(newItem.toStringFile() + '\n');
                } catch (IllegalArgumentException wrongFormat) {
                    throw new DuckException("Error! DateTime format should be dd-MM-yyyy HH:mm");
                }
            }
        } else {  // error if (by) does not appear of if (start) or (end) are in user input
            throw new DuckException("ERROR! Deadline task must have a Deadline (keyword: by). It also should not have a start or end date");
        }
    }
}
