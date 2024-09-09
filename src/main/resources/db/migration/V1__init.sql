CREATE TABLE `t_users` (
    `id` INT NOT NULL AUTO_INCREMENT,
    `username` VARCHAR(255) NOT NULL,
    `password` VARCHAR(255) NOT NULL,
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_username` (`username`)
);

CREATE TABLE `t_workout` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `workout_title` VARCHAR(255) NOT NULL,
    `workout_note` TEXT,
    `workout_type` VARCHAR(50),
    `workout_category` VARCHAR(50),
    `workout_duration` VARCHAR(50),
    `user_id` INT,
    `scheduled_date_time` DATETIME,
    `is_completed` BOOLEAN DEFAULT FALSE,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`user_id`) REFERENCES `t_users` (`id`)
);

CREATE TABLE `t_exercise` (
    `id` BIGINT NOT NULL AUTO_INCREMENT,
    `name` VARCHAR(255) NOT NULL,
    `sets` INT NOT NULL,
    `reps` INT NOT NULL,
    `weight` DOUBLE NOT NULL,
    `workout_id` BIGINT,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`workout_id`) REFERENCES `t_workout` (`id`)
);