package com.itsm.service.config;

import com.itsm.service.model.*;
import com.itsm.service.repository.TicketRepository;
import com.itsm.service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;

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

        if (ticketRepository.count() == 0) {
            List<User> users = userRepository.findAll();
            User reporter1 = users.get(0); // Jan Kowalski (EMPLOYEE)
            User reporter2 = users.get(1); // Maria Wiśniewska (EMPLOYEE)
            User reporter3 = users.get(2); // Tomasz Zieliński (EMPLOYEE)
            User specialist1 = users.get(5); // Anna Nowak (IT_SPECIALIST)
            User specialist2 = users.get(6); // Paweł Lewandowski (IT_SPECIALIST)

            Ticket ticket1 = Ticket.builder()
                    .title("Problem z drukarką sieciową w dziale HR")
                    .description("Drukarka na 2 piętrze nie pobiera papieru i wyświetla kod błędu E04.")
                    .category(TicketCategory.HARDWARE)
                    .priority(TicketPriority.MEDIUM)
                    .status(TicketStatus.NEW)
                    .reporter(reporter1)
                    .assignee(null)
                    .build();

            Ticket ticket2 = Ticket.builder()
                    .title("Wniosek o dostęp do systemu finansowego SAP")
                    .description("Proszę o nadanie uprawnień do modułu faktur dla nowego pracownika.")
                    .category(TicketCategory.ACCESS)
                    .priority(TicketPriority.HIGH)
                    .status(TicketStatus.IN_PROGRESS)
                    .reporter(reporter2)
                    .assignee(specialist1)
                    .build();

            Ticket ticket3 = Ticket.builder()
                    .title("Awaria VPN podczas pracy zdalnej")
                    .description("Klient VPN rozłącza połączenie po 5 minutach od zalogowania.")
                    .category(TicketCategory.FAILURE)
                    .priority(TicketPriority.CRITICAL)
                    .status(TicketStatus.RESOLVED)
                    .reporter(reporter3)
                    .assignee(specialist2)
                    .build();

            ticketRepository.saveAll(List.of(ticket1, ticket2, ticket3));
            System.out.println("Załadowano 3 startowe zgłoszenia do bazy danych.");
        }
    }
}