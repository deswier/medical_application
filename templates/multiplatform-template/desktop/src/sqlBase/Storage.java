package sample.phoneBook.sqlBase;

import com.google.common.collect.Multimap;
import sample.Model.subscriber.NameType;
import sample.Model.subscriber.Subscriber;
import sample.exeption.DataException;

public interface Storage {
    void save(Multimap<NameType, Subscriber> entity) throws DataException;

    void clear() throws DataException;

}