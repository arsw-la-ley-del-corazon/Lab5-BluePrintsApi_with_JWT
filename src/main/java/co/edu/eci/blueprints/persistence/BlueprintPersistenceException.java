package co.edu.eci.blueprints.persistence;


/**
 * Excepción verificada para errores de persistencia (por ejemplo, duplicados).
 */
public class BlueprintPersistenceException extends Exception {
    /**
     * Crea la excepción con un mensaje descriptivo.
     * @param msg detalle del error
     */
    public BlueprintPersistenceException(String msg) { super(msg); }
}
