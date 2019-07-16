package com.three.authserver.service;

import com.three.authserver.user.entity.Authority;
import com.three.authserver.user.entity.Role;
import com.three.authserver.user.entity.User;
import com.three.authserver.user.repository.AuthorityRepository;
import com.three.authserver.user.repository.UserRepository;
import com.three.common.enums.StatusEnum;
import com.three.commonclient.exception.BusinessException;
import com.three.common.utils.BeanCopyUtil;
import com.three.authserver.sys.SysAuthority;
import com.three.authserver.sys.SysUser;
import com.three.authserver.sys.SysRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by csw on 2019/07/13.
 * Description:
 */
@Service
public class SysUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    public SysUser findByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        SysUser sysUser = new SysUser();
        sysUser = (SysUser) BeanCopyUtil.copyBean(user, sysUser);

        Set<SysRole> sysRoleSet = new HashSet<>();
        Set<SysAuthority> authoritySet = new HashSet<>();
        for (Role role : user.getRoles()) {
            SysRole sysRole = new SysRole();
            sysRole = (SysRole) BeanCopyUtil.copyBean(role, sysRole);
            sysRoleSet.add(sysRole);
            for (Authority authority : role.getAuthorities()) {
                SysAuthority sysAuthority = new SysAuthority();
                sysAuthority = (SysAuthority) BeanCopyUtil.copyBean(authority, sysAuthority);
                authoritySet.add(sysAuthority);
            }
        }
        sysUser.setSysRoles(sysRoleSet);
        sysUser.setSysAuthorities(authoritySet);
        return sysUser;
    }


    public List<SysAuthority> findAllSysAuthorities() {
        List<SysAuthority> sysAuthorityList = new ArrayList<>();
        List<Authority> authorityList = authorityRepository.findAllByStatus(StatusEnum.OK.getCode());
        for (Authority authority : authorityList) {
            SysAuthority sysAuthority = new SysAuthority();
            sysAuthority = (SysAuthority) BeanCopyUtil.copyBean(authority, sysAuthority);
            sysAuthorityList.add(sysAuthority);
        }
        return sysAuthorityList;
    }
}
