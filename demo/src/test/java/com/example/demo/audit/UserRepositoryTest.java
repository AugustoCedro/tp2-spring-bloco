package com.example.demo.audit;

import com.example.demo.repository.AdventurerRepository;
import com.example.demo.repository.OrganizationRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@Transactional
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:postgresql://localhost:5432/postgredb",
        "spring.datasource.username=adm",
        "spring.datasource.password=adm",
        "spring.jpa.hibernate.ddl-auto=validate" // importante: não altera nada
})
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AdventurerRepository adventurerRepository;

    @Test
    void testUserRolesAndOrganization() {
        System.out.println(adventurerRepository.findAll());
    }
}
