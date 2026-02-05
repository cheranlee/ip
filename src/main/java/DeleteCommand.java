public class DeleteCommand extends Command{

    private String fullCommand;

    public DeleteCommand(String fullCommand){
        this.fullCommand = fullCommand;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws DuckException{
        try {
            int index = Integer.parseInt(this.fullCommand);
            index = index - 1;
            if (index < tasks.size() && index >= 0) {
                tasks.deleteItem(index);
                storage.deleteFromFile(index);
            } else {
                throw new DuckException("ERROR! Item Number out of range");
            }
        } catch (NumberFormatException ex3) {
            throw new DuckException("ERROR! Index not a valid number");
        }
    }
}
