package lab2;

public class ex3 {
    public static  void main(String[] args) {
        Room r1 = new Room("12A", "normal", 3);
        Room r2 = new Room();
        r2.setNumber("12B");
        r2.setType("tech");
        r2.setFloor(7);

        System.out.println(r1.getType() + " room " + r1.getNumber() + " at floor " + r1.getFloor());
        System.out.println(r2.getType() + " room " + r2.getNumber() + " at floor " + r2.getFloor());
    }
}
