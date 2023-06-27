package com.gumo.demo.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Menu
 *
 * @author gumo
 * @date 2020/06/01 20:36
 */
@Data
@Builder
public class MenuTest {
    /**
     * id
     */
    public Integer id;
    /**
     * 名称
     */
    public String name;
    /**
     * 父id ，根节点为0
     */
    public Integer parentId;
    /**
     * 子节点信息
     */
    public List<MenuTest> childList;


    public MenuTest(Integer id, String name, Integer parentId) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
    }

    public MenuTest(Integer id, String name, Integer parentId, List<MenuTest> childList) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.childList = childList;
    }

}
