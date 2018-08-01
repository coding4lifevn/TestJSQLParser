package vgu.org.parse;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import net.sf.jsqlparser.statement.select.FromItem;
import net.sf.jsqlparser.statement.select.FromItemVisitor;
import net.sf.jsqlparser.statement.select.Join;
import net.sf.jsqlparser.statement.select.Pivot;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Alias;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.ExpressionVisitor;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.expression.operators.relational.ComparisonOperator;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.parser.SimpleNode;
import net.sf.jsqlparser.parser.Token;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;

public class TestJoin {

	public static void main(String[] args) throws JSQLParserException {
		//This will create this query
		//SELECT p.name, s.orderQty FROM product AS p INNER JOIN SalesOrderDetail 
		//AS s ON p.ProductID = s.ProductID
        
		// select items
        List <SelectItem> itemList = new ArrayList<SelectItem>();
        
        SelectExpressionItem a = new SelectExpressionItem();
        Column name = new Column();
        name.setColumnName("p.name");
        Table product = new Table();
        product.setName("product");
        a.setExpression(name);
        itemList.add(a);
        Alias al = new Alias("p");
        product.setAlias(al);
        
        SelectExpressionItem b = new SelectExpressionItem();
        Column orderQty = new Column();
        orderQty.setColumnName("s.orderQty");
        Table ord = new Table();
        ord.setName("SalesOrderDetail");
        b.setExpression(orderQty);
        Alias al1 = new Alias("s");
        ord.setAlias(al1);
        itemList.add(b);
        
        List<Join> joList = new ArrayList<Join>();
        Join jo = new Join();
        EqualsTo eql = new EqualsTo();
        Column ProductID1 = new Column();
        ProductID1.setColumnName("ProductID");
        ProductID1.setTable(product);
        eql.setLeftExpression(ProductID1);
        
        Column ProductID2 = new Column();
        ProductID2.setColumnName("ProductID");
        ProductID2.setTable(ord);
        eql.setRightExpression(ProductID2);
        
        jo.setOnExpression(eql);
        jo.setInner(true);
        jo.setRightItem(ord);
        
        joList.add(jo);
        
        PlainSelect pl = new PlainSelect();
        pl.setSelectItems(itemList);
        pl.setFromItem(product);
        pl.setJoins(joList);
        System.out.println(pl);
        
	}

}
