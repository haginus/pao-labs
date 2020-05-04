import order.Order;
import order.ProductItem;
import priceConventions.PricePerQuantity;
import priceConventions.PricePerUnit;
import products.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Driver {
    public static void main(String[] args) {
        CashRegister register = new CashRegister();
        menu(register);
    }

    public static void menu(CashRegister register) {
        System.out.println("\n1. Listati produsele\n2. Adauga produs\n3. Editeaza Produs\n4. Sterge produs\n5. Comanda noua\n6. Istoric comenzi\n7. Adauga categorie\n8. Listare categorii\n9. Audit\n10. Exit");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                listProducts(register);
                break;
            case 2:
                addProduct(register);
                break;
            case 3:
                editProduct(register);
                break;
            case 4:
                deleteProduct(register);
                break;
            case 5:
                newOrder(register);
                break;
            case 6:
                listOrders(register);
                break;
            case 7:
                addCategory(register);
                break;
            case 8:
                listCategories(register);
                break;
            case 9:
                listLogs(register);
                break;
            case 10:
                break;
            default:
                menu(register);
        }
    }

    private static void listLogs(CashRegister register) {
        register.listLogs();
        menu(register);
    }

    private static void listOrders(CashRegister register) {
        List<Order> orders = (ArrayList<Order>) register.getOrders();
        for(int i = 0; i < orders.size(); i++)
            System.out.println((i+1) + ". " + orders.get(i));
        menu(register);
    }

    private static void newOrder(CashRegister register) {
        register.createNewOrder();
        Scanner scanner = new Scanner(System.in);
        String line;
        while(scanner.hasNextLine()) {
            line = scanner.nextLine();
            String[] spl = line.split(" ");
            if(spl[0].equals("+")) {
                ProductItem pi = new ProductItem(register.getProduct(spl[1]), Double.parseDouble(spl[2]));
                if (register.addToOrder(spl[1], Double.parseDouble(spl[2]))) System.out.println(pi + "SUBTOTAL: " + register.getCurrentOrder().getTotalPrice() + " RON");
                else System.out.println("Produsul nu exista!");
            }
            if(spl[0].equals("?")) System.out.println(register.getCurrentOrder());
            if(spl[0].equals("-")) {
                if (register.removeFromOrder(spl[1])) System.out.println("Produs sters.");
                else System.out.println("Produsul nu era in comanda!");
            }
            if(spl[0].equals("P")) {
                if(spl[1].equals("Cash")) register.payCurrentOrderCash(Double.parseDouble(spl[2]));
                if(spl[1].equals("Card")) register.payCurrentOrderCard(spl[2], spl[3]);
                System.out.println(register.getCurrentOrder());
                System.out.println("Zi buna! Urmatoarea comanda.");
                break;
            }
            if(spl[0].equals("D")) {
                register.discardCurrentOrder();
                System.out.println("Comanda anulata.\nZi buna! Urmatoarea comanda.");
                break;
            }
        }
        menu(register);
    }

    private static void deleteProduct(CashRegister register) {
        Scanner scanner = new Scanner(System.in);
        String barcode;
        System.out.print("Cod bare produs: ");
        barcode = scanner.next();
        Product product = register.getProduct(barcode);
        boolean res = register.deleteProduct(barcode);
        if(res) System.out.println("Produsul a fost sters cu succes.");
        else System.out.println("Produsul nu exista.");
        menu(register);
    }

    private static void editProduct(CashRegister register) {
        String barcode, name, unit;
        String price;
        Scanner scanner = new Scanner(System.in);
        System.out.print("Cod bare produs: ");
        barcode = scanner.next();
        Product product = register.getProduct(barcode);
        if(product != null) {
            System.out.print(String.format("Nume produs (%s): ", product.getName()));
            scanner.nextLine();
            name = scanner.nextLine();
            if(name.length() > 0) product.setName(name);
            unit = (product.getPrice() instanceof PricePerUnit) ? "bucata" : ((PricePerQuantity) product.getPrice()).measureUnit;
            System.out.print(String.format("Unitate masura (%s): ", unit));
            unit = scanner.nextLine();
            System.out.print(String.format("Pret (%.2f): ", product.getPrice().price));
            price = scanner.nextLine();
            double _price;
            if(price.length() > 0) _price = Double.parseDouble(price);
            else _price = product.getPrice().price;
            if(unit.length() == 0) unit = (product.getPrice() instanceof PricePerUnit) ? "bucata" : ((PricePerQuantity) product.getPrice()).measureUnit;
            if(unit.equals("bucata")) {
                product.setPrice(_price);
            } else product.setPrice(_price, unit);
            System.out.println("Produs modificat cu succes.");
        } else System.out.println("Produsul nu exista.");
        menu(register);
    }

    private static void addProduct(CashRegister register) {
        String barcode, name, unit;
        double price;
        Scanner scanner = new Scanner(System.in);
        System.out.print("Cod bare produs: ");
        barcode = scanner.next();
        scanner.nextLine();
        System.out.print("Nume produs: ");
        name = scanner.nextLine();
        System.out.print("Unitate masura (implicit bucata): ");
        unit = scanner.nextLine();
        System.out.print("Pret: ");
        price = scanner.nextDouble();
        boolean res;
        if(unit.length() == 0) {
            res = register.addProduct(barcode, name, price);
        } else {
            res = register.addProduct(barcode, name, price, unit);
        }
        if(res) System.out.println("Produs adaugat.");
        else System.out.println("Exista deja un produs cu acest cod de bare!");
        menu(register);
    }

    public static void listProducts(CashRegister register) {
        System.out.println("Produse in baza de date: ");
        register.listProducts();
        menu(register);
    }

    public static void addCategory(CashRegister register) {
        String name;
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nume categorie: ");
        name = scanner.nextLine();
        register.addCategory(name);
        menu(register);
    }

    public static void listCategories(CashRegister register) {
        register.listCategories();
        menu(register);
    }
}
