import java.util.Scanner;
public class pb2
{
    public static void main(String[] args) {
        Scanner scanner = new Scanner (System. in );
        String integ = scanner.nextLine();
        Integer a = Integer.parseInt(integ);
        integ = scanner.nextLine();
        Integer b = Integer.parseInt(integ);
        String r1 = a.toString();
        String r2 = a.toString();
        String r3 = a.toString();
        if(a == b) r1 += " == " + b;
        else r1 += " != " + b;
        if(a < b) {
            r2 += " < " + b;
            r3 += " <= " + b;
        }
        else {
            r2 += " > " + b;
            r3 += " >= " + b;
        }
       
        System.out.println(r1);
        System.out.println(r2);
        System.out.println(r3);
    }
 
}