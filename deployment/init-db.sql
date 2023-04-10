-- -- ### Создание роли базы данных ###
CREATE ROLE shelter_role WITH LOGIN PASSWORD 'shelter_q1';

-- -- ### Создание базы данных ###
CREATE DATABASE shelter OWNER shelter_role;
