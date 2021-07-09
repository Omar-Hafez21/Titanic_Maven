package Tablesaw_joinery;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import tech.tablesaw.api.DateColumn;
import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.NumberColumn;
import tech.tablesaw.api.StringColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.joining.DataFrameJoiner;

public class TableSaw {
    public static void main(String[] args) throws IOException{
        Table table_1 = Table.read().csv("src/main/resources/titanic_1.csv");
        Table table_2 = Table.read().csv("src/main/resources/titanic_2.csv");
        System.out.println("=======================================================================================");
        Table dataStructure = table_1.structure();
        Table dataStructure_2 = table_2.structure();
        System.out.println(dataStructure);
        System.out.println(dataStructure_2);
        System.out.println("=======================================================================================");
        Table summary = table_1.summary();
        Table summary_2 = table_2.summary();
        System.out.println(summary);
        System.out.println(summary_2);
        System.out.println("=======================================================================================");
        int rowCount = table_1.rowCount();
        List<LocalDate> dateList = new ArrayList<>();
        for(int i=0; i<rowCount; i++) {
            dateList.add(LocalDate.of(2021, 3, i%31==0?1:i%31));
        }
        DateColumn dateColumn = DateColumn.create("Fake Date",dateList);
        Table editedData = table_1.insertColumn(table_1.columnCount(), dateColumn);
        System.out.println(editedData);
        System.out.println("=======================================================================================");
        NumberColumn mappedGenderColumn = null;
        StringColumn gender = (StringColumn)table_1.column("sex");
        List<Number> mappedGenderValue = new ArrayList<>();
        for(String value : gender) {
            if((value != null) && (value.equalsIgnoreCase("female"))) {
                mappedGenderValue.add(new Double(1));
            }
            else {
                mappedGenderValue.add(new Double(0));
            }
        }
        mappedGenderColumn = DoubleColumn.create("Gender", mappedGenderValue);
        Table editedGenderData = table_1.addColumns(mappedGenderColumn);
        editedGenderData = (Table) editedGenderData.removeColumns("Sex");
        System.out.println(editedGenderData);
        System.out.println("=======================================================================================");
        Table newMergedData = table_1.addColumns(table_2.column("PassengerId"));
        System.out.println(newMergedData);
        System.out.println("=======================================================================================");
        DataFrameJoiner dfj = new DataFrameJoiner(table_1,"name");
        System.out.println(dfj.inner(true, table_2));
    }
}