//package com.itheima.health.security;
//
//import com.itheima.health.pojo.Permission;
//import com.itheima.health.pojo.Role;
//import com.itheima.health.pojo.User;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
///**
// * 用于替代 在 spring-security配置文件中配置的角色及权限
// */
//public class SecurityUserDetailsService implements UserDetailsService {
//
//    @Autowired
//    private BCryptPasswordEncoder passwordEncoder;
//
//    // 模拟数据库的用户记录，如下User类是health_common中的自定义实体类User
//    // 修改Role、Permission，为其增加不带参、带参构造方法
//    private static Map<String, User> userDb = new HashMap();
//
//    static {
//        User user1 = new User();
//        user1.setUsername("admin");
//        user1.setPassword("123");
//        // 用户权限与角色
//        Role role1 = new Role("系统管理员", "ROLE_ADMIN");
//        role1.getPermissions().add(new Permission("添加权限", "add"));
//        role1.getPermissions().add(new Permission("删除权限", "delete"));
//        role1.getPermissions().add(new Permission("更新权限", "update"));
//        role1.getPermissions().add(new Permission("查询权限", "find"));
//        user1.getRoles().add(role1);
//        userDb.put(user1.getUsername(), user1);
//
//        User userZhangSan = new User();
//        userZhangSan.setUsername("zhangsan");
//        userZhangSan.setPassword("123");
//        Role role2 = new Role("数据分析员", "ROLE_READER");
//        role2.getPermissions().add(new Permission("查询权限", "find"));
//        userZhangSan.getRoles().add(role2);
//        userDb.put(userZhangSan.getUsername(), userZhangSan);
//
//        User userLisi = new User();
//        userLisi.setUsername("lisi");
//        userLisi.setPassword("123");
//        Role role3 = new Role("运营管理员", "ROLE_OMS");
//
//        role3.getPermissions().add(new Permission("添加权限", "add"));
//        role3.getPermissions().add(new Permission("更新权限", "update"));
//        userLisi.getRoles().add(role3);
//        userDb.put(userLisi.getUsername(), userLisi);
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        // 模拟从数据库读取用户信息
//        User user = userDb.get(username);
//        if (user == null) {
//            return null;
//        }
//        // 获取用户角色及权限
//        List<GrantedAuthority> authorityList = new ArrayList<>();
//        for (Role role : user.getRoles()) {
//            // 角色关键词
//            authorityList.add(new SimpleGrantedAuthority(role.getKeyword()));
//            for (Permission permission : role.getPermissions()) {
//                // 把权限放入列表
//                authorityList.add(new SimpleGrantedAuthority(permission.getKeyword()));
//            }
//        }
//        // 封装成UserDetail对象
//        // 必须是加密的密码, 无加密, 默认在前面加 {noop}
//        // String authPassword = "{noop}" + user.getPassword();
//        String authPassword = passwordEncoder.encode(user.getPassword());
//        System.out.println("加密后:"+ authPassword);
//        UserDetails userDetails = new org.springframework.security.core.userdetails.User(username, authPassword, authorityList);
//        return userDetails;
//    }
//}
