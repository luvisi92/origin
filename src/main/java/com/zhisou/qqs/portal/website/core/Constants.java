package com.zhisou.qqs.portal.website.core;

/**
 * 存放应用常量
 * 
 * @author     GuoJun
 * @version    1.0
 * @since      2017-03-10
 */
public class Constants {

    /**
     * 用户用于加密/解密的密匙
     */
    public static final String LOGIN_KEY = "LOGIN_KEY";
    
    /**
     * 短信失效时间单位分钟
     */
    public static final int LOST_TIME = 2;
    /**
     * 客服类型
     */
    public static final int CUSTOMERSERVICE = 1;
    
    /**
     * 买家类型
     */
    public static final int BUYER = 3;
    
    /**
     * shiro AES加/解密KEY
     * 需16位字符
     */
    public static final String TOKEN_KEY = "Ac_Mi_Lan_SZ_CN1";
    
    /**
     * 修改资料类型
     */
    public static interface DataType {
        
        //昵称
        String NICK_NAME = "nickname";
        
        //支付密码
        String PAY_PWD = "payPwd";
        
        //登录密码
        String PWD = "pwd";
        
        //头像
        String FACE = "face";
        
        //邀请码inviterCode
        String INVITER = "inviter";
    }
    
    /**
     * 绑定类型
     */
    public static interface BindingType {
        
        //已存在
        String BINDING_HAVE = "have";
        
        //新注册
        String BINDING_NEW = "new";

    }
}
