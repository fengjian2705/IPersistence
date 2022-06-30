import org.dom4j.DocumentException;
import org.junit.Test;
import pro.fengjian.Resources;
import pro.fengjian.SqlSession;
import pro.fengjian.SqlSessionFactory;
import pro.fengjian.SqlSessionFactoryBuilder;
import pro.fengjian.dao.UserDao;
import pro.fengjian.dao.UserMapper;
import pro.fengjian.pojo.User;

import java.beans.IntrospectionException;
import java.beans.PropertyVetoException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

public class UserDaoTest {

    private UserDao userDao = new UserDao();

    @Test
    public void testSelectOne() throws DocumentException, PropertyVetoException, ClassNotFoundException, IllegalAccessException, IntrospectionException, InstantiationException, SQLException, InvocationTargetException, NoSuchFieldException {

        User user = new User();
        user.setId(1);
        user.setUsername("jack");

        User u = userDao.selectOne(user);
        System.out.println(u);
    }

    @Test
    public void testSelectOneMapper() throws DocumentException, PropertyVetoException, ClassNotFoundException {


        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourcesAsStream("SqlMapConfig.xml"));
        SqlSession sqlSession = sqlSessionFactory.openSqlSession();

        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = new User();
        user.setId(1);
        user.setUsername("jack");

        User u = mapper.selectOne(user);
        System.out.println(u);
    }

}
