/**
 * This class is generated by jOOQ
 */
package de.uni_muenster.sopra2015.gruppe8.octobus.jooqGenerated.tables;

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
public class Buses extends org.jooq.impl.TableImpl<de.uni_muenster.sopra2015.gruppe8.octobus.jooqGenerated.tables.records.BusesRecord> {

	private static final long serialVersionUID = 459303662;

	/**
	 * The reference instance of <code>buses</code>
	 */
	public static final de.uni_muenster.sopra2015.gruppe8.octobus.jooqGenerated.tables.Buses BUSES = new de.uni_muenster.sopra2015.gruppe8.octobus.jooqGenerated.tables.Buses();

	/**
	 * The class holding records for this type
	 */
	@Override
	public java.lang.Class<de.uni_muenster.sopra2015.gruppe8.octobus.jooqGenerated.tables.records.BusesRecord> getRecordType() {
		return de.uni_muenster.sopra2015.gruppe8.octobus.jooqGenerated.tables.records.BusesRecord.class;
	}

	/**
	 * The column <code>buses.buses_id</code>.
	 */
	public final org.jooq.TableField<de.uni_muenster.sopra2015.gruppe8.octobus.jooqGenerated.tables.records.BusesRecord, java.lang.Integer> BUSES_ID = createField("buses_id", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>buses.licencePlate</code>.
	 */
	public final org.jooq.TableField<de.uni_muenster.sopra2015.gruppe8.octobus.jooqGenerated.tables.records.BusesRecord, java.lang.String> LICENCEPLATE = createField("licencePlate", org.jooq.impl.SQLDataType.CLOB.length(10).nullable(false), this, "");

	/**
	 * The column <code>buses.numberOfSeats</code>.
	 */
	public final org.jooq.TableField<de.uni_muenster.sopra2015.gruppe8.octobus.jooqGenerated.tables.records.BusesRecord, java.lang.Integer> NUMBEROFSEATS = createField("numberOfSeats", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>buses.standingRoom</code>.
	 */
	public final org.jooq.TableField<de.uni_muenster.sopra2015.gruppe8.octobus.jooqGenerated.tables.records.BusesRecord, java.lang.Integer> STANDINGROOM = createField("standingRoom", org.jooq.impl.SQLDataType.INTEGER, this, "");

	/**
	 * The column <code>buses.manufacturer</code>.
	 */
	public final org.jooq.TableField<de.uni_muenster.sopra2015.gruppe8.octobus.jooqGenerated.tables.records.BusesRecord, java.lang.String> MANUFACTURER = createField("manufacturer", org.jooq.impl.SQLDataType.CLOB.length(200), this, "");

	/**
	 * The column <code>buses.model</code>.
	 */
	public final org.jooq.TableField<de.uni_muenster.sopra2015.gruppe8.octobus.jooqGenerated.tables.records.BusesRecord, java.lang.String> MODEL = createField("model", org.jooq.impl.SQLDataType.CLOB.length(200), this, "");

	/**
	 * The column <code>buses.nextInspectionDue</code>.
	 */
	public final org.jooq.TableField<de.uni_muenster.sopra2015.gruppe8.octobus.jooqGenerated.tables.records.BusesRecord, java.lang.Integer> NEXTINSPECTIONDUE = createField("nextInspectionDue", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "");

	/**
	 * The column <code>buses.articulatedBus</code>.
	 */
	public final org.jooq.TableField<de.uni_muenster.sopra2015.gruppe8.octobus.jooqGenerated.tables.records.BusesRecord, java.lang.Boolean> ARTICULATEDBUS = createField("articulatedBus", org.jooq.impl.SQLDataType.BOOLEAN.nullable(false), this, "");

	/**
	 * Create a <code>buses</code> table reference
	 */
	public Buses() {
		this("buses", null);
	}

	/**
	 * Create an aliased <code>buses</code> table reference
	 */
	public Buses(java.lang.String alias) {
		this(alias, de.uni_muenster.sopra2015.gruppe8.octobus.jooqGenerated.tables.Buses.BUSES);
	}

	private Buses(java.lang.String alias, org.jooq.Table<de.uni_muenster.sopra2015.gruppe8.octobus.jooqGenerated.tables.records.BusesRecord> aliased) {
		this(alias, aliased, null);
	}

	private Buses(java.lang.String alias, org.jooq.Table<de.uni_muenster.sopra2015.gruppe8.octobus.jooqGenerated.tables.records.BusesRecord> aliased, org.jooq.Field<?>[] parameters) {
		super(alias, de.uni_muenster.sopra2015.gruppe8.octobus.jooqGenerated.DefaultSchema.DEFAULT_SCHEMA, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.Identity<de.uni_muenster.sopra2015.gruppe8.octobus.jooqGenerated.tables.records.BusesRecord, java.lang.Integer> getIdentity() {
		return de.uni_muenster.sopra2015.gruppe8.octobus.jooqGenerated.Keys.IDENTITY_BUSES;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public org.jooq.UniqueKey<de.uni_muenster.sopra2015.gruppe8.octobus.jooqGenerated.tables.records.BusesRecord> getPrimaryKey() {
		return de.uni_muenster.sopra2015.gruppe8.octobus.jooqGenerated.Keys.PK_BUSES;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public java.util.List<org.jooq.UniqueKey<de.uni_muenster.sopra2015.gruppe8.octobus.jooqGenerated.tables.records.BusesRecord>> getKeys() {
		return java.util.Arrays.<org.jooq.UniqueKey<de.uni_muenster.sopra2015.gruppe8.octobus.jooqGenerated.tables.records.BusesRecord>>asList(de.uni_muenster.sopra2015.gruppe8.octobus.jooqGenerated.Keys.PK_BUSES);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public de.uni_muenster.sopra2015.gruppe8.octobus.jooqGenerated.tables.Buses as(java.lang.String alias) {
		return new de.uni_muenster.sopra2015.gruppe8.octobus.jooqGenerated.tables.Buses(alias, this);
	}

	/**
	 * Rename this table
	 */
	public de.uni_muenster.sopra2015.gruppe8.octobus.jooqGenerated.tables.Buses rename(java.lang.String name) {
		return new de.uni_muenster.sopra2015.gruppe8.octobus.jooqGenerated.tables.Buses(name, null);
	}
}
