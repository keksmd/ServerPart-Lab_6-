package commands;

import main.Command;
import main.CommandType;
import main.Response;
import spacemarines.Weapon;
import utilites.interfaces.methods;

import java.util.Arrays;

import static main.App.collectionManager;

public class GroupByWeapon extends Command implements methods {
    public final String name = "group_counting_by_weapon_type";

    public GroupByWeapon() {
        super();
        this.commandType = CommandType.WITHOUT_ARGUMENTS;
    }

    public Response calling(String[] args, String v) {
        Response resp = super.calling(args, v);
        StringBuilder s = new StringBuilder();
        Arrays.stream(Weapon.values()).forEach(gun -> s.append(String.format("%s : %d%n", gun.name(), collectionManager.getCollectionStream().filter(w -> w.getWeaponType() == gun).count())));
        resp.addMessage(s.toString());
        return resp;
    }

}
