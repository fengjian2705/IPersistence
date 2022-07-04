package pro.fengjian.mapper;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import org.mybatis.caches.redis.RedisCache;
import pro.fengjian.pojo.User;

import java.util.List;

@CacheNamespace(implementation = RedisCache.class)
public interface UserMapper {
    User selectOne(User user);

    List<User> selectList();

    List<User> selectUserWithOrder();

    List<User> selectUserWithRole();

    @Insert("insert into user(username) values (#{username})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(User user);

    @Select("select * from user where id = #{id}")
    User findById(int id);

    @Select("select * from user where id = #{id}")
//    @Options(useCache = false)
    User selectOneUserById(int id);

    @Update("update user set username = #{username} where id = #{id}")
    int update(User user);

    @Select("select * from user")
    @Results({
            @Result(property = "id",column = "id"),
            @Result(property = "username",column = "username"),
            @Result(property = "order_list",column = "id",javaType = List.class,
            many =@Many(fetchType = FetchType.LAZY,select = "pro.fengjian.mapper.OrderMapper.selectOrderByUid") )
    })
    List<User> selectUserWithOrderLazy();
}
