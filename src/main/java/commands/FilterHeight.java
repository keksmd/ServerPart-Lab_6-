package commands;
import main.Command;
import main.CommandType;
import main.Response;
import utilites.interfaces.methods;

import static main.App.collectionManager;

public class FilterHeight extends Command implements methods {
    public final String name = "filter_greater_than_height";

    public FilterHeight() {
        super();
        this.commandType = CommandType.VALUE_ARGUMENTED;
    }

    public Response calling(String[] args, String v) {
        Response resp = super.calling(args, v);
        StringBuilder s = new StringBuilder();
        collectionManager.getCollectionStream().filter(w -> w.getHeight() > Integer.parseInt(getValue())).forEach(s::append);
        resp.addMessage(s.toString());
        return resp;
    }
}
