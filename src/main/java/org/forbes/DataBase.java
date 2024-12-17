package org.forbes;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class DataBase {
    public Connection conn;

    public void connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:db.db";
            conn = DriverManager.getConnection(url);
            System.out.println("Подключение установлено.");
        } catch (SQLException e) {
            System.out.println("Ошибка подключения к базе данных: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Драйвер не найден: " + e.getMessage());
        }
    }

    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS forbes (" +
                     "rank INTEGER, " +
                     "name TEXT, " +
                     "networth REAL, " +
                     "age INTEGER, " +
                     "country TEXT, " +
                     "source TEXT, " +
                     "industry TEXT)";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Таблица успешно создана.");
        } catch (SQLException e) {
            System.out.println("Ошибка создания таблицы: " + e.getMessage());
        }
    }

    public void dropTable() {
        String sql = "DROP TABLE IF EXISTS forbes";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Таблица успешно удалена.");
        } catch (SQLException e) {
            System.out.println("Ошибка удаления таблицы: " + e.getMessage());
        }
    }

    public void insertPeople(List<Person> people) {
        String insertSQL = "INSERT INTO forbes (rank, name, networth, age, country, source, industry) VALUES (?, ?, ?, ?, ?, ?, ?);";

        try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            for (Person person : people) {
                pstmt.setInt(1, person.getRank());
                pstmt.setString(2, person.getName());
                pstmt.setDouble(3, person.getNetworth());
                pstmt.setInt(4, person.getAge());
                pstmt.setString(5, person.getCountry());
                pstmt.setString(6, person.getSource());
                pstmt.setString(7, person.getIndustry());
                pstmt.addBatch();
            }
            pstmt.executeBatch();
            System.out.println("Данные успешно добавлены в базу данных.");
        } catch (SQLException e) {
            System.out.println("Ошибка вставки данных: " + e.getMessage());
        }
    }

    public String getYoungestBillionaire() {
        String getSQL = "SELECT * FROM forbes WHERE country = 'France' AND networth > 10 AND age = (SELECT MIN(age) FROM forbes WHERE country = 'France' AND networth > 10);";

        try (Statement statement = conn.createStatement();
             ResultSet result = statement.executeQuery(getSQL)) {

            if (result.next()) {
                return result.getString("name");
            } else {
                return "Результат не найден";
            }
        } catch (SQLException e) {
            return "Ошибка выполнения запроса: " + e.getMessage();
        }
    }

    public String getBiggestCapitalInEnergy() {
        String getSQL = "SELECT name, source, MAX(networth) FROM forbes WHERE country = 'United States' AND industry = 'Energy';";

        try (Statement statement = conn.createStatement();
             ResultSet result = statement.executeQuery(getSQL)) {

            if (result.next()) {
                return result.getString("name") + " " + result.getString("source");
            } else {
                return "Результат не найден";
            }
        } catch (SQLException e) {
            return "Ошибка выполнения запроса: " + e.getMessage();
        }
    }
    
    public Map<String, Double> getTotalNetWorthByCountry() {
        Map<String, Double> countryNetWorth = new HashMap<>();
        String sql = "SELECT country, SUM(networth) AS total_networth FROM forbes GROUP BY country";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String country = rs.getString("country");
                double totalNetworth = rs.getDouble("total_networth");
                countryNetWorth.put(country, totalNetworth);
            }

        } catch (SQLException e) {
            System.out.println("Ошибка получения данных: " + e.getMessage());
        }

        return countryNetWorth;
    }
}

