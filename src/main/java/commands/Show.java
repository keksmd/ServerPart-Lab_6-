package commands;
import main.Command;
import main.CommandType;
import main.Response;
import utilites.interfaces.Methods;

import static main.App.collectionManager;

public class Show extends Command implements Methods {
    public final String name = "show";
    public static Show staticFactory(String[] args,String value){
        Show inst =  new Show();
        inst.setValue(value);
        inst.setArgs(args);
        return  inst;
    };
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
