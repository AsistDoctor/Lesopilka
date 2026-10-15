    package services;

    import models.Workpiece;
    import models.TreeType;
    import models.Diameter;

    import java.util.Optional;
    import java.util.ArrayList;
    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;

    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;

    public class SawMill {
        private static final Logger logger =
                LoggerFactory.getLogger(SawMill.class);


        public Map<TreeType, Integer> process(List<Workpiece> logs){
            // Map<TreeType, List<log>> LogsType = new HashMap<>(); Сначала хотел отдельным циклом собирать Map
            logger.info("Начата обработка {} заготовок", logs.size());

            Map<TreeType, Integer> result = new HashMap<>();

            result.put(TreeType.OAK, 0);
            result.put(TreeType.PINE, 0);
            result.put(TreeType.MAPLE, 0);

            for(Workpiece log : logs){
                TreeType type = log.getTreeType();
                int actualDiameter = log.getDiameterMm();
                int leng = log.getLength();

                Optional<Diameter> exactDiameter =
                        Diameter.fromMillimeters(actualDiameter);

                Diameter diam;

                if (exactDiameter.isPresent()) {
                    diam = exactDiameter.get();
                } else {
                    diam = findClosestDiameter(actualDiameter);

                    logger.warn(
                            "Диаметр {} мм не задан. Используем ближайший: {} мм",
                            actualDiameter,
                            diam.getMillimeters()
                    );
                }

                int quantity = leng / 2;

                if(diam == Diameter.MM200){
                    quantity *= 3;
                }

                if(diam == Diameter.MM500){
                    quantity *= 7;
                }

                if(diam == Diameter.MM700){
                    quantity *= 12;
                }


                result.put(type, result.get(type) + quantity);
            }
            logger.info("Обработка заготовок завершена");
            return result;
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
