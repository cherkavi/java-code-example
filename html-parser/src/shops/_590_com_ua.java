package shops;

import java.util.ArrayList;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import shop_list.html.parser.engine.multi_page.BaseMultiPage;
import shop_list.html.parser.engine.multi_page.ESectionEnd;
import shop_list.html.parser.engine.multi_page.section.ResourceSection;
import shop_list.html.parser.engine.record.Record;

public class _590_com_ua extends BaseMultiPage{

	@Override
	protected String getCharset() {
		return "windows-1251";
	}

	private ESectionEnd[] sectionEndCondition=new ESectionEnd[]{ESectionEnd.NEXT_RECORDS_ZERO_SIZE};
	@Override
	protected ESectionEnd[] getSectionEndConditions() {
		return sectionEndCondition;
	}

	@Override
	public String getShopUrlStartPage() {
		return "http://www.590.com.ua";
	}

	@Override
	protected long getTimeoutForReadNextPage() {
		return 2000;
	}

	@Override
	protected long getTimeoutForReadNextSection() {
		return 200;
	}
	
	@Override
	protected ArrayList<ResourceSection> getSection() {
		// TODO STUB
		ArrayList<ResourceSection> returnValue=new ArrayList<ResourceSection>();
		returnValue.add(new CurrentSession("Стиральные машины","http://www.590.com.ua/catalog_cat_23_cbrand_6_p_1.html"));
		return returnValue;
	}
	
	@Override
	protected String getXmlPathToDataBlock() {
		return "/html/body/div/div[3]/form/ul";
	}

	@Override
	protected ArrayList<Record> readRecordsFromBlock(Node node) {
		if((node!=null)&&(node.hasChildNodes())){
			NodeList list=node.getChildNodes();
			ArrayList<Record> returnValue=new ArrayList<Record>();
			for(int counter=0;counter<list.getLength();counter++){
				if(  (list.item(counter) instanceof Element)
				   &&( ((Element)list.item(counter)).getTagName().equals("li"))){
					Element currentElement=(Element)list.item(counter);
					// li
					Element element=(Element)this.parser.getNodeFromNodeAlternative(currentElement, "/p[2]/a");
					
					Element elementPrice=(Element)this.parser.getNodeFromNodeAlternative(currentElement, "/p[4]/strong");
					// ----------------
					String name=element.getTextContent();
					String url=element.getAttribute("href");
					String description=null;
					Float price=Float.parseFloat(elementPrice.getTextContent().trim().toLowerCase().replaceAll("грн.", ""));
					Float priceUSD=null;
					Float priceEURO=null;
					
					returnValue.add(new Record(name,description,url, price, priceUSD, priceEURO));
				}
			}
			return returnValue;
		}else{
			return null;
		}
	}

	
	class CurrentSession extends ResourceSection{
		private int counter=(-1);
		private String lastUrl=null;
		
		public CurrentSession(String name, String url) {
			super(name, url);
			this.lastUrl=url;
		}

		@Override
		public String getUrlToNextPage() {
			if(counter==(-1)){
				// первое обращение к странице для получения данных
				counter=1;
				return this.lastUrl;
			}else{
				// увеличение счетчика на странице 
				counter++;
				int indexUnderScore=this.lastUrl.lastIndexOf("_");
				// int indexDot=this.lastUrl.lastIndexOf("_");
				this.lastUrl=this.lastUrl.substring(0,indexUnderScore+1)+counter+".html";
				return this.lastUrl;
			}
		}
		
	}


	@Override
	protected Record getRecordFromElement(Element currentElement) {
		Element elementName=(Element)this.parser.getNodeFromNodeAlternative(currentElement, "/p[2]/a");
		
		Element elementPrice=(Element)this.parser.getNodeFromNodeAlternative(currentElement, "/p[4]/strong");
		// ----------------
		String name=elementName.getTextContent();
		String url=elementName.getAttribute("href");
		String description=null;
		Float price=Float.parseFloat(elementPrice.getTextContent().trim().toLowerCase().replaceAll("грн.", ""));
		Float priceUSD=null;
		Float priceEURO=null;
		return new Record(name,description,url, price, priceUSD, priceEURO);
	}

	@Override
	protected String getRecordTagNameInBlock() {
		return "li";
	}


}


