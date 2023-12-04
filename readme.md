# ok-workoutapp

Учебный проект курса
[Kotlin Backend Developer](https://otus.ru/lessons/kotlin/?int_source=courses_catalog&int_term=programming).


Workoutapp -- ваш персональный тренер и компаньон для тренировок. Создавайте индивидуальные
тренировки, следите за своим прогрессом и оставайтесь мотивированными на пути к лучшей физической форме.


## Документация

1. Маркетинг
    1. [Заинтересанты](./docs/01-marketing/02-stakeholders.md)
    2. [Целевая аудитория](./docs/01-marketing/01-target-audience.md)
    3. [Конкурентный анализ](./docs/01-marketing/03-concurrency.md)
    4. [Анализ экономики](./docs/01-marketing/04-economy.md)
    5. [Пользовательские истории](./docs/01-marketing/05-user-stories.md)

2. DevOps
   1. [Схема инфраструктуры](./docs/02-devops/01-infrastruture.md)
   2. [Схема мониторинга](./docs/02-devops/02-monitoring.md)

3. Приемочные тесты - [ok-workoutapp-acceptance](ok-workoutapp-acceptance)

4. Архитектура
   1. [Компонентная схема](./docs/04-architecture/01-arch.md)
   2. [Интеграционная схема](./docs/04-architecture/02-integration.md)
   3. [Описание API](./docs/04-architecture/03-api.md)

# Структура проекта

1. Транспортные модели и мапперы
   1. [specs](specs) - спецификации openapi
   2. [ok-workoutapp-api-v1-jackson](ok-workoutapp-api-v1-jackson) используется jackson
   3. [ok-workoutapp-mappers-v1](ok-workoutapp-mappers-v1) Мапперы из транспортных моделей во внутренние модели

2. Бизнес-логика и внутренние модели
   1. [ok-workoutapp-common](ok-workoutapp-common) Внутренние модели, общие хелперы и интерфейсы
   2. [ok-workoutapp-stubs](ok-workoutapp-stubs) Стабы

3. Приложения (точки входа)
   1. [ok-workoutapp-app-common](ok-workoutapp-app-common) Общий код для приложений
   2. [ok-workoutapp-app-spring](ok-workoutapp-app-spring) Spring
   3. [ok-workoutapp-app-kafka](ok-workoutapp-app-kafka) Kafka