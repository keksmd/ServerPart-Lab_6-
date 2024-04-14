package commands;

import main.Command;
import main.CommandType;
import main.Response;
import utilites.interfaces.methods;

public class Exit extends Command implements methods {

    public final String name = "exit";

    public Exit() {
        super();
        this.commandType = CommandType.WITHOUT_ARGUMENTS;
    }

    public Response calling(String[] args, String v) {
        Response resp = super.calling(args, v);
        resp.setFlag(false);
        new Save().calling(args, v);
        return resp;

    }


}
