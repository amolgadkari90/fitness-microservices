package com.fitness.aiservice.enums;

/**
 * Standard response codes for User service.
 *
 * Naming rules:
 * - USER_* → User domain / business outcomes
 * - GEN_*  → System / technical failures only
 *
 * Codes are API contracts and must NEVER change.
 * Messages are human-readable and MAY evolve.
 */
public enum ResponseCode {

    /* ===================== SUCCESS ===================== */

    // Generic success response for read / update operations
    USER_2000("USER_2000", "success"),

    // Resource successfully created
    USER_2001("USER_2001", "Activity created"),

    // Request processed successfully but no response body (delete, idempotent calls)
    USER_2004("USER_2004", "No content"),


    /* ===================== CLIENT / DOMAIN ERRORS ===================== */

    // Input validation failed (missing fields, invalid values)
    USER_4001("USER_4001", "Activity validation error"),

    // Requested user/resource not found
    USER_4004("USER_4004", "Activity not found"),

    // Conflict due to duplicate data or concurrent modification
    USER_4090("USER_4090", "Activity already present"),


    /* ===================== AUTHORIZATION ===================== */

    // Authentication required or token invalid
    USER_4010("USER_4010", "Unauthorized access"),

    // Authenticated but not allowed to perform this action
    USER_4030("USER_4030", "Forbidden access"),


    /* ===================== SYSTEM / TECHNICAL ===================== */

    // Unhandled exception, application crash, unexpected failure
    GEN_5000("GEN_5000", "System failure"),

    // Dependent service down (DB, cache, external API)
    GEN_5003("GEN_5003", "Service unavailable"),

    // System timeout while processing request
    GEN_5004("GEN_5004", "Timeout");
    
    

    private final String code;
    private final String message;

    ResponseCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String code() {
        return code;
    }

    public String message() {
        return message;
    }
}
