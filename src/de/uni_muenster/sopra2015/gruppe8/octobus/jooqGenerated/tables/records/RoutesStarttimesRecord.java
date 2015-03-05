/**
 * This class is generated by jOOQ
 */
package de.uni_muenster.sopra2015.gruppe8.octobus.jooqGenerated.tables.records;

/**
 * This class is generated by jOOQ.
 */
@javax.annotation.Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.5.3"
	},
	comments = "This class is generated by jOOQ"
)
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class RoutesStarttimesRecord extends org.jooq.impl.UpdatableRecordImpl<de.uni_muenster.sopra2015.gruppe8.octobus.jooqGenerated.tables.records.RoutesStarttimesRecord> implements org.jooq.Record4<java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.Integer> {

	private static final long serialVersionUID = 1829732025;

	/**
	 * Setter for <code>routes_startTimes.routes_startTimes_id</code>.
	 */
	public void setRoutesStarttimesId(java.lang.Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>routes_startTimes.routes_startTimes_id</code>.
	 */
	public java.lang.Integer getRoutesStarttimesId() {
		return (java.lang.Integer) getValue(0);
	}

	/**
	 * Setter for <code>routes_startTimes.routes_id</code>.
	 */
	public void setRoutesId(java.lang.Integer value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>routes_startTimes.routes_id</code>.
	 */
	public java.lang.Integer getRoutesId() {
		return (java.lang.Integer) getValue(1);
	}

	/**
	 * Setter for <code>routes_startTimes.dayOfWeek</code>.
	 */
	public void setDayofweek(java.lang.String value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>routes_startTimes.dayOfWeek</code>.
	 */
	public java.lang.String getDayofweek() {
		return (java.lang.String) getValue(2);
	}

	/**
	 * Setter for <code>routes_startTimes.startTime</code>.
	 */
	public void setStarttime(java.lang.Integer value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>routes_startTimes.startTime</code>.
	 */
	public java.lang.Integer getStarttime() {
		return (java.lang.Integer) getValue(3);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Record1<java.lang.Integer> key() {
		return (org.jooq.Record1) super.key();
	}

	// -------------------------------------------------------------------------
	// Record4 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row4<java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.Integer> fieldsRow() {
		return (org.jooq.Row4) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row4<java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.Integer> valuesRow() {
		return (org.jooq.Row4) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field1() {
		return de.uni_muenster.sopra2015.gruppe8.octobus.jooqGenerated.tables.RoutesStarttimes.ROUTES_STARTTIMES.ROUTES_STARTTIMES_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field2() {
		return de.uni_muenster.sopra2015.gruppe8.octobus.jooqGenerated.tables.RoutesStarttimes.ROUTES_STARTTIMES.ROUTES_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field3() {
		return de.uni_muenster.sopra2015.gruppe8.octobus.jooqGenerated.tables.RoutesStarttimes.ROUTES_STARTTIMES.DAYOFWEEK;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field4() {
		return de.uni_muenster.sopra2015.gruppe8.octobus.jooqGenerated.tables.RoutesStarttimes.ROUTES_STARTTIMES.STARTTIME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Integer value1() {
		return getRoutesStarttimesId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Integer value2() {
		return getRoutesId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value3() {
		return getDayofweek();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Integer value4() {
		return getStarttime();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RoutesStarttimesRecord value1(java.lang.Integer value) {
		setRoutesStarttimesId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RoutesStarttimesRecord value2(java.lang.Integer value) {
		setRoutesId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RoutesStarttimesRecord value3(java.lang.String value) {
		setDayofweek(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RoutesStarttimesRecord value4(java.lang.Integer value) {
		setStarttime(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RoutesStarttimesRecord values(java.lang.Integer value1, java.lang.Integer value2, java.lang.String value3, java.lang.Integer value4) {
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached RoutesStarttimesRecord
	 */
	public RoutesStarttimesRecord() {
		super(de.uni_muenster.sopra2015.gruppe8.octobus.jooqGenerated.tables.RoutesStarttimes.ROUTES_STARTTIMES);
	}

	/**
	 * Create a detached, initialised RoutesStarttimesRecord
	 */
	public RoutesStarttimesRecord(java.lang.Integer routesStarttimesId, java.lang.Integer routesId, java.lang.String dayofweek, java.lang.Integer starttime) {
		super(de.uni_muenster.sopra2015.gruppe8.octobus.jooqGenerated.tables.RoutesStarttimes.ROUTES_STARTTIMES);

		setValue(0, routesStarttimesId);
		setValue(1, routesId);
		setValue(2, dayofweek);
		setValue(3, starttime);
	}
}