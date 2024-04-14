package spacemarines;

public class Coordinates {
    private Long x; //Максимальное значение поля: 625, Поле не может быть null
    private Float y; //Значение поля должно быть больше -354, Поле не может быть null

    public Coordinates(Long x1, Float y1) {
        this.x = x1;
        this.y = y1;
    }

    public Coordinates() {
    }

    public Long getX() {
        return x;
    }

    public void setX(Long x) {
        this.x = x;
    }

    ;

    public Float getY() {
        return y;
    }

    public void setY(Float y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return x + "," + y;
    }
}
