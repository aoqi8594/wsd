package com.qzsoft.tah.dao.wsd;


import com.qzsoft.tah.entity.YwpzUserSEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author aq
 * @version 1.0 2021/6/1
 */
@Repository
public interface YwpzUserSDao extends JpaRepository<YwpzUserSEntity,Long> {

    @Modifying
    @Transactional
    @Query(value = "delete from ywpz_user_s ",nativeQuery = true)
    void delete();

    @Query(value = "select * from ywpz_user_s where login_name = :loginName ",nativeQuery = true)
    YwpzUserSEntity selUser(String loginName);

}
