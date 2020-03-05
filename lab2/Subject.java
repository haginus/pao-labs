package lab2;

public class Subject {
    private int noOfStudents;
    private Room room;
    private Person teacher;

    public Subject(Room room, Person teacher, int noOfStudents) {
        this.room = room;
        this.teacher = teacher;
        this.noOfStudents = noOfStudents;
    }

    public int getNoOfStudents() {
        return noOfStudents;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Person getTeacher() {
        return teacher;
    }

    public void setTeacher(Person teacher) {
        this.teacher = teacher;
    }

    public void setNoOfStudents(int noOfStudents) {
        this.noOfStudents = noOfStudents;
    }

    @Override
    public String toString() {
        return "At " + room.toString() + "\n" + "By " + teacher + "\n" + noOfStudents + " people attending";
    }
}
