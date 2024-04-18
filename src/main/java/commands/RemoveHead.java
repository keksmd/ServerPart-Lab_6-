package commands;
import main.Command;
import main.CommandType;
import main.Response;
import utilites.interfaces.Methods;

import static main.App.collectionManager;

public class RemoveHead extends Command implements Methods {
    public final String name = "remove_head";
    public static RemoveHead staticFactory(String[] args,String value){
        RemoveHead inst =  new RemoveHead();
        inst.setValue(value);
        inst.setArgs(args);
        return  inst;
    };

    public RemoveHead() {
        super();
        this.commandType = CommandType.WITHOUT_ARGUMENTS;
    }

    public Response calling(String[] args, String v) {
        Response resp = super.calling(args, v);
        collectionManager.poll();
        return resp;
    }
}
