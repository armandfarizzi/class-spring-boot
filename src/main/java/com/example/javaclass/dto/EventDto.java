package com.example.javaclass.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventDto {
    private String id;
    @JsonProperty("event_name")
    private String eventName;
    private String message;
}
