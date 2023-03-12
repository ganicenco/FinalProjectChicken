package ro.itschool.csv.helper;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Component;
import ro.itschool.entity.Product;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

@Component
public class CsvFileGenerator {
    public void writeProductsToCsv(List<Product> products, Writer writer) {
        try {

            CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT);
            for (Product product : products) {
                printer.printRecord(product.getId(), product.getName(), product.getQuantity(), product.getPrice(),
                        product.getAge(), product.getGender());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
