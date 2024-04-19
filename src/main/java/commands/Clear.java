package commands;

import utilites.interfaces.*;
import main.*;

import static main.App.collectionManager;

public class Clear extends Command implements Callable {


    public Clear() {
        super();
        this.commandType = CommandType.WITHOUT_ARGUMENTS;
    }
    public final String name = "clear";
    public static Clear staticFactory(String[] args,String value){
        Clear inst =  new Clear();
        inst.setValue(value);
        inst.setArgs(args);
        return  inst;
    };


    public Response calling(String[] args, String v) {
        Response resp = super.calling(args, v);
        collectionManager.clear();
        return resp;
    }
}

