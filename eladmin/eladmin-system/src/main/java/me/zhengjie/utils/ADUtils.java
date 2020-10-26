package me.zhengjie.utils;

import lombok.Data;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import java.util.Hashtable;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName ADUtils.java
 * @Description TODO
 * @createTime 2020年10月21日 11:40:00
 */
@Data
public class ADUtils {
    /** 
    * @Description: 验证用户输入的账号和密码是否在域中 
    * @Params: [userName, password] 
    * @return: boolean
    */
    String userName;//AD域认证，用户的登录UserName
    String password;//AD域认证，用户的登录PassWord*/
    String host = "10.66.52.52";//AD域IP
    String domain = "@justech.com";//域名后缀
    String port = "389"; //端口，一般默认389
    String url = new String("ldap://" + host + ":" + port);//固定写法
    String user = userName.indexOf(domain) > 0 ? userName : userName
            + domain;
    public boolean validateUser(String userName,String password){
        Boolean flag=false;
        /*String userName = "chenxin.jiang";//AD域认证，用户的登录UserName
        String password = "Jiang.183";//AD域认证，用户的登录PassWord*/

        Hashtable env = new Hashtable();//实例化一个Env
        DirContext ctx = null;
        env.put(Context.SECURITY_AUTHENTICATION, "simple");//LDAP访问安全级别(none,simple,strong),一种模式，这么写就行
        env.put(Context.SECURITY_PRINCIPAL, user); //用户名
        env.put(Context.SECURITY_CREDENTIALS, password);//密码
        env.put(Context.INITIAL_CONTEXT_FACTORY,
                "com.sun.jndi.ldap.LdapCtxFactory");// LDAP工厂类
        env.put(Context.PROVIDER_URL, url);//Url
        try {
            ctx = new InitialDirContext(env);// 初始化上下文
            System.out.println("身份验证成功!");
            flag=true;
        } catch (AuthenticationException e) {
            System.out.println("身份验证失败!");
            e.printStackTrace();
        } catch (javax.naming.CommunicationException e) {
            System.out.println("AD域连接失败!");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("身份验证未知异常!");
            e.printStackTrace();
        } finally{
            if(null!=ctx){
                try {
                    ctx.close();
                    ctx=null;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }
}
