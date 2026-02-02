public class Item {
    private String text;
    private boolean done = false;
    private TaskType type;

    /**
     * Constructor Class
     * @param text Description of Task
     * @param type Enum ToDo, Deadline or Event
     */
    public Item(String text, TaskType type){
        this.set_text(text);
        this.set_type(type);
    }

    /**
     * Overriding in-built toString method for a particular task (item)
     * @return a string  e.g. [ ] [X] Do Homework
     */
    public String toString() {
        return "[" + this.string_type() + "]" + this.string_done() + " " + this.get_text();
    }

    /**
     * String format all information about Item to save on hard disk
     * @return a string e.g. T | 0 | Do Homework
     */
    public String toStringFile(){
        return this.string_type() + " | " + string_done_int() + " | " + this.get_text();
    }

    /**
     * Getter method for text
     * @return text
     */
    String get_text(){
        return this.text;
    }

    /**
     * Setter method for text
     * @param text
     */
    void set_text(String text){
        this.text = text;
    }

    /**
     * Getter method for done --> Identify if a task is marked / unmarked
     * @return done
     */
    Boolean get_done(){
        return this.done;
    }

    /**
     * Helper function to convert get_done boolean info into a printable string for toString method
     * @return string
     */
    String string_done(){
        if (this.get_done()) {
            return "[X]";
        } else {
            return "[ ]";
        }
    }

    /**
     * Helper function for toStringFile which formats 'done' (mark/unmark) as 1 or 0
     * @return string (either "1" if done=true or "0" if done=false)
     */
    String string_done_int(){
        if (this.get_done()) {
            return "1";
        } else {
            return "0";
        }
    }

    /**
     * Setter method for done
     * @param isDone
     */
    void set_done(Boolean isDone){
        this.done = isDone;
    }

    /**
     * Setter method for type
     * @param tasktype (which is an enum ToDo, Deadline or Event)
     */
    void set_type(TaskType tasktype){
        this.type = tasktype;
    }

    /**
     * Getter method for type
     * @return type
     */
    TaskType get_type(){
        return this.type;
    }

    /**
     * Helper function to convert get_type enum info into a printable string for toString method
     * @return
     */
    String string_type(){
        if (this.get_type() == TaskType.ToDos) {
            return "T";
        } else {
            if (this.get_type() == TaskType.Events) {
                return "E";
            } else {
                if (this.get_type() == TaskType.Deadlines) {
                    return "D";
                }
            }
        }
        return " ";
    }
}
