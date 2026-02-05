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


public class Parser {
    private Ui ui;

    public Parser(Ui ui) {
        this.ui = ui;
    }

    public Command parse(String command) throws DuckException{
        while (!command.contains("bye")) {
            if (command.contains("list")) {
                return new ListCommand();
            } else if (command.contains("mark") || command.contains("unmark")) {
                command = command.trim();
                int space_pos = command.indexOf(" ");
                if (space_pos == -1) {
                    throw new DuckException("ERROR! Mark/Unmark command must have an integer index behind");
                } else {
                    String subCommand = command.substring(space_pos + 1);
                    subCommand = subCommand.trim();
                    return new MarkUnmarkCommand(subCommand);
                }
            } else if (command.contains("todo") || command.contains("deadline") || command.contains("event")) {
                command = command.trim();
                int space_pos = command.indexOf(" ");
                if (space_pos == -1) {
                    throw new DuckException("ERROR! Description of Task cannot be empty");  // to catch cases where only todo deadline event are the input
                } else {
                    String subCommand = command.substring(space_pos + 1);
                    subCommand = subCommand.trim();
                    int by_datetime_pos = subCommand.indexOf("by");
                    int start_datetime_pos = subCommand.indexOf("start");
                    int end_datetime_pos = subCommand.indexOf("end");
                    if (command.contains("todo")) {  // no date or time attached
                        return new TodoCommand(by_datetime_pos, start_datetime_pos, end_datetime_pos, subCommand);
                    } else if (command.contains("deadline")) {  // (by) date & time
                        return new DeadlineCommand(by_datetime_pos, start_datetime_pos, end_datetime_pos, subCommand);
                    } else if (command.contains("event")) {   // (start) (end) date & time
                        return new EventCommand(by_datetime_pos, start_datetime_pos, end_datetime_pos, subCommand);
                    }
                }
            } else if (command.contains("delete")){
                command = command.trim();
                int space_pos = command.indexOf(" ");
                if (space_pos == -1) {
                    throw new DuckException("ERROR! Delete command must have an integer index behind");
                } else {
                    String subCommand = command.substring(space_pos + 1);
                    subCommand = subCommand.trim();
                    return new DeleteCommand(subCommand);
                }
            } else {
                throw new DuckException("ERROR! Invalid Command.");
            }
        }
        return new ByeCommand();
    }
}
