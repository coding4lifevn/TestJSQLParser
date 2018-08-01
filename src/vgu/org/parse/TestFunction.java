package vgu.org.parse;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.Function;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.GreaterThan;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.OrderByElement;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;

public class TestFunction {
	public static void main(String[] args) throws JSQLParserException {
		String stmt = "SELECT COUNT(CustomerID), Country FROM Customers GROUP BY Country"
				+ " HAVING COUNT(CustomerID) > 5 ORDER BY COUNT(CustomerID) DESC";
		
		CCJSqlParserManager parserManager = new CCJSqlParserManager();
		Select select = (Select) parserManager.parse(new StringReader(stmt));
        PlainSelect plainSelect = (PlainSelect) select.getSelectBody();
        
        List<SelectItem> itemList = new ArrayList<>();
        
        SelectExpressionItem a = new SelectExpressionItem();
        
        Column customerID = new Column();
        customerID.setColumnName("CustomerID");
        
        List<Expression> exList1 = new ArrayList<>();
        exList1.add(customerID);
        
        ExpressionList expl = new ExpressionList();
        expl.setExpressions(exList1);
        
        Function a1 = new Function();
        a1.setName("COUNT");
        a1.setParameters(expl);
        
        a.setExpression(a1);
        itemList.add(a);
        
        SelectExpressionItem b = new SelectExpressionItem();
        
        Column Country = new Column();
        Country.setColumnName("Country");
        
        b.setExpression(Country);
        itemList.add(b);
        
        GreaterThan having = new GreaterThan();
        LongValue five = new LongValue("5");
        having.setLeftExpression(a1);
        having.setRightExpression(five);
        
        List<OrderByElement> orByList = new ArrayList<>();
        OrderByElement orBy1 = new OrderByElement();
        orBy1.setExpression(a1);
        orBy1.setAsc(false);
        orByList.add(orBy1);
        
        List<Expression> colList = new ArrayList<>();
        colList.add(Country);
        
        Table customers = new Table();
        customers.setName("Customers");
        
        PlainSelect pl = new PlainSelect();
        pl.setFromItem(customers);
        pl.setGroupByColumnReferences(colList);
        pl.setHaving(having);
        pl.setOrderByElements(orByList);
        pl.setSelectItems(itemList);
        
        System.out.println(pl);
	}
}
