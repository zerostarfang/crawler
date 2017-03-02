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
 * ץȡ�������������ڹ������ŵ������ҳ
 * @author zerofang
 *
 */
public class NewsDown
{
	private static int index=0;
	public static void main(String[] args)
	{	
		String starturl="http://news.sina.com.cn/china/";  //�������Ź���
		NewsDown newsdown=new NewsDown();
		newsdown.Sinaload(starturl);			
	}
	public void Sinaload(String starturl)
	{
		String urlstr=starturl;     //���url
		String urlstring=null;
		String link=null;	
		Set<String> hashset=new HashSet<String>(); //�ö����urlȥ��
		StringQuery query=new StringQuery();       //�ö����ṩ���в���
		NewsExtractor ne=new NewsExtractor();      //�ö������dom��ȡ����
		System.out.println("��ǰ�����url��"+urlstr);
		try{
			Document startdoc=Jsoup.connect(urlstr).timeout(10000).get();   //��ʱ����
			Elements startelements=startdoc.select("a[href*=http://news.sina.com.cn/c/]");
	        for (Element element : startelements) 
	        {     
	            link=element.attr("href"); 
	            if(!hashset.contains(link))     //��������в�����url����Ѹ�url��ӵ������У�Ȼ����ӵ�������
	            {
	            	hashset.add(link);          //��ӵ�����
	            	query.input(link);          //��������µ�����
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
			System.out.println("��ǰ�����url��"+urlstring);
			try{
				Document doc=Jsoup.connect(urlstring).timeout(10000).get();   //��ʱʱ�����ú�����
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
		            if(!hashset.contains(link))     //��������в�����url����Ѹ�url��ӵ������У�Ȼ����ӵ�������
		            {
		            	hashset.add(link);          //��ӵ�����
		            	query.input(link);          //��������µ�����
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














