/**
 * 
 */
package mil.navy.safecen.solr.router;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import mil.navy.safecen.pegasus.solr.pump.PumpData2Arg0;

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
public class RowProcessor implements Processor {

	@Override
	public void process(Exchange exchange) throws Exception {
		@SuppressWarnings("unchecked")
		Map<String, Object> row = exchange.getIn().getBody(Map.class);

		final BigDecimal pkUserWorkspaceMonitor = ((BigDecimal) row.get("PK_USER_WORKSPACE_MONITOR"));
		final String recordId = String.valueOf(pkUserWorkspaceMonitor.longValue());
		final String summaryId = ((String) row.get("SUMMARY_ID"));               
		final String reportId = ((String) row.get("REPORT_ID"));               
		final String reportType = ((String) row.get("REPORT_TYPE"));              
		final String status = ((String) row.get("STATUS"));                   
		final BigDecimal isLegacy = ((BigDecimal) row.get("IS_LEGACY"));                
		final String action = ((String) row.get("ACTION"));                   
		final Timestamp created = ((Timestamp) row.get("CREATED"));                  

		exchange.getOut().setHeader("is_legacy",
				isLegacy.shortValue() > 0 ? "yes" : "no");
		exchange.getOut().setHeader("record_id", recordId);
		exchange.getOut().setHeader("report_id", reportId);
		exchange.getOut().setHeader("report_type", reportType);
		exchange.getOut().setHeader("report_action", action);
		
		/**
		 * This is the JSON version
		 */
		final PumpData2Arg0 bean = new PumpData2Arg0();
		bean.setAction(action);
		Calendar c = Calendar.getInstance(Locale.getDefault());
		c.setTime(new Date(created.getTime()));
		bean.setCreated(c);
		bean.setIs_legacy(isLegacy.shortValue() > 0 ? true : false);
		bean.setRecord_id(recordId);
		bean.setReport_type(reportType);
		bean.setStatus(status);
		bean.setSummary_id(summaryId);
		bean.setReport_serl(reportId);
		exchange.getOut().setBody(bean);

		/**
		 * This is the XML version
		 */
//		GregorianCalendar c = new GregorianCalendar();
//		c.setTime(new Date(created.getTime()));
//		XMLGregorianCalendar date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
//		try {
//			MonitorRecord monitorRecord = new MonitorRecord();
//			monitorRecord.setAction(action);
//			monitorRecord.setCreated(date2);
//			monitorRecord.setIsLegacy(isLegacy.shortValue() > 0 ? true : false);
//			monitorRecord.setRecordId(String.valueOf(pkUserWorkspaceMonitor.longValue()));
//			monitorRecord.setReportType(ReportTypeType.fromValue(reportType));
//			monitorRecord.setStatus(StatusType.fromValue(status));
//			monitorRecord.setSummaryId(summaryId);
//			JAXBContext jaxbContext = JAXBContext.newInstance(MonitorRecord.class);
//			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
//			// output pretty printed
//			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//			jaxbMarshaller.marshal(monitorRecord, sw);
//			exchange.getOut().setBody(monitorRecord);
//		} catch (JAXBException e) {
//			e.printStackTrace();
//		}
	}

}
