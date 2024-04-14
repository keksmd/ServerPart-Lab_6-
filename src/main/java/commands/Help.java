package commands;
import main.Command;
import main.CommandType;
import main.Response;
import utilites.interfaces.methods;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;


public class Help extends Command implements methods {
    public final String name = "help";

    public Help() {
        super();
        this.commandType = CommandType.WITHOUT_ARGUMENTS;
    }

    public Response calling(String[] args, String v) {
        Response resp = super.calling(args, v);
        resp.setMessages(Arrays.stream("help| справка/info| информация/show| элементы коллекции/add{element}| добавить элемент/update{id element}| обновить элемент по id/remove_by_id{id}| удалить элемент id/clear| очистить/save| сохранить/execute_script{filename}| исполнить команды из файла/exit| выйти/remove_head| удалить и показать первый элемент/add_if_max{element}| добавить элемент,если он-максимальный/add_if_min{element}| добавить элемент,если он-минимальный/group_counting_by_weapon_type| элементы коллекции по значению weaponType/filter_greater_than_height{height}| элементы,с height больше заданного/print_field_descending_loyal| вывести loyal всех элементов".split("/")).collect(Collectors.toCollection(ArrayList::new)));
        return resp;
    }
}
