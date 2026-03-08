package duck.userinteraction;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * User Interface Class. Outputs messages to be printed on console.
 */
public class Ui {

    /**
    * Print Instructions for user.
    */
    public String printInfo() {
        return "Command List: "
                + "list/pond  mark/unmark  todo  deadline  event  delete  cheer"
                + "find  datesearch  bye  help\n"
                + "DATE: dd-MM-yyyy       time: HH:mm (24-hr)\n\n"
                + "User Guide: https://cheranlee.github.io/ip/";
    }

    /**
     * Message shown when Exiting Program.
     */
    public String showBye() {
        return "Happy paddling, see you again soon!";
    }

    /**
     * Print Error Message.
     *
     * @param error Error Message.
     */
    public String showError(Exception error) {
        return error.getMessage();
    }

    /**
     * Print Output of TaskList Operation.
     *
     * @param string output
     */
    public String showOperationOutput(String string) {
        return string;
    }

    /**
     * Wrapper for printing list of results when 'find' keyword is used.
     *
     * @param foundResult list of 'find' results from FindCommand.execute()
     */
    public String showWord(List<String> foundResult) {
        return "Here are the matching tasks in your list:\n"
                + IntStream.range(0, foundResult.size())
                .mapToObj(i -> (i + 1) + ". " + foundResult.get(i))
                .collect(Collectors.joining("\n"));
    }

    /**
     * Wrapper for printing list of results when 'datesearch' keyword is used.
     *
     * @param date LocalDate.
     * @param tasksOnDate Tasks that fall on date. List of Strings.
     * @param tasksBefDate Tasks that fall before date. List of Strings.
     * @return output to be printed in GUI.
     */
    public String showDateSearch(LocalDate date, List<String> tasksOnDate, List<String> tasksBefDate) {
        String dateString = date.format(DateTimeFormatter.ofPattern("MMM dd yyyy"));
        System.out.println(tasksOnDate);
        String onString = tasksOnDate.isEmpty() ? "No Tasks due!"
                : IntStream.range(0, tasksOnDate.size())
                .mapToObj(i -> (i + 1) + ". " + tasksOnDate.get(i))
                .collect(Collectors.joining("\n"));
        String befString = tasksBefDate.isEmpty() ? "No Tasks due!"
                : IntStream.range(0, tasksBefDate.size())
                .mapToObj(i -> (i + 1) + ". " + tasksBefDate.get(i))
                .collect(Collectors.joining("\n"));
        return "Here are the tasks due on " + dateString + ":\n"
                + onString + "\n" + "\n"
                + "Here are the tasks due before " + dateString + ":\n"
                + befString;
    }
}
