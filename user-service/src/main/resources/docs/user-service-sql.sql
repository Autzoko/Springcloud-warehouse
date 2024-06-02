SET FOREIGN_KEY_CHECKS = 0;

CREATE TABLE `warehouse_user` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'user id',
    `name` VARCHAR(32) NOT NULL COMMENT 'username',
    `password` VARCHAR(256) NOT NULL COMMENT 'password',
    `category` BIT(1) NULL DEFAULT NULL COMMENT 'user category: 0-user, 1-admin',
    `create_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'user create time',
    `update_time` TIMESTAMP NULL DEFAULT NULL COMMENT 'user info last update time',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 0;