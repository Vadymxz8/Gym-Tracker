--liquibase formatted sql

 --changeset vadym.tkach:4

 -- Add column token for users

 ALTER TABLE users ADD column token UUID;