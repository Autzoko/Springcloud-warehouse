USE `springcloud_warehouse`;

SET FOREIGN_KEY_CHECKS = 0;

CREATE TABLE `warehouse_delivery` (
    `id` VARCHAR(512) NOT NULL COMMENT 'delivery id',
    `source_id` BIGINT NOT NULL COMMENT 'source warehouse id',
    `destination_id` BIGINT NOT NULL COMMENT 'destination warehouse id',
    `status` BIT(1) DEFAULT NULL COMMENT 'delivery status: 0 - delivering, 1 - finished',
    `create_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'transaction create time',
    `update_time` TIMESTAMP NULL DEFAULT NULL COMMENT 'transaction info last update time',
    PRIMARY KEY (`id`) USING BTREE,
    FOREIGN KEY (`source_id`) REFERENCES `warehouse_warehouse`(`id`),
    FOREIGN KEY (`destination_id`) REFERENCES `warehouse_warehouse`(`id`)
) ENGINE = InnoDB ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;