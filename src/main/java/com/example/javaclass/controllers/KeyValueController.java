package com.example.javaclass.controllers;

import com.example.javaclass.dto.KeyValueDto;
import com.example.javaclass.utils.JedisUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1")
public class KeyValueController {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private JedisUtil jedisUtil;

    @GetMapping("/key-value/{key}")
    public ResponseEntity<Object> getValueByKey(
            @PathVariable String key) {
        var val = jedisUtil.hgetAsObj("data", key);
        return ResponseEntity.ok(KeyValueDto.builder().value(val).key(key).build());
    }

    @PostMapping("/key-value")
    public ResponseEntity<Object> saveValue(
            HttpServletRequest request) throws IOException {
        String test = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        KeyValueDto test2 = objectMapper.readValue(test, KeyValueDto.class);

        jedisUtil.hset("data", test2.getKey(), test2.getValue());
        return ResponseEntity.ok(test2);
    }
}
