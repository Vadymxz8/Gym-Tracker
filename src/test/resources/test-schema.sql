CREATE TABLE users (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE workouts (
    id UUID PRIMARY KEY,
    date DATE NOT NULL,
    name VARCHAR(50) NOT NULL,
    workout_type VARCHAR(50) NOT NULL,
    note TEXT,
    user_id UUID NOT NULL
);

CREATE TABLE exercises (
    id UUID PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    type VARCHAR(15),
    muscle_group VARCHAR(50)
);

CREATE TABLE workout_exercise (
    id UUID PRIMARY KEY,
    reps INTEGER NOT NULL,
    weight NUMERIC,
    sets INTEGER NOT NULL,
    note TEXT,
    exercise_id UUID NOT NULL,
    workout_id UUID NOT NULL
);
