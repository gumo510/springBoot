package com.gumo.demo.model.vo;


import com.gumo.demo.model.dto.DataItem;
import lombok.Data;

import java.util.HashMap;

/**
 * 发送数据集result封装
 * @author gumo
 */
@Data
public class ResultVO {
    private String touser;
    private String template_id;
    private String topcolor;
    private HashMap<String, DataItem> data;

    private ResultVO(String _touser, String _template_id, String _topcolor, HashMap<String, DataItem> _data) {
        this.touser = _touser;
        this.template_id = _template_id;
        this.topcolor = _topcolor;
        this.data = _data;
    }

    public static ResultVO initializeResultVo(String _touser, String _template_id, String _topcolor){
        return new ResultVO(_touser,_template_id,_topcolor,null);
    }

    public static ResultVO initializeResultVo(String _touser, String _template_id, String _topcolor,HashMap<String, DataItem> _data){
        return new ResultVO(_touser,_template_id,_topcolor,_data);
    }

    public ResultVO setAttribute(String key, DataItem item){
        if(this.data==null) {
            this.data = new HashMap<String,DataItem>();
        }
        this.data.put(key,item);
        return this;
    }
}