package commands;
import main.Command;
import main.CommandType;
import main.Response;
import utilites.interfaces.methods;

import static main.App.collectionManager;


public class Save extends Command implements methods {
    public final String name = "save";

    public Save() {
        super();
        this.commandType = CommandType.WITHOUT_ARGUMENTS;
    }

    public Response calling(String[] args, String v) {
        Response resp = super.calling(args, v);
        collectionManager.save();
        return resp;
    }

}