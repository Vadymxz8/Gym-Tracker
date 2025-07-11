--liquibase formatted sql

 --changeset vadym.tkach:3

 -- Allow null for users password

 ALTER TABLE users ALTER column password DROP NOT NULL;