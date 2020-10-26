package me.zhengjie.utils;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.Control;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import javax.naming.ldap.SortControl;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Properties;

/**
 * @author admin
 * @version 1.0.0
 * @ClassName LDAPConnector.java
 * @Description TODO
 * @createTime 2020年10月21日 10:52:00
 */
public class LDAPConnector {
    private static LDAPConnector instance;
    private String url;
    private String baseDN;
    private String bindDN;
    private String bindPassword;
    private final Hashtable<String, String> env = new Hashtable<String, String>();
    private final Control[] sortControls = new SortControl[1];
    LdapContext dirContext=null;

    {
        try {
            sortControls[0] = new SortControl("sAMAccountName", Control.CRITICAL);
        } catch (IOException ex) {
        }
    }

    public LDAPConnector() {
        try {
            InputStream in =  LDAPConnector.class.getClassLoader().getResourceAsStream("ldap.properties");
            Properties properties = new Properties();
            properties.load(in);
            url = properties.getProperty("url");
            baseDN = properties.getProperty("baseDN");
            bindDN = properties.getProperty("bindDN");
            bindPassword = properties.getProperty("bindPassword");
            // set up environment for creating initial context
            env.put(Context.PROVIDER_URL, url);
            env.put(Context.SECURITY_PRINCIPAL, bindDN);
            env.put(Context.SECURITY_CREDENTIALS, bindPassword);
            env.put(Context.SECURITY_AUTHENTICATION, "simple");
            env.put("java.naming.batchsize", "50");
            env.put("com.sun.jndi.ldap.connect.timeout", "3000");
            env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            env.put("com.sun.jndi.ldap.connect.pool", "true");

            env.put("com.sun.jndi.ldap.connect.pool.maxsize", "3");
            env.put("com.sun.jndi.ldap.connect.pool.prefsize", "1");
            env.put("com.sun.jndi.ldap.connect.pool.timeout", "300000");
            env.put("com.sun.jndi.ldap.connect.pool.initsize", "1");
            env.put("com.sun.jndi.ldap.connect.pool.authentication", "simple");
            env.put("java.naming.ldap.attributes.binary", "objectGUID");
        } catch (Exception e) {
            // ignore error
            e.printStackTrace();
        }
    }

    public static LDAPConnector getInstance() {
        if (instance == null)
            instance = new LDAPConnector();
        return instance;
    }

    /**
    * @Description: 验证用户名和密码在域中是否正确
    * @Params: [username, password]
    * @return: boolean
    */
    public boolean validateUser(String username, String password) {
        boolean passed = false;
        try {
            dirContext = new InitialLdapContext(env,sortControls);
            dirContext.setRequestControls(sortControls);
            SearchControls controls = new SearchControls();
            controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            String filter = "(sAMAccountName=" + username + ")";
            NamingEnumeration<?> answer = dirContext.search(baseDN, filter, controls);
            String userDN;
            String user=null;
            while (answer.hasMoreElements()) {
                SearchResult searchResult = (SearchResult) answer.next();
                //获得用户DN
                userDN = searchResult.getName();
                String split = userDN.split(",")[0].split("=")[1];
                user=split;
            }
            Hashtable<String, String> env = new Hashtable<String, String>();
            env.put(Context.PROVIDER_URL, url);
            env.put(Context.SECURITY_PRINCIPAL,user+"@justech.com");
            env.put(Context.SECURITY_CREDENTIALS, password);
            env.put(Context.SECURITY_AUTHENTICATION, "simple");
            env.put("com.sun.jndi.ldap.connect.timeout", "1000");
            env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            // create initial context
            DirContext context = new InitialDirContext(env);
            passed = true;
            context.close();
        } catch (NamingException e) {
            e.printStackTrace();
        } finally {
            if (dirContext != null) {
                try {
                    dirContext.close();
                } catch (NamingException e) {
                    e.printStackTrace();
                }
            }

        }
        return passed;
    }

    /**
    * @Description: 根据用户名获取工号
    * @Params: [username] 用户名
    * @return: java.lang.String 工号
    */
    public String getEmpNo(String username){
        String empNo=null;
        try {
            dirContext = new InitialLdapContext(env, sortControls);
            dirContext.setRequestControls(sortControls);
            SearchControls controls = new SearchControls();
            controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            String filter = "(sAMAccountName=" + username + ")";
            NamingEnumeration<?> answer = dirContext.search(baseDN, filter, controls);
            while (answer.hasMoreElements()) {
                SearchResult sr = (SearchResult) answer.next();
                empNo=sr.getAttributes().get("pager").toString();
            }
        } catch (NamingException e) {
            e.printStackTrace();
        } finally {
            if (dirContext != null) {
                try {
                    dirContext.close();
                } catch (NamingException e) {
                    e.printStackTrace();
                }
            }
        }
        return empNo.split(":")[1].trim().toUpperCase();
    }

}
