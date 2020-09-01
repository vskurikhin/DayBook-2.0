package su.svn.daybook.utils;

import java.io.*;

@SuppressWarnings("unchecked")
public class SerializeUtil {
    public static <T extends Serializable> T clone(T o) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            // сохраняем состояние в поток и закрываем поток
            try (ObjectOutputStream ous = new ObjectOutputStream(baos)) {
                ous.writeObject(o);
            } catch (IOException e) {
                throw e;
            }
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            // создаём и инициализируем состояние
            return (T) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
