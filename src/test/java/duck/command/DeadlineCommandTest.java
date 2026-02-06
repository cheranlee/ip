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
    public void noErrorTest(){
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
    public void missingInfoError(){
        TaskList tasks = new TaskList();
        Ui ui = new Ui();
        Storage storage = new Storage("test");
        DeadlineCommand deadline = new DeadlineCommand(9, -1, -1,"walk dog by ");
        DuckException e = assertThrows(DuckException.class,
                () -> deadline.execute(tasks, ui, storage));
        assertEquals("ERROR! Description / Deadline of Task cannot be empty", e.getMessage());
    }

    @Test
    public void wrongDateFormatError(){
        TaskList tasks = new TaskList();
        Ui ui = new Ui();
        Storage storage = new Storage("test");
        DeadlineCommand deadline = new DeadlineCommand(9, -1, -1,"walk dog by 14 APR 2025");
        DuckException e = assertThrows(DuckException.class,
                () -> deadline.execute(tasks, ui, storage));
        assertEquals("Error! DateTime format should be dd-MM-yyyy HH:mm", e.getMessage());
    }

    @Test
    public void noByKeyword(){
        TaskList tasks = new TaskList();
        Ui ui = new Ui();
        Storage storage = new Storage("test");
        DeadlineCommand deadline = new DeadlineCommand(-1, -1, -1,"walk dog start 14-02-2025 end 24-02-2025");
        DuckException e = assertThrows(DuckException.class,
                () -> deadline.execute(tasks, ui, storage));
        assertEquals("ERROR! Deadline task must have a Deadline (keyword: by). It also should not have a start or end date", e.getMessage());
    }

}