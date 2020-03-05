package lab2;

public class ex4 {
    public static  void main(String[] args) {
        Room r1 = new Room("12A", "normal", 3);
        Person p1 = new Person("Jane", "Roe", 56, 2233444L, "teacher");
        Room r2 = new Room("15", "lab", 2);
        Person p2 = new Person("Alex", "Popescu", 42, 2237L, "teacher");

        Subject s1 = new Subject(r1, p1, 20);
        Subject s2 = new Subject(r2, p2, 14);

        System.out.println(s1);
        System.out.println();
        System.out.println(s2);

    }
}
