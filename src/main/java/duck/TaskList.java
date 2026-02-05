package duck;

import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;

import duck.command.ByeCommand;
import duck.command.Command;
import duck.command.DeadlineCommand;
import duck.command.DeleteCommand;
import duck.command.EventCommand;
import duck.command.ListCommand;
import duck.command.MarkUnmarkCommand;
import duck.command.TodoCommand;

/**
 * Manages a list of all tasks
 */
public class TaskList {
    private List<Item> tasklist;

    public TaskList(){
        this.tasklist = new ArrayList<>();
    }

    public TaskList(List<String> oldTasks){
        this.tasklist = new ArrayList<>();
        this.loadTasks(oldTasks);
    }

    public void loadTasks(List<String> oldTasks) {
        int count = 0;
        for (String line : oldTasks) {
            String[] splitString = line.split("\\s\\|\\s");
            String taskType_char = splitString[0].trim();
            String done_char = splitString[1].trim();
            String description = splitString[2].trim();
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMM dd yyyy");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");

            switch (taskType_char) {
                case "T" -> {
                    this.addItem(new Item(description));
                }
                case "D" -> {
                    String dateStr = splitString[3].trim();
                    String timeStr = splitString[4].trim();
                    LocalDate date = (dateStr.equals("null")) ? null : LocalDate.parse(dateStr, dateFormatter);
                    LocalTime time = (timeStr.equals("null")) ? null : LocalTime.parse(timeStr, timeFormatter);
                    this.addItem(new Item(description, date, time));
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
                    this.addItem(new Item(description, dateOne, timeOne, dateTwo, timeTwo));
                }
            }

            if (done_char.equals("1")) {
                this.markUnmarkItem(true, count);
            }
            count = count + 1;
        }
    }

    /**
     * Add a new task to the list
     * @param item instance of Item class
     */
    public void addItem(Item item) {
        this.tasklist.add(item);
        System.out.println("\t-------------------------------------------------------------");
        System.out.println("\tQuack! I've added this task:");
        System.out.println("\t\t"+ item.toString());
        System.out.println("\tNow you have " + this.size() + " tasks in the list");
        System.out.println("\t-------------------------------------------------------------");
    }

    /**
     * Retrieves item at specific index
     * @param index integer
     * @return Item (at specific index)
     */
    public Item getItem(int index){
        return this.tasklist.get(index);
    }

    /**
     * Deletes task at index index from list
     * @param index integer
     */
    public void deleteItem(int index){
        Item i = this.tasklist.get(index);
        this.tasklist.remove(index);
        System.out.println("\t-------------------------------------------------------------");
        System.out.println("\tQuack! I've removed this task:");
        System.out.println("\t\t"+ i.toString());
        System.out.println("\tNow you have " + this.size() + " tasks in the list");
        System.out.println("\t-------------------------------------------------------------");
    }

    /**
     * returns number of tasks in list
     * @return size (integer)
     */
    public int size(){
        return this.tasklist.size();
    }

    /**
     * uses toString method in item class to print the whole list of tasks
     * @return total_str
     */
    public String toString() {
        String total_str = "";
        if (this.size() > 0) {
            int count = 0;
            System.out.println("\tHere are the tasks in your list:");
            for (Item i : this.tasklist) {
                count++;
                total_str = total_str + '\t' + Integer.toString(count) + ". " + i.toString() + '\n';
            }
        } else {
            total_str = "Relax! You have no tasks";
        }
        return total_str;
    }

    /**
     * Marks / Unmarks item at index as done / not done
     * @param mark mark w X if true ; leave blank if false
     * @param index integer (row number)
     */
    public String markUnmarkItem(boolean mark, int index){
        Item i = this.tasklist.get(index);
        if (mark) {     // mark as done
            if (!i.get_done()) {
                i.set_done(true);
                System.out.println("\t-------------------------------------------------------------");
                System.out.println("\tQuack-ity! I've marked this task as done:");
                System.out.println("\t"+ i.toString());
                System.out.println("\t-------------------------------------------------------------");
                return i.toStringFile();
            } else {
                throw new IllegalArgumentException(("Item Already Marked as Done!"));
            }
        } else {         // unmark to show not done
            if (i.get_done()) {
                i.set_done(false);
                System.out.println("\t-------------------------------------------------------------");
                System.out.println("\tAww! I've marked this task as not done yet:");
                System.out.println("\t"+ i.toString());
                System.out.println("\t-------------------------------------------------------------");
                return i.toStringFile();
            } else {
                throw new IllegalArgumentException(("Item Already Marked as Not Done!"));
            }
        }
    }


}
