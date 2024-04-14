/**
 * Класс управляющий основной коллекцией
 */
package main;

import spacemarines.SpaceMarine;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static utilites.ObjectConverter.toJson;

public class CollectionManager {
    private static CollectionManager manager = null;
    public Date lastUpdated;
    private PriorityQueue<SpaceMarine> collection = new PriorityQueue<>();

    private CollectionManager() {
        this.collection = new PriorityQueue<>();
    }

    public static CollectionManager getCollectionManager() {
        if (manager == null) {
            manager = new CollectionManager();
        }
        return manager;
    }

    public void add(SpaceMarine spm) {
        this.collection.add(spm);
    }

    public Stream<SpaceMarine> getCollectionStream() {
        return collection.stream();
    }

    public void clear() {
        collection.clear();
    }

    /**
     * Основная коллекция
     */
    public void sort() {
        this.collection = collection.stream().sorted().collect(Collectors.toCollection(PriorityQueue::new));
    }

    public void removeById(int id) {
        collection.removeIf(c -> c.getId() == id);
    }

    public boolean isEmpty() {
        return collection.isEmpty();
    }

    public SpaceMarine poll() {
        return collection.poll();
    }

    public void describe() {

    }

    public void save() {
        collection.forEach(spaceMarine -> {
            String jsonString = toJson(spaceMarine) + "\n";
            try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(App.jarFile.getParentFile(), App.fileName)))) {
                bos.write(jsonString.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public int getCollectionSize() {
        return collection.size();
    }


}
