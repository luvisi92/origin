package com.zhisou.qqs.portal.website.core;

import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import com.likegene.framework.core.shiro.redis.RedisSessionDAO;

/**
 * 
 * shiro用户权限控制
 * 
 * @author JiangTao
 * @version 1.0
 * @since 2015-7-20
 */
@Service("authorizeRealm")
public class AuthorizeRealm extends AuthorizingRealm
{
    private Logger Logger = LoggerFactory.getLogger(AuthorizeRealm.class);
    
    @Autowired
    private CacheManager cacheManager;
    
    public AuthorizeRealm()
    {
        setName("AuthorizeProvider"); // This name must match the name in the
                                      // user // User
//        setCredentialsMatcher(new HashedCredentialsMatcher(
//                Sha256Hash.ALGORITHM_NAME));
    }

    /**
     * validate the login method of user name and password
     * 
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
            AuthenticationToken authcToken) throws AuthenticationException
    {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        String username = token.getUsername();
        
        return new SimpleAuthenticationInfo(username, token.getPassword(), getName());
    }

    /**
     * get the permission add into the simpleAuthorizationInfo
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(
            PrincipalCollection principals)
    {
        String username = (String) principals.fromRealm(getName()).iterator()
                .next();
        if (username != null)
        {
            String permissionKey = RedisSessionDAO.SHIRO_REDIS_SESSION_PRE+username;
            Cache cache = cacheManager.getCache("USER_ROLES");
            
            if (cache == null || cache.get(permissionKey) == null){
                Logger.debug("加载权限信息");
                SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
                Set<String> roles = new HashSet<String>();
                if (username.equals("admin")){
                    roles.add("ADMIN");
                } else {
                    roles.add(":sys:index");
                    roles.add(":sys:SysFile:index");
                    roles.add(":sys:SysFile:save");
                    roles.add(":sys:SysFile:update");
                    roles.add("NONE");
                }
                roles.add("NONE");
                info.setRoles(roles);
                cache.put(permissionKey, info);
                return info;
            } else {
                Logger.debug("从缓存中读取权限信息");
                SimpleAuthorizationInfo info = (SimpleAuthorizationInfo) cache.get(permissionKey).get();
                return info;
            }
        }
        else
        {
            return null;
        }
    }
    
    

}
