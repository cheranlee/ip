package duck.tasks;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * Class which defines a task.
 * Each task is one Item object.
 */
public class Item {
    private String text;
    private boolean done = false;
    private TaskType type;
    private LocalDate firstDate = null;
    private LocalTime firstTime = null;
    private LocalDate secondDate = null;
    private LocalTime secondTime = null;

    /**
     * Constructor Class for Todo.
     *
     * E.g. todo borrow book.
     * @param text Description. e.g. 'borrow book'.
     */
    public Item(String text) {
        this.setText(text);
        this.setType(TaskType.ToDos);
    }

    /**
     * Constructor Class for Deadline.
     *
     * E.g. Deadline return book by 24-02-2026 15:00.
     * @param text Description. e.g. 'return book'
     * @param firstDate LocalDate. e.g. '24-02-2026'
     * @param firstTime LocalTime. e.g. '15:00'
     */
    public Item(String text, LocalDate firstDate, LocalTime firstTime) {
        this.setText(text);
        this.setType(TaskType.Deadlines);
        this.setFirstDate(firstDate);
        this.setFirstTime(firstTime);
    }

    /**
     * Constructor Class for Event.
     *
     * E.g. Event school start 24-02-2026 15:00 end 24-02-2026 18:00.
     * @param text Description. e.g. 'school'.
     * @param firstDate Start LocalDate. '24-02-2026'.
     * @param firstTime Start LocalTime. '15:00'.
     * @param secondDate End LocalDate. '24-02-2026'.
     * @param secondTime End LocalTime. '18:00'.
     */
    public Item(String text, LocalDate firstDate, LocalTime firstTime, LocalDate secondDate, LocalTime secondTime) {
        this.setText(text);
        this.setType(TaskType.Events);
        this.setFirstDate(firstDate);
        this.setFirstTime(firstTime);
        this.setSecondDate(secondDate);
        this.setSecondTime(secondTime);
    }

    /**
     * String formatting for LocalDate datatype.
     * Helper function used by toString() and toStringFile() methods.
     *
     * @param date LocalDate datatype.
     * @return String (which will be used in printing to console + storing to hard disk) e.g. "null" or "Mar 23 2024".
     */
    private String formatDate(LocalDate date) {
        return date == null ? "null" : this.getDateString(date);
    }

    /**
     * String formatting for LocalTime datatype.
     * Helper function used by toString() and toStringFile() methods.
     *
     * @param time LocalTime datatype.
     * @return String (which will be used in printing to console + storing to hard disk) e.g. "null" or "12:00pm".
     */
    private String formatTime(LocalTime time) {
        return time == null ? "null" : this.getTimeString(time);
    }

    /**
     * String Formatting for Deadline Task.
     *
     * @param date LocalDate.
     * @param time LocalTime.
     * @return String.
     */
    private String formatDeadline(LocalDate date, LocalTime time) {
        return " (by: " + this.formatDate(date) + " " + this.formatTime(time) + ")";
    }

    /**
     * String Formatting for Event Task.
     *
     * @param dateOne Start Date.
     * @param dateTwo End Date.
     * @param timeOne Start Time.
     * @param timeTwo End Time.
     * @return String.
     */
    private String formatEvent(LocalDate dateOne, LocalDate dateTwo, LocalTime timeOne, LocalTime timeTwo) {
        return " (start: " + this.formatDate(dateOne) + " " + this.formatTime(timeOne)
                + ") (end: " + this.formatDate(dateTwo) + " " + this.formatTime(timeTwo) + ")";
    }

    /**
     * Represent Item as a String for printing to JavaFX Console.
     *
     * @return Formatted string  e.g. [ ] [X] Do Homework
     */
    public String toString() {
        String base = "[" + this.stringType() + "]" + this.stringDone() + " " + this.getText();
        switch(this.getType()) {
        case TaskType.ToDos -> {
            return base;
        }
        case TaskType.Deadlines -> {
            return base + formatDeadline(this.getFirstDate(), this.getFirstTime());
        }
        case TaskType.Events -> {
            return base + formatEvent(this.getFirstDate(), this.getSecondDate(),
                    this.getFirstTime(), this.getSecondTime());
        }
        default -> {
            return "";
        }
        }
    }

    /**
     * Represent Item as a String to save on hard disk.
     *
     * @return Formatted string e.g. T | 0 | Do Homework or E | 0 | School | 24-02-2025 | 13:00 | 24-02-2025 | 18:00.
     */
    public String toStringFile() {
        String base = this.stringType() + " | " + stringDoneInt() + " | " + this.getText();
        switch(this.getType()) {
        case TaskType.ToDos -> {
            return base;
        }
        case TaskType.Deadlines -> {
            return base + " | " + this.formatDate(this.getFirstDate()) + " | "
                    + this.formatTime(this.getFirstTime());
        }
        case TaskType.Events -> {
            return base + " | " + this.formatDate(this.getFirstDate()) + " | "
                    + this.formatTime(this.getFirstTime()) + " | " + this.formatDate(this.getSecondDate())
                    + " | " + this.formatTime(this.getSecondTime());
        }
        default -> {
            return "";
        }
        }
    }


    public String getText() {
        return this.text;
    }



    void setText(String text) {
        this.text = text;
    }


    LocalDate getFirstDate() {
        return this.firstDate;
    }



    void setFirstDate(LocalDate date) {
        this.firstDate = date;
    }

    /**
     * String formatting for LocalDate date.
     * Called by formatDate() method.
     *
     * @param date LocalDate.
     * @return String Formatted String representing date.
     */
    String getDateString(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("MMM dd yyyy"));
    }



    LocalTime getFirstTime() {
        return this.firstTime;
    }


    void setFirstTime(LocalTime time) {
        this.firstTime = time;
    }

    /**
     * String formatting for LocalTime time.
     * Called by formatTime() method.
     *
     * @param time LocalTime.
     * @return String Formatted String representing time.
     */
    String getTimeString(LocalTime time) {
        return time.format(DateTimeFormatter.ofPattern("hh:mm a"));
    }


    LocalDate getSecondDate() {
        return this.secondDate;
    }



    void setSecondDate(LocalDate date) {
        this.secondDate = date;
    }


    LocalTime getSecondTime() {
        return this.secondTime;
    }



    void setSecondTime(LocalTime time) {
        this.secondTime = time;
    }


    /**
     * Getter method for done --> Identify if a task is marked / unmarked.
     *
     * @return done true <= done ; false <= not done.
     */
    public Boolean getDone() {
        return this.done;
    }

    /**
     * Helper function for toString which formats 'done' (mark/unmark) as [X] or [ ].
     *
     * @return string.
     */
    String stringDone() {
        if (this.getDone()) {
            return "[X]";
        } else {
            return "[ ]";
        }
    }

    /**
     * Helper function for toStringFile which formats 'done' (mark/unmark) as 1 or 0.
     *
     * @return string  ("1" if done=true or "0" if done=false).
     */
    String stringDoneInt() {
        return this.getDone() ? "1" : "0";
    }


    void setDone(Boolean isDone) {
        this.done = isDone;
    }

    void setType(TaskType tasktype) {
        this.type = tasktype;
    }

    TaskType getType() {
        return this.type;
    }

    /**
     * Helper function for toString to convert getType enum info into a printable string.
     *
     * @return string. e.g. [T] or [E] or [D].
     */
    String stringType() {
        return switch (this.getType()) {
        case TaskType.ToDos -> "T";
        case TaskType.Events -> "E";
        case TaskType.Deadlines -> "D";
        default -> " ";
        };
    }
}
