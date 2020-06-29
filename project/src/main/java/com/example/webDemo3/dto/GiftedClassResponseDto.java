package com.example.webDemo3.dto;

import com.example.webDemo3.entity.GiftedClass;
import lombok.Data;

import java.util.List;

/**
 * kimpt142 - 29/6
 */
@Data
public class GiftedClassResponseDto {
    private List<GiftedClass> giftedClassList;
    private MessageDTO message;
}
