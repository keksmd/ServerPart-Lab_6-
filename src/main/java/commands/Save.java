package commands;
import main.Command;
import main.CommandType;
import main.Response;
import utilites.interfaces.Methods;

import static main.App.collectionManager;


public class Save extends Command implements Methods {
    public Save() {
        super();
        this.commandType = CommandType.WITHOUT_ARGUMENTS;
    }
    public final String name = "save";
    public static Save staticFactory(String[] args,String value){
        Save inst =  new Save();
        inst.setValue(value);
        inst.setArgs(args);
        return  inst;
    };



    public Response calling(String[] args, String v) {
        Response resp = super.calling(args, v);
        collectionManager.save();
        return resp;
    }

}