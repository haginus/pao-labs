package lab2;



public class ex2 {
    public static  void main(String[] args) {
        Person p1 = new Person("John", "Doe", 24, 1123444L, "student");
        Person p2 = new Person("Jane", "Roe", 56, 2233444L, "teacher");

        System.out.println(p1.getName() + " " + p1.getSurname() + "(age " + p1.getAge() + ") " + p1.getId() + " " + p1.getType());
        System.out.println(p2.getName() + " " + p2.getSurname() + " (age " + p2.getAge() + ") " + p2.getId() + " " + p2.getType());
    }
}
