package reporter;

import java.text.MessageFormat;


public class MoneyToString {
	public static void main(String[] args){
		System.out.println("begin:");
		MoneyToString money=new MoneyToString();
		System.out.println("value:"+money.moneytostr(4.2));
		System.out.println("value:"+money.moneytostr(401.00));
		System.out.println("end:");
	}

	
	static String RubOneUnit, RubTwoUnit, RubFiveUnit, RubSex,
                      KopOneUnit, KopTwoUnit, KopFiveUnit, KopSex;
        StringBuffer money2str = new StringBuffer();
 
	public MoneyToString(Double theMoney, String theISOstr) {
		FillSuffix(theISOstr);
		moneytostr(theMoney);
	}
	public MoneyToString() {
		FillSuffix(null);
		//moneytostr(theMoney);
	}
 
	public void FillSuffix(String theISOstr) {
		/*org.w3c.dom.Document xmlDoc = null;
 
		javax.xml.parsers.DocumentBuilderFactory DocFactory = javax.xml.parsers.DocumentBuilderFactory.newInstance();
 
		try {
			javax.xml.parsers.DocumentBuilder xmlDocBuilder = DocFactory.newDocumentBuilder();
			xmlDoc = xmlDocBuilder.parse( new java.io.File("currlist.xml") );
		} catch (org.xml.sax.SAXException sxe) {
			Exception  x = sxe;
			if (sxe.getException() != null)
				x = sxe.getException();
			x.printStackTrace();
		} catch (javax.xml.parsers.ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (java.io.IOException ioe) {
			ioe.printStackTrace();
		}
		org.w3c.dom.Element theISOElement = (org.w3c.dom.Element) (xmlDoc.getElementsByTagName(theISOstr)).item(0);
 
		RubOneUnit  = theISOElement.getAttribute("RubOneUnit");
		RubTwoUnit  = theISOElement.getAttribute("RubTwoUnit");
		RubFiveUnit = theISOElement.getAttribute("RubFiveUnit");
		KopOneUnit  = theISOElement.getAttribute("KopOneUnit");
		KopTwoUnit  = theISOElement.getAttribute("KopTwoUnit");
		KopFiveUnit = theISOElement.getAttribute("KopFiveUnit");
		RubSex      = theISOElement.getAttribute("RubSex");
		KopSex      = theISOElement.getAttribute("KopSex");
		*/
		RubOneUnit  = "гривн€";
		RubTwoUnit  = "гривнi";
		RubFiveUnit = "гривень";
		KopOneUnit  = "копiйка";
		KopTwoUnit  = "копiйки";
		KopFiveUnit = "копiйок";
		RubSex      = "F";
		KopSex      = "F";
	}
 
	public String moneytostr(Double theMoney) {
		money2str=new StringBuffer();
		int triadNum = 0;
		int theTriad;
 
		int intPart = theMoney.intValue();
		int fractPart = (int) Math.round((theMoney.doubleValue() - intPart)*100);
		if (intPart == 0) money2str.append("Ќоль ");
		do {
			theTriad = intPart % 1000;
			money2str.insert(0, triad2Word(theTriad, triadNum, RubSex));
			if (triadNum == 0) {
				int range10 = (theTriad % 100) / 10;
				int range = theTriad % 10;
				if (range10 == 1) {
					money2str = money2str.append(RubFiveUnit);
				} else {
					switch (range) {
						case 1: money2str = money2str.append(RubOneUnit); break;
						case 2:
						case 3:
						case 4: money2str = money2str.append(RubTwoUnit); break;
						default: money2str = money2str.append(RubFiveUnit); break;
					}
				}
			}
			intPart = intPart/1000;
			triadNum++;
		}while(intPart > 0); 
		//while(theTriad !=0);
 
		/*money2str = money2str.append(" ").append(triad2Word(fractPart, 0, KopSex));
		if ((fractPart % 10) == 1) {
			money2str = money2str.append(KopOneUnit);
		} else {
			switch (fractPart % 10) {
				case 0:{
					if(fractPart==0){
						money2str.append("ноль ");
					};
					money2str = money2str.append(KopFiveUnit); break;
				}
				case 1: money2str = money2str.append(KopOneUnit); break;
				case 2:
				case 3:
				case 4: money2str = money2str.append(KopTwoUnit); break;
				default:  money2str = money2str.append(KopFiveUnit); break;
			}
		}
		money2str.setCharAt(0, Character.toUpperCase(money2str.charAt (0)));
		 */
		MessageFormat format=new MessageFormat(" {0,number,00} коп.");
		money2str.append(format.format(new Object[]{new Double(fractPart)}));
		money2str.setCharAt(0, Character.toUpperCase(money2str.charAt(0)));
		return money2str.toString();
	}
 
	static private String triad2Word(int triad, int triadNum, String Sex) {
		StringBuffer triadWord = new StringBuffer(28); // дев€тьсот восемьдес€т четыре - 28 достаточно ?
 
		if (triad == 0) {
			return triadWord.toString();
		}
		
		int range = triad / 100;
		switch (range) {
			default: break;
			/*case 1:  triadWord = triadWord.append("сто ");       break;
			case 2:  triadWord = triadWord.append("двести ");    break;
			case 3:  triadWord = triadWord.append("триста ");    break;
			case 4:  triadWord = triadWord.append("четыреста "); break;
			case 5:  triadWord = triadWord.append("п€тьсот ");   break;
			case 6:  triadWord = triadWord.append("шестьсот ");  break;
			case 7:  triadWord = triadWord.append("семьсот ");   break;
			case 8:  triadWord = triadWord.append("восемьсот "); break;
			case 9:  triadWord = triadWord.append("дев€тьсот "); break;*/
			case 1:  triadWord = triadWord.append("сто ");       break;
			case 2:  triadWord = triadWord.append("двiстi ");    break;
			case 3:  triadWord = triadWord.append("триста ");    break;
			case 4:  triadWord = triadWord.append("чотириста "); break;
			case 5:  triadWord = triadWord.append("п'€тсот ");   break;
			case 6:  triadWord = triadWord.append("шiстсот ");  break;
			case 7:  triadWord = triadWord.append("сiмсот ");   break;
			case 8:  triadWord = triadWord.append("вiсiмсот "); break;
			case 9:  triadWord = triadWord.append("дев'€тсот "); break;
		}
		
		range = (triad % 100) / 10;
		switch (range) {
			default: break;
			case 2:  triadWord = triadWord.append("двадц€ть ");    break;
			case 3:  triadWord = triadWord.append("тридц€ть ");    break;
			case 4:  triadWord = triadWord.append("сорок ");       break;
			case 5:  triadWord = triadWord.append("п'€тдес€т ");   break;
			case 6:  triadWord = triadWord.append("шiстдес€т ");  break;
			case 7:  triadWord = triadWord.append("сiмдес€т ");   break;
			case 8:  triadWord = triadWord.append("вiсiмдес€т "); break;
			case 9:  triadWord = triadWord.append("дев'€носто ");   break;
		}
		
		int range10 = range;
		range = triad % 10;
		if (range10 == 1) {
			switch (range) {
				case 0: triadWord = triadWord.append("дес€ть ");       break;
				case 1: triadWord = triadWord.append("одинадц€ть ");  break;
				case 2: triadWord = triadWord.append("дванадц€ть ");   break;
				case 3: triadWord = triadWord.append("тринадц€ть ");   break;
				case 4: triadWord = triadWord.append("чотирнадц€ть "); break;
				case 5: triadWord = triadWord.append("п'€тнадц€ть ");   break;
				case 6: triadWord = triadWord.append("шiстнадц€ть ");  break;
				case 7: triadWord = triadWord.append("сiмнадц€ть ");   break;
				case 8: triadWord = triadWord.append("вiсiмнадц€ть "); break;
				case 9: triadWord = triadWord.append("дев'€тнадц€ть "); break;
			}
		} else {
			switch (range) {
				default: break;
			        case 1:	if (triadNum == 1)
						triadWord = triadWord.append("одна ");
					else
						if (Sex.equals("M")) triadWord = triadWord.append("один ");
						if (Sex.equals("F")) triadWord = triadWord.append("одна ");
					break;
				case 2: if (triadNum == 1)
						triadWord = triadWord.append("две ");
					else
						if (Sex.equals("M")) triadWord = triadWord.append("два ");
						if (Sex.equals("F")) triadWord = triadWord.append("двi ");
					break;
				case 3: triadWord = triadWord.append("три ");    break;
				case 4: triadWord = triadWord.append("чотири "); break;
				case 5: triadWord = triadWord.append("п'€ть ");   break;
				case 6: triadWord = triadWord.append("шiсть ");  break;
				case 7: triadWord = triadWord.append("сiм ");   break;
				case 8: triadWord = triadWord.append("вiсiм "); break;
				case 9: triadWord = triadWord.append("дев'€ть "); break;
			}
		}
	
	switch (triadNum) {
		default: break;
		case 1:	if (range10 == 1)
				triadWord = triadWord.append("тис€ч ");
		        else {
				switch (range) {
					default: triadWord = triadWord.append("тис€ч ");  break;
					case 1:  triadWord = triadWord.append("тис€ча "); break;
					case 2:
					case 3:
					case 4:  triadWord = triadWord.append("тис€чi "); break;
				}
			}
			break;
		case 2: if (range10 == 1)
				triadWord = triadWord.append("мiльйонiв ");
			else {
				switch (range) {
					default: triadWord = triadWord.append("мiльйонiв "); break;
					case 1:  triadWord = triadWord.append("мiльйон ");   break;
					case 2:
					case 3:
					case 4:  triadWord = triadWord.append("мiльйону ");  break;
				}
			}
			break;
		case 3: if (range10 == 1)
				triadWord = triadWord.append("мiлiардiв ");
			else {
				switch (range) {
					default: triadWord = triadWord.append("мiлiардiв "); break;
					case 1:  triadWord = triadWord.append("мiлiард ");   break;
					case 2:
					case 3:
					case 4:  triadWord = triadWord.append("мiлiарду ");  break;
				}
			}
			break;
		case 4: if (range10 == 1)
				triadWord = triadWord.append("трилiонiв ");
			else {
				switch (range) {
					default: triadWord = triadWord.append("трилiонiв "); break;
					case 1:  triadWord = triadWord.append("трилiон ");   break;
					case 2:
					case 3:
					case 4:  triadWord = triadWord.append("трилiону ");  break;
				}
			}
			break;
		}
	return triadWord.toString();
	}
}

/*
<?xml version="1.0" encoding="windows-1251" standalone="yes"?>
<CurrencyList>
 
    <RUR CurrID="810" CurrName="–оссийские рубли"
         RubOneUnit="рубль" RubTwoUnit="рубл€" RubFiveUnit="рублей" RubSex="M"
         KopOneUnit="копейка" KopTwoUnit="копейки" KopFiveUnit="копеек" KopSex="F"
    />
 
    <DEM CurrID="276" CurrName="Ќемецкие марки"
         RubOneUnit="марка" RubTwoUnit="марки" RubFiveUnit="марок" RubSex="F"
         KopOneUnit="пфенниг" KopTwoUnit="пфеннига" KopFiveUnit="пфеннигов" KopSex="M"
    />
 
    <USD CurrID="840" CurrName="ƒоллары —Ўј"
         RubOneUnit="доллар" RubTwoUnit="доллара" RubFiveUnit="долларов" RubSex="M"
         KopOneUnit="цент" KopTwoUnit="цента" KopFiveUnit="центов" KopSex="M"
    />
 
</CurrencyList>


*/