package com.zhisou.qqs.portal.website.core;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

/**
 * 
 * 重写角色验证方法，允许拥有一个角色即可授权访问
 *
 * @author            JiangTao
 * @version           1.0
 * @since             2015-7-27
 */
public class RolesAuthorizationFilter
  extends AuthorizationFilter
{
  public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
    throws IOException
  {
    Subject subject = getSubject(request, response);
    String[] rolesArray = (String[])mappedValue;
    if ((rolesArray == null) || (rolesArray.length == 0)) {
      return true;
    }
    Set<String> roles = CollectionUtils.asSet(rolesArray);
    
    for (String role : roles)
    {
        if(subject.hasRole(role))
            return true;
    }
    return false;
//    return subject.hasAllRoles(roles);
  }
}
