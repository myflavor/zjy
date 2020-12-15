package cn.myflavor.zjy.mapper;

import cn.myflavor.zjy.entity.Cdk;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface CdkeyMapper {
    @Options(useGeneratedKeys = true, keyProperty = "id")
    @Insert("insert into cdk(id,cdkey,month) values(#{id},#{cdkey},#{month})")
    boolean addCdk(Cdk cdk);

    @Select("select * from cdk where cdkey = #{cdkey}")
    Cdk findByCdkey(@Param("cdkey") String cdkey);

    @Delete("delete from cdk where cdkey = #{cdkey}")
    void delByCdkey(@Param("cdkey") String cdkey);

    @Update("update cdk set month=#{month} where cdkey = #{cdkey}")
    boolean updataByCdkey(@Param("cdkey") String cdkey, int month);

    @Select("select * from cdk order by id")
    List<Cdk> findAllCdkey();

    @Select("select * from cdk where id = #{id}")
    Cdk findById(String id);

    @Update("update cdk set cdkey=#{cdkey},month=#{month} where id = #{id}")
    boolean updataById(Cdk cdk);

    @Delete("delete from cdk where id = #{id}")
    boolean delById(String id);

    @Select("select count(*) from cdk")
    String cdkCount();

    @Select("select * from cdk order by id limit #{no},#{page}")
    List<Cdk> findCdkeysLimit(@Param("page") int page, @Param("no") int no);
}
