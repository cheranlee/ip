package duck.command;
import duck.*;
import duck.Storage;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TodoCommandTest {

    @Test
    public void no_error_test() {
        try {
            TaskList tasks = new TaskList();
            Ui ui = new Ui();
            Storage storage = new Storage("test");
            TodoCommand todo = new TodoCommand(-1, -1, -1,"borrow book");
            todo.execute(tasks, ui, storage);
            assertEquals("borrow book", tasks.getItem(tasks.size()-1).getText());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void error() {
        TaskList tasks = new TaskList();
        Ui ui = new Ui();
        Storage storage = new Storage("test");
        TodoCommand todo = new TodoCommand(3, -1, -1, "borrow book");
        DuckException e = assertThrows(DuckException.class,
                () -> todo.execute(tasks, ui, storage));
        assertEquals("Todo task should not have deadline, start or end date", e.getMessage());

    }
}