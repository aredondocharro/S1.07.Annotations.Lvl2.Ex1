public class Main {
    public static void main(String[] args) {
        Person p = new Person("Alejandro", 31);
        try {
            JsonSerializer.serialize(p);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}