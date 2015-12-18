/* Generated By:JJTree: Do not edit this line. Cond.java */

package wdb.parser;

import java.lang.reflect.Array;
import java.util.ArrayList;

import wdb.metadata.*;
import wdb.Adapter;

public class Cond extends SimpleNode {
	protected AttributePath attributePath;
	protected Object value;
	protected String quantifier;
	
  public Cond(int id) {
    super(id);
  }

  public Cond(QueryParser p, int id) {
    super(p, id);
  }
  
  public void setAttribute(AttributePath attribute)
  {
  	this.attributePath = attribute;
  }
  
  public void setValue(Object value)
  {
  	this.value = value;
  }

  public void setQuantifier(String quantifier)
  {
  	this.quantifier = quantifier.trim();
  }
  public String toString()
  {
  	return attributePath.attribute + " " + quantifier + " " + value;
  }
  public IndexSelectResult filterObjectsWithIndexes(Adapter da, ArrayList indexes) throws Exception
  {
	  IndexSelectResult isr = new IndexSelectResult();
	  if(this.attributePath.levelsOfIndirection() > 0 || !this.quantifier.equals("="))
	  {
		  //These conditions are not supported so return a "scan" or "can't help" result
		  return isr;
	  }
	  else
	  {
		  isr.addDva(this.attributePath.attribute, this.value);
		  return isr;
	  }
  }
  public boolean eval(Adapter da, WDBObject wdbO) throws Exception
  {
  	if(this.quantifier.equals("="))
  	{
  		return wdbO.getAttributeValue(this.attributePath, da).contains(value);
  	}
  	if(this.quantifier.equals("<>"))
  	{
  		ArrayList<Object> values = wdbO.getAttributeValue(this.attributePath, da);
  		for(int i = 0; i < values.size(); i++)
  		{
  			if(!values.get(i).equals(value))
  				return true;
  		}
  		return false;
  	}
  	
  	throw new IllegalStateException("Symbol \"" + this.quantifier + "\" is not a vaid quantifier");
  }
  /*
  public boolean eval(Adapter da, WDBObject wdbO) throws Exception
  {
	WDBObject refWdbO;
	Integer[] ref_all;
	String target_class;
	
	//Set the currently referenced object to the one we are looking at
	refWdbO = wdbO;
	//Now traverse the evas to get to the dva
	for(int k = attributePath.levelsOfIndirection() - 1; k >= 0; k--)
	{
		ref_all = refWdbO.GetEVARef_all(attributePath.getIndirection(k));
		target_class = refWdbO.GetEVATarget_class(attributePath.getIndirection(k));
		if(target_class == "null")
		{
			//No special target class for this particular object. Probably because
			//we are looking up inverses. Get the default for this attribute
			Attribute attribute = da.GetAttribute(attributePath.getIndirection(k), refWdbO.getClassName());
			if(attribute.getClass() == EVA.class)
			{
				target_class = ((EVA)attribute).baseClassName;
			}
		}
		if(ref_all != null)
		{
			refWdbO = da.GetWDBObject(ref_all[0], target_class);
		}
		else
		{
			System.out.println(attributePath.getIndirection(k) + " is not a valid EVA");
		}
	}
	
	//Now we are at the desired eva class, check the value of the dva
  	Object o = refWdbO.GetDVAValue(attributePath.attribute);
  	if(o == null)
  	{
  		System.out.println(attributePath.attribute + " is not a defined attribute!");
  		return false;
  	}
  	else
  	{
  		if(o.getClass().isArray() && attributePath.index != null)
		{
			return Array.get(o, attributePath.index.intValue()).equals(value);
		}
		else if((!o.getClass().isArray()) && attributePath.index == null)
		{
			return o.equals(value);
		}
		else if((!o.getClass().isArray()) && attributePath.index != null)
		{
			System.out.println("Field " + attributePath.attribute + " is not an array!");
			return false;
		}
		else
		{
			System.out.println("Field " + attributePath.attribute + " is an array!");
			return false;
		}
  	}
  }*/
}
