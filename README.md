# WolframX


## Скриншоты работы приложения
### Главная страница
![изображение](https://github.com/MaxEmelyanovich/WolframX/assets/99658243/5587712e-f278-4211-866c-eaa9c6f6f435)

### Экран login
![изображение](https://github.com/MaxEmelyanovich/WolframX/assets/99658243/365f07fe-9da9-498b-aa95-00c8d486f1f7)

### Вычисления (метод Гаусса)
![изображение](https://github.com/MaxEmelyanovich/WolframX/assets/99658243/0a5ea2cc-84cf-4c2b-b749-02ce9204f443)
### Вычисления (операции с двумя векторами)
![изображение](https://github.com/MaxEmelyanovich/WolframX/assets/99658243/6421d3ab-d1ed-4d1e-8f61-95cd2c513955)

### Чат (экран Ivan)
![изображение](https://github.com/MaxEmelyanovich/WolframX/assets/99658243/fa3c25cf-514f-45ec-ba93-b07af8cda957)

### Чат (экран Alex)
![изображение](https://github.com/MaxEmelyanovich/WolframX/assets/99658243/a2b9d15c-15de-4b3c-89a6-2385343bdfa3)

### История вычислений
![изображение](https://github.com/MaxEmelyanovich/WolframX/assets/99658243/36a769cb-a5a7-4ea9-be05-1a8d93d619cb)

## Инструкция по развертыванию (Hamachi)
Для организации сетевого взаимодействия используется инструмент создания виртуальных сетей Hamachi

Основные шаги для организации сетевого взаимодействия через Hamachi:
1. Создание виртуальной сети: Пользователь создает виртуальную сеть, указывая имя сети и пароль. Это действие создает виртуальный сетевой адаптер на компьютере и присваивает ему уникальный IP-адрес в пределах этой сети.
2. Подключение клиентов к сети: Клиенты, установившие Hamachi и зная имя сети и пароль, присоединяются к созданной виртуальной сети. После успешного подключения клиент также получает уникальный IP-адрес в рамках этой сети.
3. Пересылка данных: Hamachi устанавливает защищенное соединение между компьютерами внутри виртуальной сети. Он использует технологию туннелирования, чтобы создать виртуальный туннель между клиентскими и серверными компьютерами, что позволяет передавать данные между ними.
4. На клиентской машине, где расположен фронтенд приложения, указывается IP-адрес сервера в качестве адреса для отправки запросов. В Hamachi IP-адрес сервера будет отображаться рядом с именем сервера в списке подключенных устройств.
5. В бэкенд приложении, которое работает на серверной машине, определен Spring REST контроллер для обработки запросов, полученных через сетевое взаимодействие с клиентским приложением через Hamachi.

## Инструкция по настройке проекта для локального запуска (Docker)

Контейнер БД
1. Взять образ mysql с Docker Hub: docker pull mysql/mysql-server:latest
2. Запустить контейнер командой docker run -d -p 3306:3306 --name mysql_container -e MYSQL_ROOT_PASSWORD=your_password mysql/mysql-server:latest
3. Получить доступ к mysql внутри контейнера: docker exec -it mysql_container mysql -uroot -p
4. Ввести пароль

Контейнер backend
1. В корневой директории проекта находится Dockerfile: https://github.com/MaxEmelyanovich/WolframX/blob/main/wolframx/Dockerfile
2. Собрать приложение в jar файл с помощью команды mvn clean package
3. Собрать docker image с помощью команды docker build -t my-spingboot-app, где my-springboot-app это имя образа
4. Запустить контейнер командой docker run -p 8080:8080 my-springboot-app

Контейнер frontend
1. В корневой директории проекта находится Dockerfile: https://github.com/MaxEmelyanovich/WolframX/blob/front/front/my-app/Dockerfile
2. Собрать docker image с помощью команды docker build -t my-react-app, где my-react-app это имя образа
3. Запустить контейнер командой docker run -d -p 3000:3000 my-react-app
## Стек технологий

Backend:
 - Язык программирования: Java
 - Фреймворк: Spring Boot
 - База данных: MySQL
 - ORM: Spring Data JPA
 - Безопасность: Spring Security
 - Вебсокеты: Spring WebSocket with STOMP
 - Документация API: Springdoc OpenAPI (Swagger)
 - Логирование: Log4j2
 - Сборка проекта: Maven
 - Дополнительные библиотеки:
   - Lombok - для сокращения кода
   - exp4j - для вычисления математических выражений
   - Spring Boot Starter Mail - для отправки email
   - JNI - для интеграции с нативным C++ кодом (GaussSolver)

Frontend:
 - Язык программирования: JavaScript (JSX)
 - Библиотека/Фреймворк: React (v18.2.0)
 - Сборка проекта: Create React App (CRA) (v5.0.1)
 - Роутинг: React Router (v6.22.3), React Router DOM (v6.22.3)
 - Интернационализация: react-i18next (v14.1.0) с i18next (v23.11.2)
 - Стили: CSS (с использованием CSS Modules)
   - Tailwind CSS (v3.0.2) - утилитарный фреймворк для быстрой стилизации.
 - Взаимодействие с сервером: fetch API
 - Вебсокеты:
   - sockjs-client (v1.6.1) - для эмуляции вебсокетов в старых браузерах.
   - stompjs (v2.3.3) - клиент STOMP для работы с вебсокетами на сервере.
 - Дополнительные библиотеки:
   - html-to-image (v1.11.11) - для создания скриншотов веб-страниц.



## Схема базы данных:

![entities drawio(1)](https://github.com/MaxEmelyanovich/WolframX/assets/99658243/2f6c5cde-886a-4c40-8f94-816817a8d2f6)

### Описание:
Схема базы данных описывает структуру для хранения информации о пользователях, чатах, сообщениях и выполненных расчетах.
Она разделена на две основные части:
1. Вычисления: Сущности User and Calculation отвечают за хранение информации о пользователях, выполняющих математические операции, и результатах этих вычислений.
2. Чат: Сущности User, Chat, Message и Member обеспечивают функционал чата, где пользователи могут общаться в общих или приватных комнатах.

Сущности:
 - User: Хранит информацию о пользователях:
   - user_id: Уникальный идентификатор пользователя (целое число, автоинкремент).
   - first_name: Имя пользователя.
   - last_name: Фамилия пользователя (опционально).
   - email: Электронная почта пользователя (уникальное значение).
   - password: Пароль пользователя (хешированный).
 - Calculation: Хранит информацию о выполненных расчетах:
   - calculation_id: Уникальный идентификатор расчета (целое число, автоинкремент).
   - user_id: Идентификатор пользователя, выполнившего расчет.
   - task: Текст задачи.
   - result: Текст результата.
 - Chat: Хранит информацию о чатах:
   - chat_id: Уникальный идентификатор чата (целое число, автоинкремент).
   - chat_name: Название чата.
 - Message: Хранит информацию о сообщениях:
   - message_id: Уникальный идентификатор сообщения (целое число, автоинкремент).
   - user_id: Идентификатор пользователя, отправившего сообщение.
   - chat_id: Идентификатор чата, в котором отправлено сообщение.
   - message_content: Текст сообщения.
 - Member: Вспомогательная таблица для связи пользователей с чатами (many-to-many):
   - user_id: Идентификатор пользователя.
   - chat_id: Идентификатор чата.
   
Связи:
 - User - Calculation: Один-ко-многим (один пользователь может выполнить много расчетов).
 - User - Message: Один-ко-многим (один пользователь может отправить много сообщений).
 - Chat - Message: Один-ко-многим (один чат может содержать много сообщений).
 - User - Chat: ManyToMany (один пользователь может быть участником многих чатов, и один чат может иметь много участников) through Member.



## Документирование API (Swagger)
В данном приложении Swagger используется для автоматической генерации документации API. Библиотека Springdoc OpenAPI интегрирована с Spring Boot и предоставляет пользовательский интерфейс Swagger UI для визуализации документации.
![изображение](https://github.com/MaxEmelyanovich/WolframX/assets/99658243/1731c6f6-34ca-437a-ac2d-de36971284ca)

Далее будет рассмотрено использование аннотаций Swagger в коде на примере одного из классов API

```
@Tag(name = "Вычисления", description = "API для выполнения математических операций")
public class IntegralController {
	...
  @PostMapping("/trapezoidal")
      @Operation(summary = "Вычисление определенного интеграла методом трапеций",
                 description = "Вычисляет определенный интеграл с использованием метода трапеций.")
      @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Результат вычисления интеграла",
                  content = @Content(mediaType = "application/json", schema = @Schema(implementation = IntegralResponse.class))),
          @ApiResponse(responseCode = "400", description = "Ошибка в формате входных данных")
      })
      public ResponseEntity<?> calculateTrapezoidalIntegral(@RequestBody IntegralRequest integralRequest) {
  	...
  }
	…
}
```
В классе IntegralController аннотации Swagger используются для описания каждого endpoint'а API:

```@Tag(name = "Вычисления", description = "API для выполнения математических операций")```:
 - Определяет тег "Вычисления" для группировки endpoint'ов, связанных с математическими операциями.
 - Устанавливает описание тега - "API для выполнения математических операций".

```@Operation(summary = "Вычисление определенного интеграла методом трапеций", description = "Вычисляет определенный интеграл с использованием метода трапеций.")```:
 - summary: Краткое описание endpoint'а - "Вычисление определенного интеграла методом трапеций".
 - description: Более подробное описание endpoint'а.

```@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Результат вычисления интеграла", content = @Content(mediaType = "application/json", schema = @Schema(implementation = IntegralResponse.class))), @ApiResponse(responseCode = "400", description = "Ошибка в формате входных данных") })```:
 - Описывает возможные ответы API:
   - ```@ApiResponse(responseCode = "200", ...)```: Ответ с кодом 200 (OK) - возвращается в случае успешного вычисления интеграла.
     - description: Описание ответа - "Результат вычисления интеграла".
     - content: Описание формата ответа:
       - mediaType: Тип данных - "application/json".
       - schema: Схема данных, описывающая структуру ответа - IntegralResponse.
   - ```@ApiResponse(responseCode = "400", ...)```: Ответ с кодом 400 (Bad Request) - возвращается в случае ошибки в формате входных данных.

```@PostMapping("/trapezoidal")```:
 - Указывает, что endpoint обрабатывает POST-запросы по пути "/calculations/integrals/trapezoidal".

```@RequestBody IntegralRequest integralRequest```:
 - Определяет, что endpoint принимает тело запроса в формате JSON, соответствующее классу IntegralRequest

### Пример

Документация Swagger для endpoint'а calculateTrapezoidalIntegral будет выглядеть следующим образом:
![изображение](https://github.com/MaxEmelyanovich/WolframX/assets/99658243/a09e03f6-7463-45d0-ab5b-63656d462f0d)
![изображение](https://github.com/MaxEmelyanovich/WolframX/assets/99658243/9a24b198-b804-4c63-8152-67168e8c7a3d)




## Пример логов

```
2024-05-26 19:58:26.391 [http-nio-8080-exec-5] INFO  framexteam.wolframx.authentication.controller.AuthorizationController - Попытка авторизации пользователя: n@gmail.com
2024-05-26 19:58:26.467 [http-nio-8080-exec-5] DEBUG framexteam.wolframx.authentication.service.MyUserDetailsService - Searching for user with email: n@gmail.com
2024-05-26 19:58:26.469 [http-nio-8080-exec-5] DEBUG framexteam.wolframx.authentication.service.MyUserDetailsService - Found user: email=n@gmail.com, status=offline
2024-05-26 19:58:26.545 [http-nio-8080-exec-5] DEBUG framexteam.wolframx.authentication.service.UserService - Fetching user by email: n@gmail.com
2024-05-26 19:58:26.548 [http-nio-8080-exec-5] DEBUG framexteam.wolframx.authentication.service.UserService - Saving user: User(userId=1, email=n@gmail.com, password=$2a$10$GuVM38U6HRr7i0qWBhqP.ulbcmW2Tf9eqdp3aBIs7S2sMRb81fU2q, firstName=n, lastName=n, status=online, activationToken=null, enabled=true)
2024-05-26 19:58:26.552 [http-nio-8080-exec-5] INFO  framexteam.wolframx.authentication.controller.AuthorizationController - Пользователь n@gmail.com успешно авторизован
2024-05-26 19:58:58.439 [http-nio-8080-exec-6] DEBUG framexteam.wolframx.authentication.service.UserService - Fetching user by email: n@gmail.com
2024-05-26 19:58:58.441 [http-nio-8080-exec-6] INFO  framexteam.wolframx.authentication.controller.AuthorizationController - Пользователь n@gmail.com выходит из системы
2024-05-26 19:58:58.441 [http-nio-8080-exec-6] DEBUG framexteam.wolframx.authentication.service.UserService - Saving user: User(userId=1, email=n@gmail.com, password=$2a$10$GuVM38U6HRr7i0qWBhqP.ulbcmW2Tf9eqdp3aBIs7S2sMRb81fU2q, firstName=n, lastName=n, status=offline, activationToken=null, enabled=true)
```

Логи показывают последовательность событий, связанных с авторизацией и выходом пользователя n@gmail.com в приложении.
1. Авторизация:
2024-05-26 19:58:26.391: Пользователь с email n@gmail.com пытается войти в систему.
2024-05-26 19:58:26.467: Сервис MyUserDetailsService начинает поиск пользователя по email в базе данных.
2024-05-26 19:58:26.469: Пользователь найден в базе данных, его статус offline.
2024-05-26 19:58:26.545: Сервис UserService получает полную информацию о пользователе.
2024-05-26 19:58:26.548: Статус пользователя меняется на online и он сохраняется в базу данных.
2024-05-26 19:58:26.552: Авторизация прошла успешно, пользователь n@gmail.com вошел в систему.
2. Выход:
2024-05-26 19:58:58.439: Сервис UserService получает информацию о пользователе n@gmail.com по запросу из контроллера AuthorizationController.
2024-05-26 19:58:58.441: Зафиксирован выход пользователя n@gmail.com.
2024-05-26 19:58:58.441: Статус пользователя меняется на offline, и информация сохраняется в базу данных.
В итоге:
Логи показывают успешный процесс авторизации пользователя, его вход в систему и последующий выход из системы. Статус пользователя в базе данных меняется с offline на online при авторизации и обратно на offline при выходе.

