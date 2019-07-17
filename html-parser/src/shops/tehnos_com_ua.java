package shops;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import shop_list.html.parser.engine.record.Record;
import shop_list.html.parser.engine.single_page.TableSinglePage;
// FIXME - JRE break with ERROR, maybe need increase size of Memory
public class tehnos_com_ua extends TableSinglePage{

	@Override
	protected Record getRecordFromRow(Node node) {
		Record returnValue=null;
		try{
			String name=null;
			String url=null;
			String description=null;
			Float price=null;
			Float priceUSD=null;
			Float priceEURO=null;
			
			Element nodeName=(Element)this.parser.getNodeFromNodeAlternative(node, "/td[1]/a");
			url=nodeName.getAttribute("href");
			name=this.parser.getTextContentFromNodeAlternative(node, "/td[1]/a/b", "").trim();
			
			Element priceElement=(Element)this.parser.getNodeFromNodeAlternative(node, "/td[2]/nobr/b");
			String priceElementText=priceElement.getTextContent().replaceAll("[, $]", "").toLowerCase().trim();
			priceUSD=Float.parseFloat(priceElementText);
			
			returnValue=new Record(name,description,url, price, priceUSD, priceEURO);
		}catch(Exception ex){
			returnValue=null;
			logger.error(this, "#getRecordFromRow Exception:"+ex.getMessage());
		}
		return returnValue;
	}

	@Override
	protected String getSectionNameFromRow(Node node) {
		return this.parser.getTextContentFromNodeAlternative(node, "/td/a/b", "");
	}

	@Override
	protected boolean isRecord(Node node) {
		if(this.parser.getChildElementCount(node, "td")==2){
			return true;
		}else{
			return false;
		}
	}

	@Override
	protected boolean isSection(Node node) {
		if(this.parser.getChildElementCount(node, "td")==1){
			return true;
		}else{
			return false;
		}
	}

	@Override
	protected String getCharset() {
		return "windows-1251";
	}

	@Override
	protected String getFullHttpUrlToPrice() {
		return "http://www.tehnos.com.ua/index.php?show_price=yes";
	}

	@Override
	public String getShopUrlStartPage() {
		return "http://www.tehnos.com.ua";
	}

	@Override
	protected String getXmlPathToMainBlock() {
		return "/html/body/table[4]/tbody/tr[2]/td[3]/table[2]/tbody/tr/td[2]/table[2]/tbody";
	}

}
