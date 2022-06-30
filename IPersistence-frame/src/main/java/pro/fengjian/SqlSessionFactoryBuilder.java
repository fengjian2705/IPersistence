package pro.fengjian;

import org.dom4j.DocumentException;

import java.beans.PropertyVetoException;
import java.io.InputStream;

public class SqlSessionFactoryBuilder {

    private Configuration configuration;

    public SqlSessionFactoryBuilder() {
        this.configuration = new Configuration();
    }

    public SqlSessionFactory build(InputStream inputStream) throws DocumentException, PropertyVetoException, ClassNotFoundException {

        // 解析配置文件，封装 Configuration
        XMLConfigureBuilder xmlConfigureBuilder = new XMLConfigureBuilder(configuration);
        Configuration configuration = xmlConfigureBuilder.parseConfiguration(inputStream);

        // 创建 sqlSessionFactory
        SqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(configuration);
        return sqlSessionFactory;
    }
}
