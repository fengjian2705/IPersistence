import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;
import pro.fengjian.mapper.OrderMapper;
import pro.fengjian.pojo.Order;

import java.io.InputStream;
import java.util.List;

public class OrderMapperTest {

    @Test
    public void selectListTest() throws Exception{

        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        InputStream resourceAsStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(resourceAsStream, "prod");
        SqlSession sqlSession = sqlSessionFactory.openSession(true);

        OrderMapper orderMapper = sqlSession.getMapper(OrderMapper.class);
        List<Order> orderList = orderMapper.selectList();
        for (Order order : orderList) {
            System.out.println(order);
        }


    }

    @Test
    public void findAllOrder() throws Exception{

        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        InputStream resourceAsStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = sqlSessionFactoryBuilder.build(resourceAsStream, "prod");
        SqlSession sqlSession = sqlSessionFactory.openSession(true);

        OrderMapper orderMapper = sqlSession.getMapper(OrderMapper.class);
        List<Order> orderList = orderMapper.findAll();
        for (Order order : orderList) {
            System.out.println(order);
        }
    }
}
