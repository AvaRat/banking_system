package model;


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
	
	public String getAddress()
	{
		return permanentAddress.toString();
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
