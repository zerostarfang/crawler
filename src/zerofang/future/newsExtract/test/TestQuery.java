package zerofang.future.newsExtract.test;

import zerofang.future.newsExtract.StringQuery;

public class TestQuery {
	public static void main(String[] args)
	{
		StringQuery query=new StringQuery();
		query.input("hu");
		query.output();
		query.input("hu");
		query.input("wan");
		query.output();
		query.input("ting");
		while(!query.isEmpty())
		{
			System.out.println(query.output());
		}
	}
}
