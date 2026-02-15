package duck.userinteraction;

import duck.command.ByeCommand;
import duck.command.CheerCommand;
import duck.command.Command;
import duck.command.DeadlineCommand;
import duck.command.DeleteCommand;
import duck.command.EventCommand;
import duck.command.FindCommand;
import duck.command.HelpCommand;
import duck.command.ListCommand;
import duck.command.MarkUnmarkCommand;
import duck.command.TodoCommand;
import duck.exception.ParserException;

/**
 * Class which makes sense of user input
 */
public class Parser {

    private Command parseMark(String userInput) throws ParserException {
        userInput = userInput.trim();
        int spacePos = userInput.indexOf(" ");
        if (spacePos == -1) {
            throw new ParserException("Mark/Unmark userInput must have an integer index behind");
        } else {
            String argument = userInput.substring(spacePos + 1);
            argument = argument.trim();
            return new MarkUnmarkCommand(argument, userInput.contains("unmark"));
        }
    }

    private Command parseTodoDeadlineEvent(String userInput) throws ParserException {
        userInput = userInput.trim();
        int spacePos = userInput.indexOf(" ");
        if (spacePos == -1) {
            throw new ParserException("Description of Task cannot be empty");
        } else {
            String argument = userInput.substring(spacePos + 1);
            argument = argument.trim();
            int byDatetimePos = argument.indexOf("by");
            int startDatetimePos = argument.indexOf("start");
            int endDatetimePos = argument.indexOf("end");
            if (userInput.contains("todo")) { // no date or time attached
                return new TodoCommand(byDatetimePos, startDatetimePos, endDatetimePos, argument);
            } else if (userInput.contains("deadline")) { // (by) date & time
                return new DeadlineCommand(byDatetimePos, startDatetimePos, endDatetimePos, argument);
            } else if (userInput.contains("event")) { // (start) (end) date & time
                return new EventCommand(byDatetimePos, startDatetimePos, endDatetimePos, argument);
            }
        }
        return null;
    }

    private Command parseDelete(String userInput) throws ParserException {
        userInput = userInput.trim();
        int spacePos = userInput.indexOf(" ");
        if (spacePos == -1) {
            throw new ParserException("Delete userInput must have an integer index behind");
        } else {
            String argument = userInput.substring(spacePos + 1);
            return new DeleteCommand(argument.trim());
        }
    }

    private Command parseFind(String userInput) throws ParserException {
        userInput = userInput.trim();
        int spacePos = userInput.indexOf(" ");
        if (spacePos == -1) {
            throw new ParserException("Find userInput must have keyword(s) behind");
        } else {
            String argument = userInput.substring(spacePos + 1);
            argument = argument.trim();
            return new FindCommand(argument);
        }
    }

    /**
     * Processes user input and calls relevant Command.
     *
     * @param userInput User input as a String.
     * @return Command object --> cld be todo, deadline, event, delete, mark etc.
     * @throws ParserException Self-defined Exception Class which identifies error (using error message).
     */
    public Command parse(String userInput) throws ParserException {
        if (userInput.contains("bye")) {
            return new ByeCommand();
        } else if (userInput.contains("list")) {
            return new ListCommand();
        } else if (userInput.contains("mark") || userInput.contains("unmark")) {
            return parseMark(userInput);
        } else if (userInput.contains("todo") || userInput.contains("deadline") || userInput.contains("event")) {
            return parseTodoDeadlineEvent(userInput);
        } else if (userInput.contains("delete")) {
            return parseDelete(userInput);
        } else if (userInput.contains("find")) {
            return parseFind(userInput);
        } else if (userInput.contains("cheer")) {
            return new CheerCommand();
        } else if (userInput.contains("help")) {
            return new HelpCommand();
        } else {
            throw new ParserException("Invalid Command.");
        }
    }
}
