package de.uni_muenster.sopra2015.gruppe8.octobus.model;

import java.util.Date;
import java.util.HashSet;

/**
 * Created by Jonas on 27.02.2015.
 * Edited by Lars on 27.02.2015.
 */
public class Employee
{
	private String name;
	private String firstName;
	private String address;
	private String zipCode;
	private String city;
	private Date birthDate;
	private String phone;
	private String mail;
	private String username;
	private String password;
	private String salt;
	private String note;
	private HashSet<Role> roles;

	public Employee(String name, String firstName, String address, String zipCode, String city, Date birthDate,
					String phone, String mail, String username, String password, String salt, String note, HashSet<Role> roles)
	{
		this.name = name;
		this.firstName = firstName;
		this.address = address;
		this.zipCode = zipCode;

		this.city = city;
		this.birthDate = birthDate;
		this.phone = phone;
		this.mail = mail;
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

	public Date getBirthDate()
	{
		return birthDate;
	}

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public String getMail()
	{
		return mail;
	}

	public void setMail(String mail)
	{
		this.mail = mail;
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
}
