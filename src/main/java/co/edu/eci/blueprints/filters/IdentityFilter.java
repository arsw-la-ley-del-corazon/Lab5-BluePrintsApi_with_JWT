package co.edu.eci.blueprints.filters;

import co.edu.eci.blueprints.model.Blueprint;
import org.springframework.stereotype.Component;

/**
 * Filtro por defecto que retorna el blueprint sin modificaciones.
 *
 * <p>Equivale al comportamiento base previo a la configuración de filtros
 * personalizados. Es útil para desactivar efectos de filtrado.</p>
 */
@Component
public class IdentityFilter implements BlueprintsFilter {
    @Override
    public Blueprint apply(Blueprint bp) { return bp; }
}
