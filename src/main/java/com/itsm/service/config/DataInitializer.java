package com.itsm.service.config;

import com.itsm.service.model.Role;
import com.itsm.service.model.User;
import com.itsm.service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;


    @Override
    public void run(String... args) {
        if (userRepository.count() == 0) {

            User employee1 = User.builder()
                    .firstName("Jan")
                    .lastName("Kowalski")
                    .email("jan.kowalski@itsm.local")
                    .department("HR")
                    .role(Role.EMPLOYEE)
                    .build();

            User employee2 = User.builder()
                    .firstName("Maria")
                    .lastName("Wiśniewska")
                    .email("maria.wisniewska@itsm.local")
                    .department("Finance")
                    .role(Role.EMPLOYEE)
                    .build();

            User employee3 = User.builder()
                    .firstName("Tomasz")
                    .lastName("Zieliński")
                    .email("tomasz.zielinski@itsm.local")
                    .department("Sales")
                    .role(Role.EMPLOYEE)
                    .build();

            User employee4 = User.builder()
                    .firstName("Katarzyna")
                    .lastName("Wójcik")
                    .email("katarzyna.wojcik@itsm.local")
                    .department("Marketing")
                    .role(Role.EMPLOYEE)
                    .build();

            User employee5 = User.builder()
                    .firstName("Michał")
                    .lastName("Kamiński")
                    .email("michal.kaminski@itsm.local")
                    .department("HR")
                    .role(Role.EMPLOYEE)
                    .build();

            User specialist1 = User.builder()
                    .firstName("Anna")
                    .lastName("Nowak")
                    .email("anna.nowak@itsm.local")
                    .department("IT")
                    .role(Role.IT_SPECIALIST)
                    .build();

            User specialist2 = User.builder()
                    .firstName("Paweł")
                    .lastName("Lewandowski")
                    .email("pawel.lewandowski@itsm.local")
                    .department("IT")
                    .role(Role.IT_SPECIALIST)
                    .build();

            User specialist3 = User.builder()
                    .firstName("Magdalena")
                    .lastName("Dąbrowska")
                    .email("magdalena.dabrowska@itsm.local")
                    .department("IT")
                    .role(Role.IT_SPECIALIST)
                    .build();

            User admin1 = User.builder()
                    .firstName("Piotr")
                    .lastName("Wiśniewski")
                    .email("admin@itsm.local")
                    .department("IT")
                    .role(Role.ADMIN)
                    .build();

            User admin2 = User.builder()
                    .firstName("Aleksandra")
                    .lastName("Kowalczyk")
                    .email("aleksandra.kowalczyk@itsm.local")
                    .department("IT")
                    .role(Role.ADMIN)
                    .build();

            userRepository.saveAll(List.of(
                    employee1,
                    employee2,
                    employee3,
                    employee4,
                    employee5,
                    specialist1,
                    specialist2,
                    specialist3,
                    admin1,
                    admin2
            ));

            System.out.println("Załadowano 10 startowych użytkowników do bazy danych.");
        }
    }
}
