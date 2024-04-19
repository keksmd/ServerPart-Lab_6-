package commands;
import main.Command;
import main.CommandType;
import main.Response;
import spacemarines.SpaceMarine;
import utilites.interfaces.Callable;

import static main.App.collectionManager;

public class PrintFieldDescendingLoyal extends Command implements Callable {
    public PrintFieldDescendingLoyal() {
        super();
        this.commandType = CommandType.WITHOUT_ARGUMENTS;
    }

    public final String name = "print_field_descending_loyal";
    public static PrintFieldDescendingLoyal staticFactory(String[] args,String value){
        PrintFieldDescendingLoyal inst =  new PrintFieldDescendingLoyal();
        inst.setValue(value);
        inst.setArgs(args);
        return  inst;
    };



    public Response calling(String[] args, String v) {
        Response resp = super.calling(args, v);
        StringBuilder s = new StringBuilder();
        collectionManager.getCollectionStream().filter(SpaceMarine::getLoyal).forEach(s::append);
        collectionManager.getCollectionStream().filter(w -> !w.getLoyal()).forEach(s::append);
        resp.addMessage(s.toString());
        return resp;

    }
}
