package cn.myflavor.zjy.mapper;

import cn.myflavor.zjy.entity.Admin;
import cn.myflavor.zjy.entity.Cdk;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface AdminMapper {
    @Insert("insert into admin(id,user,pwd,power) values(#{id},#{user},#{pwd},#{power})")
    boolean addAdmin(Admin admin);

    @Select("select * from admin where user = #{user}")
    Admin findByUser(@Param("user") String user);

    @Delete("delete from admin where user = #{user}")
    void delByUser(@Param("user") String user);

    @Update("update admin set pwd=#{pwd},power=#{power} where user = #{user}")
    boolean updataByUser(Admin admin);

    @Select("select * from admin")
    List<Admin> findAllAdmin();
}
