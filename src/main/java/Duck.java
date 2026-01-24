import java.util.Scanner;

public class Duck {
    public static void main(String[] args) {
        System.out.println("-------------------------------------------------------------");
        System.out.println("Quack! I'm Duck. \nWhat can I do for you?");
        System.out.println("-------------------------------------------------------------");

        Scanner new_object = new Scanner(System.in);
        String command = new_object.nextLine();
        while (!command.contains("bye")){
            System.out.println("\t-------------------------------------------------------------");
            System.out.println('\t'+ command);
            System.out.println("\t-------------------------------------------------------------");
            command = new_object.nextLine();
        }
        System.out.println("\t-------------------------------------------------------------");
        System.out.println("\tBye Quack! Hope to see you again soon!");
        System.out.println("\t-------------------------------------------------------------");
    }
}
