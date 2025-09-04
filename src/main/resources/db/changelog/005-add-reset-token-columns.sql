--liquibase formatted sql

--changeset vadym.tkach:5

-- Add columns for password reset functionality

ALTER TABLE users ADD COLUMN IF NOT EXISTS reset_token UUID;

ALTER TABLE users ADD COLUMN IF NOT EXISTS reset_token_expires_at TIMESTAMP;
