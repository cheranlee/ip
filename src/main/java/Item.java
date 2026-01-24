public class Item {
    private String text;
    private boolean done = false;

    // Constructor Class
    public Item(String text){
        this.set_text(text);
    }

    // Overriding in-built toString method
    public String toString() {
        return this.string_done() + " " + this.get_text();
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
            return "[ X ]";
        } else {
            return "[   ]";
        }
    }

    void set_done(Boolean val){
        this.done = val;
    }

}
