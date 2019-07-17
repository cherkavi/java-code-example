package com.cherkashyn.vitalii.tools.sqlexecutor;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.io.File;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class SqlexecutorApplicationTests {

	@Test
	public void readArguments() {
		// given
		String[] arguments= new String []{"-brandserveryaml", "C:\\project\\ccp-brand-server\\migration-job\\build\\resources\\main\\application.yml ",
				"-sqlfile", "C:\\temp\\1.sql",
				"pinmailer=968",
				"brandname=229099017"};

		// when
		InputParameters input = SqlexecutorApplication.readFromCommandLine(arguments);

		// then
		Assert.assertNotNull(input);
		Assert.assertNotNull(input.getBrandServerSettings());
		Assert.assertNotNull(input.getSqlTemplate());
		Assert.assertNotNull(input.getTemplateValues());
		Assert.assertEquals(2, input.getTemplateValues().size());
		Assert.assertTrue(input.getTemplateValues().get(0).startsWith("pinmailer"));
		Assert.assertTrue(input.getTemplateValues().get(1).startsWith("brandname"));
	}

	@Test
	public void readDataSource() {
		// given
		String pathToTestFile = Thread.currentThread().getContextClassLoader().getResource("application.yml").getFile();
		File file = new File(pathToTestFile);

		// when
		Connection connection = SqlexecutorApplication.getDataSource(file);

		// then
		Assert.assertNotNull(connection);
	}


	@Test
	public void readTemplate() {
		// given
		String pathToTestFile = Thread.currentThread().getContextClassLoader().getResource("1.sql").getFile();
		File file = new File(pathToTestFile);

		// when
		String result = SqlexecutorApplication.enrichTemplate(file,Arrays.asList("pinmailer=333333", "brandname=2222"));

		// then
		Assert.assertEquals("update PAYMENT_CARD set pin_mailer = '333333' where id = '3cae4940-72d1-4fb4-9866-1064f24a1770';", result);
	}

	@Test
	public void convertConsoleParameters(){
		// given
		List<String> values = Arrays.asList("my.name = 10", "another.name.value=value for test");

		// when
		Map<String, String> result = SqlexecutorApplication.parseToPairs(values);

		// then
		Assert.assertEquals("10", result.get("my.name"));
		Assert.assertEquals("value for test", result.get("another.name.value"));
	}



}
