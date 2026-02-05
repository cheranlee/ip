package duck;

import duck.command.ByeCommand;
import duck.command.Command;
import duck.command.DeadlineCommand;
import duck.command.DeleteCommand;
import duck.command.EventCommand;
import duck.command.ListCommand;
import duck.command.MarkUnmarkCommand;
import duck.command.TodoCommand;

/**
 * Defines enum such that TaskType can only be Todo, Deadline or Event
 */
public enum TaskType{
    ToDos, Deadlines, Events;
}