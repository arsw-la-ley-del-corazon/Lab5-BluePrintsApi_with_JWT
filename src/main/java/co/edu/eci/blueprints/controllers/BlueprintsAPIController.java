package co.edu.eci.blueprints.controllers;

import co.edu.eci.blueprints.model.Blueprint;
import co.edu.eci.blueprints.model.Point;
import co.edu.eci.blueprints.persistence.BlueprintNotFoundException;
import co.edu.eci.blueprints.persistence.BlueprintPersistenceException;
import co.edu.eci.blueprints.services.BlueprintsServices;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * Controlador REST para operaciones CRUD sobre {@link Blueprint}.
 */
@RestController
@Tag(name = "Blueprints", description = "Operaciones sobre Blueprints")
@RequestMapping("/api/v1/blueprints")
public class BlueprintsAPIController {

    private final BlueprintsServices services;

    /**
     * Constructor con inyección del servicio.
     * @param services capa de servicios de blueprints
     */
    public BlueprintsAPIController(BlueprintsServices services) { this.services = services; }

    // GET /blueprints
    @GetMapping
    @Operation(summary = "Listar todos los blueprints",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Consulta exitosa",
                content = @Content(schema = @Schema(implementation = ApiResponse.class)))
        })
    /**
     * Lista todos los blueprints disponibles.
     * @return respuesta con el conjunto de blueprints
     */
    public ResponseEntity<ApiResponse<Set<Blueprint>>> getAll() {
        Set<Blueprint> data = services.getAllBlueprints();
        return ResponseEntity.ok(new ApiResponse<>(200, "execute ok", data));
    }

    // GET /blueprints/{author}
    @GetMapping("/{author}")
    @Operation(summary = "Listar blueprints por autor",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Consulta exitosa",
                content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Autor sin blueprints",
                content = @Content(schema = @Schema(implementation = ApiResponse.class)))
        })
    /**
     * Obtiene los blueprints de un autor.
     * @param author autor a consultar
     * @return respuesta con el conjunto o 404 si no existen
     */
    public ResponseEntity<?> byAuthor(@PathVariable String author) {
        try {
            Set<Blueprint> data = services.getBlueprintsByAuthor(author);
            return ResponseEntity.ok(new ApiResponse<>(200, "execute ok", data));
        } catch (BlueprintNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(404, e.getMessage(), null));
        }
    }

    // GET /blueprints/{author}/{bpname}
    @GetMapping("/{author}/{bpname}")
    @Operation(summary = "Obtener blueprint por autor y nombre",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Consulta exitosa",
                content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "No encontrado",
                content = @Content(schema = @Schema(implementation = ApiResponse.class)))
        })
    /**
     * Obtiene un blueprint específico.
     * @param author autor
     * @param bpname nombre del blueprint
     * @return respuesta con el blueprint o 404 si no existe
     */
    public ResponseEntity<?> byAuthorAndName(@PathVariable String author, @PathVariable String bpname) {
        try {
            Blueprint data = services.getBlueprint(author, bpname);
            return ResponseEntity.ok(new ApiResponse<>(200, "execute ok", data));
        } catch (BlueprintNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(404, e.getMessage(), null));
        }
    }

    // POST /blueprints
    @PostMapping
    @Operation(summary = "Crear un nuevo blueprint",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "201", description = "Creado",
                content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Solicitud inválida",
                content = @Content(schema = @Schema(implementation = ApiResponse.class)))
        })
    /**
     * Crea un nuevo blueprint.
     * @param req cuerpo de la solicitud con autor, nombre y puntos
     * @return 201 si se crea, 400 si hay conflicto o error de persistencia
     */
    public ResponseEntity<?> add(@Valid @RequestBody NewBlueprintRequest req) {
        try {
            Blueprint bp = new Blueprint(req.author(), req.name(), req.points());
            services.addNewBlueprint(bp);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(201, "created", null));
        } catch (BlueprintPersistenceException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(400, e.getMessage(), null));
        }
    }

    // PUT /blueprints/{author}/{bpname}/points
    @PutMapping("/{author}/{bpname}/points")
    @Operation(summary = "Agregar un punto a un blueprint",
        responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "202", description = "Aceptado",
                content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "No encontrado",
                content = @Content(schema = @Schema(implementation = ApiResponse.class)))
        })
    /**
     * Agrega un punto a un blueprint existente.
     * @param author autor del blueprint
     * @param bpname nombre del blueprint
     * @param p punto a agregar
     * @return 202 si se agrega, 404 si no existe
     */
    public ResponseEntity<?> addPoint(@PathVariable String author, @PathVariable String bpname,
                                      @RequestBody Point p) {
        try {
            services.addPoint(author, bpname, p.x(), p.y());
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(new ApiResponse<>(202, "accepted", null));
        } catch (BlueprintNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(404, e.getMessage(), null));
        }
    }

    /**
     * Request body para crear un nuevo blueprint.
     * @param author autor del blueprint
     * @param name nombre del blueprint
     * @param points lista de puntos
     */
    public record NewBlueprintRequest(
            @NotBlank String author,
            @NotBlank String name,
            @Valid java.util.List<Point> points
    ) { }
}
