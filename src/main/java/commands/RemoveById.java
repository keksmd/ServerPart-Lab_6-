package commands;

import main.Command;
import main.CommandType;
import main.Response;
import utilites.interfaces.Methods;

import static main.App.collectionManager;

public class RemoveById extends Command implements Methods {
    public RemoveById() {
        super();
        this.commandType = CommandType.VALUE_ARGUMENTED;
    }

    public final String name = "remove_by_id";
    public static RemoveById staticFactory(String[] args,String value){
        RemoveById inst =  new RemoveById();
        inst.setValue(value);
        inst.setArgs(args);
        return  inst;
    };



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
