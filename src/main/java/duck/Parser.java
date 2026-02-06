package duck;

import jdk.jfr.Event;

import java.util.Scanner;

import duck.command.ByeCommand;
import duck.command.Command;
import duck.command.DeadlineCommand;
import duck.command.DeleteCommand;
import duck.command.EventCommand;
import duck.command.ListCommand;
import duck.command.MarkUnmarkCommand;
import duck.command.TodoCommand;

/**
 * Class which makes sense of user input
 */
public class Parser {
    private Ui ui;

    /**
     * Constructor Class
     * Interacts with UI to obtain commands + print information to terminal
     * @param ui
     */
    public Parser(Ui ui) {
        this.ui = ui;
    }

    /**
     * Main Code that processes user input
     * @param command user input (string)
     * @return Command object --> cld be todo, deadline, event, delete, mark etc.
     * @throws DuckException Self-defined Exception Class which identifies error (using error message)
     */
    public Command parse(String command) throws DuckException{
        while (!command.contains("bye")) {
            if (command.contains("list")) {
                return new ListCommand();
            } else if (command.contains("mark") || command.contains("unmark")) {
                command = command.trim();
                int spacePos = command.indexOf(" ");
                if (spacePos == -1) {
                    throw new DuckException("ERROR! Mark/Unmark command must have an integer index behind");
                } else {
                    String subCommand = command.substring(spacePos + 1);
                    subCommand = subCommand.trim();
                    return new MarkUnmarkCommand(subCommand);
                }
            } else if (command.contains("todo") || command.contains("deadline") || command.contains("event")) {
                command = command.trim();
                int spacePos = command.indexOf(" ");
                if (spacePos == -1) {
                    throw new DuckException("ERROR! Description of Task cannot be empty");  // to catch cases where only todo deadline event are the input
                } else {
                    String subCommand = command.substring(spacePos + 1);
                    subCommand = subCommand.trim();
                    int byDatetimePos = subCommand.indexOf("by");
                    int startDatetimePos = subCommand.indexOf("start");
                    int endDatetimePos = subCommand.indexOf("end");
                    if (command.contains("todo")) {  // no date or time attached
                        return new TodoCommand(byDatetimePos, startDatetimePos, endDatetimePos, subCommand);
                    } else if (command.contains("deadline")) {  // (by) date & time
                        return new DeadlineCommand(byDatetimePos, startDatetimePos, endDatetimePos, subCommand);
                    } else if (command.contains("event")) {   // (start) (end) date & time
                        return new EventCommand(byDatetimePos, startDatetimePos, endDatetimePos, subCommand);
                    }
                }
            } else if (command.contains("delete")){
                command = command.trim();
                int spacePos = command.indexOf(" ");
                if (spacePos == -1) {
                    throw new DuckException("ERROR! Delete command must have an integer index behind");
                } else {
                    String subCommand = command.substring(spacePos + 1);
                    subCommand = subCommand.trim();
                    return new DeleteCommand(subCommand);
                }
            } else if (command.contains("find")){
                command = command.trim();
                int space_pos = command.indexOf(" ");
                if (space_pos == -1) {
                    throw new DuckException("ERROR! Find command must have keyword(s) behind");
                } else {
                    String subCommand = command.substring(space_pos + 1);
                    subCommand = subCommand.trim();
                    return new FindCommand(subCommand);
                }
            } else if (command.contains("cheer")) {
                return new CheerCommand();
            } else {
                throw new DuckException("ERROR! Invalid Command.");
            }
        }
        return new ByeCommand();
    }
}
