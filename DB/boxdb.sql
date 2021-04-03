-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema boxdb
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `boxdb` ;

-- -----------------------------------------------------
-- Schema boxdb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `boxdb` DEFAULT CHARACTER SET utf8 ;
USE `boxdb` ;

-- -----------------------------------------------------
-- Table `user`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `user` ;

CREATE TABLE IF NOT EXISTS `user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NULL,
  `last_name` VARCHAR(45) NULL,
  `email` VARCHAR(100) NULL,
  `username` VARCHAR(45) NULL,
  `password` VARCHAR(45) NULL,
  `created_at` DATETIME NULL,
  `updated_at` DATETIME NULL,
  `active` TINYINT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `location`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `location` ;

CREATE TABLE IF NOT EXISTS `location` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `type` VARCHAR(45) NOT NULL,
  `created_at` DATETIME NULL,
  `updated_at` DATETIME NULL,
  `active` TINYINT NULL,
  `user_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_location_user1_idx` (`user_id` ASC),
  CONSTRAINT `fk_location_user1`
    FOREIGN KEY (`user_id`)
    REFERENCES `user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `box`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `box` ;

CREATE TABLE IF NOT EXISTS `box` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  `room` VARCHAR(45) NULL,
  `content` TEXT NULL,
  `is_fragile` TINYINT NULL,
  `img1_url` VARCHAR(1000) NULL,
  `img2_url` VARCHAR(1000) NULL,
  `qr_code` VARCHAR(1000) NULL,
  `created_at` DATETIME NULL,
  `updated_at` DATETIME NULL,
  `active` TINYINT NOT NULL,
  `location_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_box_location_idx` (`location_id` ASC),
  CONSTRAINT `fk_box_location`
    FOREIGN KEY (`location_id`)
    REFERENCES `location` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

SET SQL_MODE = '';
DROP USER IF EXISTS boxuser@localhost;
SET SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';
CREATE USER 'boxuser'@'localhost' IDENTIFIED BY 'boxuser';

GRANT SELECT, INSERT, TRIGGER, UPDATE, DELETE ON TABLE * TO 'boxuser'@'localhost';

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `user`
-- -----------------------------------------------------
START TRANSACTION;
USE `boxdb`;
INSERT INTO `user` (`id`, `first_name`, `last_name`, `email`, `username`, `password`, `created_at`, `updated_at`, `active`) VALUES (1, 'Wally', 'Wallington', 'wwallington@fakeemail.com', 'demo', 'demo', NULL, NULL, 1);

COMMIT;


-- -----------------------------------------------------
-- Data for table `location`
-- -----------------------------------------------------
START TRANSACTION;
USE `boxdb`;
INSERT INTO `location` (`id`, `type`, `created_at`, `updated_at`, `active`, `user_id`) VALUES (1, 'Storage Unit', NULL, NULL, 1, 1);

COMMIT;


-- -----------------------------------------------------
-- Data for table `box`
-- -----------------------------------------------------
START TRANSACTION;
USE `boxdb`;
INSERT INTO `box` (`id`, `name`, `room`, `content`, `is_fragile`, `img1_url`, `img2_url`, `qr_code`, `created_at`, `updated_at`, `active`, `location_id`) VALUES (1, 'Bedroom Closet 1', 'Bedroom Closet', 'clothes', 0, NULL, NULL, NULL, NULL, NULL, 1, 1);

COMMIT;

