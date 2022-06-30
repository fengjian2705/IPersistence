package pro.fengjian.dao;

import org.dom4j.DocumentException;
import pro.fengjian.Resources;
import pro.fengjian.SqlSession;
import pro.fengjian.SqlSessionFactory;
import pro.fengjian.SqlSessionFactoryBuilder;
import pro.fengjian.pojo.User;

import java.beans.IntrospectionException;
import java.beans.PropertyVetoException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

public class UserDao {

    public User selectOne(User user) throws DocumentException, PropertyVetoException, ClassNotFoundException, IllegalAccessException, IntrospectionException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException {

        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourcesAsStream("SqlMapConfig.xml"));
        SqlSession sqlSession = sqlSessionFactory.openSqlSession();

        return sqlSession.selectOne("UserMapper.selectOne", user);
    }
}
