public class Item {
    private String text;
    private boolean done = false;
    private TaskType type;

    // Constructor Class
    public Item(String text, TaskType type){
        this.set_text(text);
        this.set_type(type);
    }

    // Overriding in-built toString method
    public String toString() {
        return this.string_type() + this.string_done() + " " + this.get_text();
    }

    // Getter and Setter Methods
    String get_text(){
        return this.text;
    }

    void set_text(String text){
        this.text = text;
    }

    Boolean get_done(){
        return this.done;
    }

    String string_done(){
        if (this.get_done()) {
            return "[X]";
        } else {
            return "[ ]";
        }
    }

    void set_done(Boolean val){
        this.done = val;
    }

    void set_type(TaskType tasktype){
        this.type = tasktype;
    }

    TaskType get_type(){
        return this.type;
    }

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
