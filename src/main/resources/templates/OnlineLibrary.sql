-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Хост: 127.0.0.1:3306
-- Время создания: Окт 15 2023 г., 02:08
-- Версия сервера: 8.0.30
-- Версия PHP: 7.2.34

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- База данных: `OnlineLibrary`
--

-- --------------------------------------------------------

--
-- Структура таблицы `comments`
--

CREATE TABLE `comments` (
  `id` binary(16) NOT NULL,
  `ban` bit(1) DEFAULT NULL,
  `commentary` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `date` datetime(6) DEFAULT NULL,
  `publication_id` binary(16) DEFAULT NULL,
  `publisher_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Структура таблицы `publications`
--

CREATE TABLE `publications` (
  `id` binary(16) NOT NULL,
  `ban` bit(1) DEFAULT NULL,
  `date` datetime(6) DEFAULT NULL,
  `genre` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `link` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `publisher_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `title` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Дамп данных таблицы `publications`
--

INSERT INTO `publications` (`id`, `ban`, `date`, `genre`, `link`, `publisher_name`, `title`) VALUES
(0x00000000000000000000000000000000, b'0', '2023-10-15 01:54:40.000000', 'Антиутопия', 'https://book.ru/', '', '1984'),
(0x00000000000000000000000000000001, b'0', '2023-10-15 01:58:28.000000', 'Фантастика', 'https://book.ru/', '', 'Эрагон');

-- --------------------------------------------------------

--
-- Структура таблицы `roles`
--

CREATE TABLE `roles` (
  `id` bigint NOT NULL,
  `name` enum('ADMIN','MODERATOR','USER') COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Дамп данных таблицы `roles`
--

INSERT INTO `roles` (`id`, `name`) VALUES
(0, 'USER'),
(1, 'MODERATOR'),
(2, 'ADMIN');

-- --------------------------------------------------------

--
-- Структура таблицы `users`
--

CREATE TABLE `users` (
  `id` binary(16) NOT NULL,
  `ban` bit(1) DEFAULT NULL,
  `date` datetime(6) DEFAULT NULL,
  `email` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `password` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `request_to_redactor` bit(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Дамп данных таблицы `users`
--

INSERT INTO `users` (`id`, `ban`, `date`, `email`, `name`, `password`, `request_to_redactor`) VALUES
(0x00000000000000000000000000000000, b'0', '2023-10-15 01:48:42.000000', 'admin@gmail.com', 'admin', '$2a$12$Zvq7XEVfSTzWdcUBivtyCe/vPPSbSU2F9MBtGoLnWp/1OQs6TDhZW', b'0'),
(0x00000000000000000000000000000001, b'0', '2023-10-15 01:50:30.000000', 'user@gmail.com', 'user', '$2a$12$oc4gSWyoje3mrJHL6O9uSObu.No7RB9al67ksS13q2845z3e8ZGem', b'0');

-- --------------------------------------------------------

--
-- Структура таблицы `users_roles`
--

CREATE TABLE `users_roles` (
  `user_id` binary(16) NOT NULL,
  `role_id` bigint NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Дамп данных таблицы `users_roles`
--

INSERT INTO `users_roles` (`user_id`, `role_id`) VALUES
(0x00000000000000000000000000000001, 0),
(0x00000000000000000000000000000000, 2);

--
-- Индексы сохранённых таблиц
--

--
-- Индексы таблицы `comments`
--
ALTER TABLE `comments`
  ADD PRIMARY KEY (`id`);

--
-- Индексы таблицы `publications`
--
ALTER TABLE `publications`
  ADD PRIMARY KEY (`id`);

--
-- Индексы таблицы `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`id`);

--
-- Индексы таблицы `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- Индексы таблицы `users_roles`
--
ALTER TABLE `users_roles`
  ADD PRIMARY KEY (`user_id`,`role_id`),
  ADD KEY `FKj6m8fwv7oqv74fcehir1a9ffy` (`role_id`);

--
-- AUTO_INCREMENT для сохранённых таблиц
--

--
-- AUTO_INCREMENT для таблицы `roles`
--
ALTER TABLE `roles`
  MODIFY `id` bigint NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Ограничения внешнего ключа сохраненных таблиц
--

--
-- Ограничения внешнего ключа таблицы `users_roles`
--
ALTER TABLE `users_roles`
  ADD CONSTRAINT `FK2o0jvgh89lemvvo17cbqvdxaa` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `FKj6m8fwv7oqv74fcehir1a9ffy` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
