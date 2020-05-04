package fileServices;

import priceConventions.PricePerQuantity;
import priceConventions.PricePerUnit;
import products.Product;

import java.io.*;
import java.util.HashMap;

public class ProductFileService
{
    private static ProductFileService single_instance = null;

    private String csvPath = "products.csv";

    private ProductFileService() { }

    public void saveToFile(HashMap<String, Product> products) throws IOException {
        FileWriter csvWriter = new FileWriter(csvPath);
        csvWriter.append("barcode,name,price,priceUnit\n");
        for (Product product : products.values()) {
            csvWriter.append(product.getBarcode()).append(",");
            csvWriter.append(product.getName()).append(",");
            if (product.getPrice() instanceof PricePerUnit) {
                csvWriter.append(Double.toString(product.getPrice().price));
            } else if (product.getPrice() instanceof PricePerQuantity) {
                csvWriter.append(Double.toString(product.getPrice().price)).append(',').append(((PricePerQuantity) product.getPrice()).measureUnit);
            }
            csvWriter.append('\n');
        }

        csvWriter.flush();
        csvWriter.close();
    }

    public HashMap<String, Product> loadFromFile() throws IOException {
        BufferedReader csvReader = new BufferedReader(new FileReader(csvPath));
        HashMap<String, Product> products = new HashMap<>();
        String row;
        csvReader.readLine(); // read CSV header line
        while ((row = csvReader.readLine()) != null) {
            Product product;
            String[] data = row.split(",");
            if(data.length == 3) {
                product = new Product(data[0], data[1], Double.parseDouble(data[2]));
            } else if(data.length == 4) {
                product = new Product(data[0], data[1], Double.parseDouble(data[2]), data[3]);
            }
            else throw new IOException("fisier corupt");
            products.put(product.getBarcode(), product);
        }
        csvReader.close();
        return products;
    }

    public static ProductFileService getInstance()
    {
        if (single_instance == null)
            single_instance = new ProductFileService();

        return single_instance;
    }
}