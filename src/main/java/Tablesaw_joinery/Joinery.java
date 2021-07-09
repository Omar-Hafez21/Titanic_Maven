package Tablesaw_joinery;

import java.util.List;
import java.util.stream.Collectors;
import joinery.DataFrame;

public class Joinery {
    public static void main(String[] args) {
        Eda_Joinery eda = new Eda_Joinery();
        DataFrame titanic_1 = eda.readCSV("src/main/resources/titanic_1.csv");
        DataFrame titanic_2 = eda.readCSV("src/main/resources/titanic_2.csv");
        System.out.println("Titanic" + eda.getStatistics(titanic_1));
        System.out.println("Titanic_2" + eda.getStatistics(titanic_2));
        System.out.println("=======================================================================================");
        System.out.println("Titanic \n" + titanic_1.columns());
        System.out.println("Titanic_2 \n" + titanic_2.columns());
        System.out.println("=======================================================================================");
        List gender = (List) titanic_1.col("sex")
                .stream()
                .map(g->g.toString()
                .equalsIgnoreCase("male")? 1:0)
                .collect(Collectors.toList());
        titanic_1.add("gender", gender);
        titanic_1 = titanic_1.drop("sex");
        gender = (List) titanic_2.col("Sex")
                .stream()
                .map(g->g.toString()
                .equalsIgnoreCase("male")? 1:0)
                .collect(Collectors.toList());
        titanic_2.add("gender", gender);
        titanic_2 = titanic_2.drop("Sex");
        System.out.println("titanic\n==========\n" + titanic_1 + "\n" + "titanic_2\n==========\n" + titanic_2);
        System.out.println("=======================================================================================");
        DataFrame mergedData = titanic_1.join(titanic_2);
        System.out.println(mergedData.columns());
        System.out.println("=======================================================================================");
    }
}