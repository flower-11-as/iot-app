package com.scrawl.iot.web.shiro;

import com.scrawl.iot.web.dao.entity.Manager;
import com.scrawl.iot.web.dao.mapper.ManagerMapper;
import com.scrawl.iot.web.dao.mapper.ManagerRoleMapper;
import com.scrawl.iot.web.dao.mapper.RoleMenuMapper;
import com.scrawl.iot.web.enums.ManagerStatusEnum;
import com.scrawl.iot.web.exception.BizException;
import com.scrawl.iot.web.helper.ApplicationContextHelper;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.SimpleByteSource;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * Description:
 * Created by as on 2018/3/25.
 */
public class IotShiroRealm extends AuthorizingRealm {

    private static final String REALM_NAME = IotShiroRealm.class.getName();

    @Override
    public void setName(String name) {
        super.setName(REALM_NAME);
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        Manager manager = (Manager) principals.getPrimaryPrincipal();
        ManagerRoleMapper managerRoleMapper = ApplicationContextHelper.getBean(ManagerRoleMapper.class);
        RoleMenuMapper roleMenuMapper = ApplicationContextHelper.getBean(RoleMenuMapper.class);
        List<Integer> roles = managerRoleMapper.selectRoleIdsByManagerId(manager.getId());
        List<String> permissions = roleMenuMapper.selectPermissionsByManagerId(manager.getId());
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        if (!ObjectUtils.isEmpty(roles) || !ObjectUtils.isEmpty(permissions)) {
            if (!ObjectUtils.isEmpty(roles)) {
                authorizationInfo.addRoles(roles.stream().map(Object::toString).collect(Collectors.toList()));
            }
            if (!ObjectUtils.isEmpty(permissions)) {
                authorizationInfo.addStringPermissions(permissions.stream().filter(StringUtils::isNotBlank).collect(Collectors.toList()));
            }
        }
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        AtomicReference<SimpleAuthenticationInfo> simpleAuthenticationInfo = new AtomicReference<>();
        ManagerMapper managerMapper = ApplicationContextHelper.getBean(ManagerMapper.class);
        Optional<Manager> optional = Optional.ofNullable(managerMapper.selectByUsername(usernamePasswordToken.getUsername()));

        // 校验是否禁用
        optional.filter(manager -> manager.getStatus().equals(ManagerStatusEnum.OFF.getCode())).ifPresent(manager -> {throw new BizException("SYS10002");});

        // 校验是否
        optional.ifPresent(manager -> simpleAuthenticationInfo.set(new SimpleAuthenticationInfo(manager,
                manager.getPassword(), new SimpleByteSource(manager.getSalt()), REALM_NAME)));

        return simpleAuthenticationInfo.get();
    }
}
