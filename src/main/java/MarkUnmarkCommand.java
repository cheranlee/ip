public class MarkUnmarkCommand extends Command{

    private String fullCommand;

    public MarkUnmarkCommand(String fullCommand){
        this.fullCommand = fullCommand;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DuckException{
        try {
            int index = Integer.parseInt(this.fullCommand);
            index = index - 1;
            if (index < tasks.size() && index >= 0) {
                try {
                    String editedItemString = tasks.markUnmarkItem(!this.fullCommand.contains("unmark"), index);
                    storage.editFile(index, editedItemString);
                } catch (IllegalArgumentException ex) {
                    if (ex.getMessage().contains("Not")) {
                        throw new DuckException("\tItem already marked as not done!");
                    } else {
                        throw new DuckException("\tItem already marked as done!");
                    }
                }
            } else {
                throw new DuckException("ERROR! Item Number out of range");
            }
        } catch (NumberFormatException ex2) {
            throw new DuckException("ERROR! Index not a valid number");
        }
    }
}
