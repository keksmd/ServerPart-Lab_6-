package utilites;

import spacemarines.SpaceMarine;

import java.util.HashMap;

import static main.App.collectionManager;

public class GetById {
    static final HashMap<Integer, SpaceMarine> map = new HashMap<>();

    static {
        collectionManager.getCollectionStream().forEach(w -> map.put(w.getId(), w));
    }

    public static SpaceMarine getById(int id) {
        return map.get(id);
    }
}
