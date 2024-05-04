# Skill_FP_Spring

<body><p><strong>Skill_FP_Spring</strong> — это финальный проект на платформе <strong>Skillfactory</strong>, представляющий собой реализацию <strong>API для Интернет-банка</strong>.</p>
<h2>Задача</h2>
<p>Цель проекта — создать <strong>REST API</strong> для управления банковскими счетами.</p>

<h2>Реализованные операции API</h2>
<ol>
    <li>
        <strong>Получение текущего баланса пользователя</strong>
        <ul>
            <li>Эндпоинт: <code>GET localhost:8080/api/users/1/balance</code></li>
        </ul>
    </li>
    <li>
        <strong>Снятие заданной суммы с баланса пользователя</strong>
        <ul>
            <li>Эндпоинт: <code>PUT localhost:8080/api/users/1/takeMoney?amount=5.0</code></li>
        </ul>
    </li>
    <li>
        <strong>Пополнение баланса на заданную сумму</strong>
        <ul>
            <li>Эндпоинт: <code>PUT localhost:8080/api/users/1/putMoney?amount=10.0</code></li>
        </ul>
    </li>
    <li>
        <strong>Отображение списка операций за выбранный период</strong>
        <ul>
            <li>Эндпоинт: <code>GET localhost:8080/api/users/1/operations</code></li>
            <li>Эндпоинт с указанием диапазона дат: <code>GET localhost:8080/api/users/1/operations?startDate=2023-11-28&endDate=2024-04-14</code></li>
        </ul>
    </li>
    <li>
        <strong>Перевод денег между пользователями</strong>
        <ul>
            <li>Эндпоинт: <code>PUT localhost:8080/api/users/2/transfer?recipientId=1&amount=20.00</code></li>
        </ul>
    </li>
</ol>

<h2>Структура базы данных</h2>

![image](https://github.com/icy37/Skill_FP_Spring/assets/4716456/990aebe5-a78f-4f53-aeae-18642f52fbf1)
