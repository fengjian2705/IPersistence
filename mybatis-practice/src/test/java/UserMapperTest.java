import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import pro.fengjian.mapper.UserMapper;
import pro.fengjian.pojo.User;

import java.io.InputStream;
import java.util.List;

public class UserMapperTest {

    @Test
    public void testCacheOne() throws Exception{

        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("SqlMapConfig.xml"));
        SqlSession sqlSession = sqlSessionFactory.openSession();

        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = userMapper.selectOneUserById(1);
        System.out.println("第一次查询: "+user);

        user.setUsername("jack3");
        int update = userMapper.update(user);
        System.out.println("更新条数 "+update);
        sqlSession.commit();

        User user2 = userMapper.selectOneUserById(1);
        System.out.println("第二次查询: "+user2);

        sqlSession.close();
    }

    @Test
    public void testCacheTwo() throws Exception{

        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("SqlMapConfig.xml"));
        SqlSession sqlSession1 = sqlSessionFactory.openSession();

        UserMapper mapper1 = sqlSession1.getMapper(UserMapper.class);

        User user1 = mapper1.selectOneUserById(1);
        System.out.println("第一次查询(sqlSession1): "+user1);


        user1.setUsername("jack3");
        int update = mapper1.update(user1);
        System.out.println("更新条数 "+update);

        sqlSession1.commit();
        sqlSession1.close();

        SqlSession sqlSession2 = sqlSessionFactory.openSession();
        UserMapper mapper2 = sqlSession2.getMapper(UserMapper.class);
        User user2 = mapper2.selectOneUserById(1);
        System.out.println("第二次查询（sqlSession2）: "+ user2);
        sqlSession2.close();


    }

    @Test
    public void testPageHelperPlugin() throws Exception{

        InputStream resourceAsStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(resourceAsStream, "prod");
        SqlSession sqlSession = sqlSessionFactory.openSession();

        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        PageHelper.startPage(1,10);
        List<User> userList = mapper.selectList();

        PageInfo<User> userPageInfo = new PageInfo<>(userList);
        System.out.println(userPageInfo);

    }

    @Test
    public void testSelectUserWithOrderLazy() throws Exception{

        InputStream resourceAsStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(resourceAsStream, "dev");
        SqlSession sqlSession = sqlSessionFactory.openSession();

        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> userList =  mapper.selectUserWithOrderLazy();
        for (User user : userList) {
            System.out.println(user.getUsername());
        }

    }
}
