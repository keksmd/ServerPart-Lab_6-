package commands;

import main.Command;
import main.CommandType;
import main.Response;
import spacemarines.Chapter;
import spacemarines.Coordinates;
import spacemarines.SpaceMarine;
import spacemarines.Weapon;
import utilites.interfaces.methods;

import static main.App.collectionManager;
import static utilites.CheckingReader.checkyRead;

public class Add extends Command implements methods {
    public final String name = "add";


    public Add() {
        super();
        this.commandType = CommandType.ELEMENT_ARGUMENTED;

    }

    public Response calling(String[] args, String v) {
        Response resp = super.calling(args, v);


        collectionManager.add(
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

        collectionManager.sort();
        return resp;

    }

}
