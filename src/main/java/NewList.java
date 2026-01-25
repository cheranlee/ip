import java.io.IOException;
import java.nio.channels.IllegalChannelGroupException;
import java.util.List;
import java.util.ArrayList;

public class NewList {
    private List<Item> list = new ArrayList<>();

    public void addItem(Item item) {
        list.add(item);
        System.out.println("\t-------------------------------------------------------------");
        System.out.println("\tQuack! I've added this task:");
        System.out.println("\t\t"+ item.toString());
        System.out.println("\tNow you have " + this.size() + " tasks in the list");
        System.out.println("\t-------------------------------------------------------------");
    }

    public int size(){
        return this.list.size();
    }

    public String toString() {
        String total_str = "";
        if (this.size() > 0) {
            int count = 0;
            System.out.println("\tHere are the tasks in your list:");
            for (Item i : list) {
                count++;
                total_str = total_str + '\t' + Integer.toString(count) + ". " + i.toString() + '\n';
            }
        } else {
            total_str = "Relax! You have no tasks";
        }
        return total_str;
    }

    public void markUnmarkItem(boolean mark, int idx){
        if (idx <= list.size()) {
            Item i = list.get(idx - 1);
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
        } else {
            throw new IndexOutOfBoundsException("Invalid Item Number. You do not have this task.");
        }
    }


}
