/**
 * Error Detection List
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

package duck.exception;

/**
 * Errors with arguments provided by User (e.g. start date after end date).
 */
public class DuckException extends Exception {
    public DuckException(String errorMessage) {
        super(errorMessage);
    }
}
