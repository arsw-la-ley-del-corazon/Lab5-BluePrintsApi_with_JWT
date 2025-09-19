package co.edu.eci.blueprints.model;

/**
 * Punto 2D inmutable representado por sus coordenadas enteras.
 *
 * <p>Esta clase es un {@code record}, por lo que proporciona automáticamente
 * {@code equals}, {@code hashCode} y {@code toString} basados en sus
 * componentes.</p>
 *
 * @param x coordenada horizontal
 * @param y coordenada vertical
 * @since 1.0.0
 */
public record Point(int x, int y) { }
