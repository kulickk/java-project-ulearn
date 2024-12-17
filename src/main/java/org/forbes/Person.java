package org.forbes;

public class Person {
    public int rank;
    public String name;
    public double networth;
    public int age;
    public String country;
    public String source;
    public String industry;

    public Person(int rank, String name, double networth, int age, String country, String source, String industry) {
        this.rank = rank;
        this.name = name;
        this.networth = networth;
        this.age = age;
        this.country = country;
        this.source = source;
        this.industry = industry;
    }

    public int getRank() {
        return rank;
    }

    public String getName() {
        return name;
    }

    public double getNetworth() {
        return networth;
    }

    public int getAge() {
        return age;
    }

    public String getCountry() {
        return country;
    }

    public String getSource() {
        return source;
    }

    public String getIndustry() {
        return industry;
    }

    @Override
    public String toString() {
        return "Person{" +
                "rank=" + rank +
                ", name='" + name + '\'' +
                ", networth=" + networth +
                ", age=" + age +
                ", country='" + country + '\'' +
                ", source='" + source + '\'' +
                ", industry='" + industry + '\'' +
                '}';
    }
}
