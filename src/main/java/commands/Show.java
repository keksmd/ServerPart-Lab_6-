package commands;
import main.Command;
import main.CommandType;
import main.Response;
import utilites.interfaces.methods;

import static main.App.collectionManager;

public class Show extends Command implements methods {
    public final String name = "show";

    public Show() {
        super();
        this.commandType = CommandType.WITHOUT_ARGUMENTS;
    }

    public Response calling(String[] args, String v) {
        Response resp = super.calling(args, v);
        if (collectionManager.isEmpty()) {
            resp.addMessage("В коллекции нет элементов");
        }
        collectionManager.getCollectionStream().forEach(
                w -> resp.addMessage(w + "\n"));
        return resp;
    }
}
