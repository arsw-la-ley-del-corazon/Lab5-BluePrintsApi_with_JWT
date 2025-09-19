package co.edu.eci.blueprints.filters;

import co.edu.eci.blueprints.model.Blueprint;
import co.edu.eci.blueprints.model.Point;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Filtro que elimina puntos consecutivos duplicados (misma coordenada {@code x,y}).
 *
 * <p>Ejemplo: la secuencia [(1,1), (1,1), (2,2)] se transforma a
 * [(1,1), (2,2)].</p>
 *
 * <p>Activación mediante perfil Spring: {@code "redundancy"}.</p>
 */
@Component
@Profile("redundancy")
public class RedundancyFilter implements BlueprintsFilter {
    @Override
    public Blueprint apply(Blueprint bp) {
        List<Point> in = bp.getPoints();
        if (in.isEmpty()) return bp;
        List<Point> out = new ArrayList<>();
        Point prev = null;
        for (Point p : in) {
            if (prev == null || !(prev.x()==p.x() && prev.y()==p.y())) {
                out.add(p);
                prev = p;
            }
        }
        return new Blueprint(bp.getAuthor(), bp.getName(), out);
    }
}
