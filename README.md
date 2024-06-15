### 1 数据库设计

![WechatIMG41166](https://github.com/Autzoko/Springcloud-warehouse/assets/88358084/034e223d-8455-48eb-ae6a-9d0123757322)


### 2 微服务设计

#### 2.1 Gateway

#### 2.2 CRM

##### MicroService Name:transaction_service

##### Database Table

###### Customer

```sql
CREATE TABLE `customer` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'user id',
    `name` VARCHAR(32) NOT NULL COMMENT 'username',
    `address` VARCHAR(512) DEFAULT NULL COMMENT 'address',
    `create_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'user create time',
    `update_time` TIMESTAMP NULL DEFAULT NULL COMMENT 'user info last update time',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB ROW_FORMAT = DYNAMIC;

```



###### Transaction

```sql
CREATE TABLE `warehouse_transaction` (
    `id` VARCHAR(512) NOT NULL COMMENT 'transaction id',
    `provider_id` BIGINT NOT NULL COMMENT 'provider id',
    `consumer_id` BIGINT NOT NULL COMMENT 'consumer id',
    `commodity_id` VARCHAR(512) NOT NULL COMMENT 'trade commodity id',
    `num_transaction` BIGINT DEFAULT 0 COMMENT 'number of transaction',
    `price` FLOAT NOT NULL COMMENT 'total price',
    `status` ENUM('pending', 'ongoing', 'complete', 'cancelled') DEFAULT 'pending' NOT NULL COMMENT 'transaction status',
    `create_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'transaction create time',
    `update_time` TIMESTAMP NULL DEFAULT NULL COMMENT 'transaction info last update time',
    PRIMARY KEY (`id`) USING BTREE,
    FOREIGN KEY (`provider_id`) REFERENCES `warehouse_user`(`id`),
    FOREIGN KEY (`consumer_id`) REFERENCES `warehouse_user`(`id`),
    FOREIGN KEY (`commodity_id`) REFERENCES `warehouse_commodity`(`id`)
) ENGINE = InnoDB ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
```

##### API



#### 2.3 Delivery

##### MicroService Name:delivery-service

##### Database Table

###### Delivery

```sql
CREATE TABLE `warehouse_delivery` (
    `id` VARCHAR(512) NOT NULL COMMENT 'delivery id',
    `source_id` BIGINT NOT NULL COMMENT 'source warehouse id',
    `destination` VARCHAR(255) DEFAULT NULL COMMENT 'delivery destination',
    `status` BIT(1) DEFAULT NULL COMMENT 'delivery status: 0 - delivering, 1 - finished',
    `create_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'transaction create time',
    `update_time` TIMESTAMP NULL DEFAULT NULL COMMENT 'transaction info last update time',
    PRIMARY KEY (`id`) USING BTREE,
    FOREIGN KEY (`source_id`) REFERENCES `warehouse_warehouse`(`id`),
) ENGINE = InnoDB ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
```

##### API

#### 2.4 Payment

##### MicroService Name:payment_service

##### Database Table

###### Transaction

```sql
CREATE TABLE `warehouse_transaction` (
    `id` VARCHAR(512) NOT NULL COMMENT 'transaction id',
    `provider_id` BIGINT NOT NULL COMMENT 'provider id',
    `consumer_id` BIGINT NOT NULL COMMENT 'consumer id',
    `commodity_id` VARCHAR(512) NOT NULL COMMENT 'trade commodity id',
    `num_transaction` BIGINT DEFAULT 0 COMMENT 'number of transaction',
    `price` FLOAT NOT NULL COMMENT 'total price',
    `status` ENUM('pending', 'ongoing', 'complete', 'cancelled') DEFAULT 'pending' NOT NULL COMMENT 'transaction status',
    `create_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'transaction create time',
    `update_time` TIMESTAMP NULL DEFAULT NULL COMMENT 'transaction info last update time',
    PRIMARY KEY (`id`) USING BTREE,
    FOREIGN KEY (`provider_id`) REFERENCES `warehouse_user`(`id`),
    FOREIGN KEY (`consumer_id`) REFERENCES `warehouse_user`(`id`),
    FOREIGN KEY (`commodity_id`) REFERENCES `warehouse_commodity`(`id`)
) ENGINE = InnoDB ROW_FORMAT = DYNAMIC;

SET FOREIGN_KEY_CHECKS = 1;
```

##### API

#### 2.5 Warehouse

##### MicroService Name:warehouse-service

##### Database Table

###### Warehouse

```sql
DROP TABLE IF EXISTS `warehouse_warehouse`;
CREATE TABLE `warehouse_warehouse` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'warehouse id',
    `stock` BIGINT DEFAULT 0 COMMENT 'current amount of commodities in warehouse',
    `create_time` TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'warehouse create time',
    `update_time` TIMESTAMP NULL DEFAULT NULL COMMENT 'warehouse info last update time',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB ROW_FORMAT = DYNAMIC;

```

###### Commodity

```sql
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


```

##### API
