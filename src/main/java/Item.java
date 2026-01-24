public class Item {
    private String text;

    // Constructor Class
    public Item(String text){
        this.set_text(text);
    }

    // Overriding in-built toString method
    public String toString() {
        return this.get_text();
    }

    // Getter and Setter Methods
    String get_text(){
        return this.text;
    }

    void set_text(String text){
        this.text = text;
    }
}
