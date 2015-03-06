package de.uni_muenster.sopra2015.gruppe8.octobus.model;

import java.util.Date;
import java.util.HashSet;

/**
 * Created by Jonas on 27.02.2015.
 * Edited by Lars on 27.02.2015.
 */
public class Employee
{
	private int id;			// database-internal id. is set when object is added to database
    private String name;
    private String firstName;
    private String address;
    private String zipCode;
    private String city;
    private Date dateOfBirth;
    private String phone;
    private String email;
    private String username;
    private String password;
    private String salt;
    private String note;
	private HashSet<Role> roles;

	public Employee()
	{
		roles = new HashSet<>();
	}

    public Employee(String name, String firstName, String address, String zipCode, String city, Date dateOfBirth,
                    String phone, String email, String username, String password, String salt, String note, HashSet<Role> roles)
    {
        this.name = name;
        this.firstName = firstName;
        this.address = address;
        this.zipCode = zipCode;

        this.city = city;
        this.dateOfBirth = dateOfBirth;
        this.phone = phone;
        this.email = email;
        this.username = username;
        this.password = password;
        this.salt = salt;
        this.note = note;
		this.roles = roles;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getZipCode()
    {
        return zipCode;
    }

    public void setZipCode(String zipCode)
    {
        this.zipCode = zipCode;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public Date getDateOfBirth()
    {
        return dateOfBirth;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getUsername()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getSalt()
    {
        return salt;
    }

    public void setSalt(String salt)
    {
        this.salt = salt;
    }

    public String getNote()
    {
        return note;
    }

    public void setNote(String note)
    {
        this.note = note;
    }

	public void addRole(Role role)
	{
		roles.add(role);
	}

	public boolean isRole(Role role)
	{
		return roles.contains(role);
	}

	public void deleteRole(Role role) {roles.remove(role);}

	public void setRoles (HashSet<Role> roles)
	{
		this.roles = roles;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public void setDateOfBirth(Date dateOfBirth)
	{
		this.dateOfBirth = dateOfBirth;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}
}
