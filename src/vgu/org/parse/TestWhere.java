package vgu.org.parse;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.GreaterThan;
import net.sf.jsqlparser.expression.operators.relational.ItemsList;
import net.sf.jsqlparser.expression.operators.relational.MinorThan;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;

public class TestWhere {
	public static void main(String[] args) throws JSQLParserException {
		//This will create this query
		//SELECT ID, Name, Salary FROM CUSTOMERS WHERE Salary > 2000 OR age < 25
    
	    Table cus = new Table();
	    cus.setName("CUSTOMERS");
	    
	    List<SelectItem> itemList = new ArrayList<>();
	    SelectExpressionItem a = new SelectExpressionItem();
	    Column id = new Column();
	    id.setColumnName("ID");
	    a.setExpression(id);
	    
	    SelectExpressionItem b = new SelectExpressionItem();
	    Column name = new Column();
	    name.setColumnName("Name");
	    b.setExpression(name);
	    
	    SelectExpressionItem c = new SelectExpressionItem();
	    Column sal = new Column();
	    sal.setColumnName("Salary");
	    c.setExpression(sal);
	    
	    itemList.add(a);
	    itemList.add(b);
	    itemList.add(c);
	    
	    GreaterThan gt = new GreaterThan();
	    gt.setLeftExpression(sal);
	    LongValue lv1 = new LongValue("2000");
	    gt.setRightExpression(lv1);
	    
	    MinorThan mn = new MinorThan();
	    Column age = new Column();
	    age.setColumnName("age");
	    mn.setLeftExpression(age);
	    LongValue lv2 = new LongValue("25");
	    mn.setRightExpression(lv2);
	    
	    OrExpression where = new OrExpression(gt, mn);
	    
	    PlainSelect pl = new PlainSelect();
	    pl.setSelectItems(itemList);
	    pl.setFromItem(cus);
	    pl.setWhere(where);
	    
	    System.out.println(pl);
	}

}
