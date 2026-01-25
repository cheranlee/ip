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
                    } catch (IllegalArgumentException ex) {
                        if (ex.getMessage().contains("Not")) {
                            System.out.println("\tItem already marked as not done!");
                        } else {
                            System.out.println("\tItem already marked as done!");
                        }
                    } catch (IndexOutOfBoundsException ex2) {
                        System.out.println("\tInvalid Item Number");
                    }
                } else {
                    command = command.trim();
                    int space_pos = command.indexOf(" ");
                    String sub_command = command.substring(space_pos + 1);
                    sub_command = sub_command.trim();
                    if (command.contains("todo")) {  // no date or time attached
                        MasterList.addItem(new Item(sub_command, TaskType.ToDos));
                    } else {
                        if (command.contains("deadline")) {  // /by date & time
                            int by_datetime_pos = sub_command.indexOf("by ");
                            String by_datetime = sub_command.substring(by_datetime_pos + 3);
                            String description = sub_command.substring(0, by_datetime_pos);
                            String new_string = description + " (by: " + by_datetime + ")";
                            MasterList.addItem(new Item(new_string, TaskType.Deadlines));
                        } else {
                            if (command.contains("event")) {   // /start /end date & time
                                int start_datetime_pos = sub_command.indexOf("start ");
                                int end_datetime_pos = sub_command.indexOf("end ");
                                String start_datetime = sub_command.substring(start_datetime_pos + 6, end_datetime_pos-1);
                                String end_datetime = sub_command.substring(end_datetime_pos + 4);
                                String description = sub_command.substring(0, start_datetime_pos);
                                String new_string = description + " (start: " +  start_datetime + ") (end: " + end_datetime + ")";
                                MasterList.addItem(new Item(new_string, TaskType.Events));
                            }
                        }
                    }
                }
            }
            command = new_object.nextLine();
        }
        System.out.println("\t-------------------------------------------------------------");
        System.out.println("\tBye Quack! Hope to see you again soon!");
        System.out.println("\t-------------------------------------------------------------");
    }
}
