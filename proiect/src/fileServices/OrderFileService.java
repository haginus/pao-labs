package fileServices;

import order.Order;
import order.ProductItem;
import paymentMethods.PaymentMethod;
import paymentMethods.PaymentMethodCard;
import paymentMethods.PaymentMethodCash;
import products.Product;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrderFileService
{
    private static OrderFileService single_instance = null;

    private String ordersCsvPath = "orders.csv";
    private String orderItemsCsvPath = "order-items.csv";

    private OrderFileService() { }

    public void saveToFile(List<Order> orders) throws IOException {
        FileWriter ordersWriter = new FileWriter(ordersCsvPath);
        FileWriter itemsWriter = new FileWriter(orderItemsCsvPath);
        ordersWriter.append("id,total,payment_method\n");
        itemsWriter.append("order_id,product_barcode,quantity\n");
        for (Order order: orders) {
            ordersWriter.append(Integer.toString(order.getId())).append(',');
            ordersWriter.append(Double.toString(order.getTotalPrice())).append(',');
            if(order.getPaymentMethod() instanceof PaymentMethodCash) {
                ordersWriter.append(Double.toString(((PaymentMethodCash) order.getPaymentMethod()).getHandledCash()));
            } else if(order.getPaymentMethod() instanceof PaymentMethodCard) {
                ordersWriter.append(((PaymentMethodCard) order.getPaymentMethod()).getCardNumber()).append(',').append(((PaymentMethodCard) order.getPaymentMethod()).getCardType());
            }
            ordersWriter.append('\n');
            for(ProductItem productItem : order.getItems()) {
                itemsWriter.append(Integer.toString(order.getId())).append(',');
                itemsWriter.append(productItem.getProduct().getBarcode()).append(',');
                itemsWriter.append(Double.toString(productItem.getQuantity()));
                itemsWriter.append('\n');
            }
        }
        ordersWriter.flush();
        ordersWriter.close();
        itemsWriter.flush();
        itemsWriter.close();
    }

    public List<Order> loadFromFile(HashMap<String, Product> products) throws IOException {
        BufferedReader ordersReader = new BufferedReader(new FileReader(ordersCsvPath));
        List<Order> orders = new ArrayList<>();

        String row;
        String itemRow;
        ordersReader.readLine(); // read CSV header line
        while ((row = ordersReader.readLine()) != null) {
            Order order;
            String[] data = row.split(",");
            PaymentMethod paymentMethod;
            if(data.length == 3) {
                paymentMethod = new PaymentMethodCash(Double.parseDouble(data[2]), Double.parseDouble(data[1]));
            } else if(data.length == 4) {
                paymentMethod = new PaymentMethodCard(data[2], data[3], Double.parseDouble(data[1]));
            } else throw new IOException("fisier corupt");
            List<ProductItem> productItems = new ArrayList<>();
            BufferedReader itemsReader = new BufferedReader(new FileReader(orderItemsCsvPath));
            itemsReader.readLine();
            while ((itemRow = itemsReader.readLine()) != null) {
                String[] itemData = itemRow.split(",");
                if (itemData.length != 3)
                    throw new IOException("fisier corupt");
                if(itemData[0].equals(data[0])) { // if order_id of product equals this current order
                    productItems.add(new ProductItem(products.get(itemData[1]), Double.parseDouble(itemData[2])));
                }
            }
            itemsReader.close();
            order = new Order(Integer.parseInt(data[0]), Double.parseDouble(data[1]), productItems, paymentMethod);
            orders.add(order);
        }
        ordersReader.close();
        return orders;
    }

    public static OrderFileService getInstance()
    {
        if (single_instance == null)
            single_instance = new OrderFileService();

        return single_instance;
    }
}