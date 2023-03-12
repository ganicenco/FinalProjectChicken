package ro.itschool.csv.helper;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.web.multipart.MultipartFile;
import ro.itschool.controller.model.ProductDTO;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

public class CvsHelper {
    public static List<ProductDTO> getProductsFromCsv (MultipartFile file) throws IOException {
            Reader reader = new InputStreamReader(file.getInputStream());
            CsvToBean csvReader = new CsvToBeanBuilder(reader)
                    .withType(ProductDTO.class)
                    .withSeparator(',')
                    .withIgnoreLeadingWhiteSpace(true)
                    .withIgnoreEmptyLine(true)
                    .withSkipLines(1)
                    .build();
            return csvReader.parse();
        }
    }
