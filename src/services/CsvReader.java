package services;

import com.opencsv.CSVReader;
import exceptions.UnknownTreeTypeException;
import models.Diameter;
import models.Workpiece;
import models.TreeType;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CsvReader {

    private static final Logger logger =
            LoggerFactory.getLogger(CsvReader.class);

    private int unknownCount = 0;

    public List<Workpiece> read(String path) throws Exception {

        logger.info("Начато чтение файла {}", path);
        List<Workpiece> logs = new ArrayList<>();

        CSVReader reader = new CSVReader(new FileReader(path));

        String[] row;

        while ((row = reader.readNext()) != null) {

            try {
                Workpiece workpiece = new Workpiece(
                        parseTreeType(row[0]),
                        Diameter.valueOf(row[1]),
                        Integer.parseInt(row[2])
                );

                logs.add(workpiece);

            } catch (UnknownTreeTypeException e) {
                unknownCount++;
                logger.warn("Неизвестная порода дерева: {}. Заготовка пропущена", row[0]);
            }
        }

        reader.close();
        return logs;
    }

    private TreeType parseTreeType(String value) throws UnknownTreeTypeException {

        try {
            return TreeType.valueOf(value);

        } catch (IllegalArgumentException e) {
            throw new UnknownTreeTypeException(
                    "Неизвестная порода дерева: " + value
            );
        }
    }

    public int getUnknownCount() {
        return unknownCount;
    }
}