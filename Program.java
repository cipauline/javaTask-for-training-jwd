package by.student.javaswreader;

public class Program extends Software {

    private String size;
    private String cost;
    private String name;

    public Program(String type, String size, String cost, String name) {

        super(type);
        this.size = size;
        this.cost = cost;
        this.name = name;
    }

    public String getSize() {

        return size;
    }

    public String getCost() {

        return cost;
    }

    public String getName() {

        return name;
    }

}
