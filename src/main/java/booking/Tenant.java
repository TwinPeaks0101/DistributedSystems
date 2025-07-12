package booking;

public class Tenant {
    private String name;

    public Tenant(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Tenant{" +
                "name='" + name + '\'' +
                '}';
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
