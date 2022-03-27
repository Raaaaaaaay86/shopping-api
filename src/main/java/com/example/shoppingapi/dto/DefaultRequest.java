package com.example.shoppingapi.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class DefaultRequest {
    Map<String, Object> data;
}
