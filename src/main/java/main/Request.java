package main;
import java.lang.reflect.Field;

public class Request extends Message {
    public Command commandToExecute;

    public Command getCommandToExecute() {
        return commandToExecute;
    }

    public void setCommandToExecute(Command commandToExecute) {
        this.commandToExecute = commandToExecute;
    }

}
