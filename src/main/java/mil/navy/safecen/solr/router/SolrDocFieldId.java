/**
 * 
 */
package mil.navy.safecen.solr.router;

/**
 * @author kerry.baumer
 *
 */
public enum SolrDocFieldId {
	   ID_BRNCH_SVC("brnch_svc"),
	   ID_CITY("city"),
	   ID_CLUSTER("cluster"),
	   ID_CLUSTER_C("cluster_c"),
	   ID_CONUS_I("conus_i"),
	   ID_COUNTRY("country"),
	   ID_COUNTY("county"),
	   ID_DOD_PROP_COST("dod_prop_cost" ),
	   ID_ENVIRONMENT("environment"),
	   ID_EVENT_CHARZN("event-charzn"),
	   ID_EVENT_CLASSN("event-classn"),
	   ID_EVENT_COST("event_cost" ),
	   ID_EVENT_LOCN("event_locn"),
	   ID_EVENT_LOCN_LAT("event_locn_lat"),
	   ID_EVENT_LOCN_LON("event_locn_lon"),
	   ID_EVENT_LOCN_TXT("event_locn_txt"),
	   ID_FY("fy"),
	   ID_HT_ID("ht_id"),
	   ID_INJ_CLASSNC("inj_classnc"),
	   ID_INVOLVED_ACFT("involved_acft"),
	   ID_INVOLVED_UNIT("involved_unit"),
	   ID_KEY_WORDS("key_words"),
	   ID_LOC("loc"),
	   ID_MISH_DATE("mish_date"),
	   ID_MISH_T("mish_t"),
	   ID_NARRATIVE("narrative"),
	   ID_NON_DOD_PROP_COST("non_dod_prop_cost" ),
	   ID_NOT_COUNTD_RSN_C("not_countd_rsn_c"),
	   ID_ON_BASE_I("on_base_i"),
	   ID_PRIV_NARR("priv_narr"),
	   ID_QRTR("qrtr"),
	   ID_REPORTING_UNIT("reporting_unit"),
	   ID_REPORT_CATG_C("report_catg_c"),
	   ID_REPORT_TYPE("report_type"),
	   ID_RPRTBL_EVENT_SERL("rprtbl_event_serl"),
	   ID_RPRT_DATE("rprt_date"),
	   ID_RPT_CATG("rpt_catg"),
	   ID_RPT_CLASS("rpt_class"),
	   ID_RPT_CLASS_DESC("rpt_class_desc"),
	   ID_RPT_T("rpt_t"),
	   ID_RPT_T_DESC("rpt_t_desc"),
	   ID_SECTION("section"),
	   ID_SHORT_NARR("short_narr"),
	   ID_SHOW_IN_SEARCH_EDIT("show_in_search_edit"),
	   ID_SHOW_IN_SEARCH_TOOL("show_in_search_tool"),
	   ID_STATE("state"),
	   ID_TAGS("tags"),
	   ;


	private final String  ident;
	private SolrDocFieldId(String ident) {
		this.ident = ident;
	}
	
	public String getIdentifier() {
		return ident;
	}
}