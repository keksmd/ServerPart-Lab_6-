package commands;

import main.Command;
import main.CommandType;
import main.Response;
import spacemarines.Chapter;
import spacemarines.Coordinates;
import spacemarines.SpaceMarine;
import spacemarines.Weapon;
import utilites.interfaces.Methods;

import java.util.Comparator;

import static main.App.collectionManager;
import static utilites.CheckingReader.checkyRead;

public class AddIfMin extends Command implements Methods {
    public final String name = "add_if_min";

    public static AddIfMin staticFactory(String[] args,String value){
        AddIfMin inst =  new AddIfMin();
        inst.setValue(value);
        inst.setArgs(args);
        return  inst;
    };

    public AddIfMin() {
        super();
        this.commandType = CommandType.ELEMENT_ARGUMENTED;
    }

    public Response calling(String[] args, String v) {
        Response resp = super.calling(args, v);
        SpaceMarine spm = (
                new SpaceMarine(
                        (String) checkyRead("s", args[0]),
                        new Coordinates(
                                (Long) checkyRead("l", args[1]),
                                (Float) checkyRead("f", args[2])),
                        (Long) checkyRead("l", args[3]),
                        (Boolean) checkyRead("b", args[4]),
                        (Float) checkyRead("f", args[5]),
                        Weapon.choose(
                                (String) checkyRead("s", args[6])),
                        new Chapter(
                                (String) checkyRead("s", args[7]),
                                (String) checkyRead("s", args[8]))));
        if (spm.compareTo(collectionManager.getCollectionStream().min(Comparator.naturalOrder()).get()) < 0) {
            new Add().calling(args, v);
        } else {
            resp.setSuccess(false);
        }
        return resp;
    }

}
