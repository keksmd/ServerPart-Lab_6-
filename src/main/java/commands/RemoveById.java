package commands;

import main.Command;
import main.CommandType;
import main.Response;
import utilites.interfaces.methods;

import static main.App.collectionManager;

public class RemoveById extends Command implements methods {

    public final String name = "remove_by_id";

    public RemoveById() {
        super();
        this.commandType = CommandType.VALUE_ARGUMENTED;
    }

    public Response calling(String[] args, String v) {
        Response resp = super.calling(args, v);
        if (collectionManager.getCollectionStream().anyMatch(w -> String.valueOf(w.getId()).equals(this.getValue()))) {
            collectionManager.removeById(Integer.parseInt(this.getValue()));
        } else {
            resp.addMessage("Ошибка, не существует элемента с таким ID");
            resp.setSuccess(false);
        }
        return resp;
    }

}
