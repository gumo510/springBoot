package com.gumo.demo.webserver;

import com.thoughtworks.xstream.XStream;

/**
 * @author wangtianwen
 */
public class MathInstituteXmlUtil {

      private static final XStream xstream = XStreamFactory.getXStream();

    public static String toXml(Row[] rows) {
        Table table = new Table();
        table.setRow(rows);
        return xstream.toXML(table);
    }

    public static <T> T fromXml(String xml, Class<T> cls) {
        xstream.processAnnotations(cls);
        return (T) xstream.fromXML(xml);
    }
}
