package pro.fengjian.mapper;

import org.apache.ibatis.annotations.*;
import pro.fengjian.pojo.Order;
import pro.fengjian.pojo.User;

import java.util.List;

public interface OrderMapper {

    Order selectOne(Order order);

    List<Order> selectList();

    @Insert("insert into `order`(total,order_time,uid) values (#{total},#{order_time},#{uid})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Order order);

    @Select("select * from `order`")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "total", column = "total"),
            @Result(property = "order_time", column = "order_time"),
            @Result(property = "user", column = "uid", javaType = User.class,
                    one = @One(select = "pro.fengjian.mapper.UserMapper.findById"))
    }
    )
    List<Order> findAll();
}
