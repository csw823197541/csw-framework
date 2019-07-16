package com.three.authserver.user.service;

import com.three.authserver.user.entity.Authority;
import com.three.authserver.user.entity.Role;
import com.three.authserver.user.entity.User;
import com.three.authserver.user.param.RoleParam;
import com.three.authserver.user.repository.AuthorityRepository;
import com.three.authserver.user.repository.RoleRepository;
import com.three.authserver.user.repository.UserRepository;
import com.three.commonjpa.base.service.BaseService;
import com.three.common.vo.PageQuery;
import com.three.common.vo.PageResult;
import com.three.commonclient.exception.BusinessException;
import com.three.common.utils.BeanCopyUtil;
import com.three.commonclient.utils.BeanValidator;
import com.three.common.utils.StringUtil;
import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by csw on 2019/03/30.
 * Description:
 */
@Service
public class RoleService extends BaseService<Role> {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private UserRepository userRepository;

    public PageResult<Role> query(PageQuery pageQuery, int code, String searchKey, String searchValue) {
        Sort sort = new Sort(Sort.Direction.ASC, "createDate");
        return query(roleRepository, pageQuery, sort, code, searchKey, searchValue);
    }

    public List<Role> findAll(int code) {
        return roleRepository.findAllByStatus(code);
    }

    @Transactional
    public void create(RoleParam param) {
        BeanValidator.check(param);

        Role role = new Role();
        role = (Role) BeanCopyUtil.copyBean(param, role);

//        role.setCreateBy(LoginUser.getLoginUser());

        roleRepository.save(role);
    }

    @Transactional
    public void update(RoleParam param) {
        Preconditions.checkNotNull(param.getId(), "修改记录Id不可以为null");

        Role role = getEntityById(roleRepository, param.getId());
        role.setRoleName(param.getRoleName());
        role.setRemark(param.getRemark());
//        role.setUpdateBy(LoginUser.getLoginUser());

        roleRepository.save(role);
    }

    @Transactional
    public void delete(String ids, int code) {
        Set<Long> idSet = StringUtil.getStrToIdSet(ids);

        List<Role> roleList = new ArrayList<>();
        for (Long id : idSet) {
            Role role = getEntityById(roleRepository, id);

            Set<User> userSet = role.getUsers();
            if (userSet.size() > 0) {
                StringBuilder str = new StringBuilder();
                for (User user : userSet) {
                    str.append(user.getUsername()).append("、");
                }
                str.deleteCharAt(str.length() - 1);
                throw new BusinessException("该角色(id:" + id + ")已被用户(" + str.toString() + ")绑定，不能删除，请先解绑");
            }

            role.setStatus(code);
            roleList.add(role);
        }
        roleRepository.deleteAll(roleList);
    }

    public List<Map<String, Object>> findAuthTree(Long roleId) {
        Role role = roleRepository.getOne(roleId);

        Sort sort = new Sort(Sort.Direction.ASC, "sort");
        List<Authority> authorityList = authorityRepository.findAll(sort);

        List<Map<String, Object>> authTrees = new ArrayList<>();
        for (Authority authority : authorityList) {
            Map<String, Object> authTree = new HashMap<>();
            authTree.put("id", authority.getId());
            authTree.put("name", getAuthorityName(authority));
            authTree.put("pId", authority.getParentId());
            authTree.put("open", true);
            authTree.put("checked", role.getAuthorities().contains(authority));
            authTrees.add(authTree);
        }
        return authTrees;
    }

    private String getAuthorityName(Authority authority) {
//        if (AdminEnum.YES.getCode() == LoginUser.getLoginUser().getIsAdmin()) { // 超级管理员
//            return authority.getAuthorityName() + "(" + authority.getAuthorityUrl() + ")";
//        }
        return authority.getAuthorityName();
    }

    @Transactional
    public void assignRoleAuth(Long roleId, String authIds) {
        Role role = getEntityById(roleRepository, roleId);

        if (StringUtil.isBlank(authIds)) { // 没有绑定权限
            role.setAuthorities(new HashSet<>());
        } else { // 绑定新的权限
            Set<Authority> authoritySet = getAuthoritySet(authIds);
            role.setAuthorities(authoritySet);
        }

        roleRepository.save(role);
    }

    private Set<Authority> getAuthoritySet(String authIds) {
        String[] authIdArray = StringUtils.split(authIds, ",");
        Set<Long> authIdSet = StringUtil.getStringArrayToIdSet(authIdArray);
        return new HashSet<>(authorityRepository.findAllById(authIdSet));
    }

    @Transactional
    public void assignRoleUser(Long roleId, String userIds) {
        Role role = getEntityById(roleRepository, roleId);

        Set<Long> userIdSet = StringUtil.getStrToIdSet(userIds);
        Set<User> userSet = new HashSet<>(userRepository.findAllById(userIdSet));

        // 用户删除该角色
        userSet.forEach(user -> user.getRoles().remove(role));

        userRepository.saveAll(userSet);
    }
}
