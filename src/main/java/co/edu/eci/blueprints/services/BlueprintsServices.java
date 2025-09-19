package co.edu.eci.blueprints.services;

import co.edu.eci.blueprints.filters.BlueprintsFilter;
import co.edu.eci.blueprints.model.Blueprint;
import co.edu.eci.blueprints.persistence.BlueprintNotFoundException;
import co.edu.eci.blueprints.persistence.BlueprintPersistence;
import co.edu.eci.blueprints.persistence.BlueprintPersistenceException;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * Capa de servicios para operaciones de negocio sobre {@link Blueprint}.
 *
 * <p>Orquesta la persistencia y la aplicación de filtros configurados.</p>
 */
@Service
public class BlueprintsServices {

    private final BlueprintPersistence persistence;
    private final BlueprintsFilter filter;

    /**
     * Inyección de dependencias del repositorio y filtro.
     * @param persistence implementación de persistencia
     * @param filter filtro activo de blueprints
     */
    public BlueprintsServices(BlueprintPersistence persistence, BlueprintsFilter filter) {
        this.persistence = persistence;
        this.filter = filter;
    }


    /**
     * Registra un nuevo blueprint.
     * @param bp blueprint a registrar
     * @throws BlueprintPersistenceException si el blueprint ya existe
     */
    public void addNewBlueprint(Blueprint bp) throws BlueprintPersistenceException {
        persistence.saveBlueprint(bp);
    }

    /**
     * Obtiene todos los blueprints.
     * @return conjunto (posiblemente vacío) de blueprints
     */
    public Set<Blueprint> getAllBlueprints() {
        return persistence.getAllBlueprints();
    }

    /**
     * Obtiene los blueprints de un autor.
     * @param author autor
     * @return conjunto no vacío de blueprints
     * @throws BlueprintNotFoundException si el autor no tiene blueprints
     */
    public Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException {
        return persistence.getBlueprintsByAuthor(author);
    }

    /**
     * Obtiene un blueprint por autor y nombre aplicando el filtro configurado.
     * @param author autor del blueprint
     * @param name nombre del blueprint
     * @return blueprint filtrado
     * @throws BlueprintNotFoundException si no existe
     */
    public Blueprint getBlueprint(String author, String name) throws BlueprintNotFoundException {
        return filter.apply(persistence.getBlueprint(author, name));
    }

    /**
     * Agrega un punto a un blueprint existente.
     * @param author autor
     * @param name nombre del blueprint
     * @param x coordenada X
     * @param y coordenada Y
     * @throws BlueprintNotFoundException si el blueprint no existe
     */
    public void addPoint(String author, String name, int x, int y) throws BlueprintNotFoundException {
        persistence.addPoint(author, name, x, y);
    }
}
