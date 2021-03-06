DROP SCHEMA IF EXISTS `internet_shop`;
CREATE SCHEMA `internet_shop` DEFAULT CHARACTER SET utf8 ;
CREATE TABLE `internet_shop`.`items` (
                                         `item_id` INT NOT NULL AUTO_INCREMENT,
                                         `name` VARCHAR(225) NOT NULL,
                                         `price` DECIMAL(10,2) NOT NULL,
                                         PRIMARY KEY (`item_id`))
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8;


CREATE TABLE `internet_shop`.`users` (
                                         `user_id` INT NOT NULL AUTO_INCREMENT,
                                         `user_name` VARCHAR(45) NOT NULL unique,
                                         `user_pass` VARCHAR(1000) NOT NULL,
                                         `user_salt` VARBINARY(1000) NOT NULL,
                                         `user_token` VARCHAR(100) NOT NULL,
                                         PRIMARY KEY (`user_id`));

CREATE TABLE `internet_shop`.`orders` (
                                          `order_id` INT NOT NULL AUTO_INCREMENT,
                                          `user_id` INT NOT NULL,
                                          PRIMARY KEY (`order_id`),
                                          INDEX `user_id_fk_idx` (`user_id` ASC) VISIBLE,
                                          CONSTRAINT `user_id_fk`
                                              FOREIGN KEY (`user_id`)
                                                  REFERENCES `internet_shop`.`users` (`user_id`)
                                                  ON DELETE cascade
                                                  ON UPDATE cascade);


CREATE TABLE `internet_shop`.`orders_items` (
                                                `order_id` INT NOT NULL,
                                                `item_id` INT NOT NULL,
                                                INDEX `item_id_fk_idx` (`item_id` ASC) VISIBLE,
                                                INDEX `order_id_fk_idx` (`order_id` ASC) VISIBLE,
                                                CONSTRAINT `order_id_fk`
                                                    FOREIGN KEY (`order_id`)
                                                        REFERENCES `internet_shop`.`orders` (`order_id`)
                                                        ON DELETE cascade
                                                        ON UPDATE cascade,
                                                CONSTRAINT `item_id_fk`
                                                    FOREIGN KEY (`item_id`)
                                                        REFERENCES `internet_shop`.`items` (`item_id`)
                                                        ON DELETE cascade
                                                        ON UPDATE cascade);

CREATE TABLE `internet_shop`.`buckets` (
                                           `bucket_id` INT NOT NULL AUTO_INCREMENT,
                                           `user_id` INT NOT NULL,
                                           PRIMARY KEY (`bucket_id`),
                                           INDEX `user_id_buckets_fk_idx` (`user_id` ASC) VISIBLE,
                                           CONSTRAINT `user_id_buckets_fk`
                                               FOREIGN KEY (`user_id`)
                                                   REFERENCES `internet_shop`.`users` (`user_id`)
                                                   ON DELETE cascade
                                                   ON UPDATE cascade);

CREATE TABLE `internet_shop`.`buckets_items` (
                                                 `bucket_id` INT NOT NULL,
                                                 `item_id` INT NOT NULL,
                                                 INDEX `buckets_items_bucket_id_idx` (`bucket_id` ASC) VISIBLE,
                                                 INDEX `buckets_items_item_id_idx` (`item_id` ASC) VISIBLE,
                                                 CONSTRAINT `buckets_items_bucket_id`
                                                     FOREIGN KEY (`bucket_id`)
                                                         REFERENCES `internet_shop`.`buckets` (`bucket_id`)
                                                         ON DELETE cascade
                                                         ON UPDATE cascade,
                                                 CONSTRAINT `buckets_items_item_id`
                                                     FOREIGN KEY (`item_id`)
                                                         REFERENCES `internet_shop`.`items` (`item_id`)
                                                         ON DELETE cascade
                                                         ON UPDATE cascade);


CREATE TABLE `internet_shop`.`roles` (
                                         `role_id` INT NOT NULL AUTO_INCREMENT,
                                         `role_name` VARCHAR(45) NOT NULL,
                                         PRIMARY KEY (`role_id`));

INSERT INTO `internet_shop`.`roles` (role_name) VALUES ('ADMIN'), ('USER');

CREATE TABLE `internet_shop`.`users_roles` (
                                               `user_id` INT NOT NULL,
                                               `role_id` INT NOT NULL,
                                               INDEX `users_roles_user_id_fk_idx` (`user_id` ASC) VISIBLE,
                                               INDEX `users_roles_role_id_fk_idx` (`role_id` ASC) VISIBLE,
                                               CONSTRAINT `users_roles_user_id_fk`
                                                   FOREIGN KEY (`user_id`)
                                                       REFERENCES `internet_shop`.`users` (`user_id`)
                                                       ON DELETE cascade
                                                       ON UPDATE cascade,
                                               CONSTRAINT `users_roles_role_id_fk`
                                                   FOREIGN KEY (`role_id`)
                                                       REFERENCES `internet_shop`.`roles` (`role_id`)
                                                       ON DELETE cascade
                                                       ON UPDATE cascade);
INSERT INTO `internet_shop`.`users`(user_name, user_pass, user_salt, user_token) VALUES ('ADMIN', 'bd90cd56dc1cf1367c4326ddebb35c7b03e50978aa111f0ebc360a39c0ffb49619eb2f92d85a4b5fd04341ee332b496bf3b7c07d43505543544585439e8307c8', x'4C940A94CA7FC6B5CCCACD9D8C40E889', 'ec184950-20d4-471d-b637-126bd938d3f5');
INSERT INTO `internet_shop`.`users_roles` VALUES (1, 1);
