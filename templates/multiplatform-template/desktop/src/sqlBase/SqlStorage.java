//package sqlBase;
//import com.google.common.collect.Multimap;
//import com.google.common.collect.TreeMultimap;
//import sample.Model.subscriber.NameType;
//import sample.Model.subscriber.Subscriber;
//import sample.exeption.DataException;
//import sample.sqlHelper.SqlHelper;
//
//import java.sql.DriverManager;
//import java.sql.ResultSet;
//
//public class SqlStorage implements Storage {
//
//    private final SqlHelper sqlHelper;
//
//    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
//        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
//    }
//
//    private void addMapElements(Subscriber s) throws DataException {
//        sqlHelper.<Void>execute("INSERT INTO subscriber (full_name, phone) VALUES (?,?)", ps -> {
//            ps.setString(1, s.getName().getFullName());
//            ps.setInt(2, Integer.parseInt(String.valueOf(s.getPhone().getPhone())));
//            ps.execute();
//            return null;
//        });
//    }
//
//    @Override
//    public void save(Multimap<NameType, Subscriber> entity) throws DataException {
//        clear();
//        entity.entries().forEach(entry -> {
//            try {
//                addMapElements(entry.getValue());
//            } catch (DataException e) {
//                e.printStackTrace();
//            }
//        });
//        sqlHelper.execute("INSERT INTO subscriber (full_name, phone) VALUES (?,?)", ps -> {
//            ps.setString(1, "Max Kornev" );
//            ps.setInt(2, 2405531);
//            ps.execute();
//            return null;
//        });
//    }
//
//    @Override
//    public void clear() throws DataException {
//        sqlHelper.execute("DELETE FROM subscriber");
//    }
//
//    @Override
//    public Multimap<NameType, Subscriber> getAllSorted() throws DataException {
//        Multimap<NameType, Subscriber> map = TreeMultimap.create();
//        return sqlHelper.execute("SELECT * FROM subscriber s ORDER BY full_name, phone", ps -> {
//            ResultSet rs = ps.executeQuery();
//            while (rs.next()) {
//                Subscriber s = new Subscriber(rs.getString("full_name"), rs.getString("phone"));
//                map.put(s.getName(), s);
//            }
//            return map;
//        });
//    }
//}