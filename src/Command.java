/*
    The command pattern interface that allows for the execution of commands
 */
public interface Command {

   /*
    * The command pattern interface contains two methods:
    * Execute - Performs some action
    * Unexecute - Undo an action
    */

    public abstract void execute(String msg);

}
