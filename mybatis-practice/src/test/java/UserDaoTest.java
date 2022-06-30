import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import pro.fengjian.dao.UserDao;
import pro.fengjian.mapper.UserMapper;
import pro.fengjian.pojo.User;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class UserDaoTest {

    private UserDao userDao = new UserDao();

    @Test
    public void testSelectOne() throws IOException {

        User user = new User();
        user.setId(1);
        user.setUsername("jack");
        User u = this.userDao.selectOne(user);
        System.out.println(u);
    }

    @Test
    public void testSelectList() throws Exception {

        List<User> userList = this.userDao.selectList();
        for (User user : userList) {
            System.out.println(user);
        }
    }

    @Test
    public void testSelectOneMapper() throws IOException {

        SqlSessionFactoryBuilder factoryBuilder = new SqlSessionFactoryBuilder();
        InputStream inputStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = factoryBuilder.build(inputStream, "prod");
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = new User();
        user.setId(1);
        user.setUsername("jack");
        User u = mapper.selectOne(user);
        System.out.println(u);
    }

    @Test
    public void testSelectListMapper() throws IOException {

        SqlSessionFactoryBuilder factoryBuilder = new SqlSessionFactoryBuilder();
        InputStream inputStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = factoryBuilder.build(inputStream, "dev");
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> userList = mapper.selectList();
        for (User user : userList) {
            System.out.println(user);
        }
    }

    @Test
    public void selectUserWithOrder() throws Exception{
        SqlSessionFactoryBuilder factoryBuilder = new SqlSessionFactoryBuilder();
        InputStream inputStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = factoryBuilder.build(inputStream, "prod");
        SqlSession sqlSession = sqlSessionFactory.openSession();

        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> userList = mapper.selectUserWithOrder();
        for (User user : userList) {
            System.out.println(user);
        }

    }

    @Test
    public void selectUserWithRole() throws Exception{

        SqlSessionFactoryBuilder factoryBuilder = new SqlSessionFactoryBuilder();
        InputStream inputStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = factoryBuilder.build(inputStream, "prod");
        SqlSession sqlSession = sqlSessionFactory.openSession();

        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> userList = mapper.selectUserWithRole();
        for (User user : userList) {
            System.out.println(user);
        }

    }

    @Test
    public void insert() throws Exception{

        SqlSessionFactoryBuilder factoryBuilder = new SqlSessionFactoryBuilder();
        InputStream inputStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = factoryBuilder.build(inputStream, "prod");
        SqlSession sqlSession = sqlSessionFactory.openSession(true);

        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = new User();
        user.setUsername("rose");
        int insert = mapper.insert(user);
        System.out.println(insert);

    }
}
