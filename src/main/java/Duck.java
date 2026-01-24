import java.io.IOException;
import java.util.Scanner;

public class Duck {
    public static void main(String[] args) {
        System.out.println("-------------------------------------------------------------");
        System.out.println("Quack! I'm Duck. \nWhat can I do for you?");
        System.out.println("-------------------------------------------------------------");

        NewList MasterList = new NewList();
        Scanner new_object = new Scanner(System.in);
        String command = new_object.nextLine();
        while (!command.contains("bye")) {
            if (command.contains("list")) {
                System.out.println("\tHere are the tasks in your list: ");
                System.out.println(MasterList.toString());
            } else {
                if (command.contains("mark") || command.contains("unmark")) {
                    String int_str = command.replaceAll("[^0-9]", "");
                    int item_num = Integer.parseInt(int_str);
                    try {
                        if (command.contains("unmark")) {    //UNMARK
                            MasterList.markUnmarkItem(false, item_num);
                        } else {                             // MARK
                            MasterList.markUnmarkItem(true, item_num);
                        }
                    } catch(IllegalArgumentException ex) {
                        if (ex.getMessage().contains("Not")) {
                            System.out.println("\tItem already marked as not done!");
                        } else {
                            System.out.println("\tItem already marked as done!");
                        }
                    } catch(IndexOutOfBoundsException ex2) {
                        System.out.println("\tInvalid Item Number");
                    }
                } else {
                    MasterList.addItem(new Item(command));
                }
            }
            command = new_object.nextLine();
        }
        System.out.println("\t-------------------------------------------------------------");
        System.out.println("\tBye Quack! Hope to see you again soon!");
        System.out.println("\t-------------------------------------------------------------");
    }
}
