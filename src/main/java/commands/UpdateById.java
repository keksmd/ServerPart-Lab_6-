package commands;
import main.Command;
import main.CommandType;
import main.Response;
import utilites.interfaces.methods;

import static main.App.collectionManager;

public class UpdateById extends Command implements methods {
    public final String name = "update_by_id";

    public UpdateById() {
        super();
        this.commandType = CommandType.ELEMENT_AND_VALUE_ARGUMENTED;
    }

    public Response calling(String[] args, String v) {
        Response resp = super.calling(args, v);
        if (collectionManager.getCollectionStream().anyMatch(w -> w.getId() == Integer.parseInt(this.getValue()))) {
            collectionManager.getCollectionStream().filter(w -> w.getId() == Integer.parseInt(this.getValue())).findFirst().get().update(this.getArgs());
        } else {
            resp.addMessage("Ошибка, не существует элемента с таким ID");
            resp.setSuccess(false);
        }
        return resp;
    }

}