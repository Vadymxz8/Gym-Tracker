CREATE TABLE Users (
    id UUID PRIMARY KEY,
    name TEXT NOT NULL,
    email TEXT UNIQUE NOT NULL,
    password TEXT NOT NULL
);

CREATE TABLE Workouts (
    id UUID PRIMARY KEY,
    date DATE NOT NULL,
    name VARCHAR(50) NOT NULL,
    workout_type VARCHAR(50) NOT NULL CHECK (workout_type IN ('FULLBODY', 'UPPER_LOWER', 'SPLIT')) DEFAULT 'FULLBODY',
    note TEXT,
    user_id UUID NOT NULL REFERENCES users(id)
);

CREATE TABLE Exercises (
    id UUID PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    type VARCHAR(15),
    muscle_group VARCHAR(50)
);


CREATE TABLE Workout_Exercise (
    id UUID PRIMARY KEY,
    reps INTEGER NOT NULL,
    weight NUMERIC(5,2),
    sets INTEGER NOT NULL,
    note TEXT,
    exercise_id UUID NOT NULL REFERENCES exercises(id) ON DELETE CASCADE,
    workout_id UUID NOT NULL REFERENCES workouts(id) ON DELETE CASCADE
);

CREATE TABLE Reminders (
    id UUID PRIMARY KEY,
    time TIMESTAMPTZ NOT NULL,
    message TEXT DEFAULT 'Сьогодні в тебе тренування!' NOT NULL,
    user_id UUID REFERENCES users(id) ON DELETE CASCADE
);