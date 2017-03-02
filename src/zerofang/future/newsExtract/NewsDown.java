package zerofang.future.newsExtract;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.*;
import org.jsoup.Jsoup;   
import org.jsoup.nodes.Document;   
import org.jsoup.nodes.Element;   
import org.jsoup.select.Elements;   

/***
 * 抓取国内新浪网关于国内新闻的相关网页
 * @author zerofang
 *
 */
public class NewsDown
{
	private static int index=0;
	public static void main(String[] args)
	{	
		String starturl="http://news.sina.com.cn/china/";  //国内新闻滚动
		NewsDown newsdown=new NewsDown();
		newsdown.Sinaload(starturl);			
	}
	public void Sinaload(String starturl)
	{
		String urlstr=starturl;     //入口url
		String urlstring=null;
		String link=null;	
		Set<String> hashset=new HashSet<String>(); //该对象对url去重
		StringQuery query=new StringQuery();       //该对象提供队列操作
		NewsExtractor ne=new NewsExtractor();      //该对象解析dom抽取正文
		System.out.println("当前请求的url是"+urlstr);
		try{
			Document startdoc=Jsoup.connect(urlstr).timeout(10000).get();   //超时设置
			Elements startelements=startdoc.select("a[href*=http://news.sina.com.cn/c/]");
	        for (Element element : startelements) 
	        {     
	            link=element.attr("href"); 
	            if(!hashset.contains(link))     //如果集合中不含该url，则把该url添加到集合中，然后添加到队列中
	            {
	            	hashset.add(link);          //添加到集合
	            	query.input(link);          //队列添加新的链接
	            }   
	        }
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		while(!query.isEmpty())
		{
			String titlestr="";
			String contentstr="";
			urlstring=query.output();
			System.out.println("当前请求的url是"+urlstring);
			try{
				Document doc=Jsoup.connect(urlstring).timeout(10000).get();   //超时时间设置很有用
				Element title=doc.select("title").first();
				titlestr=title.text();
				contentstr=ne.parseUrl(doc);
				System.out.println(titlestr);
				System.out.println(contentstr);
				saveNews(titlestr+"######"+contentstr);
				Elements elements=doc.select("a[href*=http://news.sina.com.cn/c/]");
		        for (Element element : elements) 
		        {     
		            link=element.attr("href"); 
		            if(!hashset.contains(link))     //如果集合中不含该url，则把该url添加到集合中，然后添加到队列中
		            {
		            	hashset.add(link);          //添加到集合
		            	query.input(link);          //队列添加新的链接
		            }
		        }
			}catch(Exception e)
			{
				e.printStackTrace();
			}    	
		}
	}
	public void saveNews(String news)
	{
		String filename=null;
		File file=null;
		DecimalFormat df=new DecimalFormat("00000");
		filename=df.format(index++);
		filename="D:\\DATA\\"+filename;
		file=new File(filename);
		try{
			file.createNewFile();
			BufferedWriter outfile=new BufferedWriter(new FileWriter(file));
			outfile.write(news);
			outfile.flush();
			outfile.close();
		}catch(IOException e)
		{
			e.printStackTrace();
		}
		
	}
}














