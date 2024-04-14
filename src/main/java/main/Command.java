/**
 * Класс реализует паттерн command, и служит
 * для вызова разных команд в зависимости от того,что было введено в консоль
 */
package main;

import commands.*;
import commands.UpdateById;
import commands.Add;
import commands.AddIfMax;
import commands.AddIfMin;
import commands.FilterHeight;
import commands.RemoveById;
import main.CommandType;
import main.Response;
import utilites.interfaces.methods;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class Command implements methods {
    public CommandType commandType = null;
    /**
     * Переменная,где хранится ссылка на наследника {@link Command},который и реализует нужную команду
     */

    public String[] args;
    String value;
    private String name = "command";

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

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

    public CommandType getCommandType() {
        return commandType;
    }

    public void setCommandType(CommandType commandType) {
        this.commandType = commandType;
    }

    public Command revalidate(String name) {
        return commandReader(name).castInto(this);
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
     * @return объект, поле cmd,которого имеет реализацию команды переданной в {@link Command#commandReader(String)}
     */
    public Command commandReader(String str) {
        Command cmd;
        String[] words = str.split(" ");
        if (words.length == 1) {
            cmd = switch (str.toLowerCase()) {
                case "add" -> new Add();
                case "add_if_max" -> new AddIfMax();
                case "add_if_min" -> new AddIfMin();
                case "help" -> new Help();
                case "clear" -> new Clear();
                case "exit" -> new Exit();
                case "remove_head" -> new RemoveHead();
                case "group_counting_by_weapon_type" -> new GroupByWeapon();
                case "print_field_descending_loyal" -> new PrintFieldDescendingLoyal();
                case "show" -> new Show();
                case "info" -> new Info();
                default -> new NotFound();
            };
        } else if (words.length == 2) {
            switch (words[0].toLowerCase()) {
                case "update_by_id":
                    UpdateById upd = new UpdateById();
                    upd.setValue(words[1]);
                    cmd = upd;
                    break;

                case "remove_by_id":
                    RemoveById rmd = new RemoveById();
                    rmd.setValue(words[1]);
                    cmd = rmd;

                    break;
                case "filter_greater_than_height":
                    FilterHeight fh = new FilterHeight();
                    fh.setValue(words[1]);
                    cmd = fh;
                    break;
                default:
                    cmd = new NotFound();
            }
        } else {

            cmd = new NotFound();
        }

        return cmd;

    }
}
