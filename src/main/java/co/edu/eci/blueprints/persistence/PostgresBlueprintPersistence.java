package co.edu.eci.blueprints.persistence;


import co.edu.eci.blueprints.model.Blueprint;
import co.edu.eci.blueprints.model.Point;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * Implementación de {@link BlueprintPersistence} respaldada por PostgreSQL
 * mediante {@link JdbcTemplate}. Marcada como {@link Primary} para ser la
 * implementación preferida por Spring cuando hay múltiples beans.
 */
@Repository
@Primary
@Profile("postgres") 
public class PostgresBlueprintPersistence implements BlueprintPersistence {

    private final JdbcTemplate jdbc;

    /**
     * Crea el repositorio con el {@link JdbcTemplate} inyectado.
     * @param jdbc plantilla JDBC configurada contra la base de datos
     */
    public PostgresBlueprintPersistence(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private static final RowMapper<Blueprint> BP_ROW_MAPPER = (rs, rowNum) -> new Blueprint(
            rs.getString("author"),
            rs.getString("name"),
            Collections.emptyList()
    );

    private static final RowMapper<Point> POINT_ROW_MAPPER = (rs, rowNum) -> new Point(
            rs.getInt("x"), rs.getInt("y")
    );

    @Override
    public void saveBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        // Check existence
        Integer count = jdbc.queryForObject(
                "SELECT COUNT(*) FROM blueprints WHERE author=? AND name=?",
                Integer.class, bp.getAuthor(), bp.getName());
        if (count != null && count > 0) {
            throw new BlueprintPersistenceException("Blueprint already exists: " + bp.getAuthor() + ":" + bp.getName());
        }
        jdbc.update("INSERT INTO blueprints(author, name) VALUES(?, ?)", bp.getAuthor(), bp.getName());
        if (bp.getPoints() != null) {
            for (Point p : bp.getPoints()) {
                jdbc.update("INSERT INTO blueprint_points(author, name, x, y) VALUES(?, ?, ?, ?)",
                        bp.getAuthor(), bp.getName(), p.x(), p.y());
            }
        }
    }

    @Override
    public Blueprint getBlueprint(String author, String name) throws BlueprintNotFoundException {
        List<Blueprint> list = jdbc.query(
        "SELECT author, name FROM blueprints WHERE author=? AND name=?",
                BP_ROW_MAPPER, author, name);
        if (list.isEmpty()) throw new BlueprintNotFoundException("Blueprint not found: " + author + "/" + name);
        Blueprint bp = list.get(0);
        List<Point> pts = jdbc.query("SELECT x, y FROM blueprint_points WHERE author=? AND name=? ORDER BY id",
                POINT_ROW_MAPPER, author, name);
        // Create new object with points
        return new Blueprint(bp.getAuthor(), bp.getName(), pts);
    }

    @Override
    public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException {
        List<Blueprint> bps = jdbc.query("SELECT author, name FROM blueprints WHERE author=? ORDER BY name",
                BP_ROW_MAPPER, author);
        if (bps.isEmpty()) throw new BlueprintNotFoundException("No blueprints for author: " + author);
        Set<Blueprint> out = new HashSet<>();
        for (Blueprint bp : bps) {
            List<Point> pts = jdbc.query("SELECT x, y FROM blueprint_points WHERE author=? AND name=? ORDER BY id",
                    POINT_ROW_MAPPER, bp.getAuthor(), bp.getName());
            out.add(new Blueprint(bp.getAuthor(), bp.getName(), pts));
        }
        return out;
    }

    @Override
    public Set<Blueprint> getAllBlueprints() {
        List<Blueprint> bps = jdbc.query("SELECT author, name FROM blueprints ORDER BY author, name", BP_ROW_MAPPER);
        Set<Blueprint> out = new HashSet<>();
        for (Blueprint bp : bps) {
            List<Point> pts = jdbc.query("SELECT x, y FROM blueprint_points WHERE author=? AND name=? ORDER BY id",
                    POINT_ROW_MAPPER, bp.getAuthor(), bp.getName());
            out.add(new Blueprint(bp.getAuthor(), bp.getName(), pts));
        }
        return out;
    }

    @Override
    public void addPoint(String author, String name, int x, int y) throws BlueprintNotFoundException {
        Integer count = jdbc.queryForObject(
                "SELECT COUNT(*) FROM blueprints WHERE author=? AND name=?",
                Integer.class, author, name);
        if (count == null || count == 0) throw new BlueprintNotFoundException("Blueprint not found: " + author + "/" + name);
        jdbc.update("INSERT INTO blueprint_points(author, name, x, y) VALUES(?, ?, ?, ?)", author, name, x, y);
    }
}
