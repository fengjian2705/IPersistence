package pro.fengjian;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.List;

public class DefaultSqlSession implements SqlSession {

    private Configuration configuration;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
    }

    public <E> List<E> selectList(String statementId, Object... params) throws IllegalAccessException, IntrospectionException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException {

        SimpleExecutor simpleExecutor = new SimpleExecutor();
        return simpleExecutor.query(configuration, configuration.getMappedStatementMap().get(statementId), params);
    }

    public <T> T selectOne(String statementId, Object... params) throws IllegalAccessException, IntrospectionException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException {

        List<Object> list = this.selectList(statementId, params);
        if (list.size() <= 1) {
            return (T) list.get(0);
        } else {
            throw new RuntimeException("期望查询结果 1 条，实际" + list.size() + "条");
        }
    }

    public void close() {

    }

    @Override
    public <T> T getMapper(Class<?> mapperClass) {

        return (T) Proxy.newProxyInstance(
                DefaultSqlSession.class.getClassLoader(),
                new Class[]{mapperClass},
                (proxy, method, args) -> {
                    // 约定 statementId = className.method
                    String methodName = method.getName();
                    String className = method.getDeclaringClass().getName();
                    String statementId = className + "." + methodName;
                    MappedStatement mappedStatement = this.configuration.getMappedStatementMap().get(statementId);
                    // 根据被调用方法返回类型判断调用的方法
                    Type genericReturnType = method.getGenericReturnType();
                    if (genericReturnType instanceof ParameterizedType) {
                        return this.selectList(statementId, args);
                    } else {
                        return this.selectOne(statementId, args);
                    }
                });
    }
}
