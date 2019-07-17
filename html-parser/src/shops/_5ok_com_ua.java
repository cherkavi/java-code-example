package shops;

import java.util.ArrayList;

import org.w3c.dom.Element;

import shop_list.html.parser.engine.multi_page.BaseMultiPage;
import shop_list.html.parser.engine.multi_page.ESectionEnd;
import shop_list.html.parser.engine.multi_page.section.ResourceSection;
import shop_list.html.parser.engine.record.Record;

// FIXME - проверить работоспособность
public class _5ok_com_ua extends BaseMultiPage{

	@Override
	protected String getCharset() {
		return "utf-8";
	}

	private ESectionEnd[] sectionEndCondition=new ESectionEnd[]{ESectionEnd.NEXT_RECORDS_SHOW_FIRST};
	@Override
	protected ESectionEnd[] getSectionEndConditions() {
		return sectionEndCondition;
	}

	@Override
	public String getShopUrlStartPage() {
		return "http://www.5ok.com.ua";
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
		returnValue.add(new CurrentSession("Микроволновые печи","http://www.5ok.com.ua/Products_138_154_0_0_0_0_0_1.html"));
		return returnValue;
	}
	
	@Override
	protected String getXmlPathToDataBlock() {
		return "/html/body/form/div[4]/div/div[2]/div[2]/table/tbody/tr/td/div/ul/li";
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
		Element element=(Element)this.parser.getNodeFromNodeAlternative(currentElement, "/div[1]/h4/a");
		
		Element elementPrice=(Element)this.parser.getNodeFromNodeAlternative(currentElement, "/div[1]/div[3]/span/");
		// ----------------
		String name=element.getTextContent();
		String url=element.getAttribute("href");
		String description=null;
		Float price=Float.parseFloat(elementPrice.getTextContent().trim());
		Float priceUSD=null;
		Float priceEURO=null;
		
		return new Record(name,description,url, price, priceUSD, priceEURO);
	}

	@Override
	protected String getRecordTagNameInBlock() {
		// TODO Auto-generated method stub
		return "li";
	}


}


