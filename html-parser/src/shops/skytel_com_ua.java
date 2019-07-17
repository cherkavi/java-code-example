package shops;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import shop_list.html.parser.engine.record.Record;
import shop_list.html.parser.engine.single_page.TableSinglePage;

public class skytel_com_ua extends TableSinglePage{

	@Override
	protected Record getRecordFromRow(Node node) {
		Record returnValue=null;
		try{
			if(((Element)this.parser.getNodeFromNodeAlternative(node, "/td[2]/nobr")).getTextContent().trim().indexOf("���")<0){
				String name=null;
				String url=null;
				String description=null;
				Float price=null;
				Float priceUSD=null;
				Float priceEURO=null;
				
				Element nodeName=(Element)this.parser.getNodeFromNodeAlternative(node, "/td[1]/a");
				url=nodeName.getAttribute("href");
				name=nodeName.getTextContent().trim();
				
				Element priceElement=(Element)this.parser.getNodeFromNodeAlternative(node, "/td[3]/b/nobr");
				// System.out.println("Source   price String: "+priceElement.getTextContent().trim());
				// System.out.println("Replaced price String: "+priceElement.getTextContent().trim().replaceAll("[\\$ ,]", ""));
				String priceElementText=priceElement.getTextContent().trim().replaceAll("[$ ,]", "");
				priceUSD=Float.parseFloat(priceElementText);
				
				returnValue=new Record(name,description,url, price, priceUSD, priceEURO);
			}else{
				// ��� �� ������
			}
		}catch(Exception ex){
			returnValue=null;
			logger.error(this, "#getRecordFromRow Exception:"+ex.getMessage());
		}
		return returnValue;
	}

	@Override
	protected String getSectionNameFromRow(Node node) {
		return this.parser.getTextContentFromNodeAlternative(node, "td/a", "");
	}

	@Override
	protected boolean isRecord(Node node) {
		if(this.parser.getChildElementCount(node, "td")==3){
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
		return "utf-8";
	}

	@Override
	protected String getFullHttpUrlToPrice() {
		return "http://www.skytel.com.ua/index.php?ukey=pricelist";
	}

	@Override
	public String getShopUrlStartPage() {
		return "http://www.skytel.com.ua";
	}

	@Override
	protected String getXmlPathToMainBlock() {
		return "/html/body/div/table/tbody/tr/td[3]/div[2]/center/p[2]/table/tbody";
	}

}
