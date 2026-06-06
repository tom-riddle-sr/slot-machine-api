package com.example.demo.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.ArrayList;
import java.util.List;

// @Converter：告訴 JPA 這是一個型別轉換器。
// AttributeConverter<List<String>, String> 的意思是：
//   Java 這邊是 List<String>，存進 DB 那邊變成 String（一個 JSON 文字欄位）。
@Converter
public class StringListConverter implements AttributeConverter<List<String>, String> {

    // ObjectMapper：Jackson 提供的 JSON 轉換工具，負責 物件 <-> JSON 字串 互轉
    private static final ObjectMapper objectMapper = new ObjectMapper();

    // 【存進 DB 時】JPA 自動呼叫：把 List 變成 JSON 字串
    // 例：["🍒","🍋","🍊"] → "[\"🍒\",\"🍋\",\"🍊\"]"
    @Override
    public String convertToDatabaseColumn(List<String> attribute) {
        // 沒資料就存空陣列的 JSON，避免存進 null
        if (attribute == null || attribute.isEmpty()) {
            return "[]";
        }
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (Exception e) {
            // 轉換失敗就丟出例外，讓問題浮現而不是默默存錯資料
            throw new RuntimeException("symbols 轉成 JSON 失敗", e);
        }
    }

    // 【從 DB 讀出時】JPA 自動呼叫：把 JSON 字串轉回 List
    // 例："[\"🍒\",\"🍋\",\"🍊\"]" → ["🍒","🍋","🍊"]
    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        // DB 是空的就回一個空 List，避免後面用到 null 出錯
        if (dbData == null || dbData.isEmpty()) {
            return new ArrayList<>();
        }
        try {
            // TypeReference 告訴 Jackson「我要轉回 List<String>」這個型別
            return objectMapper.readValue(dbData, new TypeReference<List<String>>() {});
        } catch (Exception e) {
            throw new RuntimeException("JSON 轉回 symbols 失敗", e);
        }
    }
}
