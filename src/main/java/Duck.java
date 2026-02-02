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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;
import java.nio.file.Paths;
import java.util.List;

public class Duck {
<<<<<<< Updated upstream
=======
    public static String home = System.getProperty("user.dir");
    public static Path folder_path = Paths.get(home, "data");
    public static Path file_path = folder_path.resolve("duck.txt");

    /**
     * Create Directory and File to store tasks in Hard Disk
     * @param MasterList
     */
    private static void onStartup(NewList MasterList) {
        // check if file exists
        boolean directoryExists = Files.exists(folder_path);
        if (!directoryExists) {
            // directory does not exist -- create folder
            try {
                Files.createDirectories(folder_path);
            } catch (IOException folderError) {
                System.out.println("Unable to create directory: " + folderError.getMessage());
            }
        }
        boolean fileExists = Files.exists(file_path);
        if (!fileExists) {
            // file does not exist -- create file
            try {
                System.out.println("CREATING FILE");
                Files.createFile((file_path));
            } catch (IOException fileError) {
                System.out.println("Unable to create file: " + fileError.getMessage());
            }
        }
        else {
            // file exists  -- load data upon startup
            loadData(MasterList);
        }
        System.out.println("SETUP COMPLETE -- DUCK.TXT file in data src/main/data");
    }

    /**
     * Loads existing data in Hard Disk upon startup
     * @param MasterList
     */
    private static void loadData(NewList MasterList){
       try {
           List<String> lines = Files.readAllLines(file_path);
           int count = 0;
           for (String line : lines) {
               String[] splitString = line.split("\\s\\|\\s");
               String taskType_char = splitString[0].trim();
               String done_char = splitString[1].trim();
               String description = splitString[2].trim();

               switch (taskType_char) {
                   case "T" -> MasterList.addItem(new Item(description, TaskType.ToDos));
                   case "D" -> MasterList.addItem(new Item(description, TaskType.Deadlines));
                   case "E" -> MasterList.addItem(new Item(description, TaskType.Events));
               }

               if (done_char.equals("1")) {
                   MasterList.markUnmarkItem(true, count);
               }
               count = count + 1;
           }
       } catch (IOException loadError) {
           System.out.println("Unable to read existing file: " + loadError.getMessage());
       }
    }

>>>>>>> Stashed changes
    /**
     * Request user input and process request as Todo, Deadline or Event
     * Mark/Unmark an item as done
     * Allow deletion of item
     */
<<<<<<< Updated upstream
    public static void main(String[] args) {
        System.out.println("-------------------------------------------------------------");
        System.out.println("Quack! I'm Duck.\nWhat can I do for you?");
        System.out.println("-------------------------------------------------------------");
=======
    private static void printInfo(){
>>>>>>> Stashed changes
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

<<<<<<< Updated upstream
        NewList MasterList = new NewList();
=======
    }

    /**
     * Helper Function to add new task entry to hard disk
     * Called by todoMethod, deadlineMethod, eventMethod
     * @param content
     */
    private static void addToFile(String content){
        try {
            // Write content to file
            Files.writeString(file_path, content, StandardOpenOption.APPEND);
        } catch (IOException appendError) {
            System.out.println("Unable to append to file: " + appendError.getMessage());
        }
    }

    /**
     * Helper function to delete task entry from hard disk
     * Called by deleteItemMethod(command, MasterList)
     * @param lineNumber
     */
    private static void deleteFromFile(int lineNumber){
        try {
            List<String> lines = Files.readAllLines(file_path);
            lines.remove(lineNumber);
            String totalStr = "";
            for (String line: lines) {
                totalStr = totalStr + line + '\n';
            }
            Files.writeString(file_path, totalStr);   // Overwrites by default
        } catch (IOException deleteError) {
            System.out.println("Unable to delete from file: " + deleteError.getMessage());
        }
    }

    /**
     * Helper function to edit (mark / unmark task as done) a task entry in hard disk
     * Called by markUnmarkItemMethod(command, MasterList)
     * @param linNumber
     * @param editedEntry
     */
    private static void editFile(int linNumber, String editedEntry){
        try {
            List<String> lines = Files.readAllLines(file_path);
            lines.set(linNumber, editedEntry);
            String totalStr = "";
            for (String line: lines) {
                totalStr = totalStr + line + '\n';
            }
            Files.writeString(file_path, totalStr);
        } catch (IOException editError) {
            System.out.println("Unable to edit file: " + editError.getMessage());
        }
    }

    /**
     * Helper function to mark and unmark a task entry as done
     * Called by main function when user inputs mark / unmark keyword
     * @param command
     * @param MasterList
     */
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
                        String editedItemString = MasterList.markUnmarkItem(!command.contains("unmark"), idx);
                        editFile(idx, editedItemString);
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

    /**
     * Helper function to delete a task entry from list
     * Called by main function when user inputs delete keyword
     * @param command
     * @param MasterList
     */
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
                    deleteFromFile(idx);
                } else {
                    System.out.println("ERROR! Item Number out of range");
                }
            } catch (NumberFormatException ex3) {
                System.out.println("ERROR! Index not a valid number");
            }
        }
    }

    /**
     * Helper function to add an item in list as a 'ToDo' item
     * Called by main function when user inputs todo keyword
     * @param by_datetime_pos
     * @param start_datetime_pos
     * @param end_datetime_pos
     * @param sub_command
     * @param MasterList
     */
    private static void todoMethod(int by_datetime_pos, int start_datetime_pos, int end_datetime_pos, String sub_command, NewList MasterList){
        if (by_datetime_pos == -1 && start_datetime_pos == -1 && end_datetime_pos == -1) {
            MasterList.addItem(new Item(sub_command, TaskType.ToDos));
            Item newItem = MasterList.getItem(MasterList.size()-1);
            addToFile(newItem.toStringFile() + '\n');
        } else {  // error if (by), (start), (end) are in user input
            System.out.println("ERROR! Todo task should not have deadline, start or end date");
        }
    }

    /**
     * Helper function to add an item in list as a 'Deadline' item
     * Called by main function when user inputs deadline keyword
     * @param by_datetime_pos
     * @param start_datetime_pos
     * @param end_datetime_pos
     * @param sub_command
     * @param MasterList
     */
    private static void deadlineMethod(int by_datetime_pos, int start_datetime_pos, int end_datetime_pos, String sub_command, NewList MasterList){
        if (by_datetime_pos != -1 && start_datetime_pos == -1 && end_datetime_pos == -1) {
            String by_datetime = sub_command.substring(by_datetime_pos + 2);
            String description = sub_command.substring(0, by_datetime_pos);
            if (description.isBlank() || by_datetime.isBlank()) {  // check if by or description field are blank
                System.out.println("ERROR! Description / Deadline of Task cannot be empty");
            } else {
                String new_string = description + " (by: " + by_datetime + ")";
                MasterList.addItem(new Item(new_string, TaskType.Deadlines));
                Item newItem = MasterList.getItem(MasterList.size()-1);
                addToFile(newItem.toStringFile() + '\n');
            }
        } else {  // error if (by) does not appear of if (start) or (end) are in user input
            System.out.println("ERROR! Deadline task must have a Deadline (keyword: by). It also should not have a start or end date");
        }
    }

    /**
     * Helper function to add an item in list as a 'Event' item
     * Called by main function when user inputs Event keyword
     * @param by_datetime_pos
     * @param start_datetime_pos
     * @param end_datetime_pos
     * @param sub_command
     * @param MasterList
     */
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
                Item newItem = MasterList.getItem(MasterList.size()-1);
                addToFile(newItem.toStringFile() + '\n');
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
        NewList MasterList = new NewList();
        onStartup(MasterList);

        System.out.println("-------------------------------------------------------------");
        System.out.println("Quack! I'm Duck.\nWhat can I do for you?");
        System.out.println("-------------------------------------------------------------");
        printInfo();

>>>>>>> Stashed changes
        Scanner new_object = new Scanner(System.in);
        String command = new_object.nextLine();
        command = command.toLowerCase();
        while (!command.contains("bye")) {
            if (command.contains("list")) {
                System.out.println(MasterList.toString());
            } else if (command.contains("mark") || command.contains("unmark")) {
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
                                if (command.contains("unmark")) {    //UNMARK
                                    MasterList.markUnmarkItem(false, idx);
                                } else {                             // MARK
                                    MasterList.markUnmarkItem(true, idx);
                                }
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
                        if (by_datetime_pos == -1 && start_datetime_pos == -1 && end_datetime_pos == -1) {
                            MasterList.addItem(new Item(sub_command, TaskType.ToDos));
                        } else {  // error if (by), (start), (end) are in user input
                            System.out.println("ERROR! Todo task should not have deadline, start or end date");
                        }
                    } else if (command.contains("deadline")) {  // (by) date & time
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
                    } else if (command.contains("event")) {   // (start) (end) date & time
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
                }
            } else if (command.contains("delete")){
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
            } else {
                System.out.println("ERROR! Invalid Command. Use one of the following commands:");
                System.out.println("-------------------------------------------------------------");
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
            command = new_object.nextLine();
            command = command.toLowerCase();
        }
        System.out.println("\t-------------------------------------------------------------");
        System.out.println("\tBye Quack! Hope to see you again soon!");
        System.out.println("\t-------------------------------------------------------------");
    }
}

