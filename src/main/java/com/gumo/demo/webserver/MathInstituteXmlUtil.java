package com.gumo.demo.webserver;

import com.thoughtworks.xstream.XStream;

/**
 * @author wangtianwen
 */
public class MathInstituteXmlUtil {

      private static XStream xstream = XStreamFactory.getXStream();

    public static String toXml(BaseRow[] rows) {
        Table table = new Table();
        table.setRow(rows);
        // 使用 @XStreamAlias("row")等注解生效
        xstream.processAnnotations(Table.class);
        xstream.processAnnotations(BaseRow.class);
        xstream.processAnnotations(DeviceInfoBaseRow.class);
        return xstream.toXML(table);
    }

    public static <T> T fromXml(String xml, Class<T> cls) {
        xstream.processAnnotations(cls);
        return (T) xstream.fromXML(xml);
    }
}
