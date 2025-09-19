package co.edu.eci.blueprints.controllers;

/**
 * Envoltura genérica para respuestas de API.
 *
 * @param <T> tipo de la carga útil
 * @param code código de estado de la operación (puede reflejar HTTP)
 * @param message mensaje descriptivo
 * @param data carga útil de la respuesta
 */
public record ApiResponse<T>(int code, String message, T data) {}
