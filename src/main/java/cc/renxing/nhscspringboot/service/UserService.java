package cc.renxing.nhscspringboot.service;

import cc.renxing.nhscspringboot.model.Role;
import cc.renxing.nhscspringboot.model.User;
import cc.renxing.nhscspringboot.repository.RoleRepository;
import cc.renxing.nhscspringboot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public void registerUser(String username, String password) throws Exception {
        // 检查用户名是否已存在
        if (userRepository.findByUsername(username).isPresent()) {
            throw new Exception("用户名已存在！");
        }

        // 创建新用户
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password)); // 加密密码
        user.setEnabled(true);

        // 默认给用户分配 "ROLE_USER" 角色
        Role userRole = roleRepository.findByName("ROLE_USER").orElseThrow(() -> new Exception("用户角色未找到"));
        user.getRoles().add(userRole);

        // 保存用户到数据库
        userRepository.save(user);
    }
}
