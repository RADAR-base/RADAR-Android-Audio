/**
 * Autogenerated by Avro
 * 
 * DO NOT EDIT DIRECTLY
 */
package org.radarcns.phoneSensors;  
@SuppressWarnings("all")
/** Data from the log of received and made calls. */
@org.apache.avro.specific.AvroGenerated
public class PhoneSensorCall extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"PhoneSensorCall\",\"namespace\":\"org.radarcns.phoneSensors\",\"doc\":\"Data from the log of received and made calls.\",\"fields\":[{\"name\":\"time\",\"type\":\"double\",\"doc\":\"device timestamp in UTC (s)\"},{\"name\":\"timeReceived\",\"type\":\"double\",\"doc\":\"device receiver timestamp in UTC (s)\"},{\"name\":\"duration\",\"type\":\"float\",\"doc\":\"duration of the call (s)\"},{\"name\":\"target\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"},\"doc\":\"SHA-256 one-way source/target of the call\"},{\"name\":\"call_type\",\"type\":\"int\",\"doc\":\"1-Incoming, 2-Outgoing, 3-Unanswered\"}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }
  /** device timestamp in UTC (s) */
  @Deprecated public double time;
  /** device receiver timestamp in UTC (s) */
  @Deprecated public double timeReceived;
  /** duration of the call (s) */
  @Deprecated public float duration;
  /** SHA-256 one-way source/target of the call */
  @Deprecated public java.lang.String target;
  /** 1-Incoming, 2-Outgoing, 3-Unanswered */
  @Deprecated public int call_type;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>. 
   */
  public PhoneSensorCall() {}

  /**
   * All-args constructor.
   */
  public PhoneSensorCall(java.lang.Double time, java.lang.Double timeReceived, java.lang.Float duration, java.lang.String target, java.lang.Integer call_type) {
    this.time = time;
    this.timeReceived = timeReceived;
    this.duration = duration;
    this.target = target;
    this.call_type = call_type;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call. 
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return time;
    case 1: return timeReceived;
    case 2: return duration;
    case 3: return target;
    case 4: return call_type;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }
  // Used by DatumReader.  Applications should not call. 
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: time = (java.lang.Double)value$; break;
    case 1: timeReceived = (java.lang.Double)value$; break;
    case 2: duration = (java.lang.Float)value$; break;
    case 3: target = (java.lang.String)value$; break;
    case 4: call_type = (java.lang.Integer)value$; break;
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
   * Gets the value of the 'duration' field.
   * duration of the call (s)   */
  public java.lang.Float getDuration() {
    return duration;
  }

  /**
   * Sets the value of the 'duration' field.
   * duration of the call (s)   * @param value the value to set.
   */
  public void setDuration(java.lang.Float value) {
    this.duration = value;
  }

  /**
   * Gets the value of the 'target' field.
   * SHA-256 one-way source/target of the call   */
  public java.lang.String getTarget() {
    return target;
  }

  /**
   * Sets the value of the 'target' field.
   * SHA-256 one-way source/target of the call   * @param value the value to set.
   */
  public void setTarget(java.lang.String value) {
    this.target = value;
  }

  /**
   * Gets the value of the 'call_type' field.
   * 1-Incoming, 2-Outgoing, 3-Unanswered   */
  public java.lang.Integer getCallType() {
    return call_type;
  }

  /**
   * Sets the value of the 'call_type' field.
   * 1-Incoming, 2-Outgoing, 3-Unanswered   * @param value the value to set.
   */
  public void setCallType(java.lang.Integer value) {
    this.call_type = value;
  }

  /** Creates a new PhoneSensorCall RecordBuilder */
  public static org.radarcns.phoneSensors.PhoneSensorCall.Builder newBuilder() {
    return new org.radarcns.phoneSensors.PhoneSensorCall.Builder();
  }
  
  /** Creates a new PhoneSensorCall RecordBuilder by copying an existing Builder */
  public static org.radarcns.phoneSensors.PhoneSensorCall.Builder newBuilder(org.radarcns.phoneSensors.PhoneSensorCall.Builder other) {
    return new org.radarcns.phoneSensors.PhoneSensorCall.Builder(other);
  }
  
  /** Creates a new PhoneSensorCall RecordBuilder by copying an existing PhoneSensorCall instance */
  public static org.radarcns.phoneSensors.PhoneSensorCall.Builder newBuilder(org.radarcns.phoneSensors.PhoneSensorCall other) {
    return new org.radarcns.phoneSensors.PhoneSensorCall.Builder(other);
  }
  
  /**
   * RecordBuilder for PhoneSensorCall instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<PhoneSensorCall>
    implements org.apache.avro.data.RecordBuilder<PhoneSensorCall> {

    private double time;
    private double timeReceived;
    private float duration;
    private java.lang.String target;
    private int call_type;

    /** Creates a new Builder */
    private Builder() {
      super(org.radarcns.phoneSensors.PhoneSensorCall.SCHEMA$);
    }
    
    /** Creates a Builder by copying an existing Builder */
    private Builder(org.radarcns.phoneSensors.PhoneSensorCall.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.time)) {
        this.time = data().deepCopy(fields()[0].schema(), other.time);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.timeReceived)) {
        this.timeReceived = data().deepCopy(fields()[1].schema(), other.timeReceived);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.duration)) {
        this.duration = data().deepCopy(fields()[2].schema(), other.duration);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.target)) {
        this.target = data().deepCopy(fields()[3].schema(), other.target);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.call_type)) {
        this.call_type = data().deepCopy(fields()[4].schema(), other.call_type);
        fieldSetFlags()[4] = true;
      }
    }
    
    /** Creates a Builder by copying an existing PhoneSensorCall instance */
    private Builder(org.radarcns.phoneSensors.PhoneSensorCall other) {
            super(org.radarcns.phoneSensors.PhoneSensorCall.SCHEMA$);
      if (isValidValue(fields()[0], other.time)) {
        this.time = data().deepCopy(fields()[0].schema(), other.time);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.timeReceived)) {
        this.timeReceived = data().deepCopy(fields()[1].schema(), other.timeReceived);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.duration)) {
        this.duration = data().deepCopy(fields()[2].schema(), other.duration);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.target)) {
        this.target = data().deepCopy(fields()[3].schema(), other.target);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.call_type)) {
        this.call_type = data().deepCopy(fields()[4].schema(), other.call_type);
        fieldSetFlags()[4] = true;
      }
    }

    /** Gets the value of the 'time' field */
    public java.lang.Double getTime() {
      return time;
    }
    
    /** Sets the value of the 'time' field */
    public org.radarcns.phoneSensors.PhoneSensorCall.Builder setTime(double value) {
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
    public org.radarcns.phoneSensors.PhoneSensorCall.Builder clearTime() {
      fieldSetFlags()[0] = false;
      return this;
    }

    /** Gets the value of the 'timeReceived' field */
    public java.lang.Double getTimeReceived() {
      return timeReceived;
    }
    
    /** Sets the value of the 'timeReceived' field */
    public org.radarcns.phoneSensors.PhoneSensorCall.Builder setTimeReceived(double value) {
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
    public org.radarcns.phoneSensors.PhoneSensorCall.Builder clearTimeReceived() {
      fieldSetFlags()[1] = false;
      return this;
    }

    /** Gets the value of the 'duration' field */
    public java.lang.Float getDuration() {
      return duration;
    }
    
    /** Sets the value of the 'duration' field */
    public org.radarcns.phoneSensors.PhoneSensorCall.Builder setDuration(float value) {
      validate(fields()[2], value);
      this.duration = value;
      fieldSetFlags()[2] = true;
      return this; 
    }
    
    /** Checks whether the 'duration' field has been set */
    public boolean hasDuration() {
      return fieldSetFlags()[2];
    }
    
    /** Clears the value of the 'duration' field */
    public org.radarcns.phoneSensors.PhoneSensorCall.Builder clearDuration() {
      fieldSetFlags()[2] = false;
      return this;
    }

    /** Gets the value of the 'target' field */
    public java.lang.String getTarget() {
      return target;
    }
    
    /** Sets the value of the 'target' field */
    public org.radarcns.phoneSensors.PhoneSensorCall.Builder setTarget(java.lang.String value) {
      validate(fields()[3], value);
      this.target = value;
      fieldSetFlags()[3] = true;
      return this; 
    }
    
    /** Checks whether the 'target' field has been set */
    public boolean hasTarget() {
      return fieldSetFlags()[3];
    }
    
    /** Clears the value of the 'target' field */
    public org.radarcns.phoneSensors.PhoneSensorCall.Builder clearTarget() {
      target = null;
      fieldSetFlags()[3] = false;
      return this;
    }

    /** Gets the value of the 'call_type' field */
    public java.lang.Integer getCallType() {
      return call_type;
    }
    
    /** Sets the value of the 'call_type' field */
    public org.radarcns.phoneSensors.PhoneSensorCall.Builder setCallType(int value) {
      validate(fields()[4], value);
      this.call_type = value;
      fieldSetFlags()[4] = true;
      return this; 
    }
    
    /** Checks whether the 'call_type' field has been set */
    public boolean hasCallType() {
      return fieldSetFlags()[4];
    }
    
    /** Clears the value of the 'call_type' field */
    public org.radarcns.phoneSensors.PhoneSensorCall.Builder clearCallType() {
      fieldSetFlags()[4] = false;
      return this;
    }

    @Override
    public PhoneSensorCall build() {
      try {
        PhoneSensorCall record = new PhoneSensorCall();
        record.time = fieldSetFlags()[0] ? this.time : (java.lang.Double) defaultValue(fields()[0]);
        record.timeReceived = fieldSetFlags()[1] ? this.timeReceived : (java.lang.Double) defaultValue(fields()[1]);
        record.duration = fieldSetFlags()[2] ? this.duration : (java.lang.Float) defaultValue(fields()[2]);
        record.target = fieldSetFlags()[3] ? this.target : (java.lang.String) defaultValue(fields()[3]);
        record.call_type = fieldSetFlags()[4] ? this.call_type : (java.lang.Integer) defaultValue(fields()[4]);
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }
}
