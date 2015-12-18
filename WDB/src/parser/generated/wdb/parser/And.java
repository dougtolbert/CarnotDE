/* Generated By:JJTree: Do not edit this line. And.java */

package wdb.parser;

import java.util.ArrayList;

import wdb.Adapter;
import wdb.metadata.IndexSelectResult;
import wdb.metadata.WDBObject;

public class And extends SimpleNode {
  public And(int id) {
    super(id);
  }

  public And(QueryParser p, int id) {
    super(p, id);
  }
  public IndexSelectResult filterObjectsWithIndexes(Adapter da, ArrayList indexes) throws Exception
  {
	  SimpleNode n1 = (SimpleNode)children[0];
	  SimpleNode n2 = (SimpleNode)children[1];
	  IndexSelectResult leftResult = n1.filterObjectsWithIndexes(da, indexes);
	  IndexSelectResult rightResult = n2.filterObjectsWithIndexes(da, indexes);
	  return leftResult.and(leftResult);
  }
  public boolean eval(Adapter da, WDBObject wdbO) throws Exception
  {
  	SimpleNode n1 = (SimpleNode)children[0];
  	SimpleNode n2 = (SimpleNode)children[1];
  	return n1.eval(da, wdbO) && n2.eval(da, wdbO);
  }

}
