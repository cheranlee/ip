package duck.tasks;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import duck.exception.DuckException;

/**
 * Manages a list of all tasks
 */
public class TaskList {
    private List<Item> tasks;

    /**
     * Constructor Class if no pre-existing data from hard disk.
     * Creates empty tasks.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Constructor Class when there is pre-existing data from hard disk.
     * Creates tasks and loads data to tasks using loadTasks() method.
     *
     * @param oldTasks Data from hard disk (data format: list of strings).
     */
    public TaskList(List<String> oldTasks) throws DuckException {
        this.tasks = new ArrayList<>();
        this.loadTasks(oldTasks);
    }

    /**
     * Add pre-existing tasks to current tasklist.
     *
     * @param oldTasks Data from hard disk (data format: list of strings).
     */
    public void loadTasks(List<String> oldTasks) throws DuckException {
        int count = 0;
        for (String line : oldTasks) {
            String[] splitString = line.split("\\s\\|\\s");
            String taskTypeChar = splitString[0].trim();
            String doneChar = splitString[1].trim();
            String description = splitString[2].trim();
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMM dd yyyy");
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");

            switch (taskTypeChar) {
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
            default -> {
                assert false : "Unexpected TaskType: " + taskTypeChar;
            }
            }
            if (doneChar.equals("1")) {
                this.markUnmarkItem(true, count);
            }
            count = count + 1;
        }
    }

    /**
     * Add a new task to the list.
     *
     * @param item Instance of Item class.
     */
    public String addItem(Item item) {
        this.tasks.add(item);
        return "Quack! I've added this task: \n" + item + '\n'
                + "Now you have " + this.size() + " tasks in the list\n";
    }

    /**
     * Retrieves task (Item) at specific index.
     *
     * @param index Retrieve task from this location.
     * @return Item (at specific index)
     */
    public Item getItem(int index) {
        return this.tasks.get(index);
    }

    /**
     * Deletes task from list.
     *
     * @param index Location of task to be deleted.
     */
    public String deleteItem(int index) {
        Item removed = this.tasks.remove(index);
        return "Quack! I've removed this task:\n" + removed + '\n'
                + "Now you have " + this.size() + " tasks in the list\n";
    }

    /**
     * Returns number of tasks in list.
     *
     * @return size Integer representing number of tasks in list.
     */
    public int size() {
        return this.tasks.size();
    }

    /**
     * Reorder tasks in File.
     *
     * @return List of ordered tasks (for saving in Hard Disk).
     */
    public String sort() {
        Collections.sort(this.tasks);
        return java.util.stream.IntStream.range(0, this.tasks.size())
                .mapToObj(i -> this.tasks.get(i).toStringFile())
                .collect(java.util.stream.Collectors.joining("\n"));
    }

    /**
     * Print list of tasks.
     *
     * @return total_str List of tasks (with string formatting).
     */
    @Override
    public String toString() {
        if (this.tasks.isEmpty()) {
            return "Relax! You have no tasks";
        }
        return java.util.stream.IntStream.range(0, this.tasks.size())
                        .mapToObj(i -> (i + 1) + ". " + this.tasks.get(i))
                        .collect(java.util.stream.Collectors.joining("\n"));
    }

    /**
     * Marks / Unmarks Item at index as done / not done.
     *
     * @param markAsDone Mark w X if true ; leave blank if false.
     * @param index Location of task to be marked.
     */
    public String[] markUnmarkItem(boolean markAsDone, int index) throws DuckException {
        Item i = this.tasks.get(index);
        boolean isDone = i.getDone();
        if (markAsDone) { // mark as done
            if (!isDone) {
                i.setDone(true);
                String totalStr = "Quack-ity! I've marked this task as done:\n" + i + '\n';
                return new String[]{totalStr, i.toStringFile()};
            } else {
                throw new DuckException("Item Already Marked as Done!");
            }
        } else { // unmark to show not done
            if (isDone) {
                i.setDone(false);
                String totalStr = "Aww! I've marked this task as not done yet:\n" + i + '\n';
                return new String[]{totalStr, i.toStringFile()};
            } else {
                throw new DuckException("Item Already Marked as Not Done!");
            }
        }
    }

    /**
     * Traverse through tasks and extract Items with the keyword.
     *
     * @param word Keyword.
     * @return Formatted String of 'find' results.
     */
    public List<String> findWord(String word) {
        return this.tasks.stream()
                .filter(item -> item.getText().contains(word))
                .map(Item::toString)
                .toList();
    }
}
