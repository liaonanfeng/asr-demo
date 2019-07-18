package com.ewt360.asr.common.config;

import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.util.ObjectUtils;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;


/**
 * Mybatis & Mapper & PageHelper 配置
 *
 */
@Slf4j
@Configuration
public class MybatisConfig {

    @Value("${mybatis.mapper-locations}")
    private String mapperLocations;
    @Value("${mybatis.type-aliases-package}")
    private String typeAliasesPackage;
    @Value("${mybatis.configuration.map-underscore-to-camel-case}")
    private Boolean mapUnderscoreToCamelCase;
    @Value("${mybatis.configuration.use-generated-keys}")
    private Boolean useGeneratedKeys;


    @Bean
    public SqlSessionFactory sqlSessionFactoryBean(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
        factory.setDataSource(dataSource);
        factory.setTypeAliasesPackage(typeAliasesPackage);

        // 配置数据库表字段与对象属性字段的映射方式(下划线=》驼峰)
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setMapUnderscoreToCamelCase(mapUnderscoreToCamelCase);
        configuration.setUseGeneratedKeys(useGeneratedKeys);
        factory.setConfiguration(configuration);

        // 配置分页插件，详情请查阅官方文档
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        // 分页尺寸为0 时查询所有纪录不再执行分页
        properties.setProperty("pageSizeZero", "true");
        // 页码<=0 查询第一页，页码>=总页数查询最后一页
        properties.setProperty("reasonable", "true");
        // 支持通过 Mapper 接口参数来传递分页参数
        properties.setProperty("supportMethodsArguments", "true");
        pageHelper.setProperties(properties);
        // 添加插件
        factory.setPlugins(new Interceptor[]{pageHelper});

        // 后续初始化SqlSessionFactory会报错，这里参考MybatisProperties的代码
        Resource[] resources = null;
        if (!ObjectUtils.isEmpty(resources = resolveMapperLocations(new String[]{mapperLocations}))) {
            factory.setMapperLocations(resources);
        }
        log.info("--mybatis-->{},mybatis resources-->{},{}",dataSource.toString(),resources,resources.length);
        return factory.getObject();
    }

    private Resource[] resolveMapperLocations(String... mapperLocations) {
        PathMatchingResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();
        List<Resource> resourceList = new ArrayList<Resource>();
        if(mapperLocations != null) {
            int total = mapperLocations.length;
            for(int i = 0; i < total; ++i) {
                String mapperLocation = mapperLocations[i];
                try {
                    Resource[] mappers = resourceResolver.getResources(mapperLocation);
                    resourceList.addAll(Arrays.asList(mappers));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return resourceList.toArray(new Resource[resourceList.size()]);
    }

}

