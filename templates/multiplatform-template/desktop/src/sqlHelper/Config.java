//package sqlHelper;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.Properties;
//
//public class Config {
//    private static final File PROPS = new File("C:/Users/flenn/OneDrive/Desktop/univer/41/modern/phone_book/config/contactBook.properties");
//    private static final Config INSTANCE = new Config();
//    private final Storage storage;
//    private Properties props = new Properties();
//    private File storageDir;
//
//    public Config() {
//        try (InputStream is = new FileInputStream(PROPS)) {
//            props.load(is);
//            storageDir = new File(props.getProperty("storage.dir"));
//            storage = new SqlStorage(props.getProperty("db.url"), props.getProperty("db.user"), props.getProperty("db.password"));
//        } catch (IOException e) {
//            throw new IllegalStateException("Invalid");
//        }
//    }
//
//    public static Config get() {
//        return INSTANCE;
//    }
//
//    public File getStorageDir() {
//        return storageDir;
//    }
//
//    public Storage getStorage() {
//        return storage;
//    }
//}