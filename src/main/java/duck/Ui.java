package duck;

import java.util.Scanner;

import duck.command.ByeCommand;
import duck.command.Command;
import duck.command.DeadlineCommand;
import duck.command.DeleteCommand;
import duck.command.EventCommand;
import duck.command.ListCommand;
import duck.command.MarkUnmarkCommand;
import duck.command.TodoCommand;

/**
 * User Interface Class
 */
public class Ui {
    private Scanner newObject;

    /**
     * Constructor Class --> Initiate Scanner Object to obtain User Input
     */
    public Ui(){
        this.newObject = new Scanner(System.in);
    }

    /**
     * Welcome message shown upon startup
     */
    public void showWelcome(){
        System.out.println("-------------------------------------------------------------");
        System.out.println("Quack! I'm Duck.\nWhat can I do for you?");
        System.out.println("-------------------------------------------------------------");
    }

    /**
     * Print Instructions for user
     */
     public void printInfo(){
        System.out.println("Command List:");
        System.out.println("1. list : Print out task list");
        System.out.println("2. mark X : Mark task X (integer) as done");
        System.out.println("3. unmark X : Mark task X (integer) as not done");
        System.out.println("4. todo task : Add tasks without any date/time attached to it");
        System.out.println("5. deadline task by DATE TIME: Add task that needs to be done by a specific date/time");
        System.out.println("6. event task start DATE1 TIME1 end DATE2 TIME2 : Add task that start and end at specific dates/times");
        System.out.println("7. delete X : Delete task X (integer) from list");
        System.out.println("8. bye : End the Program");
        System.out.println("DATE format: DD-MM-YYYY \t TIME format: HH:MM (24-hr clock)");
        System.out.println("May input 1) TIME ONLY ; 2) DATE ONLY ; 3) TIME & DATE");
        System.out.println("-------------------------------------------------------------\n");
    }

    /**
     * No previously stored data in Hard Disk
     */
    public void showLoadingError(){
        System.out.println("-------------------------------------------------------------\n");
        System.out.println("No Previously stored tasks. Creating New TaskList");
        System.out.println("-------------------------------------------------------------\n");
    }

    /**
     * Obtain User Input
     * @return User Input
     */
    public String readCommand(){
        System.out.println("-------------------------------------------------------------\n");
        System.out.println("Enter Command:");
        String fullCommand = this.newObject.nextLine();
        fullCommand = fullCommand.toLowerCase();
        System.out.println("-------------------------------------------------------------\n");
        return fullCommand;
    }

    /**
     * Message shown when Exiting Program
     */
    public void showBye(){
        System.out.println("\t-------------------------------------------------------------");
        System.out.println("\tBye Quack! Hope to see you again soon!");
        System.out.println("\t-------------------------------------------------------------");
    }

    /**
     * Print Error Message
     * @param error String --> usually e.getMessage() ; where e = DuckException;
     */
    public void showError(String error){
         System.out.println(error);
    }

    /**
     * Print List of Items in tasklist
     * @param string tasklist.toString()
     */
    public void showList(String string){
         System.out.println(string);
    }

    /**
     * Print Output of TaskList Operation
     * @param string output
     */
    public void showOperationOutput(String string){
        System.out.println("\t-------------------------------------------------------------");
        System.out.println(string);
        System.out.println("\t-------------------------------------------------------------");
    }

    /**
     * Wrapper for printing list of results when 'find' keyword is used
     * @param string list of 'find' results from FindCommand.execute()
     */
    public void showWord(String string){
         System.out.println("\t-------------------------------------------------------------");
         System.out.println("\tHere are the matching tasks in your list: ");
         System.out.println(string);
         System.out.println("\t-------------------------------------------------------------");
    }

    /**
     * Wrapper for printing motivational quote
     * @param string motivational quote from CheerCommand.execute()
     */
    public void showCheer(String string){
         System.out.println("\t-------------------------------------------------------------");
         System.out.println(string);
         System.out.println("\t-------------------------------------------------------------");
    }
}
