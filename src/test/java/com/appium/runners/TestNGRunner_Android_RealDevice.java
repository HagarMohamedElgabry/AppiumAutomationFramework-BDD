/**
 * @author Hagar Abdelsamad Elgabry
 */

package com.appium.runners;
import io.cucumber.testng.CucumberOptions;

/**
 * An example of using TestNG when the test class does not inherit from
 * AbstractTestNGCucumberTests but still executes each scenario as a separate
 * TestNG test.
 */
@CucumberOptions(plugin = { "pretty", "html:target_TestNG/Android_RealDevice/cucumber-report.html", "summary"
		, "me.jvt.cucumber.report.PrettyReports:target_TestNG/Android_RealDevice/" }, features = {
				"src/test/resources/features" }, glue = {
						"com.appium.stepDefinitions" }, dryRun = false, monochrome = true, tags = "@login-feature")
public class TestNGRunner_Android_RealDevice extends RunnerBase {
}