/**
 * Класс,(вызвавши много жопной боли у разработчика),метод {@link utilites.CheckingReader#checkyRead(String, String, String, String)} которого
 * считывает аргументы команд,анализирует и принимает решение,использовать их,передав далее по цепочке
 * или запросить заново в связи с ошибкой ввода
 */
package utilites;


import java.util.*;
import java.util.function.Supplier;

import exceptions.*;


public class CheckingReader {


    public static Object checkyRead(String type, String input) {
        Scanner sc = new Scanner(input);
        Supplier<?> append;

        Object o = null;
        append = switch (type.toLowerCase()) {
            case "b" -> sc::nextBoolean;
            case "i" -> sc::nextInt;
            case "l" -> sc::nextLong;
            case "f" -> sc::nextFloat;
            case "s" -> sc::nextLine;
            default -> throw new IncorrectCommandUsing("Неверный тип данных");

        };
        try {
            return o = switch (type) {
                case "b" -> (Boolean) append.get();
                case "i" -> (Integer) append.get();
                case "l" -> (Long) append.get();
                case "f" -> (Float) append.get();
                case "s" -> (String) append.get();
                default -> o;
            };
        } catch (NoSuchElementException e) {

            throw new IncorrectCommandUsing("missed datatype in checkingReader");

        }

    }
}