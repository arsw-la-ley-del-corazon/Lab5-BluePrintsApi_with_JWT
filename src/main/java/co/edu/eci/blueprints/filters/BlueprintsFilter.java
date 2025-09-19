package co.edu.eci.blueprints.filters;

import co.edu.eci.blueprints.model.Blueprint;

/**
 * Contrato para filtros de procesamiento de blueprints.
 *
 * <p>Los filtros reciben un {@link Blueprint} y devuelven otro
 * blueprint con los cambios aplicados (por ejemplo, reducción de puntos).
 * Las implementaciones deben ser puras (no mutar el objeto de entrada)
 * a menos que se documente lo contrario.</p>
 */
public interface BlueprintsFilter {
    /**
     * Aplica la transformación del filtro sobre el blueprint dado.
     *
     * @param bp blueprint de entrada
     * @return un nuevo blueprint transformado o el mismo si no hay cambios
     */
    Blueprint apply(Blueprint bp);
}
