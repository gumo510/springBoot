package com.gumo.demo.utils;//package com.gumo.server.utils;
//
//import com.google.common.base.Joiner;
//import com.taobao.tddl.client.jdbc.TDataSource;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.mybatis.spring.annotation.MapperScan;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.Resource;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//
//import javax.sql.DataSource;
//
///**
// * 应用启动时扫描所有DAO和DO及mapper文件,需要支持按模块加载扫描
// *
// * @author
// * @since
// */
//@Configuration
//@MapperScan(value = {"com.ascp.purchase.infrastructure.persistence.indexdao"},
//            sqlSessionFactoryRef = "indexSqlSessionFactory")
//public class MybatisSingleIndexConfig {
//
//    private static final String[] DO_PACKAGE = new String[]{
//            "com.ascp.purchase.infrastructure.persistence.dal.model"
//    };
//
//    private final String MYBATIS_CONFIG = "classpath:mybatis/xml";
//
//    private final String MAPPER_LOCATIONS = "classpath*:mybatis/DAO.xml";
//
//
//    @Bean(initMethod = "init")
//    public DataSource indexDataSource(@Value("${scm.purchase.index.db.appname}")
//                                           String appName) {
//        TDataSource dataSource = new TDataSource();
//        dataSource.setAppName(appName);
//        dataSource.setDynamicRule(true);
//        return dataSource;
//    }
//
//
//
//    //@Bean
//    //public DataSourceTransactionManager transactionManager(DataSource indexDataSource) {
//    //    return new DataSourceTransactionManager(indexDataSource);
//    //}
//
//
//    @Bean
//    public SqlSessionFactoryBean indexSqlSessionFactory(DataSource indexDataSource) throws Exception {
//        PathMatchingResourcePatternResolver resolver =
//                new PathMatchingResourcePatternResolver();
//
//        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
//        sessionFactory.setDataSource(indexDataSource);
//        Resource configLocation = resolver.getResource(MYBATIS_CONFIG);
//        sessionFactory.setConfigLocation(configLocation);
//
//        String domains = Joiner.on(";").join(DO_PACKAGE);
//        sessionFactory.setTypeAliasesPackage(domains);
//
//        Resource[] mappingFiles = resolver.getResources(MAPPER_LOCATIONS);
//        sessionFactory.setMapperLocations(mappingFiles);
//
//        return sessionFactory;
//    }
//}
