package pro.fengjian.dao;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import pro.fengjian.pojo.User;

import java.io.IOException;
import java.util.List;

public class UserDao {

    public User selectOne(User user) throws IOException {

        SqlSessionFactoryBuilder factoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = factoryBuilder.build(Resources.getResourceAsStream("SqlMapConfig.xml"));
        SqlSession sqlSession = sqlSessionFactory.openSession();

        User u = (User)sqlSession.selectOne("UserMapper.selectOne",user);
        return u;
    }

    public List<User> selectList() throws IOException {
        SqlSessionFactoryBuilder factoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory sqlSessionFactory = factoryBuilder.build(Resources.getResourceAsStream("SqlMapConfig.xml"));
        SqlSession sqlSession = sqlSessionFactory.openSession();
        return sqlSession.selectList("UserMapper.selectList");
    }

}
