/**
 * Autogenerated by Avro
 * 
 * DO NOT EDIT DIRECTLY
 */
package org.radarcns.phoneSensors;  
@SuppressWarnings("all")
/** Data from log sent and received text messages. */
@org.apache.avro.specific.AvroGenerated
public class PhoneSensorSms extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"PhoneSensorSms\",\"namespace\":\"org.radarcns.phoneSensors\",\"doc\":\"Data from log sent and received text messages.\",\"fields\":[{\"name\":\"time\",\"type\":\"double\",\"doc\":\"device timestamp in UTC (s)\"},{\"name\":\"timeReceived\",\"type\":\"double\",\"doc\":\"device receiver timestamp in UTC (s)\"},{\"name\":\"target\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"},\"doc\":\"SHA-256 one-way source/target of the text\"},{\"name\":\"sms_type\",\"type\":\"int\",\"doc\":\"1-Incoming, 2-Outgoing, 3-Other\"}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }
  /** device timestamp in UTC (s) */
  @Deprecated public double time;
  /** device receiver timestamp in UTC (s) */
  @Deprecated public double timeReceived;
  /** SHA-256 one-way source/target of the text */
  @Deprecated public java.lang.String target;
  /** 1-Incoming, 2-Outgoing, 3-Other */
  @Deprecated public int sms_type;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>. 
   */
  public PhoneSensorSms() {}

  /**
   * All-args constructor.
   */
  public PhoneSensorSms(java.lang.Double time, java.lang.Double timeReceived, java.lang.String target, java.lang.Integer sms_type) {
    this.time = time;
    this.timeReceived = timeReceived;
    this.target = target;
    this.sms_type = sms_type;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call. 
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return time;
    case 1: return timeReceived;
    case 2: return target;
    case 3: return sms_type;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }
  // Used by DatumReader.  Applications should not call. 
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: time = (java.lang.Double)value$; break;
    case 1: timeReceived = (java.lang.Double)value$; break;
    case 2: target = (java.lang.String)value$; break;
    case 3: sms_type = (java.lang.Integer)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'time' field.
   * device timestamp in UTC (s)   */
  public java.lang.Double getTime() {
    return time;
  }

  /**
   * Sets the value of the 'time' field.
   * device timestamp in UTC (s)   * @param value the value to set.
   */
  public void setTime(java.lang.Double value) {
    this.time = value;
  }

  /**
   * Gets the value of the 'timeReceived' field.
   * device receiver timestamp in UTC (s)   */
  public java.lang.Double getTimeReceived() {
    return timeReceived;
  }

  /**
   * Sets the value of the 'timeReceived' field.
   * device receiver timestamp in UTC (s)   * @param value the value to set.
   */
  public void setTimeReceived(java.lang.Double value) {
    this.timeReceived = value;
  }

  /**
   * Gets the value of the 'target' field.
   * SHA-256 one-way source/target of the text   */
  public java.lang.String getTarget() {
    return target;
  }

  /**
   * Sets the value of the 'target' field.
   * SHA-256 one-way source/target of the text   * @param value the value to set.
   */
  public void setTarget(java.lang.String value) {
    this.target = value;
  }

  /**
   * Gets the value of the 'sms_type' field.
   * 1-Incoming, 2-Outgoing, 3-Other   */
  public java.lang.Integer getSmsType() {
    return sms_type;
  }

  /**
   * Sets the value of the 'sms_type' field.
   * 1-Incoming, 2-Outgoing, 3-Other   * @param value the value to set.
   */
  public void setSmsType(java.lang.Integer value) {
    this.sms_type = value;
  }

  /** Creates a new PhoneSensorSms RecordBuilder */
  public static org.radarcns.phoneSensors.PhoneSensorSms.Builder newBuilder() {
    return new org.radarcns.phoneSensors.PhoneSensorSms.Builder();
  }
  
  /** Creates a new PhoneSensorSms RecordBuilder by copying an existing Builder */
  public static org.radarcns.phoneSensors.PhoneSensorSms.Builder newBuilder(org.radarcns.phoneSensors.PhoneSensorSms.Builder other) {
    return new org.radarcns.phoneSensors.PhoneSensorSms.Builder(other);
  }
  
  /** Creates a new PhoneSensorSms RecordBuilder by copying an existing PhoneSensorSms instance */
  public static org.radarcns.phoneSensors.PhoneSensorSms.Builder newBuilder(org.radarcns.phoneSensors.PhoneSensorSms other) {
    return new org.radarcns.phoneSensors.PhoneSensorSms.Builder(other);
  }
  
  /**
   * RecordBuilder for PhoneSensorSms instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<PhoneSensorSms>
    implements org.apache.avro.data.RecordBuilder<PhoneSensorSms> {

    private double time;
    private double timeReceived;
    private java.lang.String target;
    private int sms_type;

    /** Creates a new Builder */
    private Builder() {
      super(org.radarcns.phoneSensors.PhoneSensorSms.SCHEMA$);
    }
    
    /** Creates a Builder by copying an existing Builder */
    private Builder(org.radarcns.phoneSensors.PhoneSensorSms.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.time)) {
        this.time = data().deepCopy(fields()[0].schema(), other.time);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.timeReceived)) {
        this.timeReceived = data().deepCopy(fields()[1].schema(), other.timeReceived);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.target)) {
        this.target = data().deepCopy(fields()[2].schema(), other.target);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.sms_type)) {
        this.sms_type = data().deepCopy(fields()[3].schema(), other.sms_type);
        fieldSetFlags()[3] = true;
      }
    }
    
    /** Creates a Builder by copying an existing PhoneSensorSms instance */
    private Builder(org.radarcns.phoneSensors.PhoneSensorSms other) {
            super(org.radarcns.phoneSensors.PhoneSensorSms.SCHEMA$);
      if (isValidValue(fields()[0], other.time)) {
        this.time = data().deepCopy(fields()[0].schema(), other.time);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.timeReceived)) {
        this.timeReceived = data().deepCopy(fields()[1].schema(), other.timeReceived);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.target)) {
        this.target = data().deepCopy(fields()[2].schema(), other.target);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.sms_type)) {
        this.sms_type = data().deepCopy(fields()[3].schema(), other.sms_type);
        fieldSetFlags()[3] = true;
      }
    }

    /** Gets the value of the 'time' field */
    public java.lang.Double getTime() {
      return time;
    }
    
    /** Sets the value of the 'time' field */
    public org.radarcns.phoneSensors.PhoneSensorSms.Builder setTime(double value) {
      validate(fields()[0], value);
      this.time = value;
      fieldSetFlags()[0] = true;
      return this; 
    }
    
    /** Checks whether the 'time' field has been set */
    public boolean hasTime() {
      return fieldSetFlags()[0];
    }
    
    /** Clears the value of the 'time' field */
    public org.radarcns.phoneSensors.PhoneSensorSms.Builder clearTime() {
      fieldSetFlags()[0] = false;
      return this;
    }

    /** Gets the value of the 'timeReceived' field */
    public java.lang.Double getTimeReceived() {
      return timeReceived;
    }
    
    /** Sets the value of the 'timeReceived' field */
    public org.radarcns.phoneSensors.PhoneSensorSms.Builder setTimeReceived(double value) {
      validate(fields()[1], value);
      this.timeReceived = value;
      fieldSetFlags()[1] = true;
      return this; 
    }
    
    /** Checks whether the 'timeReceived' field has been set */
    public boolean hasTimeReceived() {
      return fieldSetFlags()[1];
    }
    
    /** Clears the value of the 'timeReceived' field */
    public org.radarcns.phoneSensors.PhoneSensorSms.Builder clearTimeReceived() {
      fieldSetFlags()[1] = false;
      return this;
    }

    /** Gets the value of the 'target' field */
    public java.lang.String getTarget() {
      return target;
    }
    
    /** Sets the value of the 'target' field */
    public org.radarcns.phoneSensors.PhoneSensorSms.Builder setTarget(java.lang.String value) {
      validate(fields()[2], value);
      this.target = value;
      fieldSetFlags()[2] = true;
      return this; 
    }
    
    /** Checks whether the 'target' field has been set */
    public boolean hasTarget() {
      return fieldSetFlags()[2];
    }
    
    /** Clears the value of the 'target' field */
    public org.radarcns.phoneSensors.PhoneSensorSms.Builder clearTarget() {
      target = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    /** Gets the value of the 'sms_type' field */
    public java.lang.Integer getSmsType() {
      return sms_type;
    }
    
    /** Sets the value of the 'sms_type' field */
    public org.radarcns.phoneSensors.PhoneSensorSms.Builder setSmsType(int value) {
      validate(fields()[3], value);
      this.sms_type = value;
      fieldSetFlags()[3] = true;
      return this; 
    }
    
    /** Checks whether the 'sms_type' field has been set */
    public boolean hasSmsType() {
      return fieldSetFlags()[3];
    }
    
    /** Clears the value of the 'sms_type' field */
    public org.radarcns.phoneSensors.PhoneSensorSms.Builder clearSmsType() {
      fieldSetFlags()[3] = false;
      return this;
    }

    @Override
    public PhoneSensorSms build() {
      try {
        PhoneSensorSms record = new PhoneSensorSms();
        record.time = fieldSetFlags()[0] ? this.time : (java.lang.Double) defaultValue(fields()[0]);
        record.timeReceived = fieldSetFlags()[1] ? this.timeReceived : (java.lang.Double) defaultValue(fields()[1]);
        record.target = fieldSetFlags()[2] ? this.target : (java.lang.String) defaultValue(fields()[2]);
        record.sms_type = fieldSetFlags()[3] ? this.sms_type : (java.lang.Integer) defaultValue(fields()[3]);
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }
}
