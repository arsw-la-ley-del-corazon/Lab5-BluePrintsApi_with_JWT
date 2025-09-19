package co.edu.eci.blueprints.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Representa un plano (blueprint) compuesto por un autor, un nombre y una
 * secuencia ordenada de puntos.
 *
 * <p>La identidad de un {@code Blueprint} está determinada por la pareja
 * {@code (author, name)}. Los puntos se almacenan de forma interna y se
 * exponen como lista inmutable para preservar el encapsulamiento.</p>
 *
 * @since 1.0.0
 */
public class Blueprint {

    private final String author;
    private final String name;
    private final List<Point> points = new ArrayList<>();

    /**
     * Crea un nuevo blueprint.
     *
     * @param author autor del blueprint
     * @param name nombre del blueprint
     * @param pts lista inicial de puntos; si es {@code null} no se agregan puntos
     */
    public Blueprint(String author, String name, List<Point> pts) {
        this.author = author;
        this.name = name;
        if (pts != null) points.addAll(pts);
    }

    /**
     * Devuelve el autor del blueprint.
     * @return autor
     */
    public String getAuthor() { return author; }
    /**
     * Devuelve el nombre del blueprint.
     * @return nombre
     */
    public String getName() { return name; }
    /**
     * Devuelve la lista de puntos como vista inmutable.
     * @return lista inmutable de puntos
     */
    public List<Point> getPoints() { return Collections.unmodifiableList(points); }

    /**
     * Agrega un punto al final de la secuencia.
     * @param p punto a agregar
     */
    public void addPoint(Point p) { points.add(p); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Blueprint bp)) return false;
        return Objects.equals(author, bp.author) && Objects.equals(name, bp.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(author, name);
    }
}
