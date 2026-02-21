package duck.userinteraction;

import duck.exception.ParserException;
import duck.exception.StorageException;

/**
 * User Interface Class. Outputs messages to be printed on console.
 */
public class Ui {
    /**
    * Print Instructions for user.
    */
    public String printInfo() {
        return "Command List:\n"
                + "1. list : Print out task list\n"
                + "2. mark X : Mark task X (integer) as done\n"
                + "3. unmark X : Mark task X (integer) as not done\n"
                + "4. todo task : Add tasks without any date/time attached to it\n"
                + "5. deadline task by DATE TIME: Add task that needs to be done by a specific date/time\n"
                + "6. event task start DATE1 TIME1 end DATE2 TIME2 : Add task that start"
                + " and end at specific dates/times\n"
                + "7. delete X : Delete task X (integer) from list\n"
                + "8. bye : End the Program\n"
                + "DATE format: DD-MM-YYYY ; TIME format: HH:MM (24-hr clock)\n"
                + "May input 1) TIME ONLY ; 2) DATE ONLY ; 3) TIME & DATE\n";
    }

    /**
     * Message shown when Exiting Program.
     */
    public String showBye() {
        return "Bye Quack! Hope to see you again soon!";
    }

    /**
     * Print Error Message.
     *
     * @param error Error Message.
     */
    public String showError(Exception error) {
        if (error instanceof StorageException) {
            return "[StorageError] : " + error.getMessage();
        } else if (error instanceof ParserException) {
            return "[ParserError] : " + error.getMessage();
        } else {
            return "[DuckError] : " + error.getMessage();
        }
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
     * @param string list of 'find' results from FindCommand.execute()
     */
    public String showWord(String string) {
        return "Here are the matching tasks in your list: \n" + string;
    }

}
