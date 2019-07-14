package com.three.authserver.user.repository;


import com.three.authserver.user.entity.User;
import com.three.commonclient.base.repository.BaseRepository;

/**
 * Created by csw on 2019/03/27.
 * Description:
 */

public interface UserRepository extends BaseRepository<User, Long> {

    User findByUsername(String username);

}
