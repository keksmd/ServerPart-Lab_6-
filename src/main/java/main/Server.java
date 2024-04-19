package main;
import exceptions.MessageWasNotReadedSuccessfull;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.channels.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.LinkedBlockingDeque;

import static java.nio.channels.SelectionKey.OP_READ;
import static main.App.log;
import static utilites.AccessingAllClassesInPackage.getAllClasses;
import static utilites.ServerMessaging.nioRead;
import static utilites.ServerMessaging.nioSend;


public class Server{
    static final LinkedBlockingDeque<Request> requests = new LinkedBlockingDeque<Request>(100);
    public static HashMap<String, Method> nameToHandleMap = new HashMap<>();
    private static boolean flag = true;
    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    private Client client;

    public Server(int port) {
        try {
            log.info("Программа запущенна");
            this.serverSocketChannel = ServerSocketChannel.open();
            this.serverSocketChannel.bind(new InetSocketAddress(port));
            this.serverSocketChannel.configureBlocking(false);
            this.selector = Selector.open();
            this.serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            log.info("Сервер настроен");
        } catch (IOException e) {
            log.error("Cервер не настроен", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * Метод,задающий команды,которыми будут пользоваться клиент <br>
     * </>и сервер из пакета commands, где лежат реализации команд
     */

    private void setCommands(){
        this.client.firstMessageFromClient = false;
        StringBuilder sb = new StringBuilder();
        getAllClasses("commands").stream().
                filter(w ->
                        Arrays.stream(w.getFields()).anyMatch(
                                x -> (x.getName().equals("commandType"))) &&
                                Arrays.stream(w.getFields()).anyMatch(
                                        y -> y.getName().equals("name"))).
                forEach(w -> {
                    try {
                        nameToHandleMap.put(String.valueOf(w.getField("name").get(w.getConstructor().newInstance())),w.getMethod("staticFactory",String[].class,String.class));
                        sb.
                                append(w.getField("name").get(w.getConstructor().newInstance())).
                                append(",").
                                append(w.getField("commandType").get(w.getConstructor().newInstance()).toString()).
                                append(";");
                    } catch (IllegalAccessException | NoSuchFieldException |
                             NoSuchMethodException |
                             InstantiationException |
                             InvocationTargetException ignored) {
                    }
                });

        try {
            nioSend(this.getClientChannel(), sb.toString());
        } catch (IOException e) {
        }

    }

    /**
     * Метод,запускающий работу объекта сервера
     * @throws IOException в случае ошибки при преме-передаче сообщений
     */

    public void run() throws IOException {

        while (true) {//true
            this.getSelector().select();
            log.info("мощность итератора по селетору = {}", getSelector().selectedKeys().size());
            Iterator<SelectionKey> keysIterator = this.getSelector().selectedKeys().iterator();
            try {
                while (keysIterator.hasNext()) {
                    this.handleKey(keysIterator.next());
                    keysIterator.remove();
                }
            } catch (SocketException | ClosedChannelException e) {
                client.channel.close();
            } catch (Exception e) {
                log.error("ошибка,сервер чуть не лег", e);
            }
        }

    }

    /**
     * метод, определяющий готовность ключа к тем или иным действиям, и вызывающий методы их реализации
     * @param key
     * @throws IOException (пробрасывает выше) в случае ошибки при преме-передаче сообщений
     */
    private void handleKey(SelectionKey key) throws IOException {
        if (key.isAcceptable()) {
            this.handleAcception();
        }else if (key.isReadable()) {
            this.handleRead();
        }else if (key.isWritable()) {
            this.handleWrite();
        }
    }

    /**
     * метод обработки подключения ключа
     * @throws IOException
     */
    private void handleAcception() throws IOException{
        log.info("ключ оказался доступным");
        this.setClientChannel(this.getServerSocketChannel().accept());
        this.client.channel.configureBlocking(false);
        this.getClientChannel().register(this.getSelector(), OP_READ);
        log.info("Зарегали на селектор с read");
    }
    /**
     * метод чтения сообщения ключа
     */

    private void handleRead(){
        Request request = null;
        log.info("ключ оказался читаемым");
        try {
            request = nioRead(this.getClientChannel());
        } catch (IOException | MessageWasNotReadedSuccessfull e) {
            log.error("непрочитали(", e);
        }
        if (request != null) {
            requests.add(request);
            try {
                this.getClientChannel().register(this.getSelector(), SelectionKey.OP_WRITE, OP_READ);
            } catch (ClosedChannelException ignored) {}
        }
    }
    /**
     * метод обработки запроса клиента и отправки ответа
     * @throws IOException
     */
    private void handleWrite() throws  IOException{
        log.info("ключ оказался писаемым");
        Request request = requests.poll();
        if (request != null) {
            if (request.getMessages().get(0).equals("commands") && client.firstMessageFromClient) {
                setCommands();
            } else {
                try {
                request.commandToExecute = request.commandToExecute.revalidate(request.getMessages().get(0));

                    nioSend(this.getClientChannel(), request.getCommandToExecute().calling(request.commandToExecute.getArgs(), request.getCommandToExecute().getValue()));

                } catch (InvocationTargetException | IllegalAccessException e) {
                    log.info("Ошибка: неверно реализована команда",e);
                } catch (IOException e) {
                    log.info("неудачно отправили сообщение", e);
                    flag = false;
                }
            }
        } else {
            log.info("null");
        }
        if (flag) {
            this.getClientChannel().register(this.getSelector(), OP_READ);
        }
        flag = true;
    }


    public ServerSocketChannel getServerSocketChannel() {
        return serverSocketChannel;
    }

    public void setServerSocketChannel(ServerSocketChannel serverSocketChannel) {
        this.serverSocketChannel = serverSocketChannel;
    }

    public SocketChannel getClientChannel() {
        return this.client.channel;
    }

    public void setClientChannel(SocketChannel clientChannel) {
        this.client = new Client(clientChannel);
    }
    public Selector getSelector() {
        return selector;
    }

    public void setSelector(Selector selector) {
        this.selector = selector;
    }
}
