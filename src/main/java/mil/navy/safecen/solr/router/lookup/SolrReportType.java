/**
 * 
 */
package mil.navy.safecen.solr.router.lookup;

/**
 * @author kerry.baumer
 *
 */
public enum SolrReportType {
	AVIATION_MISHAP("Aviation Mishap", "3750"),
	AVIATION_HAZARD("Aviation Hazard", "3750"),
	AV_INITIAL_NOTIFICATION("Aviation Initial Notification", "3750"),
	CN_INITIAL_NOTIFICATION("Consolidated Initial Notification", "5102"),
	CN_HAZARD("Consolidated Hazard", "5102"),
	MV_MISHAP("Motor Vehicle Mishap", "5102"),
	AFLOAT_MISHAP("Afloat Mishap", "5102"),
	AFLOAT_HAZARD("Afloat Hazard", "5102"),
	ROD_MISHAP("Recreational/Off Duty Mishap", "5102"),
	ROD_HAZARD("Recreational/Off Duty Hazard", "5102"),
	SHORE_MISHAP("Shore/Ground Mishap", "5102"),
	SHORE_HAZARD("Shore/Ground Hazard", "5102");

	private final String description;
	private final String catgC;
	
	private SolrReportType(final String desc, final String catgC) {
		this.description = desc;
		this.catgC = catgC;
	}
	
	public String getCategory() {
		return catgC;
	}

	public String getDescription() {
		return description;
	}
}
