package commands;
import main.Command;
import main.CommandType;
import main.Response;
import utilites.interfaces.methods;

import static main.App.collectionManager;

public class RemoveHead extends Command implements methods {
    public final String name = "remove_head";

    public RemoveHead() {
        super();
        this.commandType = CommandType.WITHOUT_ARGUMENTS;
    }

    public Response calling(String[] args, String v) {
        Response resp = super.calling(args, v);
        collectionManager.poll();
        return resp;
    }
}
