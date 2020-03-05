package lab2;

import java.util.Scanner;

public class ex1 {
    public static  void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] studentGrades = new int[20];

        int grade = scanner.nextInt();
        int i = 0;
        float avg = 0;
        while(grade != -1 && i < studentGrades.length) {
            studentGrades[i] = grade;
            i++;
            avg += grade;
            grade = scanner.nextInt();
        }
        if(i != 0) {
            avg /= i;
            System.out.println(avg);
        } else System.out.println("Nu se poate face media.");

    }
}
