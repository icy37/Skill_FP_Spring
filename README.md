# Skill_FP_Spring

Финальный проект на платформе Skillfactory.
Реализация API для Интернет-банка.

Задача: реализовать Rest API по работе с банковским счетом.

Реализованые операции API:

Текущий баланс пользователя
getBalance - GET localhost:8080/api/users/1/balance

Снятие заданной суммы с баланса пользователя
takeMoney - PUT localhost:8080/api/users/1/takeMoney?amount=5.0

Пополнение баланса на заданную сумму
putMoney - PUT localhost:8080/api/users/1/putMoney?amount=10.0

Отобразить список операций за выбранный период
getOperationList- GET localhost:8080/api/users/1/operations
getOperationList- GET localhost:8080/api/users/1/operations?startDate=2023-11-28&endDate=2024-04-14

![image](https://github.com/icy37/Skill_FP_Spring/assets/4716456/5677f1d4-4044-46e0-ac7c-15276581ec8c)
