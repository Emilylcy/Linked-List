public class Name {
    private String name;
    private char gender;
    private int year;
    private int rank;
    private int number;
    private double percentage;

    public Name(String name, char gender, int year, int rank, int number, double percentage) {
        this.name = name;
        this.gender = gender;
        this.year = year;
        this.rank = rank;
        this.number = number;
        this.percentage = percentage;
    }

    public String getName() {
        return name;
    }

    public char getGender() {
        return gender;
    }

    public int getYear() {
        return year;
    }

    public int getRank() {
        return rank;
    }

    public int getNumber() {
        return number;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
