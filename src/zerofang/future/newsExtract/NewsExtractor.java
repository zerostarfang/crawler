package zerofang.future.newsExtract;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

import org.jsoup.nodes.Document;   
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;  
public class NewsExtractor {   
	private static List<Element> divList=new ArrayList<Element>();
	
	/***
	 * 
	 * @param doc
	 * @return
	 */
    public String parseUrl(Document doc) {   
    	String text="";
        wash(doc);
        washAll(doc);
        Elements bodychildren=doc.body().children();
        for(Element element : bodychildren)
        {
        	if(judgeBlockTag(element))
        	{
        		addList(element);
        	}
        }
        Iterator<Element> it=divList.iterator();
        while(it.hasNext())
        {
        	int linkcount=0;
        	int contentcount=0;
        	double LC=0;
        	Element candidate=it.next();       		
        	contentcount=candidate.text().length();  //ͳ�Ƹú�ѡ����ı��ַ�����
        	Elements links=candidate.select("a[href]");
        	linkcount=links.size();                  //ͳ�Ƹú�ѡ���������      
        	if(contentcount>50)                      //�������������󣬺�ѡ������Ȼ�����и�������һ�δ����������
        	{
        		LC=(double)linkcount/(double)contentcount;
        		if(LC<0.03)
        		{
        			 text=candidate.text();
        		}
        	}
        } 
        return text;
    } 
    
    /***
     * 
     * @param element
     */
    public void addList(Element element)
    {
    	Elements children=element.children();
    	if(children==null)
    	{
    		divList.add(element);         //��Ԫ��û����Ԫ�أ�����Ӹ�Ԫ�ص��б�
    		return;
    	}
    	else
    	{
    		boolean havediv=false;
    		for(Element child:children)
    		{
    			if(judgeBlockTag(child))  //��Ԫ����Ԫ��ΪdivԪ�أ���ݹ���ú���
        		{
    				havediv=true;
        			addList(child);
        		}
    		}
    		if(!havediv)
        		divList.add(element);     //�������Ԫ�أ�����Ԫ��ȫ��Ϊ��div��ǩ�Ļ���Ҳ��Ӹ�Ԫ�ص��б�
    	} 	
    	return;
    }
    
    /***
     * 
     * @param element
     * @return
     */
    public boolean judgeBlockTag(Element element)//�ж�Ԫ�ض�Ӧ��ǩ�ǲ��Ƿֿ��ǩ
    {
    	if(element.tagName().equals("div"))
    		return true;
    	else
    		return false;
    }
    public void washAll(Document doc)
    {
    	Elements es=doc.getAllElements();
    	for(Element element : es)
    	{
    		
    		if(judgeBlockTag(element))          //�ֿ�Ԫ�أ������ı�̫����̫����Ϊ���ģ�ɾ��
    		{
    			if(element.text().length()<50)
    				element.remove();
    		}
    		else if(element.text().equals(""))  //ɾ�������ı��ڵ�Ϊ�յ�Ԫ��
    		{
    			element.remove();
    		}
    	}
    }
    
    /***
     * 
     * @param doc
     */
    public void wash(Document doc)
    {	
    	Elements script=doc.select("script");
    	for(Element element : script)
    	{
    		element.remove();
    	}
    	Elements form=doc.select("form");
    	for(Element element : form)
    	{
    		element.remove();
    	}
    	Elements meta=doc.select("meta");
    	for(Element element : meta)
    	{
    		element.remove();
    	}
    	Elements style=doc.select("style");
    	for(Element element : style)
    	{
    		element.remove();
    	}
    	Elements iframe=doc.select("iframe");
    	for(Element element : iframe)
    	{
    		element.remove();
    	}
    	Elements font=doc.select("font");
    	for(Element element : font)
    	{
    		element.remove();
    	}
    }
}  
