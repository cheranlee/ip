/**
 * Error Detection
 * Note: Refer to input.txt in text-ui-test folder to see examples of the following errors
 * 1. Commands are not case sensitive (ie List will be recognised as list)
 * 2. Command not recognised (list/todo/deadline/...)
 * 3. Missing / Wrong keyword for the wrong command (e.g. using start/end for deadline)
 * 4. Missing description / date (for deadline / start & end)
 * 5. Mark/Unmark/Delete item number out of range (task index does not exist in list)
 * 6. Mark/Unmark/Delete invalid task number (e.g. mark 2a, mark   , mark -1)
 * 7. Mark task that has been marked (& vice versa)
 * 8. Date and time format not valid
 * 9. For event, Start Date after End Date
 */

// TAKES IN DD-MM-YYYY prints out MMM dd YYYY

public class Duck {
    public static String home = System.getProperty("user.dir");

    private Storage storage;
    private TaskList tasks;
    private Ui ui;
    private Parser parser;

    public Duck(String filePath){
        ui = new Ui();
        storage = new Storage(filePath);
        parser = new Parser(ui);
        try {
            tasks = new TaskList(storage.load());    // load prev tasks
        } catch (DuckException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    public void run(){
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                Command c = parser.parse(fullCommand);
                c.execute(tasks, ui, storage);
                isExit = c.isExit();
            } catch (DuckException e) {
                ui.showError(e.getMessage());
            }
        }
    }

    public static void main(String[] args){
        new Duck(home).run();
    }
}

