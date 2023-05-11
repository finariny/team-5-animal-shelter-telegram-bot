package com.example.team5animalsheltertelegrambot.timer;

import com.example.team5animalsheltertelegrambot.entity.animal.Animal;
import com.example.team5animalsheltertelegrambot.entity.animal.Cat;
import com.example.team5animalsheltertelegrambot.entity.animal.Dog;
import com.example.team5animalsheltertelegrambot.entity.person.Customer;
import com.example.team5animalsheltertelegrambot.entity.report.AnimalReport;
import com.example.team5animalsheltertelegrambot.properties.TelegramProperties;
import com.example.team5animalsheltertelegrambot.repository.AnimalReportRepository;
import com.example.team5animalsheltertelegrambot.service.animal.CatService;
import com.example.team5animalsheltertelegrambot.service.animal.DogService;
import com.example.team5animalsheltertelegrambot.service.bot.BotCommandService;
import com.pengrad.telegrambot.TelegramBot;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TaskTimer {
    private static final long DAYS_BEFORE_CUSTOMER_WARNING = 1L;
    private static final long DAYS_BEFORE_VOLUNTEER_WARNING = 2L;

    private final AnimalReportRepository animalReportRepository;
    private final CatService catService;
    private final DogService dogService;
    private final BotCommandService botCommandService;
    private final TelegramProperties telegramProperties;
    private final TelegramBot telegramBot;

    private LocalDateTime currentDateTime;

    /**
     * Задание, выполняемое по рассписанию
     */
    @Scheduled(cron = "${telegram.report-cron}")
    private void runTask() {
        currentDateTime = LocalDateTime.now();

        // Список всех кошек на испытательном сроке (которых усыновили)
        List<Cat> cats = catService.findOnProbation(
                ProbationType.DEADLINE_30,
                ProbationType.DEADLINE_60,
                ProbationType.DEADLINE_44
        );
        cats.forEach(this::checkReportForDeadline);

        // Список всех собак на испытательном сроке (которых усыновили)
        List<Dog> dogs = dogService.findOnProbation(
                ProbationType.DEADLINE_30,
                ProbationType.DEADLINE_60,
                ProbationType.DEADLINE_44
        );
        dogs.forEach(this::checkReportForDeadline);
    }

    /**
     * Проверка отчетов о животном на наличие просроченных
     * и отправка сообщений, если такие имеются. <br/>
     * Если 1 сутки - отправка пользователю,<br/>
     * если 2 суток, то еще и волонтеру
     *
     * @param animal животное
     */
    private void checkReportForDeadline(Animal animal) {

        // последний отчет по данной животине
        AnimalReport firstAnimalReport = animalReportRepository.findFirstByAnimalOrderByDateCreateDesc(animal);
        if (firstAnimalReport == null) {
            return;
        }
        LocalDateTime reportDeadline1 = currentDateTime.minusDays(DAYS_BEFORE_CUSTOMER_WARNING);
        LocalDateTime reportDeadline2 = currentDateTime.minusDays(DAYS_BEFORE_VOLUNTEER_WARNING);

        // если первый дедлайн прохлопал, то предупреждение ему
        if (reportDeadline1.isAfter(firstAnimalReport.getDateCreate())) {
            Customer customer = animal.getAdopter();
            String messageToUser = String.format("""
                            *Уважаемый %s %s*\\!%n
                            Вы забыли отправить ежедневный отчет об %s, поторопитесь\\!""",
                    customer.getLastName(), customer.getFirstName(), animal.getName());
            botCommandService.sendMessage(customer.getChatId(), messageToUser);

            // если и второй прохлопал, то еще и волонтеру
            if (reportDeadline2.isAfter(firstAnimalReport.getDateCreate())) {
                String messageToVolunteer = String.format("""
                                *Волонтер*,%n%s %s игнорирует отчеты об %s второй день\\!""",
                        customer.getLastName(), customer.getFirstName(), animal.getName());
                botCommandService.sendMessage(telegramProperties.volunteerChatId(), messageToVolunteer);
            }
        }
    }
}