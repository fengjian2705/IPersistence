package pro.fengjian.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import pro.fengjian.pojo.User;

import java.util.List;

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
    User selectOneUserById(int id);

    @Update("update user set username = #{username} where id = #{id}")
    int update(User user);
}
