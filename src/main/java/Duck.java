/**
 * Error Detection
 * Note: Refer to input.txt in text-ui-test folder to see examples of the following errors
 * 1. Commands are not case sensitive (ie List will be recognised as list)
 * 2. Command not recognised (list/todo/deadline/...)
 * 3. Missing / Wrong keyword for the wrong command (e.g. using start/end for deadline)
 * 4. Missing description / deadline / start & end
 * 5. Mark/Unmark/Delete item number out of range (task index does not exist in list)
 * 6. Mark/Unmark/Delete invalid task number (e.g. mark 2a, mark   , mark -1)
 * 7. Mark task that has been marked (& vice versa)
 */

import java.io.IOException;
import java.util.Scanner;

public class Duck {

    /**
     * Print Instructions for user
     */
    public static void printInfo(){
        System.out.println("Enter any of the following commands:");
        System.out.println("1. list : Print out task list");
        System.out.println("2. mark X : Mark task X (integer) as done");
        System.out.println("3. unmark X : Mark task X (integer) as not done");
        System.out.println("4. todo task : Add tasks without any date/time attached to it");
        System.out.println("5. deadline task by date : Add task that needs to be done by a specific date/time");
        System.out.println("6. event task start date1 end date2 : Add task that start and end at specific dates/times");
        System.out.println("7. delete X : Delete task X (integer) from list");
        System.out.println("8. bye : End the Program");
        System.out.println("-------------------------------------------------------------\n");

    }

    private static void markUnmarkItemMethod(String command, NewList MasterList){
        command = command.trim();
        int space_pos = command.indexOf(" ");
        if (space_pos == -1) {
            System.out.println("ERROR! Mark/Unmark command must have an integer index behind");
        } else {
            String sub_command = command.substring(space_pos + 1);
            sub_command = sub_command.trim();
            try {
                int idx = Integer.parseInt(sub_command);
                idx = idx - 1;
                if (idx < MasterList.size() && idx >= 0) {
                    try {
                        MasterList.markUnmarkItem(!command.contains("unmark"), idx);
                    } catch (IllegalArgumentException ex) {
                        if (ex.getMessage().contains("Not")) {
                            System.out.println("\tItem already marked as not done!");
                        } else {
                            System.out.println("\tItem already marked as done!");
                        }
                    }
                } else {
                    System.out.println("ERROR! Item Number out of range");
                }
            } catch (NumberFormatException ex2) {
                System.out.println("ERROR! Index not a valid number");
            }
        }
    }

    private static void deleteItemMethod(String command, NewList MasterList){
        command = command.trim();
        int space_pos = command.indexOf(" ");
        if (space_pos == -1) {
            System.out.println("ERROR! Delete command must have an integer index behind");
        } else {
            String sub_command = command.substring(space_pos + 1);
            sub_command = sub_command.trim();
            try {
                int idx = Integer.parseInt(sub_command);
                idx = idx - 1;
                if (idx < MasterList.size() && idx >= 0) {
                    MasterList.deleteItem(idx);
                } else {
                    System.out.println("ERROR! Item Number out of range");
                }
            } catch (NumberFormatException ex3) {
                System.out.println("ERROR! Index not a valid number");
            }
        }
    }

    private static void todoMethod(int by_datetime_pos, int start_datetime_pos, int end_datetime_pos, String sub_command, NewList MasterList){
        if (by_datetime_pos == -1 && start_datetime_pos == -1 && end_datetime_pos == -1) {
            MasterList.addItem(new Item(sub_command, TaskType.ToDos));
        } else {  // error if (by), (start), (end) are in user input
            System.out.println("ERROR! Todo task should not have deadline, start or end date");
        }
    }

    private static void deadlineMethod(int by_datetime_pos, int start_datetime_pos, int end_datetime_pos, String sub_command, NewList MasterList){
        if (by_datetime_pos != -1 && start_datetime_pos == -1 && end_datetime_pos == -1) {
            String by_datetime = sub_command.substring(by_datetime_pos + 2);
            String description = sub_command.substring(0, by_datetime_pos);
            if (description.isBlank() || by_datetime.isBlank()) {  // check if by or description field are blank
                System.out.println("ERROR! Description / Deadline of Task cannot be empty");
            } else {
                String new_string = description + " (by: " + by_datetime + ")";
                MasterList.addItem(new Item(new_string, TaskType.Deadlines));
            }
        } else {  // error if (by) does not appear of if (start) or (end) are in user input
            System.out.println("ERROR! Deadline task must have a Deadline (keyword: by). It also should not have a start or end date");
        }
    }

    private static void eventMethod(int by_datetime_pos, int start_datetime_pos, int end_datetime_pos, String sub_command, NewList MasterList){
        if (by_datetime_pos == -1 && start_datetime_pos != -1 && end_datetime_pos != -1) {
            String start_datetime = sub_command.substring(start_datetime_pos + 5, end_datetime_pos - 1);
            String end_datetime = sub_command.substring(end_datetime_pos + 3);
            String description = sub_command.substring(0, start_datetime_pos);
            if (start_datetime.isBlank() || end_datetime.isBlank() || description.isBlank()) {   // check if description / start / end field are blank
                System.out.println("ERROR! Description / End / Start cannot be empty");
            } else {
                String new_string = description + " (start: " + start_datetime + ") (end: " + end_datetime + ")";
                MasterList.addItem(new Item(new_string, TaskType.Events));
            }
        } else { // error if (by) appears or if (start) or (end) are not in the user input
            System.out.println("ERROR! Event task must have a start and end date (keywords: start, end). It also should not have a deadline");
        }
    }

    /**
     * Request user input and process request as Todo, Deadline or Event
     * Mark/Unmark an item as done
     * Allow deletion of item
     */
    public static void main(String[] args) {
        System.out.println("-------------------------------------------------------------");
        System.out.println("Quack! I'm Duck.\nWhat can I do for you?");
        System.out.println("-------------------------------------------------------------");
        printInfo();

        NewList MasterList = new NewList();
        Scanner new_object = new Scanner(System.in);
        String command = new_object.nextLine();
        command = command.toLowerCase();
        while (!command.contains("bye")) {
            if (command.contains("list")) {
                System.out.println(MasterList.toString());
            } else if (command.contains("mark") || command.contains("unmark")) {
                markUnmarkItemMethod(command, MasterList);
            } else if (command.contains("todo") || command.contains("deadline") || command.contains("event")) {
                command = command.trim();
                int space_pos = command.indexOf(" ");
                if (space_pos == -1) {
                    System.out.println("ERROR! Description of Task cannot be empty");  // to catch cases where only todo deadline event are the input
                } else {
                    String sub_command = command.substring(space_pos + 1);
                    sub_command = sub_command.trim();
                    int by_datetime_pos = sub_command.indexOf("by");
                    int start_datetime_pos = sub_command.indexOf("start");
                    int end_datetime_pos = sub_command.indexOf("end");
                    if (command.contains("todo")) {  // no date or time attached
                        todoMethod(by_datetime_pos, start_datetime_pos, end_datetime_pos, sub_command, MasterList);
                    } else if (command.contains("deadline")) {  // (by) date & time
                        deadlineMethod(by_datetime_pos, start_datetime_pos, end_datetime_pos, sub_command, MasterList);
                    } else if (command.contains("event")) {   // (start) (end) date & time
                        eventMethod(by_datetime_pos, start_datetime_pos, end_datetime_pos, sub_command, MasterList);
                    }
                }
            } else if (command.contains("delete")){
                deleteItemMethod(command, MasterList);
            } else {
                System.out.println("ERROR! Invalid Command. Use one of the following commands:");
                System.out.println("-------------------------------------------------------------");
                printInfo();
            }
            command = new_object.nextLine();
            command = command.toLowerCase();
        }
        System.out.println("\t-------------------------------------------------------------");
        System.out.println("\tBye Quack! Hope to see you again soon!");
        System.out.println("\t-------------------------------------------------------------");
    }
}

