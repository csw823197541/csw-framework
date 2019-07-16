package com.three.authserver.user.repository;

import com.three.authserver.user.entity.Role;
import com.three.commonjpa.base.repository.BaseRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by csw on 2019/03/27.
 * Description:
 */
public interface RoleRepository extends BaseRepository<Role, Long>, JpaSpecificationExecutor<Role> {

}
