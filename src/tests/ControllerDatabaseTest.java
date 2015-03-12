package tests;

import de.uni_muenster.sopra2015.gruppe8.octobus.controller.ControllerDatabase;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.Bus;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.Employee;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.Role;
import de.uni_muenster.sopra2015.gruppe8.octobus.model.Triple;
import org.jooq.exception.DataAccessException;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.io.File;
import java.util.Date;
import java.util.HashSet;

import static org.junit.Assert.*;

/**
 * @author Michael Biech
 */

public class ControllerDatabaseTest
{
	private static final String DB_NAME = "test.db";
	private ControllerDatabase cdb;

	private Bus testBus;
	private Employee testEmployee;
	HashSet<Role> employeeRoles;

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	/**
	 * The reason we create a new database for each test is that we expect certain objects to
	 * have certain ids in the database. Yes, this slows down tests considerably. The alternative
	 * would be to force tests to run in a certain order, which doesn't sound very appealing either,
	 * since the order in which tests are run should not influence the outcome of each test!
	 */
	@Before
	public void setUp()
	{
		cdb = ControllerDatabase.getTestingInstance(DB_NAME);
		cdb.createDatabaseTables();

		// Inspection date is 11.03.2015 14:34:21 in UTC / GMT
		testBus = new Bus("MS-OB 1337", 100, 120, "MAN", "City Line 25", new Date(1426084461000l), false);

		employeeRoles = new HashSet<>();
		employeeRoles.add(Role.BUSDRIVER);
		// Birthdate is 23.06.1962 09:55:57 in UTC / GMT
		testEmployee = new Employee(
				"Schwakowiak",
				"Herbert",
				"Katzenberger Allee 4",
				"012345",
				"Gelsenkirchen",
				new Date(-237477843000l),
				"0177 / 374934759",
				"h.schwacki@email.de",
				"h_schwa",
				"Häufige Beschwerden der Fahrgäste",
				employeeRoles);
	}

	@After
	public void tearDown() throws Exception
	{
		cdb.closeDB();
		new File(DB_NAME).delete();
	}

	@Test
	public void testBuses(){
		// Write bus to database and retrieve the resulting id
		testBus.setId(cdb.addBus(testBus));

		// Retrieve bus from database
		Bus retrievedBus = cdb.getBusById(1);

		// Make sure the bus is being retrieved properly from the database
		busEquals(testBus, retrievedBus);

		// Modify the bus into a completely different beast
		testBus.setLicencePlate("PQ-RT 4");
		testBus.setNumberOfSeats(200);
		testBus.setStandingRoom(300);
		testBus.setManufacturer("CAT");
		testBus.setModel("Business Ultra");
		// That's 03.10.2021 08:43:29 in UTC / GMT
		testBus.setNextInspectionDue(new Date(1633250609000l));
		testBus.setArticulatedBus(true);

		// Tell the database to modify the bus accordingly
		cdb.modifyBus(testBus);
		// Retrieve the bus from the database again
		retrievedBus = cdb.getBusById(1);

		// Make sure the bus is being retrieved properly from the database
		busEquals(testBus, retrievedBus);

		// Delete bus from database
		cdb.deleteBus(1);

		// Finally make sure that the bus was deleted successfully
		assertEquals(cdb.getBusById(1), null);
	}

	private void busEquals(Bus bus1, Bus bus2)
	{
		assertEquals(bus1.getLicencePlate(), bus2.getLicencePlate());
		assertEquals(bus1.getNumberOfSeats(), bus2.getNumberOfSeats());
		assertEquals(bus1.getStandingRoom(), bus2.getStandingRoom());
		assertEquals(bus1.getManufacturer(), bus2.getManufacturer());
		assertEquals(bus1.getModel(), bus2.getModel());
		assertEquals(bus1.getNextInspectionDue(), bus2.getNextInspectionDue());
	}

	@Test
	public void testEmployee()
	{
		// Write employee to database and retrieve the resulting id, salt and password hash
		Triple<Integer, String, String> returnedValues = cdb.addEmployee(testEmployee);
		testEmployee.setId(returnedValues.getFirst());
		testEmployee.setSalt(returnedValues.getSecond());
		testEmployee.setPassword(returnedValues.getThird());

		// Retrieve employee from database using both avenues available to us
		// User #1 is always root
		Employee retrievedEmployeeById = cdb.getEmployeeById(2);
		Employee retrievedEmployeeByUsername = cdb.getEmployeeByUsername("h_schwa");

		// Make sure both retrieved employees are identical
		employeeEquals(retrievedEmployeeById, retrievedEmployeeByUsername);

		// Make sure the employee is being retrieved properly from the database
		employeeEquals(testEmployee, retrievedEmployeeById);

		// Modify the employee into a completely different person
		testEmployee.setName("Schüwü");
		testEmployee.setFirstName("Herbertine");
		testEmployee.setAddress("Umgezogenenstraße 77");
		testEmployee.setZipCode("98764");
		testEmployee.setCity("Umgezogenencity");
		// Yes, that's 30.09.1894 03:19:27 UTC / GMT
		testEmployee.setDateOfBirth(new Date(-2374778433000l));
		testEmployee.setPhone("0111 / 54573647");
		testEmployee.setEmail("fasel@blubberdi.de");
		testEmployee.setUsername("h_schüwü");
		testEmployee.setPassword("3622890503342391552920839945611940883616943024588491422658336993263816284344771996659242960139440314475744407975621244384776915435992764631242636988337601");
		testEmployee.setSalt("9r5s5oul0csakqa8463ji6nd36");
		testEmployee.setNote("Überhaupt keine Beschwerden. Perfekte Netzplanerin");
		employeeRoles.remove(Role.BUSDRIVER);
		employeeRoles.add(Role.NETWORK_PLANNER);
		testEmployee.setRoles(employeeRoles);

		// Tell the database to modify the employee accordingly
		cdb.modifyEmployee(testEmployee);

		// Retrieve the employee from the database, again via both ways
		retrievedEmployeeById = cdb.getEmployeeById(2);
		retrievedEmployeeByUsername = cdb.getEmployeeByUsername("h_schüwü");

		// Make sure retrieved employees are equal
		employeeEquals(retrievedEmployeeById, retrievedEmployeeByUsername);

		// Delete employee from database
		cdb.deleteEmployee(2);

		// Finally make sure that the bus was deleted successfully
		assertEquals(cdb.getEmployeeById(2), null);
		assertEquals(cdb.getEmployeeByUsername("h_schüwü"), null);
	}

	private void employeeEquals(Employee emp1, Employee emp2)
	{
		assertEquals(emp1.getAddress(), emp2.getAddress());
		assertEquals(emp1.getCity(), emp2.getCity());
		assertEquals(emp1.getDateOfBirth(), emp2.getDateOfBirth());
		assertEquals(emp1.getEmail(), emp2.getEmail());
		assertEquals(emp1.getName(), emp2.getName());
		assertEquals(emp1.getFirstName(), emp2.getFirstName());
		assertEquals(emp1.getNote(), emp2.getNote());
		assertEquals(emp1.getSalt(), emp2.getSalt());
		assertEquals(emp1.getPassword(), emp2.getPassword());
		assertEquals(emp1.getPhone(), emp2.getPhone());
		assertEquals(emp1.getUsername(), emp2.getUsername());
		assertEquals(emp1.getZipCode(), emp2.getZipCode());
	}

	@Test
	public void testUniqueLicensePlateConstraint()
	{
		expectedException.expect(DataAccessException.class);
		expectedException.expectMessage("SQL [insert into buses (licencePlate, numberOfSeats, standingRoom, manufacturer, model, nextInspectionDue, articulatedBus) values (?, ?, ?, ?, ?, ?, ?)]; [SQLITE_CONSTRAINT]  Abort due to constraint violation (UNIQUE constraint failed: buses.licencePlate)");

		cdb.addBus(testBus);
		cdb.addBus(testBus);
	}

	@Test
	public void testUniqueUsernameConstraint()
	{
		expectedException.expect(DataAccessException.class);
		expectedException.expectMessage("SQL [insert into employees (name, firstName, address, zipCode, city, dateOfBirth, phone, email, username, salt, password, note, isBUSDRIVER, isNETWORK_PLANNER, isTICKET_PLANNER, isHR_MANAGER, isSCHEDULE_MANAGER) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)]; [SQLITE_CONSTRAINT]  Abort due to constraint violation (UNIQUE constraint failed: employees.username)");

		cdb.addEmployee(testEmployee);
		cdb.addEmployee(testEmployee);
	}



	/**
	 * This method is fairly complicated in its setup as it requires a lot of things to come together to work properly.
	 */
	@Test
	public void testTours()
	{

	}
}