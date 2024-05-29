package com.gumo.demo;

import com.google.common.collect.Maps;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TreeTest {


    /**
     * 通过ID 获取区域树名称如："areaTreeName": "test\2222",
     *
     * 定义`TreeNode` 类型确实有 `getId`、`getName` 和 `getParentId` 方法
     * 获取全部区域后 在 `buildAreaTreePath` 方法中，我使用了 `StringBuilder` 的 `insert` 方法在字符串的开头插入父节点名称，
     * 这样做是因为我们需要反向从当前节点遍历到根节点。另外，为了符合你的需求，我使用了 `"\\"` 来分隔区域名称。
     */

    @Test
    public void areaTreeNameTest() {

//        Map<Long, String> areaTreeNameMap = getAreaTreeNameMap(areaIds);
    }

//    public Map<Long, String> getAreaTreeNameMap(List<Long> areaIds) {
//        HashMap<Long, String> areaTreeNameMap = Maps.newHashMap();
//        // 查询所有区域
//        List<TArea> tAreas = tAreaMapper.selectAll(null);
//        Map<Long, TArea> treeNodeMap = tAreas.stream().collect(Collectors.toMap(bi -> bi.getId(), bi -> bi ,(b1, b2) -> b1));
//
//        for (Long id : areaIds) {
//            TArea node = treeNodeMap.get(id);
//            if (node != null) {
//                String path = buildAreaTreePath(id, treeNodeMap);
//                areaTreeNameMap.put(id, path);
//            }
//        }
//
//        return areaTreeNameMap;
//    }
//
//    private String buildAreaTreePath(Long id, Map<Long, TArea> treeNodeMap) {
//        StringBuilder pathBuilder = new StringBuilder();
//        TArea node = treeNodeMap.get(id);
//
//        while (node != null) {
//            if (pathBuilder.length() > 0) {
//                pathBuilder.insert(0, "\\");
//            }
//            pathBuilder.insert(0, node.getName());
//            node = treeNodeMap.get(node.getParentId());
//        }
//
//        return pathBuilder.toString();
//    }
}
