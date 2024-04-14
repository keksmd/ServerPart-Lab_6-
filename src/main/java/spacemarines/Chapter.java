package spacemarines;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;

public class Chapter {
    @JsonIgnore
    static final ArrayList<Chapter> chapters = new ArrayList<>();
    private String name; //Поле не может быть null, Строка не может быть пустой
    private String parentLegion;
    private String world; //Поле не может быть null

    public Chapter(String n, String w) {
        world = w;
        name = n;
        this.parentLegion = "";
    }

    public Chapter() {
    }

    ;

    public Chapter createChapter(String n, String w) {
        Chapter s = null;
        boolean flag = true;
        for (Chapter chap : chapters) {
            if (chap.equals(new Chapter(n, w))) {
                flag = false;
                s = chap;
            }
        }
        if (flag) {
            s = new Chapter(n, w);
            chapters.add(s);
        }
        return s;
    }

    @Override
    public boolean equals(Object obj) {
        boolean answ = true;
        if (this.getClass() == obj.getClass()) {
            Chapter c1 = (Chapter) obj;
            if (!c1.name.equals(this.name) || !c1.world.equals(this.world) || !c1.parentLegion.equals(this.parentLegion)) {
                answ = false;
            }
        } else {
            answ = false;
        }
        return answ && super.equals(obj);
    }

    @Override
    public String toString() {
        return name + "," + parentLegion + "," + world;
    }

    public String getWorld() {
        return world;
    }

    public void setWorld(String world) {
        this.world = world;
    }

    public String getParentLegion() {
        return parentLegion;
    }

    public void setParentLegion(String parentLegion) {
        this.parentLegion = parentLegion;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
