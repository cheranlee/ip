package duck;

import duck.command.ByeCommand;
import duck.command.Command;
import duck.command.DeadlineCommand;
import duck.command.DeleteCommand;
import duck.command.EventCommand;
import duck.command.ListCommand;
import duck.command.MarkUnmarkCommand;
import duck.command.TodoCommand;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Item {
    private String text;
    private boolean done = false;
    private TaskType type;
    private LocalDate firstDate = null;
    private LocalTime firstTime = null;
    private LocalDate secondDate = null;
    private LocalTime secondTime = null;

    /**
     * Constructor Class for Todo
     * E.g. todo borrow book
     * @param text borrow book
     */
    public Item(String text){
        this.setText(text);
        this.setType(TaskType.ToDos);
    }

    /**
     * Constructor Class for Deadline
     * E.g. Deadline return book by 24-02-2026 15:00
     * @param text return book
     * @param firstDate 24-02-2026 LocalDate
     * @param firstTime 15:00 LocalTime
     */
    public Item(String text, LocalDate firstDate, LocalTime firstTime){
        this.setText(text);
        this.setType(TaskType.Deadlines);
        this.setFirstDate(firstDate);
        this.setFirstTime(firstTime);
    }

    /**
     * Constructor Class for Event
     * E.g. Event school start 24-02-2026 15:00 end 24-02-2026 18:00
     * @param text school
     * @param firstDate 24-02-2026
     * @param firstTime 15:00
     * @param secondDate 24-02-2026
     * @param secondTime 18:00
     */
    public Item(String text, LocalDate firstDate, LocalTime firstTime, LocalDate secondDate, LocalTime secondTime){
        this.setText(text);
        this.setType(TaskType.Events);
        this.setFirstDate(firstDate);
        this.setFirstTime(firstTime);
        this.setSecondDate(secondDate);
        this.setSecondTime(secondTime);
    }

    /**
     * Helper function for string formatting LocalDate datatype
     * Used by toString() and toStringFile() methods
     * @param date LocalDate datatype
     * @return a string (which will be used in printing to console + storing to hard disk) -- "null" or "Mar 23 2024"
     */
    private String formatDate(LocalDate date){
        return date == null ? "null"  : this.getDateString(date);
    }

    /**
     * Helper function for string formatting LocalTime datatype
     * Used by toString() and toStringFile() methods
     * @param time LocalTime datatype
     * @return a string (which will be used in printing to console + storing to hard disk) -- "null" or "12:00pm"
     */
    private String formatTime(LocalTime time){
        return time == null ? "null"  : this.getTimeString(time);
    }

    /**
     * Overriding in-built toString method
     * @return a string  e.g. [ ] [X] Do Homework
     */
    public String toString() {
        String totalStr = "[" + this.stringType() + "]" + this.stringDone() + " " + this.getText();
        switch(this.getType()) {
            case TaskType.Deadlines -> {
                totalStr = totalStr + " (by: " + this.formatDate(this.getFirstDate()) + " " + this.formatTime(this.getFirstTime()) + ")";
            }
            case TaskType.Events -> {
                totalStr = totalStr + " (start: " + this.formatDate(this.getFirstDate()) + " " + this.formatTime(this.getFirstTime());
                totalStr = totalStr + ") (end: " + this.formatDate(this.getSecondDate()) + " " + this.formatTime(this.getSecondTime()) + ")";
            }
        }
        return totalStr;
    }

    /**
     * String format all information about Item to save on hard disk
     * @return a string e.g. T | 0 | Do Homework or E | 0 | School | 24-02-2025 | 13:00 | 24-02-2025 | 18:00
     */
    public String toStringFile(){
        String totalStr = "";
        switch(this.getType()) {
            case TaskType.ToDos -> {
                totalStr = totalStr + this.stringType() + " | " + stringDoneInt() + " | " + this.getText();
            }
            case TaskType.Deadlines -> {
                totalStr = totalStr + this.stringType() + " | " + stringDoneInt() + " | " + this.getText() + " | ";
                totalStr = totalStr + this.formatDate(this.getFirstDate()) + " | " + this.formatTime(this.getFirstTime());
            }
            case TaskType.Events -> {
                totalStr = totalStr + this.stringType() + " | " + stringDoneInt() + " | " + this.getText() + " | ";
                totalStr = totalStr + this.formatDate(this.getFirstDate()) + " | " + this.formatTime(this.getFirstTime());
                totalStr = totalStr + " | " + this.formatDate(this.getSecondDate()) + " | " + this.formatTime(this.getSecondTime());
            }
        }
        return totalStr;
    }

    /**
     * Getter method for text
     * @return text (String)
     */
    public String getText(){
        return this.text;
    }

    /**
     * Setter method for text
     * @param text String
     */
    void setText(String text){
        this.text = text;
    }

    /**
     * Getter method for firstDate
     * @return LocalDate
     */
    LocalDate getFirstDate(){
        return this.firstDate;
    }

    /**
     * Setter method for firstDate
     * @param date LocalDate
     */
    void setFirstDate(LocalDate date){
        this.firstDate = date;
    }

    /**
     * String formatting for LocalDate date
     * Called by formatDate() method
     * @param date LocalDate
     * @return String
     */
    String getDateString(LocalDate date){
        return date.format(DateTimeFormatter.ofPattern("MMM dd yyyy"));
    }

    /**
     * Getter method for firstTime
     * @return LocalTime
     */
    LocalTime getFirstTime(){
        return this.firstTime;
    }

    /**
     * Setter method for firstTime
     * @param time LocalTime
     */
    void setFirstTime(LocalTime time){
        this.firstTime = time;
    }

    /**
     * String formatting for LocalTime time
     * Called by formatTime() method
     * @param time LocalTime
     * @return String
     */
    String getTimeString(LocalTime time){
        return time.format(DateTimeFormatter.ofPattern("hh:mm a"));
    }

    /**
     * Getter method for secondDate
     * @return LocalDate
     */
    LocalDate getSecondDate(){
        return this.secondDate;
    }

    /**
     * Setter method for secondDate
     * @param date LocalDate
     */
    void setSecondDate(LocalDate date){
        this.secondDate = date;
    }

    /**
     * Getter Method for secondTime
     * @return LocalTime
     */
    LocalTime getSecondTime(){
        return this.secondTime;
    }

    /**
     * Setter Method for secondTime
     * @param time LocalTime
     */
    void setSecondTime(LocalTime time){
        this.secondTime = time;
    }


    /**
     * Getter method for done --> Identify if a task is marked / unmarked
     * @return done
     */
    public Boolean getDone(){
        return this.done;
    }

    /**
     * Helper function to convert getDone boolean info into a printable string for toString method
     * @return string
     */
    String stringDone(){
        if (this.getDone()) {
            return "[X]";
        } else {
            return "[ ]";
        }
    }

    /**
     * Helper function for toStringFile which formats 'done' (mark/unmark) as 1 or 0
     * @return string (either "1" if done=true or "0" if done=false)
     */
    String stringDoneInt(){
        if (this.getDone()) {
            return "1";
        } else {
            return "0";
        }
    }

    /**
     * Setter method for done
     * @param isDone boolean
     */
    void setDone(Boolean isDone){
        this.done = isDone;
    }

    /**
     * Setter method for type
     * @param tasktype (which is an enum ToDo, Deadline or Event)
     */
    void setType(TaskType tasktype){
        this.type = tasktype;
    }

    /**
     * Getter method for type
     * @return type
     */
    TaskType getType(){
        return this.type;
    }

    /**
     * Helper function to convert getType enum info into a printable string for toString method
     * @return string
     */
    String stringType(){
        if (this.getType() == TaskType.ToDos) {
            return "T";
        } else {
            if (this.getType() == TaskType.Events) {
                return "E";
            } else {
                if (this.getType() == TaskType.Deadlines) {
                    return "D";
                }
            }
        }
        return " ";
    }
}
