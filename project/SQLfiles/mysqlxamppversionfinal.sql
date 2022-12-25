-- MySQL Script generated by MySQL Workbench
-- Sun Dec 25 00:23:04 2022
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema wypozyczalnia
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema wypozyczalnia
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `wypozyczalnia` DEFAULT CHARACTER SET utf8mb3 ;
USE `wypozyczalnia` ;

-- -----------------------------------------------------
-- Table `wypozyczalnia`.`klient`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `wypozyczalnia`.`klient` (
  `idklienta` INT NOT NULL AUTO_INCREMENT,
  `imie` VARCHAR(45) NOT NULL,
  `nazwisko` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `haslo` VARCHAR(80) NOT NULL,
  `zadluzenie` FLOAT NOT NULL,
  PRIMARY KEY (`idklienta`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `wypozyczalnia`.`pojazd`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `wypozyczalnia`.`pojazd` (
  `idpojazdu` INT NOT NULL AUTO_INCREMENT,
  `nazwa` VARCHAR(45) NOT NULL,
  `stanbaterii` FLOAT NOT NULL,
  `licznikkm` FLOAT NOT NULL,
  PRIMARY KEY (`idpojazdu`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `wypozyczalnia`.`klient_pojazd`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `wypozyczalnia`.`klient_pojazd` (
  `idwypozyczenia` INT NOT NULL AUTO_INCREMENT,
  `idklientaW` INT NULL DEFAULT NULL,
  `idpojazduW` INT NULL DEFAULT NULL,
  `data_wyp` DATETIME NULL DEFAULT NULL,
  `data_zwr` DATETIME NULL DEFAULT NULL,
  PRIMARY KEY (`idwypozyczenia`),
  INDEX `idklienta_idx` (`idklientaW` ASC),
  INDEX `idpojazdu_idx` (`idpojazduW` ASC),
  CONSTRAINT `idklienta`
    FOREIGN KEY (`idklientaW`)
    REFERENCES `wypozyczalnia`.`klient` (`idklienta`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `idpojazdu`
    FOREIGN KEY (`idpojazduW`)
    REFERENCES `wypozyczalnia`.`pojazd` (`idpojazdu`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;