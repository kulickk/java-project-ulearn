package org.forbes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVParser {

    public static List<Person> readCsv() {
        List<Person> people = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("Forbes.csv"))) {
            String line;
            br.readLine();

            while ((line = br.readLine()) != null) {
                try {
                    String[] values = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

                    int rank = parseInteger(values[0]);
                    String name = values[1].trim();
                    double networth = parseDouble(values[2]);
                    int age = parseInteger(values[3]);
                    String country = values[4].trim();
                    String source = values[5].trim();
                    String industry = values[6].trim();

                    Person person = new Person(rank, name, networth, age, country, source, industry);
                    people.add(person);

                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    System.out.println("Ошибка при разборе строки: " + line + " | " + e.getMessage());
                }
            }

            System.out.println("Данные успешно считаны из CSV файла.");
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
        }

        return people;
    }

    private static int parseInteger(String value) {
        return value != null && !value.trim().isEmpty() ? Integer.parseInt(value.trim()) : 0;
    }

    private static double parseDouble(String value) {
        return value != null && !value.trim().isEmpty() ? Double.parseDouble(value.trim()) : 0.0;
    }
}