package com.fitness.userservice.dto;

import java.time.Instant;


import com.fitness.userservice.enums.ResponseCode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder(toBuilder = true)
@AllArgsConstructor
@Data
@NoArgsConstructor
public class ApiResponse <T>{
	
    /** Indicates success or failure */
    private boolean success;
    
    /** Business-level response code (not HTTP status) */
    private String code;
    
    /** Human-readable message */
    private String message;
    
    /** Actual response pay-load (null in error cases) */
    private T data;
    
//    /** Correlation ID for logs, Kafka, gateway tracing */
//    private String traceId;
    
    /** Response creation time-stamp */
    @Builder.Default
    private Instant timestamp = Instant.now();
    
    
    public static <T> ApiResponse<T> success(ResponseCode responseCode, T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .code(responseCode.code())
                .message(responseCode.message())
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> error(ResponseCode responseCode) {
        return ApiResponse.<T>builder()
                .success(false)
                .code(responseCode.code())
                .message(responseCode.message())
                .build();
    }




}
