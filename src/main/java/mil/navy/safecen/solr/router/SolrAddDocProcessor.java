/**
 * 
 */
package mil.navy.safecen.solr.router;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.SolrInputField;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import mil.navy.safecen.hermes.client.cache.reader.CachedCpUic;
import mil.navy.safecen.hermes.client.cache.reader.CachedCpUicIndexReader;
import mil.navy.safecen.hermes.client.cache.reader.CachedCpUicIndexReaderFactory;
import mil.navy.safecen.hermes.client.cache.reader.search.context.CpUicIndexReaderSearchContext;
import mil.navy.safecen.hermes.client.cache.reader.search.context.UicSearchContextBuilder;
import mil.navy.safecen.solr.router.lookup.ClusterLookup;
import mil.navy.safecen.solr.router.lookup.SolrReportType;
import mil.navy.safecen.solr.support.model.SolrDataAcftBean;
import mil.navy.safecen.solr.support.model.SolrDataBean;
import mil.navy.safecen.solr.support.model.SolrDataPrsnBean;

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
public class SolrAddDocProcessor implements Processor {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SolrAddDocProcessor.class);
	
	private ClusterLookup clusterLookup;

	@Autowired
	public void setClusterLookup (ClusterLookup clusterLookup) {
	    this.clusterLookup = clusterLookup;
	}

	@Override
	public void process(Exchange exchange) throws Exception {

		SolrDataBean bean = exchange.getIn().getBody(SolrDataBean.class);
		final String recordId = (String)exchange.getIn().getHeader("record_id");
		final boolean showInSearchEdit = bean.getShowInSearchEdit() == null ? false : bean.getShowInSearchEdit().booleanValue();
		final boolean showInSearchTool = bean.getShowInSearchTool() == null ? false : bean.getShowInSearchTool().booleanValue();
		
		LOGGER.info(ReflectionToStringBuilder.toString(bean, ToStringStyle.MULTI_LINE_STYLE));
		SolrInputDocument doc = new SolrInputDocument();
		addDocField(doc, SolrDocFieldId.ID_BRNCH_SVC, bean.getBrnchSvc());
		addDocField(doc, SolrDocFieldId.ID_CITY, bean.getCity());
		addDocField(doc, SolrDocFieldId.ID_STATE, bean.getState());
		addDocField(doc, SolrDocFieldId.ID_CONUS_I, bean.getConusI());
		addDocField(doc, SolrDocFieldId.ID_HT_ID, bean.getHtId());
		addDocField(doc, SolrDocFieldId.ID_COUNTRY, bean.getCountry());
		addDocField(doc, SolrDocFieldId.ID_COUNTY, bean.getCounty());
		addDocField(doc, SolrDocFieldId.ID_NOT_COUNTD_RSN_C, bean.getNotCountdRsnC());

		setReportingUnit(doc, bean);

		addDocField(doc, SolrDocFieldId.ID_EVENT_COST, bean.getEventCost());
		addDocField(doc, SolrDocFieldId.ID_DOD_PROP_COST, bean.getDodPropCost());
		addDocField(doc, SolrDocFieldId.ID_NON_DOD_PROP_COST, bean.getNonDodPropCost());
		addDocField(doc, SolrDocFieldId.ID_ON_BASE_I, bean.getOnBaseI());
		
/*

		addDocField(doc, SolrDocFieldId.ID_EVENT_CLASSN, bean.get);
		
		addDocField(doc, SolrDocFieldId.ID_EVENT_LOCN, bean.get);
						
		addDocField(doc, SolrDocFieldId.ID_KEY_WORDS, bean.get);
		addDocField(doc, SolrDocFieldId.ID_TAGS, bean.get);
*/
		String rptClass = bean.getRptClass(), rptClassDesc = null;
		if(StringUtils.isNotBlank(rptClass)) {
			switch(rptClass.charAt(0)) {
			case 'A':
				rptClassDesc = "Class A Mishap";
				break;
			case 'B':
				rptClassDesc = "Class B Mishap";
				break;
			case 'C':
				rptClassDesc = "Class C Mishap";
				break;
			case 'D':
				rptClassDesc = "Class D Mishap";
				break;
			case 'H':
				rptClassDesc = "Hazard";
				break;
			default:
				rptClassDesc = "Unknown Report Class: " + rptClass;
				break;
			}
		}
		addDocField(doc, SolrDocFieldId.ID_RPT_CLASS, rptClass);
		addDocField(doc, SolrDocFieldId.ID_RPT_CLASS_DESC, rptClassDesc);

		addDocField(doc, SolrDocFieldId.ID_REPORT_TYPE, bean.getReportType());		// raw report type
		try {
			SolrReportType srt = SolrReportType.valueOf(bean.getRptT()); 
			addDocField(doc, SolrDocFieldId.ID_RPT_T, srt.toString());
			addDocField(doc, SolrDocFieldId.ID_RPT_T_DESC, srt.getDescription());
			addDocField(doc, SolrDocFieldId.ID_RPT_CATG, srt.getCategory());
			addDocField(doc, SolrDocFieldId.ID_REPORT_CATG_C, srt.getCategory());
		} catch (Exception ex) {
			addDocField(doc, SolrDocFieldId.ID_RPT_T, bean.getRptT());			
			addDocField(doc, SolrDocFieldId.ID_RPT_T_DESC, "Unknown: " + bean.getRptT());
			addDocField(doc, SolrDocFieldId.ID_RPT_CATG, "other");
			addDocField(doc, SolrDocFieldId.ID_REPORT_CATG_C, "Other");
		}
		addDocField(doc, SolrDocFieldId.ID_RPRTBL_EVENT_SERL, bean.getRprtblEventSerl());
		addDocField(doc, SolrDocFieldId.ID_SHORT_NARR, bean.getShortNarr());
		addDocField(doc, SolrDocFieldId.ID_SHOW_IN_SEARCH_EDIT, showInSearchEdit);
		addDocField(doc, SolrDocFieldId.ID_SHOW_IN_SEARCH_TOOL, showInSearchTool);
		addDocField(doc, SolrDocFieldId.ID_MISH_DATE, bean.getMishDate());
		addDocField(doc, SolrDocFieldId.ID_NARRATIVE, bean.getNarrative());
		addDocField(doc, SolrDocFieldId.ID_PRIV_NARR, bean.getPrivNarr());
		
		if(bean.getEventCharzn() != null && !bean.getEventCharzn().isEmpty()) {
			for(String charzn : bean.getEventCharzn()) {
				addDocField(doc, SolrDocFieldId.ID_EVENT_CHARZN, charzn);
			}
		}
		if(bean.getLoc() != null && !bean.getLoc().isEmpty()) {
			for(String locn : bean.getLoc()) {
				addDocField(doc, SolrDocFieldId.ID_LOC, locn.trim());
			}
		}
		if(bean.getMishT() != null && !bean.getMishT().isEmpty()) {
			for(String loc : bean.getMishT()) {
				addDocField(doc, SolrDocFieldId.ID_MISH_T, loc);
			}
		}
		addDocField(doc, SolrDocFieldId.ID_EVENT_LOCN_LAT, bean.getEventLocnLat());
		addDocField(doc, SolrDocFieldId.ID_EVENT_LOCN_LON, bean.getEventLocnLon());
		addDocField(doc, SolrDocFieldId.ID_EVENT_LOCN_TXT, bean.getEventLocnTxt());
		
		
		addCaclulatedDates(doc, bean);
		setDocClusterAndSection(doc, bean);
		
		Set<String> invlvdInjClassn = new HashSet<String>();
		Set<String> invlvdUnitCodes = new HashSet<String>();
		Set<String> invlvdAcftModls = new HashSet<String>();
		
		/**
		 * Normalize injury classifications and unit codes
		 */
		List<SolrDataPrsnBean> invlvdPrsns = bean.getInvolvedPeople();
		if(invlvdPrsns != null && !invlvdPrsns.isEmpty()) {
			for(SolrDataPrsnBean prsn : invlvdPrsns) {
				invlvdUnitCodes.add(prsn.getUnitCode());
				invlvdInjClassn.add(prsn.getInjClassn());
			}
		}
		
		/**
		 * Normalize Aircraft models and unit codes
		 */
		List<SolrDataAcftBean> invlvdAcft = bean.getInvolvedAcft();
		if(invlvdAcft != null && !invlvdAcft.isEmpty()) {
			for(SolrDataAcftBean acft : invlvdAcft) {
				invlvdUnitCodes.add(acft.getAcftUnitCode());
				invlvdAcftModls.add(acft.getAcftModel());
			}
		}
		
		Iterator<String> iter = invlvdInjClassn.iterator();
		while(iter.hasNext()) {
			addDocField(doc, SolrDocFieldId.ID_INJ_CLASSNC, iter.next());
		}
		iter = invlvdUnitCodes.iterator();
		while(iter.hasNext()) {
			addInvlvdUnit(doc, iter.next(), bean.getMishDate());
		}
		iter = invlvdAcftModls.iterator();
		while(iter.hasNext()) {
			addDocField(doc, SolrDocFieldId.ID_INVOLVED_ACFT, iter.next().trim());
		}

		String xml = createAddXML(doc);
		/**
		 * Don't know why this is necessary, but it is. record_id header is blank if not reset.
		 */
		exchange.getOut().setHeader("record_id", recordId);
		exchange.getOut().setBody(xml);

	}

	private void addCaclulatedDates(SolrInputDocument doc, SolrDataBean bean) {
		DateTime dt = new DateTime(bean.getMishDate().getTime());
		int monthOfYear = dt.getMonthOfYear();
		addDocField(doc, SolrDocFieldId.ID_FY,
				String.valueOf(monthOfYear > 9 ? dt.getYear() + 1 : dt.getYear()));
		switch(monthOfYear) {
		case 10:
		case 11:
		case 12:
			addDocField(doc, SolrDocFieldId.ID_QRTR, "1st");
			break;
		case 1:
		case 2:
		case 3:
			addDocField(doc, SolrDocFieldId.ID_QRTR, "2nd");
			break;
		case 4:
		case 5:
		case 6:
			addDocField(doc, SolrDocFieldId.ID_QRTR, "3rd");
			break;
		case 7:
		case 8:
		case 9:
			addDocField(doc, SolrDocFieldId.ID_QRTR, "4th");
			break;
		}
		addDocField(doc, SolrDocFieldId.ID_RPRT_DATE, formatUTC(new Date(Long.valueOf(bean.getHtId()))));
	}

	private String createAddXML(SolrInputDocument doc) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			XMLOutputFactory out = XMLOutputFactory.newInstance();
			XMLStreamWriter writer = out.createXMLStreamWriter(bos, "UTF-8");
			writer.writeStartDocument();
			writer.writeStartElement("add");
				writer.writeStartElement("doc");
					SolrInputField fieldValue;
					for(String fieldName : doc.getFieldNames()) {				
						fieldValue = doc.getField(fieldName);
						if(fieldValue != null && fieldValue.getValues() != null) {
							Iterator<Object> i = fieldValue.getValues().iterator();
							while(i.hasNext()) {
								writer.writeStartElement("field");
								writer.writeAttribute("name", fieldName);
								writer.writeCharacters(i.next().toString());
								writer.writeEndElement();
							}
						}
					}
				writer.writeEndElement();			// End - "doc"
				writer.writeStartElement("commit");
				writer.writeEndElement();			// End - "commit"
			writer.writeEndElement();				// End - "add"
			writer.writeEndDocument();
			writer.flush();
			writer.close();
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new String(bos.toByteArray());
	}

/*	private String createDeleteXML(SolrInputDocument doc) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			XMLOutputFactory out = XMLOutputFactory.newInstance();
			XMLStreamWriter writer = out.createXMLStreamWriter(bos, "UTF-8");
			writer.writeStartDocument();
			writer.writeStartElement("delete");
			writer.writeStartElement("id");
			writer.writeCharacters((String) doc.getFieldValue("ht_id"));
			writer.writeEndElement();
			writer.writeEndElement();
			writer.writeEndDocument();
			writer.flush();
			writer.close();
		} catch (XMLStreamException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new String(bos.toByteArray());
	}
*/
	private void addDocField(SolrInputDocument doc, SolrDocFieldId id, Boolean value) {
		if(value != null) {
			doc.addField(id.getIdentifier(), value);
		} else {
			doc.addField(id.getIdentifier(), false);
		}
	}

	private void addDocField(SolrInputDocument doc, SolrDocFieldId id, String cs) {
		if(StringUtils.isNotBlank(cs)) {
			doc.addField(id.getIdentifier(), cs);
		}
	}

	private void addDocField(SolrInputDocument doc, SolrDocFieldId idNonDodPropCost, Double dblValue) {
		// TODO Auto-generated method stub
		
	}

	private void addDocField(SolrInputDocument doc, SolrDocFieldId id, Date dateValue) {
		if(dateValue != null) {
			final String formattedDate = formatUTC(dateValue);
			doc.addField(id.getIdentifier(), formattedDate);
		}
		
	}

	private String formatUTC(final Date dateValue) {
		String lv_dateFormateInUTC = "";			// Will hold the final converted date
		String lv_localTimeZone = "";
		SimpleDateFormat lv_formatter;

		// Set output format prints "2007/10/25  18:35:07 EDT(-0400)"
		lv_formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z'('Z')'");
		lv_formatter.setTimeZone(TimeZone.getTimeZone(lv_localTimeZone));

		// Convert the date from the local timezone to UTC timezone
		lv_formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
		lv_dateFormateInUTC = lv_formatter.format(dateValue);

		String date1 = "";
		String date2 = "";
		String date3 = "";
		date1 = lv_dateFormateInUTC.substring(0, 10);
		date2 = lv_dateFormateInUTC.substring(11, 19);
		date3 = date1 + "T" + date2 + "Z";
		return date3;
	}
	
	private void addInvlvdUnit(SolrInputDocument doc, String unitCode, Date mishDate) {
		CpUicIndexReaderSearchContext ctxt = new UicSearchContextBuilder()
				.addUnitCode(unitCode)
				.addMishapDate(mishDate)
				.build();
		CachedCpUicIndexReader reader = CachedCpUicIndexReaderFactory.getReader();
		List<CachedCpUic> list = reader.getUicDecodesByUicAndEventDate(ctxt);
		if(!list.isEmpty()) {
			CachedCpUic cachedCpUic = list.get(0);			// Assume first element is correct
			addDocField(doc, SolrDocFieldId.ID_INVOLVED_UNIT, cachedCpUic.getUnitCode().trim());
			addDocField(doc, SolrDocFieldId.ID_INVOLVED_UNIT, cachedCpUic.getActyNameLong());
			addDocField(doc, SolrDocFieldId.ID_INVOLVED_UNIT, cachedCpUic.getAvnSqdnName());
		} else {
			addDocField(doc, SolrDocFieldId.ID_INVOLVED_UNIT, unitCode);
		}
	}

	private void setDocClusterAndSection(final SolrInputDocument doc, final SolrDataBean bean) {
//		addDocField(doc, SolrDocFieldId.ID_CLUSTER, bean.get);
//		addDocField(doc, SolrDocFieldId.ID_CLUSTER_C, bean.get);
//		addDocField(doc, SolrDocFieldId.ID_SECTION, bean.get);
	}

	private void setReportingUnit(SolrInputDocument doc, SolrDataBean bean) {
		CpUicIndexReaderSearchContext ctxt = new UicSearchContextBuilder()
				.addUnitCode(bean.getReportingUnit())
				.addMishapDate(bean.getMishDate())
				.build();
		CachedCpUicIndexReader reader = CachedCpUicIndexReaderFactory.getReader();
		List<CachedCpUic> list = reader.getUicDecodesByUicAndEventDate(ctxt);
		if(!list.isEmpty()) {
			CachedCpUic cachedCpUic = list.get(0);			// Assume first element is correct
			addDocField(doc, SolrDocFieldId.ID_REPORTING_UNIT, cachedCpUic.getUnitCode());
			addDocField(doc, SolrDocFieldId.ID_REPORTING_UNIT, cachedCpUic.getActyNameLong());
			addDocField(doc, SolrDocFieldId.ID_REPORTING_UNIT, cachedCpUic.getAvnSqdnName());
//			addDocField(doc, SolrDocFieldId.ID_ENVIRONMENT, cachedCpUic.get);			
		} else {
			addDocField(doc, SolrDocFieldId.ID_REPORTING_UNIT, bean.getReportingUnit());
		}
	}

}
