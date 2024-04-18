package commands;
import main.Command;
import main.CommandType;
import main.Response;
import spacemarines.SpaceMarine;
import utilites.interfaces.Methods;

import static main.App.collectionManager;

public class Info extends Command implements Methods {
    public final String name = "info";
    public static Info staticFactory(String[] args,String value){
        Info inst =  new Info();
        inst.setValue(value);
        inst.setArgs(args);
        return  inst;
    };
    public Info() {
        super();
        this.commandType = CommandType.WITHOUT_ARGUMENTS;
    }

    public Response calling(String[] args, String v) {
        Response resp = super.calling(args, v);
        resp.addMessage(String.format("В коллекции : %s, обновленной %s,хранится %d элементов", SpaceMarine.class.getName(), collectionManager.lastUpdated != null ? collectionManager.lastUpdated : "never", collectionManager.getCollectionSize()));
        return resp;
    }

}
