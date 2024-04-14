package commands;

import utilites.interfaces.*;
import main.*;

import static main.App.collectionManager;

public class Clear extends Command implements methods {

    public final String name = "clear";

    public Clear() {
        super();
        this.commandType = CommandType.WITHOUT_ARGUMENTS;
    }

    public Response calling(String[] args, String v) {
        Response resp = super.calling(args, v);
        collectionManager.clear();
        return resp;
    }
}

