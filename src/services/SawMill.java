    package services;

    import models.Workpiece;
    import models.TreeType;
    import models.Diameter;

    import java.util.ArrayList;
    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;

    public class SawMill {
        protected Map<TreeType, Integer> result = new HashMap<>();

        public SawMill() {
            result.put(TreeType.OAK, 0);
            result.put(TreeType.PINE, 0);
            result.put(TreeType.MAPLE, 0);
        }

        public Map<TreeType, Integer> process(List<Workpiece> logs){
            // Map<TreeType, List<log>> LogsType = new HashMap<>(); Сначала хотел отдельным циклом собирать Map


            for(Workpiece log : logs){
                TreeType type = log.getTreeType();
                Diameter diam = log.getDiameter();
                int leng = log.getLength();

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
            return result;
        }
    }
