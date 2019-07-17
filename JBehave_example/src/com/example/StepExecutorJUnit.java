package com.example;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.io.LoadFromURL;
import org.jbehave.core.io.StoryLoader;
import org.jbehave.core.junit.JUnitStory;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.reporters.StoryReporterBuilder.Format;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;

public class StepExecutorJUnit extends JUnitStory {
 
	public static void main(String[] args) throws Throwable{
		new StepExecutorJUnit().run();
	}
	
	private StoryLoader getStoryLoader(){
		String pathToFile="C:\\\\JBehave_example\\src\\com\\example\\step_executor_j_unit.story";
    	LoadFromURL urlLoader=new LoadFromURL();
    	try {
			urlLoader.loadStoryAsText(FileUtils.readFileToString(new File(pathToFile)));
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("File does not found: "+pathToFile);
		}
		return urlLoader;
	}
	
    // Here we specify the configuration, starting from default MostUsefulConfiguration, and changing only what is needed
    @SuppressWarnings("deprecation")
	@Override
    public Configuration configuration() {
        return new MostUsefulConfiguration()
            // where to find the stories
            .useStoryLoader(new LoadFromClasspath(this.getClass())) 
            // CONSOLE and TXT reporting
            .useStoryReporterBuilder(new StoryReporterBuilder()
            						.withDefaultFormats()
            						.withFormats(Format.CONSOLE, Format.TXT));
    }
 
    // Here we specify the steps classes
    @Override
    public InjectableStepsFactory stepsFactory() {       
        // varargs, can have more that one steps classes
        return new InstanceStepsFactory(configuration(), new GridSteps());
    }
    
}