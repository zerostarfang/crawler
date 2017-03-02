package zerofang.future.newsExtract;
import java.util.*;
/*
 * this is a query instance
 */
public class StringQuery {
	private LinkedList<String> linkedList;
	public StringQuery()
	{
		linkedList=new LinkedList<String>();
	}
	public void input(String url)
	{
		linkedList.addLast(url);
	}
	public String output()
	{
		return linkedList.removeFirst();
	}
	public boolean isEmpty()
	{
		return linkedList.isEmpty();
	}
}
