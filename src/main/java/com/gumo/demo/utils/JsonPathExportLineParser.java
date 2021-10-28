package com.gumo.demo.utils;

//import com.google.common.base.Preconditions;
//import com.google.common.collect.Maps;
import org.springframework.stereotype.Component;

/**
 *
 * @author huyong
 * @date 2021-02-11
 * description: 匹配字符串转换
 */
@Component("jsonPathExportLineParser")
public class JsonPathExportLineParser {

    private final String PREFIX = "$." ;


/*    public ExportDynamicLine convertToExportDynamicLine(Object dto, List<AscpColumnMapping> mappings) {

        ExportDynamicLine line = new ExportDynamicLine();
        Map map = this.convertToMap(dto, mappings);
        line.setStages(map);
        return line;
    }



    private Map convertToMap(Object dto, List<AscpColumnMapping> mappings) {

        Preconditions.checkNotNull(dto);
        Preconditions.checkNotNull(mappings);

        Map columnMap = Maps.newHashMap();

        String jsonString = JSON.toJSONString(dto);

        mappings.forEach( mapping -> {

            String fieldName = mapping.getFieldName(); // id  name   extField.k1
            Object val = JSONPath.extract(jsonString, PREFIX + fieldName);
            columnMap.put(mapping.getFieldName(), val);

        });


        return columnMap;
    }*/


}
