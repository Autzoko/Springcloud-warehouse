package org.longman.common;

public class ServiceUrls {

    private ServiceUrls() {
        throw new IllegalStateException("cannot be instantiated");
    }

    public static final String TRANSACTION_SERVICE_URL = "http://localhost:8001/transaction";

    public static final String PAYMENT_SERVICE_URL = "http://localhost:8001/payment";

    public static final String DELIVERY_SERVICE_URL = "http://localhost:8002/delivery";

    public static final String WAREHOUSE_SERVICE_URL = "http://localhost:8003/warehouse";

    public static final String COMMODITY_SERVICE_URL = "http://localhost:8003/commodity";
}
