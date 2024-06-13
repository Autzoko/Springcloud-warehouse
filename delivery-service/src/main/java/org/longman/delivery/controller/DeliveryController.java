package org.longman.delivery.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.longman.delivery.service.DeliveryService;
import org.longman.entity.DeliveryEntity;
import org.longman.entity.dto.DeliveryDto;
import org.longman.exception.IdConflictException;
import org.longman.exception.JsonDataError;
import org.longman.exception.MissingFieldException;
import org.longman.utils.BaseController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/delivery")
public class DeliveryController extends BaseController {

    private final DeliveryService deliveryService;

    @PostMapping("/create")
    public ResponseEntity<Object> createDelivery(@RequestBody DeliveryDto deliveryDto) {
        try {
            validateDeliveryInfo(deliveryDto);
            DeliveryEntity delivery = new DeliveryEntity();

            delivery.setId(UUID.randomUUID().toString());
            delivery.setSource_id(deliveryDto.getSource_id());
            delivery.setDestination_id(deliveryDto.getDestination_id());
            delivery.setStatus(false);

            deliveryService.createDelivery(delivery);

            return success("delivery has been created");
        } catch (MissingFieldException e) {
            System.out.println(e.getMessage());
            log.error(e.getMessage());
            return fail("missing field error");
        } catch (JsonDataError e) {
            System.out.println(e.getMessage());
            log.error(e.getMessage());
            return fail("json data error");
        } catch (IdConflictException e) {
            System.out.println(e.getMessage());
            log.error(e.getMessage());
            return createDelivery(deliveryDto);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            log.error(e.getMessage());
            return fail(e.getMessage());
        }
    }

    @GetMapping("/fetch-delivery")
    public ResponseEntity<Object> getDeliveryById(@RequestParam("id") String id) {
        try {
            DeliveryEntity delivery = deliveryService.getDeliveryById(id);

            DeliveryDto deliveryDto = new DeliveryDto();
            deliveryDto.setId(id);
            deliveryDto.setSource_id(delivery.getSource_id());
            deliveryDto.setDestination_id(delivery.getDestination_id());
            deliveryDto.setStatus(delivery.isStatus());
            return success(deliveryDto);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            log.error(e.getMessage());
            return fail(e.getMessage());
        }
    }

    private static void validateDeliveryInfo(DeliveryDto deliveryDto) {
        if (Objects.isNull(deliveryDto.getSource_id())) {
            throw new JsonDataError("source id is empty");
        }
        if (Objects.isNull(deliveryDto.getDestination_id())) {
            throw new JsonDataError("destination id is empty");
        }
        if (Objects.isNull(deliveryDto.isStatus())) {
            throw new JsonDataError("status is empty");
        }
    }
}
