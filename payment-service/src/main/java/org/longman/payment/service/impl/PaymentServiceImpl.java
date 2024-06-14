package org.longman.payment.service.impl;

import lombok.RequiredArgsConstructor;
import org.longman.payment.service.PaymentService;
import org.longman.utils.WarehouseMetaObjectHandler;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Import(WarehouseMetaObjectHandler.class)
public class PaymentServiceImpl implements PaymentService {

    @Override
    public boolean pay(Float price) {
        return true;
    }
}
