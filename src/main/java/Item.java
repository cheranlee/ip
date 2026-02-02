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
        return this.string_type() + this.string_done() + " " + this.get_text();
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
     * Setter method for done
     * @param val
     */
    void set_done(Boolean val){
        this.done = val;
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
            return "[T]";
        } else {
            if (this.get_type() == TaskType.Events) {
                return "[E]";
            } else {
                if (this.get_type() == TaskType.Deadlines) {
                    return "[D]";
                }
            }
        }
        return "[ ]";
    }
}
