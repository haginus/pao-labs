import java.util.Scanner;
public class pb3_7
{
    public static void t3() {
        Scanner scanner = new Scanner (System. in );
        String integ = scanner.nextLine();
        Integer n = Integer.parseInt(integ);
        Integer sum = 0;
        for(Integer i = 1; i <= n; i++) {
            if(i % 3 == 0 || i % 5 == 0) sum += i;
        }
        System.out.print(sum);
    }
   
    public static void t4() {
        Scanner scanner = new Scanner (System. in );
        String integ = scanner.nextLine();
        Integer n = Integer.parseInt(integ);
        Integer fact = 1;
        for(Integer i = 2; i <= n; i++) {
            fact *= i;
        }
        System.out.print(fact);
    }
   
    public static void t5() {
        Scanner scanner = new Scanner (System. in );
        String integ = scanner.nextLine();
        Integer n = Integer.parseInt(integ);
        Integer div = 2;
        Boolean f = true;
        while(f && div <= n/2) {
            if(n % div == 0) f = false;
            div++;
        }
        System.out.print(f);
    }
   
    public static Integer fib(Integer n) {
    if (n <= 1)
        return n;
    return fib(n-1) + fib(n-2);
}
   
    public static void t6() {
        Scanner scanner = new Scanner (System. in );
        String integ = scanner.nextLine();
        Integer n = Integer.parseInt(integ);
        System.out.print(fib(n));
       
    }
   
   
    public static void t7() {
        Scanner scanner = new Scanner (System. in );
        String integ = scanner.nextLine();
        Integer n = Integer.parseInt(integ);
        for(Integer i = n; i >= 2; i--) {
            if(n % i == 0) {
                Integer div = 2;
                Boolean f = true;
                while(f && div <= i/2) {
                    if(i % div == 0) f = false;
                    div++;
                }
                if(f) {
                    System.out.print(i);
                    break;
                }
            }
        }
    }
}