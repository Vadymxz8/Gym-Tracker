--liquibase formatted sql

--changeset vadym.tkach:6

CREATE INDEX IF NOT EXISTS idx_workouts_user_date ON workouts(user_id, date);
CREATE INDEX IF NOT EXISTS idx_workout_exercise_workout ON workout_exercise(workout_id);
CREATE INDEX IF NOT EXISTS idx_workout_exercise_exercise ON workout_exercise(exercise_id);
