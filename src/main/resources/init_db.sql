CREATE SCHEMA `internet_shop` DEFAULT CHARACTER SET utf8 ;
CREATE TABLE `internet_shop`.`items` (
                                         `item_id` INT NOT NULL AUTO_INCREMENT,
                                         `name` VARCHAR(225) NOT NULL,
                                         `price` DECIMAL(10,2) NOT NULL,
                                         PRIMARY KEY (`item_id`))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;
INSERT INTO `internet_shop`.`items` (`name`, `price`) VALUES ('iPhone 11', '1000');
