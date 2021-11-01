package by.student.javaswreader;

public abstract class Software {

    private String type;

    public Software(String type) {

        this.type = type;
    }

    public String getName() {

        return type;
    }
}
