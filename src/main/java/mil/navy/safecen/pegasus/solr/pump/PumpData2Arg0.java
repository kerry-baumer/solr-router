/**
 * PumpData2Arg0.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package mil.navy.safecen.pegasus.solr.pump;

public class PumpData2Arg0  implements java.io.Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private java.lang.String summary_id;
    
    private java.lang.String report_serl;

    private java.lang.String report_type;

    private java.lang.String status;

    private java.lang.String action;

    private java.util.Calendar created;

    private java.lang.String record_id;  // attribute

    private boolean is_legacy;  // attribute

    public PumpData2Arg0() {
    }

    public PumpData2Arg0(
           java.lang.String summary_id,
           java.lang.String report_type,
           java.lang.String status,
           java.lang.String action,
           java.util.Calendar created,
           java.lang.String record_id,
           boolean is_legacy) {
           this.summary_id = summary_id;
           this.report_type = report_type;
           this.status = status;
           this.action = action;
           this.created = created;
           this.record_id = record_id;
           this.is_legacy = is_legacy;
    }


    /**
     * Gets the summary_id value for this PumpData2Arg0.
     * 
     * @return summary_id
     */
    public java.lang.String getSummary_id() {
        return summary_id;
    }


    /**
     * Sets the summary_id value for this PumpData2Arg0.
     * 
     * @param summary_id
     */
    public void setSummary_id(java.lang.String summary_id) {
        this.summary_id = summary_id;
    }


    /**
     * Gets the report_type value for this PumpData2Arg0.
     * 
     * @return report_type
     */
    public java.lang.String getReport_type() {
        return report_type;
    }


    /**
     * Sets the report_type value for this PumpData2Arg0.
     * 
     * @param report_type
     */
    public void setReport_type(java.lang.String report_type) {
        this.report_type = report_type;
    }


    /**
     * Gets the status value for this PumpData2Arg0.
     * 
     * @return status
     */
    public java.lang.String getStatus() {
        return status;
    }


    /**
     * Sets the status value for this PumpData2Arg0.
     * 
     * @param status
     */
    public void setStatus(java.lang.String status) {
        this.status = status;
    }


    /**
     * Gets the action value for this PumpData2Arg0.
     * 
     * @return action
     */
    public java.lang.String getAction() {
        return action;
    }


    /**
     * Sets the action value for this PumpData2Arg0.
     * 
     * @param action
     */
    public void setAction(java.lang.String action) {
        this.action = action;
    }


    /**
     * Gets the created value for this PumpData2Arg0.
     * 
     * @return created
     */
    public java.util.Calendar getCreated() {
        return created;
    }


    /**
     * Sets the created value for this PumpData2Arg0.
     * 
     * @param created
     */
    public void setCreated(java.util.Calendar created) {
        this.created = created;
    }


    /**
     * Gets the record_id value for this PumpData2Arg0.
     * 
     * @return record_id
     */
    public java.lang.String getRecord_id() {
        return record_id;
    }


    /**
     * Sets the record_id value for this PumpData2Arg0.
     * 
     * @param record_id
     */
    public void setRecord_id(java.lang.String record_id) {
        this.record_id = record_id;
    }


    /**
     * Gets the is_legacy value for this PumpData2Arg0.
     * 
     * @return is_legacy
     */
    public boolean isIs_legacy() {
        return is_legacy;
    }


    /**
     * Sets the is_legacy value for this PumpData2Arg0.
     * 
     * @param is_legacy
     */
    public void setIs_legacy(boolean is_legacy) {
        this.is_legacy = is_legacy;
    }

    public java.lang.String getReport_serl() {
		return report_serl;
	}

	public void setReport_serl(java.lang.String report_serl) {
		this.report_serl = report_serl;
	}

	private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
    	if (obj == null) return false;
        if (!(obj instanceof PumpData2Arg0)) return false;
        PumpData2Arg0 other = (PumpData2Arg0) obj;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.summary_id==null && other.getSummary_id()==null) || 
             (this.summary_id!=null &&
              this.summary_id.equals(other.getSummary_id()))) &&
            ((this.report_type==null && other.getReport_type()==null) || 
             (this.report_type!=null &&
              this.report_type.equals(other.getReport_type()))) &&
            ((this.status==null && other.getStatus()==null) || 
             (this.status!=null &&
              this.status.equals(other.getStatus()))) &&
            ((this.action==null && other.getAction()==null) || 
             (this.action!=null &&
              this.action.equals(other.getAction()))) &&
            ((this.created==null && other.getCreated()==null) || 
             (this.created!=null &&
              this.created.equals(other.getCreated()))) &&
            ((this.record_id==null && other.getRecord_id()==null) || 
             (this.record_id!=null &&
              this.record_id.equals(other.getRecord_id()))) &&
            this.is_legacy == other.isIs_legacy();
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getSummary_id() != null) {
            _hashCode += getSummary_id().hashCode();
        }
        if (getReport_type() != null) {
            _hashCode += getReport_type().hashCode();
        }
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        if (getAction() != null) {
            _hashCode += getAction().hashCode();
        }
        if (getCreated() != null) {
            _hashCode += getCreated().hashCode();
        }
        if (getRecord_id() != null) {
            _hashCode += getRecord_id().hashCode();
        }
        _hashCode += (isIs_legacy() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(PumpData2Arg0.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://pump.solr.pegasus.safecen.navy.mil/", ">pumpData2>arg0"));
        org.apache.axis.description.AttributeDesc attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("record_id");
        attrField.setXmlName(new javax.xml.namespace.QName("", "record_id"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        typeDesc.addFieldDesc(attrField);
        attrField = new org.apache.axis.description.AttributeDesc();
        attrField.setFieldName("is_legacy");
        attrField.setXmlName(new javax.xml.namespace.QName("", "is_legacy"));
        attrField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        typeDesc.addFieldDesc(attrField);
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("summary_id");
        elemField.setXmlName(new javax.xml.namespace.QName("http://pump.solr.pegasus.safecen.navy.mil/", "summary_id"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("report_type");
        elemField.setXmlName(new javax.xml.namespace.QName("http://pump.solr.pegasus.safecen.navy.mil/", "report_type"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("http://pump.solr.pegasus.safecen.navy.mil/", "status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("action");
        elemField.setXmlName(new javax.xml.namespace.QName("http://pump.solr.pegasus.safecen.navy.mil/", "action"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("created");
        elemField.setXmlName(new javax.xml.namespace.QName("http://pump.solr.pegasus.safecen.navy.mil/", "created"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "dateTime"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class<?> _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class<?> _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
