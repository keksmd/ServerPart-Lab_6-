package main;

import main.CollectionManager;
import main.Server;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

public class App {
    public static final Logger log = LogManager.getLogger(App.class.getName());
    public static final String fileName = System.getenv("JSON_LIB");
    public static File jarFile;
    public static CollectionManager collectionManager;
    public static Server server = null;

    private App() {
    }

    public static void main(String[] args) {
        collectionManager = CollectionManager.getCollectionManager();
        try {
            jarFile = new File(App.class.getProtectionDomain().getCodeSource().getLocation().toURI());
        } catch (URISyntaxException e) {
            log.error(e);
            throw new RuntimeException(e);

        }
        log.info(jarFile.getParentFile());


        try {

            /*File f  = new File(fileName);
            if (f.exists()) {
                CollectionManager.lastUpdated = new Date(f.lastModified());
                if (f.length() > 0) {
                    CollectionManager.getCollection().addAll(readAndUpdate(fileName, new TypeReference<>() {
                    }));
                }
            } else{
                new File(fileName).createNewFile();
            }

             */

            collectionManager.sort();
            server = new Server(8081);

            server.run();
        } catch (IOException e) {
            log.error("ошибка, приложение чуть не упало чуть не лег", e);
        }

    }


}