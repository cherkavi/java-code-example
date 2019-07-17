package com.cherkashyn.vitalii.tools.database.diff;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import com.cherkashyn.vitalii.tools.database.diff.gui.ButtonModel;
import com.cherkashyn.vitalii.tools.database.diff.gui.MainFrame;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws FileNotFoundException, IOException
    {
    	checkArguments(args);
    	Properties properties=new Properties();
    	properties.load(new FileInputStream(args[0]));
        new MainFrame(convertToButtonModels(properties));
    }

	private static ButtonModel[] convertToButtonModels(
			Properties properties) {
		List<ButtonModel> models=new ArrayList<ButtonModel>();
		Enumeration<?> enumeration=properties.keys();
		while(enumeration.hasMoreElements()){
			String title=enumeration.nextElement().toString();
			String command=properties.get(title).toString();
			models.add(new ButtonModel(command, title));
		}
		return models.toArray(new ButtonModel[models.size()]);
	}

	private static void checkArguments(String[] args) {
    	if(args.length==0){
    		System.err.println("need to have properties file with executors ");
    		System.exit(1);
    	}
	}
}
