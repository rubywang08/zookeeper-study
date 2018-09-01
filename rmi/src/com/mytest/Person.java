package src.com.mytest;

import java.util.Objects;

public class Person {
    private String name;      //姓名
    private String location;  //地址

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "Person:" + name + "," + location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(name, person.name) &&
                Objects.equals(location, person.location);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, location);
    }
}
