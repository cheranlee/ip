import java.util.Scanner;

public class Ui {
    private Scanner newObject;

    public Ui(){
        this.newObject = new Scanner(System.in);
    }

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

    public void showLoadingError(){
        System.out.println("-------------------------------------------------------------\n");
        System.out.println("No Previously stored tasks. Creating New TaskList");
        System.out.println("-------------------------------------------------------------\n");
    }

    public String readCommand(){
        System.out.println("-------------------------------------------------------------\n");
        System.out.println("Enter Command:");
        String fullCommand = this.newObject.nextLine();
        fullCommand = fullCommand.toLowerCase();
        System.out.println("-------------------------------------------------------------\n");
        return fullCommand;
    }

    public void showBye(){
        System.out.println("\t-------------------------------------------------------------");
        System.out.println("\tBye Quack! Hope to see you again soon!");
        System.out.println("\t-------------------------------------------------------------");
    }

    public void showError(String error){
         System.out.println(error);
    }

    public void showList(String string){
         System.out.println(string);
    }
}
