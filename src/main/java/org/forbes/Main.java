package org.forbes;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        DataBase db = new DataBase();
        db.connect();
        db.dropTable();
        db.createTable();

        List<Person> people = CSVParser.readCsv();

        db.insertPeople(people);

        ForbesChart.generateChart(db.getTotalNetWorthByCountry()); // 1 задача

        System.out.println("Самый молодой миллиардер из Франции, капитал которого превышает 10 млрд: "
                + db.getYoungestBillionaire()); // 2 задача

        System.out.println("Имя и компания бизнесмена из США, имеющего самый большой капитал в сфере Energy: "
                + db.getBiggestCapitalInEnergy()); // 3 задача

    }
}
