package cn.sspku.basics.baseClass;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * 基类数据链路层
 * @author 
 */
@NoRepositoryBean
public interface sspkuBaseDao<E, ID extends Serializable> extends JpaRepository<E, ID>, JpaSpecificationExecutor<E> {
    @Override
    E getById(ID id);
}
