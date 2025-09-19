package co.edu.eci.blueprints.persistence;

/**
 * Excepción verificada que indica que un blueprint no existe en el repositorio.
 */
public class BlueprintNotFoundException extends Exception {
    /**
     * Crea la excepción con un mensaje descriptivo.
     * @param msg detalle del error
     */
    public BlueprintNotFoundException(String msg) { super(msg); }
}
