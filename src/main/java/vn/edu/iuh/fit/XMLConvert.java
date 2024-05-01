package vn.edu.iuh.fit;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

public class XMLConvert<T> {
    private T type;

    public XMLConvert(T type) {
        this.type = type;
    }
    @SuppressWarnings("all")
    public T xml2Object(String xml) throws Exception{
        T instance = null;
        JAXBContext ctx = JAXBContext.newInstance(type.getClass());
        Unmarshaller ms = ctx.createUnmarshaller();
        instance = (T) ms.unmarshal(new StringReader(xml));
        return instance;
    }
    public String object2XML (T obj) throws Exception{
        JAXBContext ctx = JAXBContext.newInstance(type.getClass());
        Marshaller ms = ctx.createMarshaller();
        StringWriter sw = new StringWriter();
        ms.marshal(obj, sw);
        return sw.toString();
    }

}
