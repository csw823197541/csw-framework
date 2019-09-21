package com.three.commonjpa.base.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by csw on 2019/03/27.
 * Description:
 */
@NoRepositoryBean
public interface BaseRepository<T, ID> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

    /**
     * 查找ID且一个状态
     *
     * @param id     主键ID
     * @param status 状态
     */
    public T findByIdAndStatus(Long id, Integer status);

    /**
     * 查找ID且多个状态
     *
     * @param id     主键ID
     * @param status 状态数组
     */
    public T findByIdAndStatusIn(Long id, Integer[] status);

    /**
     * 批量更新数据状态
     * #{#className} 实体类对象
     *
     * @param status 状态
     * @param id     ID列表
     * @return 更新数量
     */
    @Modifying
    @Transactional
    @Query("update #{#entityName} set status = ?1  where id in ?2  and status <> 3")
    public Integer updateStatus(Integer status, List<Long> id);

    List<T> findAllByStatus(Integer code);
}
