package org.longman.utils;

import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class BaseController {

    public static ResponseEntity<Object> success(Object data) {
        Map<String, Object> dataMap = new HashMap<String, Object>(4);
        dataMap.put("success", true);
        dataMap.put("data", data);
        return ResponseEntity.ok(dataMap);
    }

    public static ResponseEntity<Object> fail(String msg) {
        Map<String, Object> dataMap = new HashMap<>(4);
        dataMap.put("success", false);
        dataMap.put("msg", msg);
        return ResponseEntity.ok(dataMap);
    }
}
