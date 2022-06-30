import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import pro.fengjian.mapper.UserMapper;
import pro.fengjian.pojo.User;

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
}
