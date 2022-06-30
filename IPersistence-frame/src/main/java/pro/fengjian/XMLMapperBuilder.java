package pro.fengjian;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

public class XMLMapperBuilder {

    private Configuration configuration;

    public XMLMapperBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    public void parseMapper(InputStream resourcesAsStream) throws DocumentException, ClassNotFoundException {

        // 读取 mapper
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(resourcesAsStream);

        Element rootElement = document.getRootElement();
        String namespace = rootElement.attributeValue("namespace");
        List<Element> selectElements = rootElement.selectNodes("//select");
        for (Element selectElement : selectElements) {
            String id = selectElement.attributeValue("id");
            String parameterType = selectElement.attributeValue("parameterType");
            String resultType = selectElement.attributeValue("resultType");
            String sql = selectElement.getTextTrim();

            Class<?> parameterTypeClass = Class.forName(parameterType);
            Class<?> resultTypeClass = Class.forName(resultType);

            // 封装 sql
            MappedStatement mappedStatement = new MappedStatement();
            mappedStatement.setId(id);
            mappedStatement.setParameterType(parameterTypeClass);
            mappedStatement.setResultType(resultTypeClass);
            mappedStatement.setSql(sql);

            // Configuration
            String mappedId = namespace+"."+id;
            configuration.getMappedStatementMap().put(mappedId,mappedStatement);
        }
    }
}
