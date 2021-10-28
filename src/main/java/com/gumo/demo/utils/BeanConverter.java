package com.gumo.demo.utils;//package com.gumo.server.utils;
//
//
//import com.google.common.base.Joiner;
//import com.google.common.base.Splitter;
//import com.google.common.collect.Lists;
//import com.google.common.collect.Sets;
//import org.mapstruct.*;
//import org.mapstruct.factory.Mappers;
//
//import java.math.BigDecimal;
//import java.util.List;
//import java.util.Set;
//import java.util.stream.Collectors;
//
///**
// *
// * @author
// * @date 2021-01-01
// */
//@Mapper(componentModel = "spring")
//public interface BeanConverter {
//
//    com.ascp.purchase.search.core.util.BeanConverter INST = Mappers.getMapper(com.ascp.purchase.search.core.util.BeanConverter.class);
//
//
//    // FIXME: 春道
//
//    @Mappings({
//        @Mapping(target = "gmtCreateLeft", ignore = true,
//            expression = "java(com.ascp.purchase.search.core.util.DateConvertUtil.convertToLong(request.getGmtCreateLeft()))"),
//        @Mapping(target = "gmtCreateRight", ignore = true,
//            expression = "java(com.ascp.purchase.search.core.util.DateConvertUtil.convertToLong(request.getGmtCreateRight()))"),
//        @Mapping(target = "bizType", source = "tenantDTO.bizType"),
//        @Mapping(target = "gmtExpirationLeft",
//            expression = "java(com.ascp.purchase.search.core.util.DateConvertUtil.convertToLong(request.getGmtExpirationLeft()))"),
//        @Mapping(target = "gmtExpirationRight",
//            expression = "java(com.ascp.purchase.search.core.util.DateConvertUtil.convertToLong(request.getGmtExpirationRight()))"),
//        @Mapping(target = "gmtReceiveFinishLeft",
//            expression = "java(com.ascp.purchase.search.core.util.DateConvertUtil.convertToLong(request.getGmtReceiveFinishLeft()))"),
//        @Mapping(target = "gmtReceiveFinishRight",
//            expression = "java(com.ascp.purchase.search.core.util.DateConvertUtil.convertToLong(request.getGmtReceiveFinishRight()))"),
//        @Mapping(target = "sfIndustryIdList", source = "request.sfIndustryLevel1IdList"),
//        @Mapping(target = "tags", source = "request.tags", qualifiedByName = "tagsForSearch"),
//
//        @Mapping(target = "pageParam.pageNum", source = "pageSearchInput.pageIndex"),
//        @Mapping(target = "pageParam.pageSize", source = "pageSearchInput.pageSize"),
//    })
//    PurchaseOrderSearchParam convert(AscpTenantDTO tenantDTO,
//                                     AscpPurchaseOrderSearchRequest request,
//                                     PageSearchInput pageSearchInput);
//
//
//    @AfterMapping
//    default void afterConvert(AscpTenantDTO tenantDTO,
//                              AscpPurchaseOrderSearchRequest request,
//                              PageSearchInput pageSearchInput,  @MappingTarget PurchaseOrderSearchParam target) {
//        extactCatIdList(request, target);
//    }
//
//
//    @Mappings({
//        @Mapping(target = "gmtCreateLeft", ignore = true,
//            expression = "java(com.ascp.purchase.search.core.util.DateConvertUtil.convertToLong(request.getGmtCreateLeft()))"),
//        @Mapping(target = "gmtCreateRight", ignore = true,
//            expression = "java(com.ascp.purchase.search.core.util.DateConvertUtil.convertToLong(request.getGmtCreateRight()))"),
//        @Mapping(target = "bizType", source = "tenantDTO.bizType"),
//        @Mapping(target = "gmtExpirationLeft",
//            expression = "java(com.ascp.purchase.search.core.util.DateConvertUtil.convertToLong(request.getGmtExpirationLeft()))"),
//        @Mapping(target = "gmtExpirationRight",
//            expression = "java(com.ascp.purchase.search.core.util.DateConvertUtil.convertToLong(request.getGmtExpirationRight()))"),
//        @Mapping(target = "gmtReceiveFinishLeft",
//            expression = "java(com.ascp.purchase.search.core.util.DateConvertUtil.convertToLong(request.getGmtReceiveFinishLeft()))"),
//        @Mapping(target = "gmtReceiveFinishRight",
//            expression = "java(com.ascp.purchase.search.core.util.DateConvertUtil.convertToLong(request.getGmtReceiveFinishRight()))"),
//        @Mapping(target = "sfIndustryIdList", source = "request.sfIndustryLevel1IdList"),
//        @Mapping(target = "tags", source = "request.tags", qualifiedByName = "tagsForSearch"),
//
//        @Mapping(target = "pageParam.pageSize", source = "scrollSearchInput.pageSize"),
//    })
//    PurchaseOrderSearchParam convert(AscpTenantDTO tenantDTO,
//                                     AscpPurchaseOrderSearchRequest request,
//                                     ScrollSearchInput scrollSearchInput);
//
//    @AfterMapping
//    default void afterConvert(AscpTenantDTO tenantDTO,
//                              AscpPurchaseOrderSearchRequest request,
//                              ScrollSearchInput scrollSearchInput,  @MappingTarget PurchaseOrderSearchParam target) {
//        extactCatIdList(request, target);
//    }
//
//
//    default void extactCatIdList(AscpPurchaseOrderSearchRequest request,
//                                 @MappingTarget PurchaseOrderSearchParam target) {
//        Set<Long> catIdSet = Sets.newHashSet();
//        AscpCollectionUtil.safeAddAll(catIdSet, request.getSfCategoryIdList());
//        AscpCollectionUtil.safeAddAll(catIdSet, request.getSfCate1IdList());
//        AscpCollectionUtil.safeAddAll(catIdSet, request.getSfCate2IdList());
//        AscpCollectionUtil.safeAddAll(catIdSet, request.getSfCate3IdList());
//        AscpCollectionUtil.safeAddAll(catIdSet, request.getSfCate4IdList());
//
//        if (CollectionUtils.isNotEmpty(catIdSet)) {
//            target.setSfCateIdList(Lists.newLinkedList(catIdSet));
//        }
//    }
//
//    @Mappings({
//        @Mapping(target = "bizType", source = "tenantDTO.bizType"),
//        @Mapping(target = "pageParam.pageNum", source = "pageSearchInput.pageIndex"),
//        @Mapping(target = "pageParam.pageSize", source = "pageSearchInput.pageSize"),
//    })
//    PurchaseOrderItemSearchParam convert(AscpTenantDTO tenantDTO, AscpPurchaseOrderItemSearchRequest request, PageSearchInput pageSearchInput);
//
//    @Mappings({
//        @Mapping(target = "bizType", source = "tenantDTO.bizType"),
//        @Mapping(target = "pageParam.pageSize", source = "scrollSearchInput.pageSize"),
//    })
//    PurchaseOrderItemSearchParam convert(AscpTenantDTO tenantDTO, AscpPurchaseOrderItemSearchRequest request, ScrollSearchInput scrollSearchInput);
//
//
//    @Mappings({
//        @Mapping(target = "bizType", source = "tenantDTO.bizType"),
//        @Mapping(target = "coGmtCreateLeft", ignore = true,
//            expression = "java(com.ascp.purchase.search.core.util.DateConvertUtil.convertToLong(request.getCoGmtCreateLeft()))"),
//        @Mapping(target = "coGmtCreateRight", ignore = true,
//            expression = "java(com.ascp.purchase.search.core.util.DateConvertUtil.convertToLong(request.getCoGmtCreateRight()))"),
//        @Mapping(target = "gmtReceiveFinishLeft",
//            expression = "java(com.ascp.purchase.search.core.util.DateConvertUtil.convertToLong(request.getGmtReceiveFinishLeft()))"),
//        @Mapping(target = "gmtReceiveFinishRight",
//            expression = "java(com.ascp.purchase.search.core.util.DateConvertUtil.convertToLong(request.getGmtReceiveFinishRight()))"),
//        @Mapping(target = "gmtExpirationLeft",
//            expression = "java(com.ascp.purchase.search.core.util.DateConvertUtil.convertToLong(request.getGmtExpirationLeft()))"),
//        @Mapping(target = "gmtExpirationRight",
//            expression = "java(com.ascp.purchase.search.core.util.DateConvertUtil.convertToLong(request.getGmtExpirationRight()))"),
//        @Mapping(target = "tags", source = "request.tags", qualifiedByName = "tagsForSearch"),
//
//        @Mapping(target = "pageParam.pageNum", source = "pageSearchInput.pageIndex"),
//        @Mapping(target = "pageParam.pageSize", source = "pageSearchInput.pageSize"),
//    })
//    ConsignOrderSearchParam convert(AscpTenantDTO tenantDTO, AscpConsignOrderSearchRequest request, PageSearchInput pageSearchInput);
//
//    @AfterMapping
//    default void afterConvert(AscpTenantDTO tenantDTO,
//                              AscpConsignOrderSearchRequest request,
//                              PageSearchInput pageSearchInput,  @MappingTarget ConsignOrderSearchParam target) {
//        extractCatIdList(request, target);
//    }
//
//
//    @Mappings({
//        @Mapping(target = "bizType", source = "tenantDTO.bizType"),
//        @Mapping(target = "coGmtCreateLeft", ignore = true,
//            expression = "java(com.ascp.purchase.search.core.util.DateConvertUtil.convertToLong(request.getCoGmtCreateLeft()))"),
//        @Mapping(target = "coGmtCreateRight", ignore = true,
//            expression = "java(com.ascp.purchase.search.core.util.DateConvertUtil.convertToLong(request.getCoGmtCreateRight()))"),
//        @Mapping(target = "gmtReceiveFinishLeft",
//            expression = "java(com.ascp.purchase.search.core.util.DateConvertUtil.convertToLong(request.getGmtReceiveFinishLeft()))"),
//        @Mapping(target = "gmtReceiveFinishRight",
//            expression = "java(com.ascp.purchase.search.core.util.DateConvertUtil.convertToLong(request.getGmtReceiveFinishRight()))"),
//        @Mapping(target = "gmtExpirationLeft",
//            expression = "java(com.ascp.purchase.search.core.util.DateConvertUtil.convertToLong(request.getGmtExpirationLeft()))"),
//        @Mapping(target = "gmtExpirationRight",
//            expression = "java(com.ascp.purchase.search.core.util.DateConvertUtil.convertToLong(request.getGmtExpirationRight()))"),
//        @Mapping(target = "tags", source = "request.tags", qualifiedByName = "tagsForSearch"),
//
//        @Mapping(target = "pageParam.pageSize", source = "scrollSearchInput.pageSize"),
//    })
//    ConsignOrderSearchParam convert(AscpTenantDTO tenantDTO, AscpConsignOrderSearchRequest request, ScrollSearchInput scrollSearchInput);
//
//
//    @AfterMapping
//    default void afterConvert(AscpTenantDTO tenantDTO,
//                              AscpConsignOrderSearchRequest request,
//                              ScrollSearchInput scrollSearchInput,  @MappingTarget ConsignOrderSearchParam target) {
//        extractCatIdList(request, target);
//    }
//
//    default void extractCatIdList(AscpConsignOrderSearchRequest request,
//                                  @MappingTarget ConsignOrderSearchParam target) {
//        Set<Long> catIdSet = Sets.newHashSet();
//        AscpCollectionUtil.safeAddAll(catIdSet, request.getSfCategoryIdList());
//        AscpCollectionUtil.safeAddAll(catIdSet, request.getSfCate1IdList());
//        AscpCollectionUtil.safeAddAll(catIdSet, request.getSfCate2IdList());
//        AscpCollectionUtil.safeAddAll(catIdSet, request.getSfCate3IdList());
//        AscpCollectionUtil.safeAddAll(catIdSet, request.getSfCate4IdList());
//
//        if (CollectionUtils.isNotEmpty(catIdSet)) {
//            target.setSfCateIdList(Lists.newLinkedList(catIdSet));
//        }
//    }
//
//    @Mappings({
//        @Mapping(target = "bizType", source = "tenantDTO.bizType"),
//        @Mapping(target = "pageParam.pageNum", source = "pageSearchInput.pageIndex"),
//        @Mapping(target = "pageParam.pageSize", source = "pageSearchInput.pageSize"),
//    })
//    ConsignOrderItemSearchParam convert(AscpTenantDTO tenantDTO, AscpConsignOrderItemSearchRequest request, PageSearchInput pageSearchInput);
//
//    @Mappings({
//        @Mapping(target = "bizType", source = "tenantDTO.bizType"),
//        @Mapping(target = "pageParam.pageSize", source = "scrollSearchInput.pageSize"),
//    })
//    ConsignOrderItemSearchParam convert(AscpTenantDTO tenantDTO, AscpConsignOrderItemSearchRequest request, ScrollSearchInput scrollSearchInput);
//
//
//    @Mappings({
//        @Mapping(target = "tags", ignore = true),
//        @Mapping(target = "purchaseContractId", source = "contractId"),
//        @Mapping(target = "paymentType", source = "payType"),
//        @Mapping(target = "deliveryType", source = "logisticsType"),
//        @Mapping(target = "creatorId", source = "creatorUserId"),
//        @Mapping(target = "totalAmount", source = "contractId"),
//        @Mapping(target = "memberId", source = "supplierCode"),
//    })
//    AscpPurchaseOrderDTO convertFromPOSinglIndexDTO(AscpPurchaseOrderSingleIndexDTO ascpPurchaseOrderSingleIndexDTO);
//
//    @Mappings({
//        @Mapping(target = "tags", ignore = true),
//        @Mapping(target = "purchaseContractId", source = "contractId"),
//        @Mapping(target = "paymentType", source = "payType"),
//        @Mapping(target = "deliveryType", source = "logisticsType"),
//        @Mapping(target = "creatorId", source = "creatorUserId"),
//        @Mapping(target = "totalAmount", source = "contractId"),
//        @Mapping(target = "memberId", source = "supplierCode"),
//        @Mapping(target = "purchaseSubjectId", source = "purchaseSubjectCode"),
//
//        @Mapping(target = "relativeOTB", expression = "java(com.ascp.purchase.search.core.util.PurchaseOrderIndexDOExtUtil.getRelativeOTB(purchaseOrderSingleIndexDO))"),
//        @Mapping(target = "futuresOtbKey", expression = "java(com.ascp.purchase.search.core.util.PurchaseOrderIndexDOExtUtil.getFuturesOtbKey(purchaseOrderSingleIndexDO))"),
//        @Mapping(target = "futuresOtbName", expression = "java(com.ascp.purchase.search.core.util.PurchaseOrderIndexDOExtUtil.getFuturesOtbName(purchaseOrderSingleIndexDO))"),
//        @Mapping(target = "cancelTime" ,expression = "java(com.ascp.purchase.search.core.util.PurchaseOrderIndexDOExtUtil.getCancelTime(purchaseOrderSingleIndexDO))"),
//        @Mapping(target = "cancelReason" ,expression = "java(com.ascp.purchase.search.core.util.PurchaseOrderIndexDOExtUtil.getCancelReason(purchaseOrderSingleIndexDO))"),
//        @Mapping(target = "deferredPeople" ,expression = "java(com.ascp.purchase.search.core.util.PurchaseOrderIndexDOExtUtil.getDeferredPeople(purchaseOrderSingleIndexDO))"),
//        @Mapping(target = "deferredTime" ,expression = "java(com.ascp.purchase.search.core.util.PurchaseOrderIndexDOExtUtil.getDeferredTime(purchaseOrderSingleIndexDO))"),
//        @Mapping(target = "deferredDesc" ,expression = "java(com.ascp.purchase.search.core.util.PurchaseOrderIndexDOExtUtil.getDeferredDesc(purchaseOrderSingleIndexDO))"),
//        @Mapping(target = "productType" ,expression = "java(com.ascp.purchase.search.core.util.PurchaseOrderIndexDOExtUtil.getProductType(purchaseOrderSingleIndexDO))"),
//        @Mapping(target = "storageUnit" ,expression = "java(com.ascp.purchase.search.core.util.PurchaseOrderIndexDOExtUtil.getStorageUnit(purchaseOrderSingleIndexDO))"),
//        @Mapping(target = "storageCondition" ,expression = "java(com.ascp.purchase.search.core.util.PurchaseOrderIndexDOExtUtil.getStorageCondition(purchaseOrderSingleIndexDO))"),
//    })
//    AscpPurchaseOrderDTO convertFromPOSinglIndexDO(PurchaseOrderSingleIndexDO purchaseOrderSingleIndexDO);
//
//
//    @AfterMapping
//    default void afterConvert(PurchaseOrderSingleIndexDO purchaseOrderSingleIndexDO,
//                              @MappingTarget AscpPurchaseOrderDTO ascpPurchaseOrderDTO) {
//        BigDecimal totalQuantityDec = purchaseOrderSingleIndexDO.getTotalQuantityDec();
//        BigDecimal totalAmountDec = purchaseOrderSingleIndexDO.getTotalAmountDec();
//
//        ascpPurchaseOrderDTO.setTotalQuantity(NormalFieldConvertUtil.convertFromWholeBigDecimalToLong(totalQuantityDec));
//        ascpPurchaseOrderDTO.setTotalAmount(NormalFieldConvertUtil.convertFromWholeBigDecimalToLong(totalAmountDec));
//    }
//
//
//    //  采购价 purchasePrice
//    // sentQty
//    // confirmQty
//    // receivedNormalQty
//    // receivedDefectiveQty
//    // quantity
//    @Mappings({
//        @Mapping(target = "purchasePriceDec", source = "purchasePrice"),
//        @Mapping(target = "productPcs", source = "pcs"),
//        @Mapping(target = "barcodes", source = "barcodes", qualifiedByName = "stringListFromString"),
//        //@Mapping(target = "sfCategoryIdPath", source = "sfCategoryIdPath", qualifiedByName = "longListFromString"),
//        @Mapping(target = "sfCategoryIdPath", ignore = true),
//        @Mapping(target = "purchaseSpec", expression = "java(com.ascp.purchase.search.core.util.NormalFieldConvertUtil.convertFromStringToBigDecimal(purchaseOrderItemSingleIndexDO.getPurchaseSpec()))"),
//    }
//    )
//    AscpPurchaseOrderItemDTO convertFromPOItemSinglIndexDO(PurchaseOrderItemSingleIndexDO purchaseOrderItemSingleIndexDO);
//
//    @AfterMapping
//    default void afterConvert(PurchaseOrderItemSingleIndexDO purchaseOrderItemSingleIndexDO,
//                              @MappingTarget AscpPurchaseOrderItemDTO ascpPurchaseOrderItemDTO) {
//        BigDecimal sentQtyDec = ascpPurchaseOrderItemDTO.getSentQtyDec();
//        BigDecimal confirmQtyDec = ascpPurchaseOrderItemDTO.getConfirmQtyDec();
//        BigDecimal receivedNormalQtyDec = ascpPurchaseOrderItemDTO.getReceivedNormalQtyDec();
//        BigDecimal receivedDefectiveQtyDec = ascpPurchaseOrderItemDTO.getReceivedDefectiveQtyDec();
//        BigDecimal quantityDec = ascpPurchaseOrderItemDTO.getQuantityDec();
//
//        ascpPurchaseOrderItemDTO.setSentQty(NormalFieldConvertUtil.convertFromWholeBigDecimalToLong(sentQtyDec));
//        ascpPurchaseOrderItemDTO.setConfirmQty(NormalFieldConvertUtil.convertFromWholeBigDecimalToLong(confirmQtyDec));
//        ascpPurchaseOrderItemDTO.setReceivedNormalQty(NormalFieldConvertUtil.convertFromWholeBigDecimalToLong(receivedNormalQtyDec));
//        ascpPurchaseOrderItemDTO.setReceivedDefectiveQty(NormalFieldConvertUtil.convertFromWholeBigDecimalToLong(receivedDefectiveQtyDec));
//        ascpPurchaseOrderItemDTO.setQuantity(NormalFieldConvertUtil.convertFromWholeBigDecimalToLong(quantityDec));
//    }
//
//
//    @Mappings({
//        @Mapping(target = "transportType", source = "logisticsType"),
//        @Mapping(target = "memberId", source = "supplierCode"),
//        @Mapping(target = "manager", source = "purchaseManager"),
//        @Mapping(target = "purchaseSubjectId", source = "purchaseSubjectCode"),
//    }
//    )
//
//    AscpConsignOrderDTO convertFromCOSingleIndexDO(ConsignOrderSingleIndexDO consignOrderSingleIndexDO);
//
//    @AfterMapping
//    default void afterConvert(ConsignOrderSingleIndexDO consignOrderSingleIndexDO,
//                              @MappingTarget AscpConsignOrderDTO ascpConsignOrderDTO) {
//        BigDecimal totalQuantityDec = ascpConsignOrderDTO.getTotalQuantityDec();
//        ascpConsignOrderDTO.setTotalQuantity(NormalFieldConvertUtil.convertFromWholeBigDecimalToLong(totalQuantityDec));
//    }
//
//
//
//    @Mappings({
//        @Mapping(target = "barcodes", source = "barcodes", qualifiedByName = "stringListFromString"),
//        @Mapping(target = "sfCategoryId", source = "categoryId"),
//        @Mapping(target = "sfCategoryName", source = "categoryName"),
//        @Mapping(target = "purchasePriceDec", source = "priceIncludingTax"),
//
//
//        //@Mapping(target = "sfCategoryIdPath", source = "sfCategoryIdPath", qualifiedByName = "longListFromString"),
//        @Mapping(target = "sfCategoryIdPath", ignore = true),
//        @Mapping(target = "purchaseSpec", expression = "java(com.ascp.purchase.search.core.util.NormalFieldConvertUtil.convertFromStringToBigDecimal(consignOrderItemSingleIndexDO.getPurchaseSpec()))"),
//    }
//    )
//    AscpConsignOrderItemDTO convertFromCOItemSingleIndexDO(ConsignOrderItemSingleIndexDO consignOrderItemSingleIndexDO);
//
//    @AfterMapping
//    default void afterConvert(ConsignOrderItemSingleIndexDO consignOrderItemSingleIndexDO,
//                              @MappingTarget AscpConsignOrderItemDTO ascpConsignOrderItemDTO) {
//        BigDecimal quantityDec = consignOrderItemSingleIndexDO.getQuantityDec();
//        BigDecimal normalQtyDec = consignOrderItemSingleIndexDO.getNormalQtyDec();
//        BigDecimal defectiveQtyDec = consignOrderItemSingleIndexDO.getDefectiveQtyDec();
//
//        ascpConsignOrderItemDTO.setQuantity(NormalFieldConvertUtil.convertFromWholeBigDecimalToLong(quantityDec));
//        ascpConsignOrderItemDTO.setNormalQty(NormalFieldConvertUtil.convertFromWholeBigDecimalToLong(normalQtyDec));
//        ascpConsignOrderItemDTO.setDefectiveQty(NormalFieldConvertUtil.convertFromWholeBigDecimalToLong(defectiveQtyDec));
//
//    }
//
//
//
//
//    @Named("tagsForSearch")
//    static String tagsForSearch(List<Integer> tags) {
//        if (null == tags) {
//            return null;
//        }
//        return TagsUtil.from(tags) + ",0";
//    }
//
//    @Named("tagsForAndSearch")
//    static String tagsForAndSearch(List<Integer> tags) {
//        if (null == tags) {
//            return null;
//        }
//        Long val = TagsUtil.from(tags);
//        return val + "," + val;
//    }
//
//
//
//    @Named("stringFromIntList")
//    static String stringFromIntList(List<Integer> intList){
//        if(CollectionUtils.isEmpty(intList)){
//            return null;
//        }else{
//            return Joiner.on(",").join(intList);
//        }
//    }
//
//
//    @Named("intListFromString")
//    static List<Integer> intListFromString(String string){
//        if(StringUtils.isEmpty(string)){
//            return Lists.newArrayList();
//        }else{
//            List<String> strList = Lists.newArrayList(Splitter.on(",").split(string));
//            return Lists.newArrayList(strList.stream().map(Integer::valueOf).collect(Collectors.toList()));
//        }
//    }
//
//
//
//
//    @Named("stringFromLongList")
//    static String stringFromLongList(List<Long> longList){
//        if(CollectionUtils.isEmpty(longList)){
//            return null;
//        }else{
//            return Joiner.on(",").join(longList);
//        }
//    }
//
//    @Named("longListFromString")
//    static List<Long> longListFromString(String string){
//        if(StringUtils.isEmpty(string)){
//            return Lists.newArrayList();
//        }else{
//            List<String> strList = Lists.newArrayList(Splitter.on(",").split(string));
//            return Lists.newArrayList(strList.stream().map(Long::valueOf).collect(Collectors.toList()));
//        }
//    }
//
//
//
//    @Named("stringFromStringList")
//    static String stringFromStringList(List<String> stringList){
//        if(CollectionUtils.isEmpty(stringList)){
//            return null;
//        }else{
//            return Joiner.on(",").join(stringList);
//        }
//    }
//
//
//    @Named("stringListFromString")
//    static List<String> stringListFromString(String string){
//        if(StringUtils.isEmpty(string)){
//            return Lists.newArrayList();
//        }else{
//            List<String> strList = Lists.newArrayList(Splitter.on(",").split(string));
//            return Lists.newArrayList(strList.stream().collect(Collectors.toList()));
//        }
//    }
//
//
//
//
//}
