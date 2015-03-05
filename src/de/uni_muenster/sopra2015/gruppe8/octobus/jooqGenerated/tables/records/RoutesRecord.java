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
public class RoutesRecord extends org.jooq.impl.UpdatableRecordImpl<de.uni_muenster.sopra2015.gruppe8.octobus.jooqGenerated.tables.records.RoutesRecord> implements org.jooq.Record4<java.lang.Integer, java.lang.String, java.lang.String, java.lang.Boolean> {

	private static final long serialVersionUID = -1149174940;

	/**
	 * Setter for <code>routes.routes_id</code>.
	 */
	public void setRoutesId(java.lang.Integer value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>routes.routes_id</code>.
	 */
	public java.lang.Integer getRoutesId() {
		return (java.lang.Integer) getValue(0);
	}

	/**
	 * Setter for <code>routes.name</code>.
	 */
	public void setName(java.lang.String value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>routes.name</code>.
	 */
	public java.lang.String getName() {
		return (java.lang.String) getValue(1);
	}

	/**
	 * Setter for <code>routes.note</code>.
	 */
	public void setNote(java.lang.String value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>routes.note</code>.
	 */
	public java.lang.String getNote() {
		return (java.lang.String) getValue(2);
	}

	/**
	 * Setter for <code>routes.night</code>.
	 */
	public void setNight(java.lang.Boolean value) {
		setValue(3, value);
	}

	/**
	 * Getter for <code>routes.night</code>.
	 */
	public java.lang.Boolean getNight() {
		return (java.lang.Boolean) getValue(3);
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
	public org.jooq.Row4<java.lang.Integer, java.lang.String, java.lang.String, java.lang.Boolean> fieldsRow() {
		return (org.jooq.Row4) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Row4<java.lang.Integer, java.lang.String, java.lang.String, java.lang.Boolean> valuesRow() {
		return (org.jooq.Row4) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Integer> field1() {
		return de.uni_muenster.sopra2015.gruppe8.octobus.jooqGenerated.tables.Routes.ROUTES.ROUTES_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field2() {
		return de.uni_muenster.sopra2015.gruppe8.octobus.jooqGenerated.tables.Routes.ROUTES.NAME;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.String> field3() {
		return de.uni_muenster.sopra2015.gruppe8.octobus.jooqGenerated.tables.Routes.ROUTES.NOTE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Field<java.lang.Boolean> field4() {
		return de.uni_muenster.sopra2015.gruppe8.octobus.jooqGenerated.tables.Routes.ROUTES.NIGHT;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Integer value1() {
		return getRoutesId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value2() {
		return getName();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.String value3() {
		return getNote();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.lang.Boolean value4() {
		return getNight();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RoutesRecord value1(java.lang.Integer value) {
		setRoutesId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RoutesRecord value2(java.lang.String value) {
		setName(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RoutesRecord value3(java.lang.String value) {
		setNote(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RoutesRecord value4(java.lang.Boolean value) {
		setNight(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RoutesRecord values(java.lang.Integer value1, java.lang.String value2, java.lang.String value3, java.lang.Boolean value4) {
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached RoutesRecord
	 */
	public RoutesRecord() {
		super(de.uni_muenster.sopra2015.gruppe8.octobus.jooqGenerated.tables.Routes.ROUTES);
	}

	/**
	 * Create a detached, initialised RoutesRecord
	 */
	public RoutesRecord(java.lang.Integer routesId, java.lang.String name, java.lang.String note, java.lang.Boolean night) {
		super(de.uni_muenster.sopra2015.gruppe8.octobus.jooqGenerated.tables.Routes.ROUTES);

		setValue(0, routesId);
		setValue(1, name);
		setValue(2, note);
		setValue(3, night);
	}
}