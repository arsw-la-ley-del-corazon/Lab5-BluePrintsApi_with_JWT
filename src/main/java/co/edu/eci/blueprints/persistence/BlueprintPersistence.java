package co.edu.eci.blueprints.persistence;

import co.edu.eci.blueprints.model.*;
import java.util.Set;

/**
 * Contrato de persistencia para operaciones CRUD básicas sobre {@link Blueprint}.
 */
public interface BlueprintPersistence {

    /**
     * Persiste un nuevo blueprint.
     * @param bp blueprint a guardar
     * @throws BlueprintPersistenceException si ya existe un blueprint con la misma identidad
     */

    void saveBlueprint(Blueprint bp) throws BlueprintPersistenceException;

    /**
     * Obtiene un blueprint por autor y nombre.
     * @param author autor
     * @param name nombre del blueprint
     * @return blueprint existente
     * @throws BlueprintNotFoundException si no existe
     */
    Blueprint getBlueprint(String author, String name) throws BlueprintNotFoundException;

    /**
     * Obtiene todos los blueprints de un autor.
     * @param author autor
     * @return conjunto no vacío de blueprints
     * @throws BlueprintNotFoundException si el autor no tiene blueprints
     */
    Set<Blueprint> getBlueprintsByAuthor(String author) throws BlueprintNotFoundException;

    /**
     * Lista todos los blueprints.
     * @return conjunto de blueprints, posiblemente vacío
     */
    Set<Blueprint> getAllBlueprints();

    /**
     * Agrega un punto al blueprint indicado.
     * @param author autor del blueprint
     * @param name nombre del blueprint
     * @param x coordenada X
     * @param y coordenada Y
     * @throws BlueprintNotFoundException si el blueprint no existe
     */
    void addPoint(String author, String name, int x, int y) throws BlueprintNotFoundException;
}
