import java.util.List;
import java.util.ArrayList;

public class NewList {
    private List<Item> list = new ArrayList<>();

    public void addItem(Item item) {
        list.add(item);
        System.out.println("\t-------------------------------------------------------------");
        System.out.println("\tadded: "+ item.toString());
        System.out.println("\t-------------------------------------------------------------");
    }

    public String toString() {
        String total_str = "";
        int count = 0;
        for (Item i : list) {
            count++;
            total_str = total_str + '\t' + Integer.toString(count) + ". " + i.toString() + '\n';
        }
        return total_str;
    }
}
