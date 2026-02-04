/**
 * Error Detection
 * Note: Refer to input.txt in text-ui-test folder to see examples of the following errors
 * 1. Commands are not case sensitive (ie List will be recognised as list)
 * 2. Command not recognised (list/todo/deadline/...)
 * 3. Missing / Wrong keyword for the wrong command (e.g. using start/end for deadline)
 * 4. Missing description / date (for deadline / start & end)
 * 5. Mark/Unmark/Delete item number out of range (task index does not exist in list)
 * 6. Mark/Unmark/Delete invalid task number (e.g. mark 2a, mark   , mark -1)
 * 7. Mark task that has been marked (& vice versa)
 * 8. Date and time format not valid
 * 9. For event, Start Date after End Date
 */

// TAKES IN DD-MM-YYYY prints out MMM dd YYYY

import java.io.IOException;
import java.nio.channels.IllegalChannelGroupException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.IllegalFormatFlagsException;
import java.util.Scanner;
import java.nio.file.Paths;
import java.util.List;

public class Duck {
    public static String home = System.getProperty("user.dir");
    public static Path folder_path = Paths.get(home, "data");
    public static Path file_path = folder_path.resolve("duck.txt");

    /**
     * Create Directory and File to store tasks in Hard Disk
     * @param MasterList list storing all task items
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
     * Stores hard disk data to current sessions's MasterList
     * @param MasterList list storing all task items
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
               DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMM dd yyyy");
               DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");

               switch (taskType_char) {
                   case "T" -> {
                       MasterList.addItem(new Item(description));
                   }
                   case "D" -> {
                       String dateStr = splitString[3].trim();
                       String timeStr = splitString[4].trim();
                       LocalDate date = (dateStr.equals("null")) ? null : LocalDate.parse(dateStr, dateFormatter);
                       LocalTime time = (timeStr.equals("null")) ? null : LocalTime.parse(timeStr, timeFormatter);
                       MasterList.addItem(new Item(description, date, time));
                   }
                   case "E" -> {
                       String dateStrOne = splitString[3].trim();
                       String timeStrOne = splitString[4].trim();
                       String dateStrTwo = splitString[5].trim();
                       String timeStrTwo = splitString[6].trim();
                       LocalDate dateOne = (dateStrOne.equals("null")) ? null : LocalDate.parse(dateStrOne, dateFormatter);
                       LocalTime timeOne = (timeStrOne.equals("null")) ? null : LocalTime.parse(timeStrOne, timeFormatter);
                       LocalDate dateTwo = (dateStrTwo.equals("null")) ? null : LocalDate.parse(dateStrTwo, dateFormatter);
                       LocalTime timeTwo = (timeStrTwo.equals("null")) ? null : LocalTime.parse(timeStrTwo, timeFormatter);
                       MasterList.addItem(new Item(description, dateOne, timeOne, dateTwo, timeTwo));
                   }
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

    /**
     * Print Instructions for user
     */
    private static void printInfo(){
        System.out.println("Enter any of the following commands:");
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
     * Helper Function to add new task entry to hard disk
     * Called by todoMethod, deadlineMethod, eventMethod
     * @param content appends this string to hard disk
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
     * @param lineNumber deletes content at lineNumber from hard disk
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
     * @param lineNumber target lineNumber to change data
     * @param editedEntry new data to replace old data
     */
    private static void editFile(int lineNumber, String editedEntry){
        try {
            List<String> lines = Files.readAllLines(file_path);
            lines.set(lineNumber, editedEntry);
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
     * @param command e.g. mark X (X = item number)
     * @param MasterList list storing all task items
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
     * @param command delete X (X = task number)
     * @param MasterList list storing all task items
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
     * @param by_datetime_pos used for deadline
     * @param start_datetime_pos used for event
     * @param end_datetime_pos used for event
     * @param sub_command e.g. 'borrow book' in 'todo borrow book'
     * @param MasterList list storing all task items
     */
    private static void todoMethod(int by_datetime_pos, int start_datetime_pos, int end_datetime_pos, String sub_command, NewList MasterList){
        if (by_datetime_pos == -1 && start_datetime_pos == -1 && end_datetime_pos == -1) {
            MasterList.addItem(new Item(sub_command));
            Item newItem = MasterList.getItem(MasterList.size()-1);
            addToFile(newItem.toStringFile() + '\n');
        } else {  // error if (by), (start), (end) are in user input
            System.out.println("ERROR! Todo task should not have deadline, start or end date");
        }
    }

    /**
     * helper function to determine if date input is of a valid format (dd-MM-yyyy)
     * @param input date input
     * @return boolean
     */
    private static boolean isValidDate(String input){
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
    private static boolean isValidTime(String input){
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
    private static Item generateDeadlineItem(String description, String datetime){
        String[] splitString = datetime.split("\\s");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalDate date = null;
        LocalTime time = null;
        if (splitString.length == 2) {
            if (isValidDate(splitString[0]) && isValidTime(splitString[1])) {
                date = LocalDate.parse(splitString[0].trim(), dateFormatter);
                time = LocalTime.parse(splitString[1].trim(), timeFormatter);
            } else if (isValidDate(splitString[1]) && isValidTime(splitString[0])) {
                date = LocalDate.parse(splitString[1].trim(), dateFormatter);
                time = LocalTime.parse(splitString[0].trim(), timeFormatter);
            } else {
                throw new IllegalArgumentException("Error! DateTime format should be dd-MM-yyyy HH:mm");
            }
        } else if (splitString.length == 1) {
            if (isValidDate(splitString[0])) {
                date = LocalDate.parse(splitString[0].trim(), dateFormatter);
            } else if (isValidTime(splitString[0])) {
                time = LocalTime.parse(splitString[0].trim(), timeFormatter);
            } else {
                throw new IllegalArgumentException("Error! DateTime format should be dd-MM-yyyy HH:mm");
            }
        }
        return new Item(description, date, time);
    }

    /** Function to instantiate new task entry if task is an Event
     * e.g. In 'event school start 24-02-2026 08:00 end 24-02-2025 13:00',
     * @param description 'school'
     * @param datetimeOne '24-02-2026 08:00'
     * @param datetimeTwo '24-02-2025 13:00'
     * @return Item
     */
    private static Item generateEventItem(String description, String datetimeOne, String datetimeTwo){
        String[] splitString = datetimeOne.split("\\s");
        LocalDate dateOne = null;
        LocalTime timeOne = null;
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        if (splitString.length == 2) {
            if (isValidDate(splitString[0]) && isValidTime(splitString[1])) {
                dateOne = LocalDate.parse(splitString[0].trim(), dateFormatter);
                timeOne = LocalTime.parse(splitString[1].trim(), timeFormatter);
            } else if (isValidDate(splitString[1]) && isValidTime(splitString[0])) {
                dateOne = LocalDate.parse(splitString[1].trim(), dateFormatter);
                timeOne = LocalTime.parse(splitString[0].trim(), timeFormatter);
            } else {
                throw new IllegalArgumentException("Error! DateTime format should be dd-MM-yyyy HH:mm");
            }
        } else if (splitString.length == 1) {
            if (isValidDate(splitString[0])) {
                dateOne = LocalDate.parse(splitString[0].trim(), dateFormatter);
            } else if (isValidTime(splitString[0])) {
                timeOne = LocalTime.parse(splitString[0].trim(),timeFormatter);
            } else {
                throw new IllegalArgumentException("Error! DateTime format should be dd-MM-yyyy HH:mm");
            }
        }
        String[] splitStringTwo = datetimeTwo.split("\\s");
        LocalDate dateTwo = null;
        LocalTime timeTwo = null;
        if (splitStringTwo.length == 2) {
            if (isValidDate(splitStringTwo[0]) && isValidTime(splitStringTwo[1])) {
                dateTwo = LocalDate.parse(splitStringTwo[0].trim(), dateFormatter);
                timeTwo = LocalTime.parse(splitStringTwo[1].trim(), timeFormatter);
            } else if (isValidDate(splitStringTwo[1]) && isValidTime(splitStringTwo[0])) {
                dateTwo = LocalDate.parse(splitStringTwo[1].trim(), dateFormatter);
                timeTwo = LocalTime.parse(splitStringTwo[0].trim(), timeFormatter);
            } else {
                throw new IllegalArgumentException("Error! DateTime format should be dd-MM-yyyy HH:mm");
            }
        } else if (splitStringTwo.length == 1) {
            if (isValidDate(splitStringTwo[0])) {
                dateTwo = LocalDate.parse(splitStringTwo[0].trim(), dateFormatter);
            } else if (isValidTime(splitStringTwo[0])) {
                timeTwo = LocalTime.parse(splitStringTwo[0].trim(), timeFormatter);
            } else {
                throw new IllegalArgumentException("Error! DateTime format should be dd-MM-yyyy HH:mm");
            }
        }
        if (dateOne == null || dateTwo == null) {
            dateOne = (dateTwo == null) ? dateOne : dateTwo;
            dateTwo = (dateOne == null) ? dateTwo : dateOne;
        }
        if (dateOne != null && dateTwo != null) {
            if (dateOne.isAfter(dateTwo)) {
                throw new IllegalArgumentException("Error! Start Date cannot be after End Date");
            } else if (dateOne.isEqual(dateTwo)) {
                if (timeOne == null || timeTwo == null) {
                    throw new IllegalArgumentException("Error! Time Info Missing");
                } else {
                    if (timeOne.isAfter(timeTwo)) {
                        throw new IllegalArgumentException("Error! Start Time cannot be after End Time");
                    }
                }
            }
        } else if (dateOne == null && dateTwo == null) {
            if (timeOne.isAfter(timeTwo)) {
                throw new IllegalArgumentException("Error! Start Time cannot be after End Time");
            }
        }
        return new Item(description, dateOne, timeOne, dateTwo, timeTwo);
    }

    /**
     * Helper function to add an item in list as a 'Deadline' item
     * Called by main function when user inputs deadline keyword
     * @param by_datetime_pos used for deadline -- identifies index of 'by' keyword
     * @param start_datetime_pos used for event
     * @param end_datetime_pos used for event
     * @param sub_command original user input without 'deadline' keyword
     * @param MasterList list for storing task items
     */
    private static void deadlineMethod(int by_datetime_pos, int start_datetime_pos, int end_datetime_pos, String sub_command, NewList MasterList){
        if (by_datetime_pos != -1 && start_datetime_pos == -1 && end_datetime_pos == -1) {
            String by_datetime = sub_command.substring(by_datetime_pos + 2);
            String description = sub_command.substring(0, by_datetime_pos);
            if (description.isBlank() || by_datetime.isBlank()) {  // check if by or description field are blank
                System.out.println("ERROR! Description / Deadline of Task cannot be empty");
            } else {
                try {
                    MasterList.addItem(generateDeadlineItem(description.trim(), by_datetime.trim()));
                    Item newItem = MasterList.getItem(MasterList.size() - 1);
                    addToFile(newItem.toStringFile() + '\n');
                } catch (IllegalArgumentException wrongFormat) {
                    System.out.println("Error! DateTime format should be dd-MM-yyyy HH:mm");
                }
            }
        } else {  // error if (by) does not appear of if (start) or (end) are in user input
            System.out.println("ERROR! Deadline task must have a Deadline (keyword: by). It also should not have a start or end date");
        }
    }

    /**
     * Helper function to add an item in list as a 'Event' item
     * Called by main function when user inputs Event keyword
     * @param by_datetime_pos used for deadline
     * @param start_datetime_pos used for event -- identifies index of 'start' keyword
     * @param end_datetime_pos used for event -- identifies index of 'end' keyword
     * @param sub_command user input without the 'event' keyword
     * @param MasterList list for storing task items 
     */
    private static void eventMethod(int by_datetime_pos, int start_datetime_pos, int end_datetime_pos, String sub_command, NewList MasterList){
        if (by_datetime_pos == -1 && start_datetime_pos != -1 && end_datetime_pos != -1) {
            String start_datetime = sub_command.substring(start_datetime_pos + 5, end_datetime_pos - 1);
            String end_datetime = sub_command.substring(end_datetime_pos + 3);
            String description = sub_command.substring(0, start_datetime_pos);
            if (start_datetime.isBlank() || end_datetime.isBlank() || description.isBlank()) {   // check if description / start / end field are blank
                System.out.println("ERROR! Description / End / Start cannot be empty");
            } else {
                try {
                    MasterList.addItem(generateEventItem(description.trim(), start_datetime.trim(), end_datetime.trim()));
                    Item newItem = MasterList.getItem(MasterList.size() - 1);
                    addToFile(newItem.toStringFile() + '\n');
                } catch (IllegalArgumentException datetimeException) {
                    if (datetimeException.getMessage().contains("format")) {
                        System.out.println("Error! DateTime format should be dd-MM-yyyy HH:mm");
                    } else if (datetimeException.getMessage().contains("Date cannot be after")) {
                        System.out.println("Error! Start Date cannot be after End Date");
                    } else if (datetimeException.getMessage().contains("Missing")) {
                        System.out.println("Error! Time Info Missing");
                    } else if (datetimeException.getMessage().contains("Time cannot be after")) {
                        System.out.println("Error! Start Time cannot be after End Time");
                    }
                }
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

