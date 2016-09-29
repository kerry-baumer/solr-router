/**
 * 
 */
package mil.navy.safecen.solr.router;

import java.io.ByteArrayOutputStream;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Process a row in the SUD_USER_WORKSPACE_MONITOR table:
 * <code><pre>
 *	PK_USER_WORKSPACE_MONITOR                 NOT NULL NUMBER(38)
 *	SUMMARY_ID                                NOT NULL VARCHAR2(32)
 *	REPORT_TYPE                                        VARCHAR2(25)
 *	STATUS                                    NOT NULL CHAR(1)
 *	IS_LEGACY                                 NOT NULL NUMBER(38)
 *	ACTION                                    NOT NULL CHAR(1)
 *	CREATED                                            TIMESTAMP(6)
 * to XML.
 * </pre></code>
 * @author kerry.baumer
 *
 */
public class SolrDelDocProcessor implements Processor {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SolrDelDocProcessor.class);

	@Override
	public void process(Exchange exchange) throws Exception {

		final String recordId = (String)exchange.getIn().getHeader("record_id");
		final String reportId = (String)exchange.getIn().getHeader("report_id");
		LOGGER.info("Received delete request for: record_id - {}, report_id - {}",
				recordId, reportId);
		String xml = "";
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			XMLOutputFactory out = XMLOutputFactory.newInstance();
			XMLStreamWriter writer = out.createXMLStreamWriter(bos, "UTF-8");
			writer.writeStartDocument();
			writer.writeStartElement("delete");
			writer.writeStartElement("id");
			writer.writeCharacters((String) reportId);
			writer.writeEndElement();
			writer.writeEndElement();
			writer.writeEndDocument();
			writer.flush();
			writer.close();
			xml = new String(bos.toByteArray());
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		exchange.getOut().setHeader("record_id", recordId);
		exchange.getOut().setBody(xml);

	}

}
