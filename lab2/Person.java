package lab2;

public class Person {
    private String name;
    private String surname;
    private int age;
    private long id;
    private String type;

    public Person() {
        type = "student";
    }

    public Person(String name, String surname, int age, long id, String type) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.id = id;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public int getAge() {
        return age;
    }

    public long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return name + " " + surname + ", age " + age + ", id " + id + ", " + type;
    }
}
