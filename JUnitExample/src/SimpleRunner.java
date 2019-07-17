import org.junit.runner.Description;
import org.junit.runner.Runner;
import org.junit.runner.notification.RunListener;
import org.junit.runner.notification.RunNotifier;

public class SimpleRunner extends Runner{
	public SimpleRunner(Class clazz){
	}
	@Override
	public Description getDescription() {
		Description description=Description.createSuiteDescription("Demonstration simple Runner");
		description.addChild(Description.createTestDescription(JUnitExample.class, "Control"));
		return description;
	}

	@Override
	public void run(RunNotifier notifier) {
		notifier.addListener(new RunListener(){
			@Override
			public void testStarted(Description description) throws Exception {
				super.testStarted(description);
				System.out.println("Test was started: "+description);
			}
		});
	}
}
