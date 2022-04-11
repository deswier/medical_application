import com.myapplication.exception.VersionException;
import com.myapplication.tools.DateParser;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestDateParser {

    @Test
    public void isDate() throws VersionException {
        assertTrue(DateParser.isDate("21-10-2000"));
        assertFalse(DateParser.isDate("10-21-2000"));
        assertFalse(DateParser.isDate("21/10/2000"));
    }
}