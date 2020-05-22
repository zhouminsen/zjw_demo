package zjw.cat.producer.config;

import com.baomidou.mybatisplus.autoconfigure.MybatisPlusProperties;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

/**
 * @author zhoum
 */
@Configuration
public class MybatisPlusConfig {

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    @Value("${spring.datasource.url}")
    private String jdbcUrl;

    @Autowired
    private DataSource dataSource;


    @Autowired
    private MybatisPlusProperties mybatisPlusProperties;

    @Bean
    public SqlSessionFactory mysqlSessionFactory() throws Exception {
        MybatisSqlSessionFactoryBean sqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        String[] mapperLocations = mybatisPlusProperties.getMapperLocations();
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources(mapperLocations[0]));
        sqlSessionFactoryBean.setConfiguration(mybatisPlusProperties.getConfiguration());
        sqlSessionFactoryBean.setPlugins(new Interceptor[]{new CatMybatisInterceptor(jdbcUrl), paginationInterceptor()});
        sqlSessionFactoryBean.setGlobalConfig(mybatisPlusProperties.getGlobalConfig());
        return sqlSessionFactoryBean.getObject();
    }
}