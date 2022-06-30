package pro.fengjian;


import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SimpleExecutor implements Executor{

    private Connection connection;

    public <E> List<E> query(Configuration configuration, MappedStatement statement, Object[] param) throws SQLException, NoSuchFieldException, IllegalAccessException, InstantiationException, IntrospectionException, InvocationTargetException {

        // 获取连接
        connection = configuration.getDataSource().getConnection();

        // 处理 sql，
        // 存储的 sql 格式为 select * from user where id = #{id} and username = #{username}
        // 需变为：select * from user where id = ? and username = ?
        BoundSql boundSql = this.getBoundSql(statement.getSql());
        String sqlText = boundSql.getSqlText();

        // 预编译
        PreparedStatement ps = connection.prepareStatement(sqlText);

        // 设置参数
        Class<?> parameterTypeClass = statement.getParameterType();
        List<ParameterMapping> parameterMappingList = boundSql.getParameterMappingList();
        for (int i = 0; i < parameterMappingList.size(); i++) {
            String name = parameterMappingList.get(i).getContent();
            Field field = parameterTypeClass.getDeclaredField(name);
            field.setAccessible(true);
            Object value = field.get(param[0]);// 传入的 user 的参数值
            ps.setObject(i+1,value);
        }

        // 执行 sql 并封装结果集
        ResultSet resultSet = ps.executeQuery();
        Class<?> resultTypeClass = statement.getResultType();
        List<E> resultList = new ArrayList<>();
        while (resultSet.next()) {
            ResultSetMetaData metaData = resultSet.getMetaData();
            E e = (E)resultTypeClass.newInstance();
            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                // 属性名
                String columnName = metaData.getColumnName(i);
                // 属性值
                Object value = resultSet.getObject(columnName);
                // 属性描述器
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(columnName,resultTypeClass);
                // 获取写方法
                Method writeMethod = propertyDescriptor.getWriteMethod();
                // 写入属性值
                writeMethod.invoke(e,value);
            }
            resultList.add(e);
        }
        return resultList;
    }

    @Override
    public void close() {

    }

    private BoundSql getBoundSql(String sql) {

        //标记处理类：主要是配合通⽤标记解析器GenericTokenParser类完成对配置⽂件等的解
        //析⼯作，其中TokenHandler主要完成处理
        ParameterMappingTokenHandler parameterMappingTokenHandler = new
                ParameterMappingTokenHandler();
        //GenericTokenParser :通⽤的标记解析器，完成了代码⽚段中的占位符的解析，然后再根
        //据给定的标记处理器(TokenHandler)来进⾏表达式的处理
        //三个参数：分别为openToken (开始标记)、closeToken (结束标记)、handler (标记
        //处 理器)
        GenericTokenParser genericTokenParser = new GenericTokenParser("#{",
                "}", parameterMappingTokenHandler);
        String parse = genericTokenParser.parse(sql);
        List<ParameterMapping> parameterMappings =
                parameterMappingTokenHandler.getParameterMappings();
        BoundSql boundSql = new BoundSql(parse, parameterMappings);
        return boundSql;
    }

}
