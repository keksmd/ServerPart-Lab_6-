/**
 * Класс реализует паттерн command, и служит
 * для вызова разных команд в зависимости от того,что было введено в консоль
 */
package main;

import commands.*;
import utilites.interfaces.Methods;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Command implements Methods {
    public CommandType commandType = null;

    public String[] args;
    String value;
    private String name = "command";
    /**
     * общий для всех классов-комманд,являющихся наследниками {@link Command}
     * метод,реализующий взаимодействие с коллекцией
     *
     * @return по умолчанию возвращает true, в реализациях boolean,показывающий,была ли выполнена команда успешно
     */
    public Response calling(String[] a, String v) {
        Response resp = new Response();
        this.value = v;
        this.args = a;
        resp.setSuccess(true);
        return resp;

    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }



    public CommandType getCommandType() {
        return commandType;
    }

    public void setCommandType(CommandType commandType) {
        this.commandType = commandType;
    }

    public Command revalidate(String name) {
        return extractCommand(name).castInto(this);
    }

    public Command castInto(Command name) {
        Command answer;
        try {
            answer = this.getClass().getConstructor().newInstance();
            answer.setArgs(name.getArgs());
            answer.setName(name.getName());
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        return answer;
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append("***** ").append(this.getClass()).append(" Details *****\n");
        for (Field f : this.getClass().getFields()) {
            try {
                f.setAccessible(true);
                if (f.get(this) == null) {
                    s.append(f.getName()).append("=").append("null").append("\n");
                } else {
                    s.append(f.getName()).append("=").append(f.get(this).toString()).append("\n");
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        s.append("*****************************");

        return s.toString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Метод, определяющий команду по вводу str
     *
     * @param str - текстовое значение команды
     * @return объект, поле cmd,которого имеет реализацию команды переданной в аргументах
     */
    public static Command extractCommand(String str){
        String[] tokens = str.split(" ");
        String prefix = "";
        for(int i = 0;i< tokens.length;i++){
            prefix+=tokens[i];
            if(Server.nameToHandleMap.containsKey(prefix)) {
                Method factory = Server.nameToHandleMap.get(prefix);
                if (i < tokens.length - 1) {
                    try {
                        return (Command)factory.invoke(null, tokens[i + 1], null);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                }else{
                    try {
                        return (Command) factory.invoke(null, null, null);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            prefix+=" ";
        }
        return new NotFound();

    }

}
