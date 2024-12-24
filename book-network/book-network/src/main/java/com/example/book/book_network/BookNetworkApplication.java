package com.example.book.book_network;

import com.example.book.book_network.role.Role;
import com.example.book.book_network.role.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;

@SpringBootApplication
@EnableJpaAuditing
@EnableAsync
@ComponentScan(basePackages = "com.example.book")

public class BookNetworkApplication {
    private static final Logger logger = (Logger) LoggerFactory.getLogger(BookNetworkApplication.class);

    public static void main(String[] args) {

//        String secretKey = generateSecretKey();
//        logger.info("Generated secret key: {}", secretKey);
        SpringApplication.run(BookNetworkApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(RoleRepository roleRepository) {
        return args -> {
            if (roleRepository.findByName("USER").isEmpty()) {
                var role = Role.builder().name("USER").build();
                roleRepository.save(role);
            }
        };
    }

    private static String generateSecretKey() {
        try {
            // Generate the HMAC-SHA256 key
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
            keyGen.init(256); // 256 bits for HMAC-SHA256
            SecretKey secretKey = keyGen.generateKey();
            // Encode the key to Base64
            return Base64.getEncoder().encodeToString(secretKey.getEncoded());
        } catch (Exception e) {
            logger.error("Error generating secret key: {}", e.getMessage());
            return null;
        }
    }

}
