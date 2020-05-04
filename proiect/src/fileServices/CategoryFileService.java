package fileServices;

import productCategories.ProductCategory;
import products.Product;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CategoryFileService {
    private static CategoryFileService single_instance = null;

    private String csvPath = "categories.csv";

    private CategoryFileService() { }

    public void saveToFile(List<ProductCategory> categories) throws IOException {
        FileWriter csvWriter = new FileWriter(csvPath);
        csvWriter.append("id,name\n");
        for(ProductCategory category : categories) {
            csvWriter.append(Integer.toString(category.getId())).append(',');
            csvWriter.append(category.getName());
            csvWriter.append('\n');
        }
        csvWriter.flush();
        csvWriter.close();
    }

    public List<ProductCategory> loadFromFile() throws IOException {
        BufferedReader csvReader = new BufferedReader(new FileReader(csvPath));
        List<ProductCategory> categories = new ArrayList<>();
        String row;
        csvReader.readLine(); // read CSV header line
        while ((row = csvReader.readLine()) != null) {
            String[] data = row.split(",");
            if(data.length != 2) {
                throw new IOException("fisier corupt");
            }
            categories.add(new ProductCategory(Integer.parseInt(data[0]), data[1]));
        }
        csvReader.close();
        return categories;
    }

    public static CategoryFileService getInstance()
    {
        if (single_instance == null)
            single_instance = new CategoryFileService();

        return single_instance;
    }
}
