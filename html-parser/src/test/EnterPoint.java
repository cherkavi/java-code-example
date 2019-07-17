package test;

import shop_list.database.connector.ConnectorSingleton;

import shop_list.html.parser.engine.EParseState;
import shop_list.html.parser.engine.IDetectEndOfParsing;
import shop_list.html.parser.engine.IManager;
import shop_list.html.parser.engine.logger.DatabaseLogger;
import shop_list.html.parser.engine.logger.ELoggerLevel;
import shop_list.html.parser.engine.logger.ILogger;
import shop_list.html.parser.engine.saver.DatabaseSaverConnection;
import shops.*;

public class EnterPoint implements IDetectEndOfParsing{
	public static void main(String[] args){
		// BasicConfigurator.configure();
		// Logger.getRootLogger().setLevel(Level.DEBUG);
		ConnectorSingleton.pathToDatabase="D:\\eclipse_workspace\\TempParser\\database\\SHOP_LIST_PARSE.GDB";
		
		// IManager source=new Lubny();
		// IManager source=new _1Brovary_com_ua();
		// IManager source=new _224_com_ua();
		// IManager source=new best_optics_com_ua();
		// IManager source=new atlant_ua_com();
		// IManager source=new ariston_ua_com();
		// IManager source=new e_home_com_ua();
		// IManager source=new lavka_kiev_ua();
		// IManager source=new mirbt_com_ua();
		// IManager source=new randl_com_ua();
		// IManager source=new shop_pcforsage_com_ua();
		// IManager source=new sova_shop_com_ua();
		// IManager source=new tdd_com_ua();
		// IManager source=new telemarket_com_ua(); // - проверить путь к файлу
		// IManager source=new vasap_com_ua();
		// IManager source=new agsat_com_ua();
		// IManager source=new denon_com_ua();
		// IManager source=new elsys_kiev_ua();
		// IManager source=new hi_star_com_ua();
		// IManager source=new numberone_kiev_ua();
		// IManager source=new skidka_com_ua();
		// IManager source=new s_mobile_com_ua();
		// IManager source=new tehnos_com_ua(); // проверить на увеличение размера памяти для виртуальной машины 
		// IManager source=new tpobut_com();
		// IManager source=new digital_in_ua(); 
		// IManager source=new kooperator_com_ua();
		// IManager source=new comfortshop_com_ua(); // проверить 
		// IManager source=new flashki_kiev_ua();
		// IManager source=new msr_kiev_ua();
		// IManager source=new tvplaneta_com_ua();
		// IManager source=new video_ochki_com_ua(); // проверить 
		// IManager source=new alma2k_kiev_ua(); 
		// IManager source=new atlas_com_ua(); 
		// IManager source=new e_lampa_com_ua();
		// IManager source=new flashtrade_com_ua();
		// IManager source=new skytel_com_ua();
		// IManager source=new absolutua_com();
		// IManager source=new _590_com_ua();
		
		IManager source=new _5ok_com_ua(); // проверить
		ILogger logger=new DatabaseLogger(true);
		logger.setLevel(ELoggerLevel.INFO);
		source.setLogger(logger);
		source.setSaver(new DatabaseSaverConnection(logger));
		
		source.start(new EnterPoint());
	}

	@Override
	public void endParsing(IManager manager, EParseState parseEndEvent) {
		System.out.println("Page: " +manager.getShopUrlStartPage()+" : "+parseEndEvent.toString());
	}
	
}
