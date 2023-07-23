# Телеграм-бот для приюта животных

## Краткое описание
Телеграм-бот отвечает на популярные вопросы людей о том, что нужно знать и уметь, чтобы забрать кошку или собаку из приюта.  
После усыновления животного, бот принимает ежедневные отчеты новых хозяев о том, как питомец приспосабливается к новой обстановке.

## Команды бота
* `/about` - присылает информацию о боте
* `/start` - начинает общение с пользователем, предоставляет выбор приюта
* `/cats` - переключает на приют для кошек
* `/dogs` - переключает на приют для собак
* `/info` - присылает группу команд с информацией о приюте
* `/shelter` - присылает информацию о приюте
* `/location` - присылает расписание работы приюта, адрес и схему проезда
* `/contact` - присылает телефон приюта
* `/security` - присылает контактные данные охраны для оформления пропуска на машину
* `/safety` - присылает PDF-файл с информацией о технике безопасности на территории приюта
* `/adopt` - присылает PDF-файл с информацией том, как взять животное из приюта
* `/report` - присылает шаблон отчета и сохраняет отчет о питомце
* `/phone` - сохраняет номер телефона пользователя для связи с волонтерами
* `/volunteer` - отправляет сообщение и телефон пользователя в чат волонтеров

## Демо
![AnimalShelterBotGif](src/main/resources/AnimalShelterBotGif.gif)

## Сборка и запуск проекта
1. Склонируйте проект:
* `git clone git@github.com:finariny/team-5-animal-shelter-telegram-bot.git`

2. Пропишите в файле _application.properties_ ваш токен телегам бота и id общего чата сотрудников:
* `telegram.volunteer-chat-id=-000000000`
* `telegram.token=************`

3. Соберите и запустите проект в Docker контейнерах (Linux):
* `cd team-5-animal-shelter-telegram-bot/`
* `mvn clean package`
* `cp target/shelter.jar deployment/docker-images/web/`
* `cd deployment/`
* `docker-compose -f docker-compose.yml up -d`

## Стек технологий
* **Язык и окружение**
  - Java 17
  - Maven
  - Spring Boot
  - Spring Web
  - Spring Data JPA
  - REST
  - GIT
  - Swagger
  - Lombok
  - Liquibase
* **База данных**
  - PostgreSQL
* **Тестирование**
  - JUnit
  - Mockito
* **Прочее**
  - Docker

## Авторы
* Кирилл Вохминов ([KaerLaende](https://github.com/KaerLaende))
* Айкануш Арутюнян ([rafaelovna](https://github.com/rafaelovna))
* Павел Турлачев ([turchev](https://github.com/turchev))
* Юрий Калынбаев ([YURIYKALYNBAEV](https://github.com/YURIYKALYNBAEV))
* Анастасия Драгомирова ([finariny](https://github.com/finariny))