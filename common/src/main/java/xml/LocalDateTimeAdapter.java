package xml;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDateTime;

/**
 * This class is needed to convert LocalDateTime fields to xml
 */
public class LocalDateTimeAdapter extends XmlAdapter<String, LocalDateTime> {
    public LocalDateTime unmarshal(String v){
        return LocalDateTime.parse(v);
    }

    public String marshal(LocalDateTime v){
        return v.toString();
    }
}