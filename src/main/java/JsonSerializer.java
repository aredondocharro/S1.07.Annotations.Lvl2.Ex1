import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;

public class JsonSerializer {

    public static void serialize(Object obj) throws IOException, IllegalAccessException {
        Class<?> clazz = obj.getClass();

        if (!clazz.isAnnotationPresent(JsonSerializable.class)) {
            throw new RuntimeException("The class is not annotated with @JsonSerializable");
        }

        JsonSerializable annotation = clazz.getAnnotation(JsonSerializable.class);
        String directory = annotation.directory();

        File dir = new File(directory);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        StringBuilder json = new StringBuilder();
        json.append("{\n");

        Field[] fields = clazz.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            json.append("  \"").append(fields[i].getName()).append("\": ");
            Object value = fields[i].get(obj);
            if (value instanceof String) {
                json.append("\"").append(value).append("\"");
            } else {
                json.append(value);
            }
            if (i < fields.length - 1) json.append(",");
            json.append("\n");
        }
        json.append("}");

        File file = new File(dir, clazz.getSimpleName() + ".json");
        FileWriter writer = new FileWriter(file);
        writer.write(json.toString());
        writer.close();

        System.out.println("Object serialized to: " + file.getPath());
    }
}