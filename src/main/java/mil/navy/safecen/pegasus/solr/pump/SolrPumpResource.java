package mil.navy.safecen.pegasus.solr.pump;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("/solr")
public interface SolrPumpResource {

    @GET
    @Path("/echo/{input}")
    @Produces("text/plain")
    public String ping(@PathParam("input") String input);

    @POST
//	@Produces("application/json")
//	@Consumes("application/json")
	@Consumes("test/plain")
    @Path("/index")
    public Response indexSolr(final String record);

    @GET
//	@Produces("application/json")
//	@Consumes("application/json")
    @Path("/altidx")
    public Response altIndexSolr(@QueryParam("summary_id") final String summaryId,
    		@QueryParam("record_type") final String recordType);

}
