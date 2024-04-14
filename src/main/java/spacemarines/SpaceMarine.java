package spacemarines;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;
import static main.App.collectionManager;
import static utilites.CheckingReader.checkyRead;

public class SpaceMarine implements Comparable<SpaceMarine> {
    /**
     * id {@link SpaceMarine}Значение поля должно быть больше 0,
     * Значение этого поля должно быть уникальным,
     * Значение этого поля должно генерироваться автоматически
     */
    private int id;
    /**
     * Имя Space Marine.
     * Поле не может быть null и не может быть пустым.
     */
    /**
     * Имя {@link SpaceMarine}.
     * Поле не может быть null и не может быть пустым.
     */
    private String name;

    /**
     * Координаты {@link SpaceMarine}.
     * Поле не может быть null.
     */
    private Coordinates coordinates;

    /**
     * Дата создания {@link SpaceMarine}.
     * Поле не может быть null и его значение должно генерироваться автоматически.
     * Формат даты: "dd-MM-yyyy HH:mm"
     */
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime creationDate;

    /**
     * Уровень здоровья {@link SpaceMarine}.
     * Значение поля должно быть больше 0.
     */
    private long health;

    /**
     * Флаг лояльности {@link SpaceMarine}.
     * Поле может быть null.
     */
    private Boolean loyal;

    /**
     * Рост {@link SpaceMarine}.
     */
    private float height;

    /**
     * Вид оружия {@link SpaceMarine}.
     * Поле может быть null.
     */
    private Weapon weaponType;

    /**
     * Глава {@link SpaceMarine}.
     * Поле не может быть null.
     */
    private Chapter chapter;


    public SpaceMarine(String n, Coordinates c, long h, Boolean l, float height, Weapon gun, Chapter ch) {
        super();
        this.name = n;
        this.health = h;
        this.coordinates = c;
        this.loyal = l;
        this.weaponType = gun;
        this.chapter = ch;
        this.height = height;
        this.id = collectionManager.getCollectionSize();
        this.creationDate = now();


    }

    public SpaceMarine() {

    }

    public float getHeight() {
        return this.height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void update(String[] args) {

        this.name = (String) checkyRead("s", args[0]);
        this.coordinates = new Coordinates(
                (Long) checkyRead("l", args[1]),
                (Float) checkyRead("f", args[2]));

        this.health = (Long) checkyRead("l", args[3]);
        this.loyal = (Boolean) checkyRead("b", args[4]);
        this.height = (Float) checkyRead("f", args[5]);
        this.weaponType = Weapon.choose(
                (String) checkyRead("s", args[6]));
        this.chapter = new Chapter(
                (String) checkyRead("s", args[7]),
                (String) checkyRead("s", args[8]));
    }

    public long getHealth() {
        return health;
    }

    public void setHealth(long health) {
        this.health = health;
    }

    public Chapter getChapter() {
        return chapter;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Boolean getLoyal() {
        return this.loyal;
    }

    public void setLoyal(Boolean loyal) {
        this.loyal = loyal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Weapon getWeaponType() {
        return weaponType;

    }

    public void setWeaponType(Weapon weaponType) {
        this.weaponType = weaponType;
    }

    @Override
    public String toString() {

        return "***** " + this.getClass() + " Details *****\n" +
                "ID=" + getId() + "\n" +
                "Name=" + getName() + "\n" +
                "health=" + getHealth() + "\n" +
                "Coordinates=" + getCoordinates() + "\n" +
                "loyal=" + getLoyal() + "\n" +
                "chapter=" + getChapter() + "\n" +
                "weapoonType=" + getWeaponType() + "\n" +
                "height=" + getHeight() + "\n" +
                "creationDate=" + getCreationDate() + "\n" +
                "*****************************";
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(SpaceMarine other) {
        return this.name.compareTo(other.name);
    }
}
