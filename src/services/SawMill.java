package services;

import models.Diameter;
import models.TreeType;
import models.Workpiece;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SawMill {

    private static final Logger logger =
            LoggerFactory.getLogger(SawMill.class);

    public Map<TreeType, Integer> process(List<Workpiece> logs) {
        logger.info("Начата обработка {} заготовок", logs.size());

        Map<TreeType, Integer> result = logs.stream()
                .collect(Collectors.groupingBy(
                        Workpiece::getTreeType,
                        () -> new EnumMap<>(TreeType.class),
                        Collectors.summingInt(this::calculateBoards)
                ));

        // Сохраняем все породы в результате, даже если их количество равно нулю.
        Arrays.stream(TreeType.values())
                .forEach(type -> result.putIfAbsent(type, 0));

        logger.info("Обработка заготовок завершена");
        return result;
    }

    private int calculateBoards(Workpiece workpiece) {
        int actualDiameter = workpiece.getDiameterMm();

        Diameter diameter = Diameter.fromMillimeters(actualDiameter)
                .orElseGet(() -> {
                    Diameter closest = findClosestDiameter(actualDiameter);

                    logger.warn(
                            "Диаметр {} мм не задан. Используем ближайший: {} мм",
                            actualDiameter,
                            closest.getMillimeters()
                    );

                    return closest;
                });

        int quantity = workpiece.getLength() / 2;

        return switch (diameter) {
            case MM200 -> quantity * 3;
            case MM500 -> quantity * 7;
            case MM700 -> quantity * 12;
        };
    }

    private Diameter findClosestDiameter(int actualDiameter) {
        Diameter closest = null;
        long smallestDifference = Long.MAX_VALUE;

        for (Diameter candidate : Diameter.values()) {
            long difference = Math.abs(
                    (long) candidate.getMillimeters() - actualDiameter
            );

            if (difference < smallestDifference
                    || (difference == smallestDifference
                    && (closest == null
                    || candidate.getMillimeters() > closest.getMillimeters()))) {

                closest = candidate;
                smallestDifference = difference;
            }
        }

        return closest;
    }
}