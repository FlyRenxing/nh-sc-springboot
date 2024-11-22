package cc.renxing.nhscspringboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers( "/cart/**").authenticated()
                        .requestMatchers("/admin/**").hasRole("ADMIN")  // 仅允许ADMIN角色访问/admin
                        .anyRequest().permitAll()  // 其他页面可以匿名访问
                )
                .rememberMe((rememberMe) -> rememberMe
                        .key("remember-me-sample")
                        .tokenValiditySeconds(86400)  // 有效时间为一天
                )
                .formLogin((form) -> form
                        .loginPage("/login")  // 设置自定义登录页面
                        .defaultSuccessUrl("/", true)  // 登录成功后跳转的页面
                        .permitAll()  // 允许所有用户访问登录页面
                )
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/logout-success")  // 跳转到自定义的退出页面
                        .permitAll()
                );
        return http.build();
    }

    // BCrypt 密码编码器
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
