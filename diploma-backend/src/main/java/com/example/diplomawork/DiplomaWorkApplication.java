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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class DiplomaWorkApplication {

    public static void main(String[] args) {
        SpringApplication.run(DiplomaWorkApplication.class, args);
    }

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedOrigins(Arrays.asList("https://almatyustazy-2023.kz", "http://localhost:3000"));
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

//    @Bean
//    @Transactional
//    CommandLineRunner run(RoleRepository roleRepository, UserRepository userRepository, GroupRepository groupRepository, TeamRepository teamRepository, StageRepository stageRepository, AnnouncementRepository announcementRepository, SubjectRepository subjectRepository, CategoryRepository categoryRepository) {
//        return args -> {
//
//            roleRepository.save(new Role(null, "ROLE_ADMIN"));
//            roleRepository.save(new Role(null, "ROLE_STUDENT"));
//            roleRepository.save(new Role(null, "ROLE_COMMISSION"));
//            roleRepository.save(new Role(null, "ROLE_SECRETARY"));
//
//            stageRepository.save(Stage.builder().name("STAGE-1").build());
//            stageRepository.save(Stage.builder().name("STAGE-2").build());
//            stageRepository.save(Stage.builder().name("STAGE-3").build());
//
//            userRepository.save(User.builder()
//                    .id(null)
//                    .firstName("admin")
//                    .lastName("admin")
//                    .middleName(null)
//                    .username("ozhek")
//                    .role(roleRepository.findByName("ROLE_ADMIN"))
//                    .password(new BCryptPasswordEncoder().encode("7db1fa534a4b"))
//                    .email("a.olzhas.a@gmail.com")
//                    .build());
//
//            userRepository.save(User.builder()
//                    .id(null)
//                    .firstName("Secretary")
//                    .lastName("Secretary")
//                    .middleName("Secretary")
//                    .username("sec")
//                    .role(roleRepository.findByName("ROLE_SECRETARY"))
//                    .password(new BCryptPasswordEncoder().encode("verySecret3$"))
//                    .build());
//
//            // ------ Groups / Schools ------
//            List<Group> groups = new ArrayList<>();
//            groups.add(Group.builder().id(null).nameRus("Школа-14").nameKaz("Мектеп-14").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-26").nameKaz("Мектеп-26").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-41").nameKaz("Мектеп-41").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-82").nameKaz("Мектеп-82").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-91").nameKaz("Мектеп-91").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-114").nameKaz("Мектеп-114").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-149").nameKaz("Мектеп-149").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-150").nameKaz("Мектеп-150").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-151").nameKaz("Мектеп-151").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-152").nameKaz("Мектеп-152").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-154").nameKaz("Мектеп-154").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-156").nameKaz("Мектеп-156").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-160").nameKaz("Мектеп-160").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-164").nameKaz("Мектеп-164").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-166").nameKaz("Мектеп-166").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-169").nameKaz("Мектеп-169").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-171").nameKaz("Мектеп-171").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-178").nameKaz("Мектеп-178").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-179").nameKaz("Мектеп-179").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-180").nameKaz("Мектеп-180").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-181").nameKaz("Мектеп-181").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-182").nameKaz("Мектеп-182").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-184").nameKaz("Мектеп-184").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-185").nameKaz("Мектеп-185").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-196").nameKaz("Мектеп-196").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-205").nameKaz("Мектеп-205").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-209").nameKaz("Мектеп-209").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-210").nameKaz("Мектеп-210").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-8").nameKaz("Мектеп-8").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-15").nameKaz("Мектеп-15").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-16").nameKaz("Мектеп-16").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-18").nameKaz("Мектеп-18").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-24").nameKaz("Мектеп-24").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-25").nameKaz("Мектеп-25").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-34").nameKaz("Мектеп-34").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-36").nameKaz("Мектеп-36").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-39").nameKaz("Мектеп-39").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-46").nameKaz("Мектеп-46").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-54").nameKaz("Мектеп-54").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-55").nameKaz("Мектеп-55").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-58").nameKaz("Мектеп-58").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-62").nameKaz("Мектеп-62").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-37").nameKaz("Мектеп-37").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-75").nameKaz("Мектеп-75").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-79").nameKaz("Мектеп-79").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-90").nameKaz("Мектеп-90").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-95").nameKaz("Мектеп-95").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-96").nameKaz("Мектеп-96").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-120").nameKaz("Мектеп-120").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-124").nameKaz("Мектеп-124").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-128").nameKaz("Мектеп-128").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-134").nameKaz("Мектеп-134").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-135").nameKaz("Мектеп-135").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-136").nameKaz("Мектеп-136").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-144").nameKaz("Мектеп-144").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-147").nameKaz("Мектеп-147").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-167").nameKaz("Мектеп-167").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-1").nameKaz("Мектеп-1").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-5").nameKaz("Мектеп-5").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-6").nameKaz("Мектеп-6").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-9").nameKaz("Мектеп-9").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-13").nameKaz("Мектеп-13").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-27").nameKaz("Мектеп-27").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-42").nameKaz("Мектеп-42").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-72").nameKaz("Мектеп-72").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-86").nameKaz("Мектеп-86").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-97").nameKaz("Мектеп-97").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-104").nameKaz("Мектеп-104").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-111").nameKaz("Мектеп-111").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-113").nameKaz("Мектеп-113").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-116").nameKaz("Мектеп-116").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-117").nameKaz("Мектеп-117").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-119").nameKaz("Мектеп-119").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-121").nameKaz("Мектеп-121").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-122").nameKaz("Мектеп-122").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-123").nameKaz("Мектеп-123").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-126").nameKaz("Мектеп-126").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-127").nameKaz("Мектеп-127").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-130").nameKaz("Мектеп-130").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-132").nameKaz("Мектеп-132").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-133").nameKaz("Мектеп-133").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-139").nameKaz("Мектеп-139").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-141").nameKaz("Мектеп-141").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-153").nameKaz("Мектеп-153").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-155").nameKaz("Мектеп-155").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-158").nameKaz("Мектеп-158").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-173").nameKaz("Мектеп-173").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-175").nameKaz("Мектеп-175").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-КТЛ").nameKaz("Мектеп-КТЛ").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-202").nameKaz("Мектеп-202").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-10").nameKaz("Мектеп-10").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-21").nameKaz("Мектеп-21").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-22").nameKaz("Мектеп-22").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-23").nameKaz("Мектеп-23").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-37").nameKaz("Мектеп-37").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-38").nameKaz("Мектеп-38").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-40").nameKaz("Мектеп-40").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-45").nameKaz("Мектеп-45").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-51").nameKaz("Мектеп-51").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-60").nameKaz("Мектеп-60").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-63").nameKaz("Мектеп-63").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-65").nameKaz("Мектеп-65").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-68").nameKaz("Мектеп-68").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-69").nameKaz("Мектеп-69").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-70").nameKaz("Мектеп-70").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-73").nameKaz("Мектеп-73").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-81").nameKaz("Мектеп-81").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-88").nameKaz("Мектеп-88").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-92").nameKaz("Мектеп-92").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-93").nameKaz("Мектеп-93").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-94").nameKaz("Мектеп-94").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-105").nameKaz("Мектеп-105").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-125").nameKaz("Мектеп-125").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-138").nameKaz("Мектеп-138").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-140").nameKaz("Мектеп-140").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-145").nameKaz("Мектеп-145").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-146").nameKaz("Мектеп-146").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-165").nameKaz("Мектеп-165").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-183").nameKaz("Мектеп-183").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-189").nameKaz("Мектеп-189").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-190").nameKaz("Мектеп-190").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-191").nameKaz("Мектеп-191").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-199").nameKaz("Мектеп-199").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-10 ").nameKaz("Мектеп-10 ").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-2").nameKaz("Мектеп-2").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-3").nameKaz("Мектеп-3").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-43").nameKaz("Мектеп-43").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-57").nameKaz("Мектеп-57").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-66").nameKaz("Мектеп-66").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-80").nameKaz("Мектеп-80").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-87").nameKaz("Мектеп-87").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-101").nameKaz("Мектеп-101").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-102").nameKaz("Мектеп-102").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-103").nameKaz("Мектеп-103").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-108").nameKaz("Мектеп-108").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-109").nameKaz("Мектеп-109").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-110").nameKaz("Мектеп-110").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-112").nameKaz("Мектеп-112").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-118").nameKaz("Мектеп-118").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-129").nameKaz("Мектеп-129").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-137").nameKaz("Мектеп-137").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-143").nameKaz("Мектеп-143").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-148").nameKaz("Мектеп-148").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-177").nameKaz("Мектеп-177").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-193").nameKaz("Мектеп-193").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-4").nameKaz("Мектеп-4").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-7").nameKaz("Мектеп-7").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-12").nameKaz("Мектеп-12").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-19").nameKaz("Мектеп-19").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-28").nameKaz("Мектеп-28").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-29").nameKaz("Мектеп-29").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-30").nameKaz("Мектеп-30").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-33").nameKaz("Мектеп-33").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-35").nameKaz("Мектеп-35").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-47").nameKaz("Мектеп-47").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-48").nameKaz("Мектеп-48").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-52").nameKaz("Мектеп-52").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-53").nameKaz("Мектеп-53").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-56").nameKaz("Мектеп-56").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-64").nameKaz("Мектеп-64").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-77").nameKaz("Мектеп-77").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-98").nameKaz("Мектеп-98").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-99").nameKaz("Мектеп-99").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-100").nameKaz("Мектеп-100").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-131").nameKaz("Мектеп-131").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-159").nameKaz("Мектеп-159").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-161").nameKaz("Мектеп-161").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-163").nameKaz("Мектеп-163").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-168").nameKaz("Мектеп-168").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-172").nameKaz("Мектеп-172").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-194").nameKaz("Мектеп-194").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-197").nameKaz("Мектеп-197").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-17").nameKaz("Мектеп-17").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-157").nameKaz("Мектеп-157").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-174").nameKaz("Мектеп-174").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-176").nameKaz("Мектеп-176").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-186").nameKaz("Мектеп-186").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-187").nameKaz("Мектеп-187").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-188").nameKaz("Мектеп-188").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-192").nameKaz("Мектеп-192").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-200").nameKaz("Мектеп-200").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-206").nameKaz("Мектеп-206").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-207").nameKaz("Мектеп-207").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-208").nameKaz("Мектеп-208").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-15").nameKaz("Мектеп-15").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-11").nameKaz("Мектеп-11").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-17").nameKaz("Мектеп-17").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-20").nameKaz("Мектеп-20").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-31").nameKaz("Мектеп-31").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-32").nameKaz("Мектеп-32").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-44").nameKaz("Мектеп-44").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-49").nameKaz("Мектеп-49").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-50").nameKaz("Мектеп-50").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-59").nameKaz("Мектеп-59").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-61").nameKaz("Мектеп-61").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-71").nameKaz("Мектеп-71").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-74").nameKaz("Мектеп-74").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-76").nameKaz("Мектеп-76").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-78").nameKaz("Мектеп-78").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-83").nameKaz("Мектеп-83").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-84").nameKaz("Мектеп-84").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-85").nameKaz("Мектеп-85").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-89").nameKaz("Мектеп-89").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-106").nameKaz("Мектеп-106").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-107").nameKaz("Мектеп-107").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-115").nameKaz("Мектеп-115").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-142").nameKaz("Мектеп-142").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-162").nameKaz("Мектеп-162").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-170").nameKaz("Мектеп-170").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-195").nameKaz("Мектеп-195").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-198").nameKaz("Мектеп-198").build());
//            groups.add(Group.builder().id(null).nameRus("Школа-203").nameKaz("Мектеп-203").build());
//            groupRepository.saveAll(groups);
//            // ------ Groups / Schools ------
//
//
//            // ------ Subjects ------
//            subjectRepository.save(new Subject(null, "Бастауыш білім беру", "Учитель начальных классов" ));
//            subjectRepository.save(new Subject(null, "Қазақ тілі мен әдебиеті", "Казахский язык и литература" ));
//            subjectRepository.save(new Subject(null, "Орыс тілі мен әдебиеті", "Русский язык и литература" ));
//            subjectRepository.save(new Subject(null, "Шет тілі мен әдебиеті", "Иностранный язык и литература" ));
//            subjectRepository.save(new Subject(null, "Математика", "Математика" ));
//            subjectRepository.save(new Subject(null, "Информатика", "Информатика" ));
//            subjectRepository.save(new Subject(null, "Физика", "Физика" ));
//            subjectRepository.save(new Subject(null, "Химия", "Химия" ));
//            subjectRepository.save(new Subject(null, "Биология", "Биология" ));
//            subjectRepository.save(new Subject(null, "География", "География" ));
//            subjectRepository.save(new Subject(null, "Тарих", "Тарих" ));
//            // ------ Subjects ------
//
//            userRepository.save(User.builder()
//                    .id(null)
//                    .firstName("Commission1")
//                    .lastName("Commission1")
//                    .middleName("Commission1")
//                    .username("com1")
//                    .role(roleRepository.findByName("ROLE_COMMISSION"))
//                    .subject(subjectRepository.getById(1L))
//                    .password(new BCryptPasswordEncoder().encode("verySecret3$"))
//                    .build());
//
//            userRepository.save(User.builder()
//                    .id(null)
//                    .firstName("Commission2")
//                    .lastName("Commission2")
//                    .middleName("Commission2")
//                    .username("com2")
//                    .role(roleRepository.findByName("ROLE_COMMISSION"))
//                    .subject(subjectRepository.getById(2L))
//                    .password(new BCryptPasswordEncoder().encode("verySecret3$"))
//                    .build());
//
//            // ------ Categories ------
//            categoryRepository.save(new Category(null, "Педагог", "Педагог"));
//            categoryRepository.save(new Category(null, "Педагог-стажер", "Педагог-тағылымдамашы"));
//            categoryRepository.save(new Category(null, "Педагог-модератор", "Педагог-модератор"));
//            categoryRepository.save(new Category(null, "Педагог-эксперт", "Педагог-сарапшы"));
//            categoryRepository.save(new Category(null, "Педагог-исследователь", "Педагог-зерттеуші"));
//            categoryRepository.save(new Category(null, "Педагог-мастер", "Педагог-шебер"));
//            // ------ Categories ------
//            announcementRepository.save(Announcement.builder().title("title").text("text").creator(userRepository.getById(1L)).build());
//        };
//    }
}
