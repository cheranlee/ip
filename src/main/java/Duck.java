import java.util.Scanner;

public class Duck {
    public static void main(String[] args) {
        System.out.println("-------------------------------------------------------------");
        System.out.println("Quack! I'm Duck. \nWhat can I do for you?");
        System.out.println("-------------------------------------------------------------");

        NewList MasterList = new NewList();
        Scanner new_object = new Scanner(System.in);
        String command = new_object.nextLine();
        while (!command.contains("bye")) {
            if (command.contains("list")) {
                System.out.println(MasterList.toString());
            } else {
                MasterList.addItem(new Item(command));
            }
            command = new_object.nextLine();
        }
        System.out.println("\t-------------------------------------------------------------");
        System.out.println("\tBye Quack! Hope to see you again soon!");
        System.out.println("\t-------------------------------------------------------------");
    }
}
