package vgu.org.parse;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.CaseExpression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.WhenClause;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.expression.operators.relational.GreaterThan;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectExpressionItem;
import net.sf.jsqlparser.statement.select.SelectItem;

public class TestCaseWhen {
	public static void main(String[] args) throws JSQLParserException {
		String stmt = "SELECT OrderID, Quantity,\r\n" + 
				"CASE\r\n" + 
				"    WHEN Quantity > 30 THEN \"The quantity is greater than 30\"\r\n" + 
				"    WHEN Quantity = 30 THEN \"The quantity is 30\"\r\n" + 
				"    ELSE \"The quantity is something else\"\r\n" + 
				"END\r\n" + 
				"FROM OrderDetails;";
		
		CCJSqlParserManager parserManager = new CCJSqlParserManager();
		Select select = (Select) parserManager.parse(new StringReader(stmt));
        PlainSelect plainSelect = (PlainSelect) select.getSelectBody();
        
        List<SelectItem> itemList = new ArrayList<>();
        
        Table orDetail = new Table();
        orDetail.setName("OrderDetails");
        
        SelectExpressionItem a = new SelectExpressionItem();
        Column OrderID = new Column();
        OrderID.setColumnName("OrderID");
        a.setExpression(OrderID);
        
        SelectExpressionItem b = new SelectExpressionItem();
        Column Quantity = new Column();
        Quantity.setColumnName("Quantity");
        b.setExpression(Quantity);
        
        SelectExpressionItem c = new SelectExpressionItem();
        Column elseEx = new Column();
        elseEx.setColumnName("The quantity is something else");
        CaseExpression caEx = new CaseExpression();
        caEx.setElseExpression(elseEx);
        
        List<WhenClause> whclauseList = new ArrayList<>();
        WhenClause wcl1 = new WhenClause();
        Column greatThan30 = new Column();
        greatThan30.setColumnName("The quantity is greater than 30");
        wcl1.setThenExpression(greatThan30);
        
        GreaterThan gt = new GreaterThan();
        LongValue lvl = new LongValue("30");
        gt.setLeftExpression(Quantity);
        gt.setRightExpression(lvl);
        wcl1.setWhenExpression(gt);
        whclauseList.add(wcl1);
        
        WhenClause wcl2 = new WhenClause();
        Column is30 = new Column();
        is30.setColumnName("The quantity is 30");
        wcl2.setThenExpression(is30);
        
        EqualsTo eq = new EqualsTo();
        eq.setLeftExpression(Quantity);
        eq.setRightExpression(lvl);
        wcl2.setWhenExpression(eq);
        
        whclauseList.add(wcl2);
        
        caEx.setWhenClauses(whclauseList);
        c.setExpression(caEx);
        
        itemList.add(a);
        itemList.add(b);
        itemList.add(c);
        
        PlainSelect pl = new PlainSelect();
        pl.setSelectItems(itemList);
        pl.setFromItem(orDetail);
        
        System.out.println(pl);
	}
}
