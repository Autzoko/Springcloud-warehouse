USE `springcloud_warehouse`;

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `warehouse_warehouse`;
CREATE TABLE `warehouse_warehouse` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'warehouse id',
    `stock` BIGINT DEFAULT 0 COMMENT 'current amount of commodities in warehouse',
    `create_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'warehouse create time',
    `update_time` TIMESTAMP NULL DEFAULT NULL COMMENT 'warehouse info last update time',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB ROW_FORMAT = DYNAMIC;


DROP TABLE IF EXISTS `warehouse_commodity`;
CREATE TABLE `warehouse_commodity` (
    `id` VARCHAR(512) NOT NULL COMMENT 'commodity id',
    `warehouse_id` BIGINT NOT NULL COMMENT 'warehouse id',
    `price` FLOAT4 NULL DEFAULT NULL COMMENT 'commodity price',
    `stock` BIGINT DEFAULT 0 COMMENT 'commodity stock',
    `create_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'commodity create time',
    `update_time` TIMESTAMP NULL DEFAULT NULL COMMENT 'commodity info last update time',
    PRIMARY KEY (`id`) USING BTREE,
    FOREIGN KEY (`warehouse_id`) REFERENCES `warehouse_warehouse`(`id`)
) ENGINE = InnoDB ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;



