package com.gumo.demo;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

public class TreeTest {


    /**
     * 通过ID 获取区域树名称如："areaTreeName": "test\2222",
     * <p>
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


    /**
     * 递归查询子节点
     *
     * @param id                根节点
     * @param templateGroupList 所有节点
     */
    //    @Test
//    public void areaTreeNameTest() {
//        List<DocumentTemplateGroup> subGroups = getAllSubGroups(groupId, templateGroupList);
//        List<Long> groupIds = subGroups.stream().map(DocumentTemplateGroup::getId).collect(Collectors.toList());
//        groupIds.add(groupId);
//}
    /**
     * 递归查询子节点
     *
     * @param id                根节点
     * @param templateGroupList 所有节点
     */
//    private List<DocumentTemplateGroup> getAllSubGroups(Long id, List<DocumentTemplateGroup> templateGroupList) {
//        List<DocumentTemplateGroup> subGroups = new ArrayList<>();
//        for (DocumentTemplateGroup group : templateGroupList) {
//            if (group.getParentId() != null && group.getParentId() == id) {
//                subGroups.add(group);
//                List<DocumentTemplateGroup> childSubGroups = getAllSubGroups(group.getId(), templateGroupList);
//                subGroups.addAll(childSubGroups);
//            }
//        }
//        return subGroups;
//    }


    /**
     * 通过泛型构造树结构
     *
     */
//    @Test
//    public void areaTreeNameTest() {
        // 获取子节点
//        List<DocTemplateListVO> templateListVOList = docTemplateListVOList.stream()
//                .filter(t -> t.getParentId() == 0)
//                .map((t) -> {
//                    // 调用泛型 getChildrens 方法
//                    t.setChildList(getChildrens(
//                            t, docTemplateListVOList,
//                            (r, m) -> Objects.equals(((DocTemplateListVO) m).getParentId(), ((DocTemplateListVO) r).getGroupId()),
//                            (m, childList) -> ((DocTemplateListVO) m).setChildList(childList)));
//                    return t;
//                }).collect(Collectors.toList());
//    }

    /**
     * 递归查询子节点，支持泛型参数
     *
     * @param root         根节点
     * @param all          所有节点
     * @param idMatcher    函数接口，比较节点 parentId 和 id 是否匹配
     * @param setChildList 函数接口，设置子节点列表
     * @return 根节点信息
     */
    private <T> List<T> getChildrens(T root, List<T> all, BiPredicate<T, T> idMatcher, BiConsumer<T, List<T>> setChildList) {
        List<T> children = all.stream().filter(m -> {
            return idMatcher.test(root, m);
        }).map((m) -> {
            List<T> childList = getChildrens(m, all, idMatcher, setChildList);
            setChildList.accept(m, childList);
            return m;
        }).collect(Collectors.toList());
        return children;
    }

    /**
     * 获取列表的根节点
     * @param treeId
     * @param tAreas
     * @param level
     * @return
     */

//    private List<TArea> getChildAreas(Long treeId, List<TArea> tAreas, Integer level) {
//        // treeId 为0 获取授权区域根节点
//        List<Long> areaIds = tAreas.stream().map(TArea::getId).collect(Collectors.toList());
//        List<TArea> childAreas = Lists.newArrayList();
//        if(treeId.equals(BigDecimal.ZERO.longValue())){
//            childAreas = tAreas.stream().filter(area -> !areaIds.contains(area.getParentId())).collect(Collectors.toList());
//        }else {
//            childAreas = tAreas.stream().filter(area -> area.getId() == treeId || area.getParentId() == treeId).collect(Collectors.toList());
//        }
//        return childAreas;
//    }

}
