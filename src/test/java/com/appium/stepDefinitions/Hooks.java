/**
 * @author Hagar Abdelsamad Elgabry
 */
package com.appium.stepDefinitions;

import java.io.IOException;

import org.openqa.selenium.OutputType;

import com.appium.manager.DriverManager;
import com.appium.manager.VideoManager;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

/*Hooks run before and after every Cucumber Scenario*/
public class Hooks {

	@Before
	public void initialize() throws Exception {

		new VideoManager().startRecording();
	}

	@After
	public void quit(Scenario scenario) throws IOException {

		/* This is for attaching the screenshot in Cucumber report */
		if (scenario.isFailed()) {
			byte[] screenshot = new DriverManager().getDriver().getScreenshotAs(OutputType.BYTES);
			scenario.attach(screenshot, "image/png", scenario.getName());
		}
		new VideoManager().stopRecording(scenario.getName());
	}
}
