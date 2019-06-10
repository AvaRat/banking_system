package model;

import org.json.JSONObject;

public class Person {
	private int age;
	private String name;
	private String surname;
	private Address permanentAddress;
	
	public Person(int age_, String name_, String surname_, String street_, int streetNr_, String city_, String country_)
	{
		age = age_;
		name = name_;
		surname = surname_;
		permanentAddress = new Address(street_, streetNr_, city_, country_);
	}
	
	public Person(JSONObject person)
	{
		age = person.getInt("age");
		name = person.getString("name");
		surname = person.getString("surname");
		permanentAddress = new Address(person.getJSONObject("permanentAddress"));
	}
	
	public JSONObject toJSON()
	{
		JSONObject person = new JSONObject();
		person.put("name", name);
		person.put("surname", surname);
		person.put("age", age);
		person.put("permanentAddress", permanentAddress.toJSON());
		return person;
	}
	
	public Person(Person other)
	{
		/*
		 * nie moge zrobic podstawienia 
		 * this = other ??
		 */
		age = other.age;
		name = other.name;
		surname = other.surname;
		permanentAddress = other.permanentAddress;
	}
	
	public Person(int age_, String name_, String surname_, Address addr)
	{
		age = age_;
		name = name_;
		surname = surname_;
		permanentAddress = new Address(addr);
	}
	public String getName()
	{
		return name;
	}
	public String getSurname()
	{
		return surname;
	}
	public String getAddress()
	{
		return permanentAddress.toString();
	}
	public String getStreet()
	{
		return permanentAddress.getStreet();
	}
	
	public int getStreetNr()
	{
		return permanentAddress.getStreetNr();
	}
	public String getCity()
	{
		return permanentAddress.getCity();
	}
	public String getCountry()
	{
		return permanentAddress.getCountry();
	}
	public String toString()
	{
		return new StringBuilder(name+ " ").append(surname + " ").append(permanentAddress.toString()).toString();
	}
	
	
	public static class Address {
		private String street;
		private String city;
		private int streetNr;
		private String country;
		
		Address(String street_, int streetNr_, String city_, String country_)
		{
			street = street_;
			city = city_;
			streetNr = streetNr_;
			country = country_;
		}
		public Address(JSONObject ob)
		{
			street = ob.getString("street");
			city = ob.getString("city");
			streetNr = ob.getInt("streetNr");
			country = ob.getString("country");
		}
		public JSONObject toJSON()
		{
			JSONObject a = new JSONObject();
			a.put("street", street);
			a.put("streetNr", streetNr);
			a.put("city", city);
			a.put("country", country);
			return a;
		}
		public String getStreet() 
		{
			return street;
		}
		public String getCity()
		{
			return city;
		}
		public int getStreetNr()
		{
			return streetNr;
		}
		public String getCountry()
		{
			return country;
		}
		
		private Address(Address addr)
		{
			street = addr.street;
			city = addr.city;
			streetNr = addr.streetNr;
			country = addr.country;
		}
		
		public String toString()
		{
			return new StringBuilder(street+ " ").append(streetNr + " ").append(city + " ").append(country).toString();
		}
	}
}
