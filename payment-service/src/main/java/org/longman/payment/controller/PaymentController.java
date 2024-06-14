package org.longman.payment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.longman.payment.service.PaymentService;
import org.longman.utils.BaseController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController extends BaseController {

    private final PaymentService paymentService;

    @GetMapping("/pay/{price}")
    public ResponseEntity<Object> pay(@PathVariable("price") Float price) {
        if (paymentService.pay(price)) {
            System.out.println("payment succeeded");
            return success("payment succeeded");
        } else {
            System.out.println("payment failed");
            return fail("payment failed");
        }
    }
}
