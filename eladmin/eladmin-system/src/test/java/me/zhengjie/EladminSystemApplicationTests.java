package me.zhengjie;

import me.zhengjie.modules.system.domain.Dept;
import me.zhengjie.modules.system.service.DeptService;
import me.zhengjie.modules.system.service.RoleService;
import me.zhengjie.modules.system.service.dto.RoleSmallDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EladminSystemApplicationTests {
    @Autowired
    private DeptService deptService;
    @Autowired
    private  PasswordEncoder passwordEncoder;
    @Autowired
    private  RoleService roleService;



    @Value("${rsa.private_key}")
    private String privateKey;

    @Test
    public void contextLoads() {

    }

    public static void main(String[] args) {
    }

    @Test
    public void getDeptById(){
        List<Dept> depts = deptService.findByPid(Long.valueOf(7));
        for(Dept dept:depts){
            System.out.println(dept.getName());
        }
      /*  System.out.println("in test------------");
        System.out.println("in test------------");
        System.out.println("in test------------");*/
    }

    @Test
    public void transUtil(){
       /* System.out.println("=========================");
        System.out.println(passwordEncoder.encode("123456"));*/
       /* String enString = "$2a$10$Egp1/gvFlt7zhlXVfEFw4OfWQCGPw0ClmMcc6FjTnvXNRVf9zdMRa";
        // 字符串解密
        RSA rsa = new RSA(privateKey, null);
        String deString = new String(rsa.decrypt(enString, KeyType.PrivateKey));    //获得解密后的string*/
       // System.out.println("解密后的字符串为:" + deString);
        //字符串加密
       /* String deString1 = "";
        RSA rsa1 = new RSA(privateKey, null);
        String enString1 = new String(rsa.decrypt(deString1, KeyType.PrivateKey));    //获得加密后的string
        System.out.println("解密后的字符串为:" + enString1);*/

    }

    @Test
    public void getRoleList(){
        List<RoleSmallDto> roleList = roleService.findByUsersId((long) 2);
        for(RoleSmallDto list:roleList ){
            System.out.println("==============" + list);
        }
    }


}

