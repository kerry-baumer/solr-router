/**
 * 
 */
package mil.navy.safecen.solr.router;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

/**
 * @author kerry.baumer
 *
 */
public class RowProcessorTest extends CamelTestSupport {

	@Override
	protected RouteBuilder createRouteBuilder()
	    throws Exception {
	  return new RouteBuilder() {
	    @Override
	    public void configure() throws Exception {
	      from("direct:in")
	        .process(new RowProcessor())
	        .to("mock:out");
	    }
	  };
	}
	
	@Test
	public void testPrepend() throws Exception {
		MockEndpoint mockOut = getMockEndpoint("mock:out");
		mockOut.message(0).body().isEqualTo("SOMETHING text");
		mockOut.message(0).header("actionTaken").isEqualTo(true);

		Map<String, Object> headers =
				new HashMap<String, Object>();
		headers.put("action", "prepend");

		Map<String, Object> body =
				new HashMap<String, Object>();
		body.put("PK_USER_WORKSPACE_MONITOR", new BigDecimal(1000));
		body.put("SUMMARY_ID", "");               
		body.put("REPORT_ID", "");               
		body.put("REPORT_TYPE", "AVIATION_MISHAP");              
		body.put("STATUS", "P");                   
		body.put("IS_LEGACY", new BigDecimal(0));                
		body.put("ACTION", "A");                   
		body.put("CREATED", null);                  

		template.sendBodyAndHeaders("direct:in", body, headers);

		assertMockEndpointsSatisfied();
	}
}
