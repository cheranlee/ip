import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

/**
 * Manages a list of all tasks
 */
public class NewList {
    private List<Item> list = new ArrayList<>();

    /**
     * Add a new task to the list
     * @param item
     */
    public void addItem(Item item) {
        this.list.add(item);
        System.out.println("\t-------------------------------------------------------------");
        System.out.println("\tQuack! I've added this task:");
        System.out.println("\t\t"+ item.toString());
        System.out.println("\tNow you have " + this.size() + " tasks in the list");
        System.out.println("\t-------------------------------------------------------------");
    }

    /**
     * Deletes task at index idx from list
     * @param idx
     */
    public void deleteItem(int idx){
        Item i = this.list.get(idx);
        this.list.remove(idx);
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
        return this.list.size();
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
            for (Item i : this.list) {
                count++;
                total_str = total_str + '\t' + Integer.toString(count) + ". " + i.toString() + '\n';
            }
        } else {
            total_str = "Relax! You have no tasks";
        }
        return total_str;
    }

    /**
     * Marks / Unmarks item at idx as done / not done
     * @param mark
     * @param idx
     */
    public void markUnmarkItem(boolean mark, int idx){
        Item i = this.list.get(idx);
        if (mark) {     // mark as done
            if (!i.get_done()) {
                i.set_done(true);
                System.out.println("\t-------------------------------------------------------------");
                System.out.println("\tQuack-ity! I've marked this task as done:");
                System.out.println("\t"+ i.toString());
                System.out.println("\t-------------------------------------------------------------");
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
            } else {
                throw new IllegalArgumentException(("Item Already Marked as Not Done!"));
            }
        }
    }


}
