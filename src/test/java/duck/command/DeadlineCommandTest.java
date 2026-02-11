package duck.command;

import duck.DuckException;
import duck.Storage;
import duck.TaskList;
import duck.Ui;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DeadlineCommandTest {

    @Test
    public void no_error_test(){
        try {
            TaskList tasks = new TaskList();
            Ui ui = new Ui();
            Storage storage = new Storage("test");
            DeadlineCommand deadline = new DeadlineCommand(9, -1, -1,"walk dog by 23-04-2025 13:00");
            deadline.execute(tasks, ui, storage);
            assertEquals("[D][ ] walk dog (by: Apr 23 2025 01:00 pm)", tasks.getItem(tasks.size()-1).toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void missing_info_error() {
        TaskList tasks = new TaskList();
        Ui ui = new Ui();
        Storage storage = new Storage("test");
        DeadlineCommand deadline = new DeadlineCommand(9, -1, -1,"walk dog by ");
        DuckException e = assertThrows(DuckException.class,
                () -> deadline.execute(tasks, ui, storage));
        assertEquals("Description / Deadline of Task cannot be empty", e.getMessage());
    }

    @Test
    public void wrong_date_format_error() {
        TaskList tasks = new TaskList();
        Ui ui = new Ui();
        Storage storage = new Storage("test");
        DeadlineCommand deadline = new DeadlineCommand(9, -1, -1,"walk dog by 14 APR 2025");
        DuckException e = assertThrows(DuckException.class,
                () -> deadline.execute(tasks, ui, storage));
        assertEquals("DateTime format should be dd-MM-yyyy HH:mm", e.getMessage());
    }

    @Test
    public void no_by_keyword() {
        TaskList tasks = new TaskList();
        Ui ui = new Ui();
        Storage storage = new Storage("test");
        DeadlineCommand deadline = new DeadlineCommand(-1, -1, -1,"walk dog start 14-02-2025 end 24-02-2025");
        DuckException e = assertThrows(DuckException.class,
                () -> deadline.execute(tasks, ui, storage));
        assertEquals("Deadline task must have a Deadline (keyword: by). It also should not have a start or end date", e.getMessage());
    }

}