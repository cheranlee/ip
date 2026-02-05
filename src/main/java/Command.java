public abstract class Command {
    public abstract void execute(TaskList tasklist, Ui ui, Storage storage)
        throws DuckException;

    public boolean isExit(){
        return false;
    }
}
