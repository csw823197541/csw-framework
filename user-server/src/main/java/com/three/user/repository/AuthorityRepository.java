package com.three.user.repository;

import com.three.user.entity.Authority;
import com.three.commonjpa.base.repository.BaseRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by csw on 2019/03/27.
 * Description:
 */
public interface AuthorityRepository extends BaseRepository<Authority, Long>, JpaSpecificationExecutor<Authority> {

    Authority findByAuthorityName(String authorityName);

    Authority findByAuthorityNameOrAuthorityUrl(String authorityName, String authorityUrl);

    List<Authority> findAllByStatusAndAuthorityType(int state, int authorityType);
}
