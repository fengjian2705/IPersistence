package pro.fengjian;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class XMLConfigureBuilder {

    private Configuration configuration;

    public XMLConfigureBuilder(Configuration configuration) {
        this.configuration = configuration;
    }


    public Configuration parseConfiguration(InputStream inputStream) throws DocumentException, PropertyVetoException, ClassNotFoundException {

        Document document = new SAXReader().read(inputStream);

        // configuration
        Element rootElement = document.getRootElement();
        List<Element> propertyElements = rootElement.selectNodes("//property");

        Properties properties = new Properties();
        for (Element propertyElement : propertyElements) {
            String name = propertyElement.attributeValue("name");
            String value = propertyElement.attributeValue("value");
            properties.setProperty(name, value);
        }

        // datasource
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass(properties.getProperty("driverClass"));
        dataSource.setJdbcUrl(properties.getProperty("jdbcUrl"));
        dataSource.setUser(properties.getProperty("user"));
        dataSource.setPassword(properties.getProperty("password"));

        configuration.setDataSource(dataSource);

        // mapper
        XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(configuration);
        List<Element> mapperElements = rootElement.selectNodes("//mapper");
        for (Element mapperElement : mapperElements) {
            String resource = mapperElement.attributeValue("resource");
            InputStream resourcesAsStream = Resources.getResourcesAsStream(resource);
            xmlMapperBuilder.parseMapper(resourcesAsStream);
        }
        return configuration;
    }
}
