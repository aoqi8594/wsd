package com.qzsoft.tah.dao.wsd;

import com.qzsoft.tah.entity.WsdCarB;
import com.qzsoft.tah.entity.WsdDevB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Repository
public interface WsdCarBDao extends JpaRepository<WsdCarB,Long> {

}
