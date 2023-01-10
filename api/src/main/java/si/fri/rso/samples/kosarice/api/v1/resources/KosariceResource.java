package si.fri.rso.samples.kosarice.api.v1.resources;

import com.kumuluz.ee.cors.annotations.CrossOrigin;
import com.kumuluz.ee.logs.cdi.Log;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import si.fri.rso.samples.kosarice.lib.Kosarica;
import si.fri.rso.samples.kosarice.services.beans.KosaricaBean;
import si.fri.rso.samples.kosarice.services.clients.AmazonRekognitionClient;


import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.logging.Logger;


@Log
@ApplicationScoped
@Path("/kosarice")
@Produces(MediaType.APPLICATION_JSON)
@Consumes({MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
@CrossOrigin(supportedMethods = "GET, POST, PUT, DELETE, HEAD, OPTIONS")
public class KosariceResource {

    private Logger log = Logger.getLogger(KosariceResource.class.getName());

    @Inject
    private KosaricaBean kosaricaBean;


    @Context
    protected UriInfo uriInfo;

    @Inject
    private AmazonRekognitionClient amazonRekognitionClient;

    @Operation(description = "Get all kosarice metadata.", summary = "Get all metadata")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "List of kosarice metadata",
                    content = @Content(schema = @Schema(implementation = Kosarica.class, type = SchemaType.ARRAY)),
                    headers = {@Header(name = "X-Total-Count", description = "Number of objects in list")}
            )})
    @GET
    public Response getKosarice() {

        List<Kosarica> kosarice = kosaricaBean.getKosaricaFilter(uriInfo);

        return Response.status(Response.Status.OK).entity(kosarice).build();
    }


    @Operation(description = "Get metadata for kosarice.", summary = "Get metadata for kosarice")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Kosarice metadata",
                    content = @Content(
                            schema = @Schema(implementation = Kosarica.class))
            )})
    @GET
    @Path("/{kosaricaId}")
    public Response getKosarice(@Parameter(description = "Metadata ID.", required = true)
                                     @PathParam("kosaricaId") Integer kosaricaId) {

        Kosarica kosarica = kosaricaBean.getKosarica(kosaricaId);

        if (kosarica == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(kosarica).build();
    }

    @Operation(description = "Add kosarica metadata.", summary = "Add metadata")
    @APIResponses({
            @APIResponse(responseCode = "201",
                    description = "Metadata successfully added."
            ),
            @APIResponse(responseCode = "405", description = "Validation error .")
    })
    @POST
    public Response createKosarica(@RequestBody(
            description = "DTO object with kosarica metadata.",
            required = true, content = @Content(
            schema = @Schema(implementation = Kosarica.class))) Kosarica kosarica) {

        if (kosarica.getCena() == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        else {
            kosarica = kosaricaBean.createKosarica(kosarica);
        }

        return Response.status(Response.Status.CREATED).entity(kosarica).build();

    }


    @Operation(description = "Update metadata for kosarica.", summary = "Update metadata")
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Metadata successfully updated."
            )
    })
    @PUT
    @Path("{kosaricaId}")
    public Response putKosarica(@Parameter(description = "Metadata ID.", required = true)
                                     @PathParam("kosaricaId") Integer kosaricaId,
                                @RequestBody(
                                             description = "DTO object with image metadata.",
                                             required = true, content = @Content(
                                             schema = @Schema(implementation = Kosarica.class)))
                                     Kosarica kosarica){

        kosarica = kosaricaBean.putKosarica(kosaricaId, kosarica);

        if (kosarica == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.NOT_MODIFIED).build();

    }

    @Operation(description = "Delete kosarica.", summary = "Delete kosarica")
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Kosarica successfully deleted."
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "Not found."
            )
    })
    @DELETE
    @Path("{kosaricaId}")
    public Response deleteKosarica(@Parameter(description = "Kosarica ID.", required = true)
                                        @PathParam("kosaricaId") Integer kosaricaId){

        boolean deleted = kosaricaBean.deleteKosarica(kosaricaId);

        if (deleted) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }


}
