package com.example.diplomawork;

import com.example.diplomawork.model.*;
import com.example.diplomawork.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@SpringBootApplication
public class DiplomaWorkApplication {

    public static void main(String[] args) {
        SpringApplication.run(DiplomaWorkApplication.class, args);
    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        corsConfiguration.setAllowedHeaders(Arrays.asList("Origin", "Access-Control-Allow-Origin", "Content-Type",
                "Accept", "Authorization", "Origin, Accept", "X-Requested-With",
                "Access-Control-Request-Method", "Access-Control-Request-Headers"));
        corsConfiguration.setExposedHeaders(Arrays.asList("Origin", "Content-Type", "Accept", "Authorization",
                "Access-Control-Allow-Origin", "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials"));
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
        return new CorsFilter(urlBasedCorsConfigurationSource);
    }

    @Bean
    @Transactional
    CommandLineRunner run(RoleRepository roleRepository, UserRepository userRepository, GroupRepository groupRepository, TeamRepository teamRepository, UserTeamRepository userTeamRepository, StageRepository stageRepository, AnnouncementRepository announcementRepository) {
        return args -> {

            roleRepository.save(new Role(null, "ROLE_ADMIN"));
            roleRepository.save(new Role(null, "ROLE_STUDENT"));
            roleRepository.save(new Role(null, "ROLE_COMMISSION"));
            roleRepository.save(new Role(null, "ROLE_SECRETARY"));

            // --------------------//
            groupRepository.save(new Group(null, "IT-1902"));
            groupRepository.save(new Group(null, "IT-1903"));
            groupRepository.save(new Group(null, "SE-1901"));
            groupRepository.save(new Group(null, "SE-1903"));
            groupRepository.save(new Group(null, "SE-1905"));
            groupRepository.save(new Group(null, "SE-1907"));

            stageRepository.save(Stage.builder().name("STAGE-1").build());
            stageRepository.save(Stage.builder().name("STAGE-2").build());
            stageRepository.save(Stage.builder().name("STAGE-3").build());

            userRepository.save(User.builder()
                    .id(null)
                    .firstName("admin")
                    .lastName("admin")
                    .middleName(null)
                    .username("admin")
                    .role(roleRepository.findByName("ROLE_ADMIN"))
                    .password(new BCryptPasswordEncoder().encode("admin"))
                    .build());

            userRepository.save(User.builder()
                    .id(null)
                    .firstName("Д.")
                    .lastName("Едилхан")
                    .middleName(null)
                    .username("edilkhan")
                    .role(roleRepository.findByName("ROLE_COMMISSION"))
                    .password(new BCryptPasswordEncoder().encode("verySecret3$"))
                    .build());

            userRepository.save(User.builder()
                    .id(null)
                    .firstName("А.")
                    .lastName("Адамова")
                    .middleName(null)
                    .username("adamova")
                    .role(roleRepository.findByName("ROLE_COMMISSION"))
                    .password(new BCryptPasswordEncoder().encode("verySecret3$"))
                    .build());

            userRepository.save(User.builder()
                    .id(null)
                    .firstName("С.")
                    .lastName("Аубакиров")
                    .middleName(null)
                    .username("aubakirov")
                    .role(roleRepository.findByName("ROLE_COMMISSION"))
                    .password(new BCryptPasswordEncoder().encode("verySecret3$"))
                    .build());

            userRepository.save(User.builder()
                    .id(null)
                    .firstName("Д.")
                    .lastName("Аябекова")
                    .middleName(null)
                    .username("ayabekova")
                    .role(roleRepository.findByName("ROLE_COMMISSION"))
                    .password(new BCryptPasswordEncoder().encode("verySecret3$"))
                    .build());

            userRepository.save(User.builder()
                    .id(null)
                    .firstName("А.")
                    .lastName("Смайыл")
                    .middleName(null)
                    .username("smaiyl")
                    .role(roleRepository.findByName("ROLE_COMMISSION"))
                    .password(new BCryptPasswordEncoder().encode("verySecret3$"))
                    .build());

            userRepository.save(User.builder()
                    .id(null)
                    .firstName("Б.")
                    .lastName("Кумалаков")
                    .middleName(null)
                    .username("kumalakov")
                    .role(roleRepository.findByName("ROLE_COMMISSION"))
                    .password(new BCryptPasswordEncoder().encode("verySecret3$"))
                    .build());

            userRepository.save(User.builder()
                    .id(null)
                    .firstName("Н.")
                    .lastName("Рахимжанов")
                    .middleName(null)
                    .username("rakhimzhanov")
                    .role(roleRepository.findByName("ROLE_COMMISSION"))
                    .password(new BCryptPasswordEncoder().encode("verySecret3$"))
                    .build());

            userRepository.save(User.builder()
                    .id(null)
                    .firstName("Secretary")
                    .lastName("Secretary")
                    .middleName("Secretary")
                    .username("sec")
                    .role(roleRepository.findByName("ROLE_SECRETARY"))
                    .password(new BCryptPasswordEncoder().encode("verySecret3$"))
                    .build());

            announcementRepository.save(Announcement.builder()
                    .title("Hello")
                    .creator(userRepository.getById(9L))
                    .build());

            // ------ User Team ------
            userRepository.save(User.builder()
                    .id(null)
                    .firstName("Olzhas")
                    .lastName("Abdykalykov")
                    .middleName("Akumzhanuly")
                    .username("ozhek")
                    .role(roleRepository.findByName("ROLE_STUDENT"))
                    .password(new BCryptPasswordEncoder().encode("verySecret3$"))
                    .build());

            teamRepository.save(Team.builder()
                    .id(null)
                    .confirmed(true)
                    .creator(userRepository.findByUsername("ozhek").get())
                    .name("ozhek")
                    .build());

            userTeamRepository.save(UserTeam.builder()
                    .id(null)
                    .user(userRepository.findByUsername("ozhek").get())
                    .team(teamRepository.findTeamByName("ozhek"))
                    .build());

            // ------ User Team ------

        };
    }
}
