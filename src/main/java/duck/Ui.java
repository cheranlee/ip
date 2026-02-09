package duck;

/**
 * User Interface Class
 */
public class Ui {

    /**
     * Welcome message shown upon startup
     */
    public String showWelcome() {
        String totalStr = "";
        totalStr = totalStr + "Quack! I'm Duck.\nWhat can I do for you?";
        return totalStr;
    }

    /**
    * Print Instructions for user
    */
    public String printInfo() {
        String totalStr = "";
        totalStr = totalStr + "Command List:\n";
        totalStr = totalStr + "1. list : Print out task list\n";
        totalStr = totalStr + "2. mark X : Mark task X (integer) as done\n";
        totalStr = totalStr + "3. unmark X : Mark task X (integer) as not done\n";
        totalStr = totalStr + "4. todo task : Add tasks without any date/time attached to it\n";
        totalStr = totalStr + "5. deadline task by DATE TIME: Add task that needs to be done by a specific date/time\n";
        totalStr = totalStr + "6. event task start DATE1 TIME1 end DATE2 TIME2 : Add task that start"
                + " and end at specific dates/times\n";
        totalStr = totalStr + "7. delete X : Delete task X (integer) from list\n";
        totalStr = totalStr + "8. bye : End the Program\n";
        totalStr = totalStr + "DATE format: DD-MM-YYYY ; TIME format: HH:MM (24-hr clock)\n";
        totalStr = totalStr + "May input 1) TIME ONLY ; 2) DATE ONLY ; 3) TIME & DATE\n";
        return totalStr;
    }

    /**
     * No previously stored data in Hard Disk
     */
    public String showLoadingError() {
        String totalStr = "";
        totalStr = totalStr + "No Previously stored tasks. Creating New TaskList";
        return totalStr;
    }

    /**
     * Message shown when Exiting Program
     */
    public String showBye() {
        String totalStr = "";
        totalStr = totalStr + "Bye Quack! Hope to see you again soon!";
        return totalStr;
    }

    /**
     * Print Error Message
     * @param error String --> usually e.getMessage() ; where e = DuckException;
     */
    public String showError(String error) {
        return "Error: " + error;
    }

    /**
     * Print List of Items in tasklist
     * @param string tasklist.toString()
     */
    public String showList(String string) {
        return string;
    }

    /**
     * Print Output of TaskList Operation
     * @param string output
     */
    public String showOperationOutput(String string) {
        return string;
    }

    /**
     * Wrapper for printing list of results when 'find' keyword is used
     * @param string list of 'find' results from FindCommand.execute()
     */
    public String showWord(String string) {
        String totalStr = "";
        totalStr = totalStr + "Here are the matching tasks in your list: \n";
        totalStr = totalStr + string;
        return totalStr;
    }

    /**
     * Wrapper for printing motivational quote
     * @param string motivational quote from CheerCommand.execute()
     */
    public String showCheer(String string) {
        return string;
    }
}
