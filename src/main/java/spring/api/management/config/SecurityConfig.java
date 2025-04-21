package spring.api.management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.disable())  // Hoặc cấu hình CORS cụ thể
                .csrf(csrf -> csrf.disable()) // Tắt CSRF bảo vệ
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // Cho phép truy cập mọi API mà không cần xác thực
                )
                .httpBasic(Customizer.withDefaults());  // Bật Basic Auth
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}

