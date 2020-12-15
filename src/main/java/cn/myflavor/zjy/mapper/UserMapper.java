package cn.myflavor.zjy.mapper;

import cn.myflavor.zjy.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;


public interface UserMapper {
    @Insert("insert into user(id,openid,mail,expiry) values(#{id},#{openid},#{mail},#{expiry})")
    boolean addUser(User user);

    @Select("select * from user where openid = #{openid}")
    User findByOpenId(@Param("openid") String openid);

    @Delete("delete from user where openid = #{openid}")
    void delByOpenId(@Param("openid") String openid);

    @Update("update user set id=#{id},mail=#{mail},expiry=#{expiry} where openid = #{openid}")
    boolean updataByOpenId(@Param("openid") String openid, int id, String mail, Date expiry);

    @Select("select * from user order by id")
    List<User> findAllUser();

    @Select("select * from user where id = #{id}")
    User findById(String id);

    @Update("update user set openid=#{openid},mail=#{mail},expiry=#{expiry} where id = #{id}")
    boolean updateById(User user);

    @Delete("delete from user where id = #{id}")
    boolean delById(String id);

    @Select("select count(*) from user ")
    String userCount();

    @Select("select * from user order by id limit #{no},#{page}")
    List<User> findCdkeysLimit(int page, int no);
}
