package fileServices;

import javafx.util.Pair;
import productCategories.ProductCategory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AuditService {
    private static AuditService single_instance = null;

    private String csvPath = "audit.csv";
    private List<Pair<Timestamp, String>> logs;


    private AuditService() {
        try {
            this.loadFromFile();
        } catch (IOException e) {
            logs = new ArrayList<>();
        }
    }

    public void log(String action_name) {
        logs.add(new Pair<>(new Timestamp(new Date().getTime()), action_name));
        try {
            this.saveToFile();
        } catch (IOException e) {
            System.out.println("Log-ul nu a fost salvat!");
        }
    }

    public void listLogs() {
        for(Pair<Timestamp, String> log : logs) {
            System.out.println("Actiunea " + log.getValue() + " petrecuta la " + log.getKey());
        }
    }

    private void loadFromFile() throws IOException {
        BufferedReader csvReader = new BufferedReader(new FileReader(csvPath));
        logs = new ArrayList<>();
        String row;
        csvReader.readLine(); // read CSV header line
        while ((row = csvReader.readLine()) != null) {
            String[] data = row.split(",");
            if(data.length != 2) {
                throw new IOException("fisier corupt");
            }
            logs.add(new Pair<>(new Timestamp(Long.parseLong(data[1])), data[0]));
        }
        csvReader.close();
    }

    private void saveToFile() throws IOException {
        FileWriter csvWriter = new FileWriter(csvPath);
        csvWriter.append("action_name,timestamp\n");
        for(Pair<Timestamp, String> log : logs) {
            csvWriter.append(log.getValue()).append(',');
            csvWriter.append(Long.toString(log.getKey().getTime()));
            csvWriter.append('\n');
        }
        csvWriter.flush();
        csvWriter.close();
    }

    public static AuditService getInstance()
    {
        if (single_instance == null)
            single_instance = new AuditService();

        return single_instance;
    }
}
