# Телеграм-бот для приюта животных
Телеграм-бот отвечает на популярные вопросы людей о том, что нужно знать и уметь, чтобы забрать животное из приюта, 
а также принимает ежедневные отчеты новых хозяев о том, как животное приспосабливается к новой обстановке.

## Сборка и запуск проекта
**Склонируйте проект:**
* git clone git@github.com:finariny/team-5-animal-shelter-telegram-bot.git

**Пропишите в файле _application.properties_ ваш токен телегам бота и id общего чата сотрудников:**
* telegram.volunteer-chat-id=-000000000
* telegram.token=************

**Соберите и запустите проект в Docker контейнерах (Linux):**
* cd team-5-animal-shelter-telegram-bot/
* mvn clean package
* cp target/shelter.jar deployment/docker-images/web/
* cd deployment/
* docker-compose -f docker-compose.yml up -d

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

### [Ссылка на бота](t.me/PankTeamAnimalShelterBot)

## Демо проекта
![](https://github.com/finariny/team-5-animal-shelter-telegram-bot/raw/master/src/main/resources/AnimalShelterBot.gif)

### [Видео-демонстрация работы бота](https://drive.google.com/drive/folders/1-C-6pCJNKzfQwpRhQ2akWGlm-j6nTa75?usp=share_link)

## Авторы
* [**KaerLaende**](https://github.com/KaerLaende) Кирилл Вохминов
* [**rafaelovna**](https://github.com/rafaelovna) Айкануш Арутюнян
* [**turchev**](https://github.com/turchev) Павел Турлачев
* [**finariny**](https://github.com/finariny) Анастасия Драгомирова
* [**YURIYKALYNBAEV**](https://github.com/YURIYKALYNBAEV) Юрий Калынбаев
