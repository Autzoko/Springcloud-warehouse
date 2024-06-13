USE `springcloud_warehouse`;

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `warehouse_transaction`;
CREATE TABLE `warehouse_transaction` (
    `id` VARCHAR(512) NOT NULL COMMENT 'transaction id',
    `provider_id` BIGINT NOT NULL COMMENT 'provider id',
    `consumer_id` BIGINT NOT NULL COMMENT 'consumer id',
    `commodity_id` VARCHAR(512) NOT NULL COMMENT 'trade commodity id',
    `num_transaction` BIGINT DEFAULT 0 COMMENT 'number of transaction',
    `destination_id` BIGINT DEFAULT NULL COMMENT 'destination warehouse id',
    `create_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'transaction create time',
    `update_time` TIMESTAMP NULL DEFAULT NULL COMMENT 'transaction info last update time',
    PRIMARY KEY (`id`) USING BTREE,
    FOREIGN KEY (`provider_id`) REFERENCES `warehouse_user`(`id`),
    FOREIGN KEY (`consumer_id`) REFERENCES `warehouse_user`(`id`),
    FOREIGN KEY (`commodity_id`) REFERENCES `warehouse_commodity`(`id`)
) ENGINE = InnoDB ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;