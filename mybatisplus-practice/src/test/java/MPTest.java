import com.baomidou.mybatisplus.core.MybatisSqlSessionFactoryBuilder;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Before;
import org.junit.Test;
import pro.fengjian.mapper.UserMapper;
import pro.fengjian.pojo.User;

import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MPTest {

    private UserMapper userMapper;
    private SqlSession sqlSession;

    @Before
    public void Init() throws Exception {
        InputStream resourceAsStream = Resources.getResourceAsStream("SqlMapConfig.xml");
        SqlSessionFactory sqlSessionFactory = new MybatisSqlSessionFactoryBuilder().build(resourceAsStream);
        this.sqlSession = sqlSessionFactory.openSession();
        this.userMapper = sqlSession.getMapper(UserMapper.class);
    }

    @Test
    public void testSelectList() throws Exception {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        QueryWrapper<User> wrapper = queryWrapper.eq("username", "jack3");
        List<User> userList = this.userMapper.selectList(wrapper);
        for (User user : userList) {
            System.out.println(user);
        }
    }

    @Test
    public void testUpdateById() {

        User user = new User();
        user.setId(1);
        user.setUsername("jack");

        int i = this.userMapper.updateById(user);

        this.sqlSession.commit();

        System.out.println("更新记录: " + i + "条 !");
    }

    @Test
    public void testUpdateByCondition() {

        User user = new User();
        user.setUsername("rose");

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", 1);

        int update = this.userMapper.update(user, queryWrapper);

        sqlSession.commit();
        System.out.println("更新记录: " + update + "条 !");

    }

    @Test
    public void updateByUpdateWrapper() {

        UpdateWrapper<User> userUpdateWrapper = new UpdateWrapper<>();
        userUpdateWrapper.eq("id", 1)
                .set("username", "jack");

        int update = this.userMapper.update(null, userUpdateWrapper);
        sqlSession.commit();
        System.out.println("更新记录: " + update + "条 !");

    }

    @Test
    public void testDeleteById() {

        int i = this.userMapper.deleteById(1L);
        this.sqlSession.commit();
        System.out.println("删除记录: " + i + "条 !");
    }

    @Test
    public void testDeleteByMap() {

        Map<String, Object> map = new HashMap<>();
        map.put("username", "jack");
        map.put("id", 1);

        int i = this.userMapper.deleteByMap(map);
        System.out.println("删除记录: " + i + "条 !");
    }

    @Test
    public void testDeleteByEntity() {

        User user = new User();
        user.setUsername("jack");
        user.setId(1);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>(user);
        int delete = this.userMapper.delete(queryWrapper);
        System.out.println("删除记录: " + delete + "条 !");

    }

    @Test
    public void testDeleteByBatchIds() {

        int i = this.userMapper.deleteBatchIds(Arrays.asList(1L, 2L));
        System.out.println("删除记录: " + i + "条 !");
    }

    @Test
    public void testInsert() {

        User user = new User();
        user.setUsername("rose");

        int insert = this.userMapper.insert(user);
        this.sqlSession.commit();
        System.out.println("插入记录: " + insert + "条 !");
        System.out.println("插入 ID: " + user.getId() );
    }

    @Test
    public void testSelectPage(){

        Page<User> userPage = new Page<>(1,1);

        IPage<User> userIPage = this.userMapper.selectPage(userPage,null);
        System.out.println("total: " + userIPage.getTotal());
        System.out.println("records: " + userIPage.getRecords());

    }

}
