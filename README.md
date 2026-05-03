# Firm System — API фрагмента информационной системы производственной фирмы

![Java](https://img.shields.io/badge/Java-21-blue) ![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-brightgreen) ![Spring Cloud](https://img.shields.io/badge/Spring_Cloud-2025-green) 
![Spring Security](https://img.shields.io/badge/Spring_Security-JWT-red)
![Kafka](https://img.shields.io/badge/Kafka--yellow)
## О проекте
Firm System — это RESTful API, разработанное на базе Spring Boot. Приложение представляет собой фрагмент информационной системы производственной фирмы, который будет обеспечивать
*  Управление производственным циклом инструментов
*  Управление производственным циклом материалов
*  Управление выпускаемой продукцией
*  Управление операциями
*  Управление нарядами
*  Управление цехами
*  Ведение отчетности
  
## Ключевые особенности
<table>
<tr>
<td>

🏢 **Микросервисная архитектура**
- Разделенные сервисы согласно бизнес-логике
- Независимое развертывание и масштабирование
🌱 **Spring Boot и Spring Cloud**
- Регистрация сервисов (Eureka)
- API Gateway (Spring Cloud Gateway)
- Сервис конфигураций (Spring config)
- Circuit Breakers & Fallback method (Resilience4j)

</td>
<td>

🐳 **Контейнеризация**
- Контейнерезированные сервисы
- Docker Compose оркестрация

📡 **Событийно-ориентированная архитектура**
- Apache Kafka брокер сообщений
- Асинхронное взаимодействие сервисов

</td>
</tr>
<tr>
<td>

🗄️ **Базы данных**
- PostgreSQL
- Redis
- Liquibase управление схемой

</td>
<td>

🔍 **Обнаружение сервисов**
- Регистрация сервисов через Eureka 
- Динамическая регистрация сервисов
- Проверка работоспособности сервисов

🛡️ **Безопасность**
- Spring Security и OAuth2

</td>
</tr>
</table>

## Стек технологий
*   **Язык:** Java 21
*   **Фреймворк:** Spring Boot 3 и Spring Cloud
*   **База данных:** PostgreSQL
*   **Кеширование** Redis
*   **Миграции БД** Liquibase
*   **ORM** Hibernate 
*   **Брокер сообщений** Apache Kafka
*   **Контейнеризация** Docker


## Сервисная архитектура
![Architecture.png](Architecture.png)

## Компоненты
<div align="center">

| Сервис | Порт | Краткое описание | Тех стек |
|---------|------|-------------|------------|
| **Gateway Service** | 8072 | Единая точка входа, маршрутизация и балансировка нагрузки | Spring Cloud Gateway |
| **Config Service** | 8888 | Централизованное управление конфигурацией | Spring Cloud Config |
| **Eureka Service** | 8070 | Обнаружение сервисов с Eureka | Spring Cloud Netflix |
| **Order Service** | 8082 | Взаимодействие с заказами | PostgreSQL + Liquibase (YAML) |
| **Product Service** | 8081 | Взаимодействие с товарами | PostgreSQL + Liquibase (YAML) |
| **Factory Service** | 8083 | Жизненный цикл обработки заказа | PostgreSQL + Liquibase (YAML) + Redis |
| **Warehouse Service** | 8084 | Склад материалов | PostgreSQL + Liquibase (YAML) |
| **Toolwarehouse Service** | 8085 | Склад инструментария | PostgreSQL + Liquibase (YAML) |
| **Employee Service** | 8080 | Взаимодействие с работниками | PostgreSQL + Liquibase (YAML) |

</div>

## Установка и запуск

## Обнаружение сервисов и доступ к API
Мониторинг всех зарегистрированных микросервисов и их работоспособности:
- **Дашбоард:** [http://localhost:8070/](http://localhost:8070/)
- **Функции:** Мониторинг работоспособности сервисов в режиме реального времени

## Запуск тестов


