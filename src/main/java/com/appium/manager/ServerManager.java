/**
 * @author Hagar Abdelsamad Elgabry
 */
package com.appium.manager;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServerHasNotBeenStartedLocallyException;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

import java.io.File;
import java.util.HashMap;

import com.appium.utils.TestUtils;
import com.appium.utils.UsernameUtils;


public class ServerManager {

    private static ThreadLocal<AppiumDriverLocalService> server = new ThreadLocal<>();

    TestUtils utils = new TestUtils();

    public AppiumDriverLocalService getServer() {
        return server.get();
    }

    public void startServer() {
        utils.log().info("starting appium server");

        /* Windows */
        // AppiumDriverLocalService server = getAppiumServiceForWindows();

        /* MAC */
        AppiumDriverLocalService server = getAppiumServiceForMac();
        server.start();

        if (server == null || !server.isRunning()) {
            utils.log().fatal("Appium server not started. ABORT!!!");
            throw new AppiumServerHasNotBeenStartedLocallyException("Appium server not started. ABORT!!!");
        }
        /* This will not print the Appium server Logs in IDE console */
        server.clearOutPutStreams();
        this.server.set(server);
        utils.log().info("Appium server started");
    }

    public AppiumDriverLocalService getAppiumServerDefault() {
        return AppiumDriverLocalService.buildDefaultService();
    }

    public AppiumDriverLocalService getAppiumServiceForWindows() {

      GlobalParams params = new GlobalParams();
        return AppiumDriverLocalService.buildService(new AppiumServiceBuilder().usingAnyFreePort()
                .withArgument(GeneralServerFlag.SESSION_OVERRIDE).withLogFile(new File(
                        params.getPlatformName() + "_" + params.getDeviceName() + File.separator + "Server.log")));
    }

    public AppiumDriverLocalService getAppiumServiceForMac() {
        GlobalParams params = new GlobalParams();
        HashMap<String, String> environment = new HashMap<String, String>();
        environment.put("PATH",
                "/Library/Internet Plug-Ins/JavaAppletPlugin.plugin/Contents/Home/bin:/Users"
						+ UsernameUtils.getUserName() + "/Library/Android/sdk/tools:/Users/"
						+ UsernameUtils.getUserName() + "/Library/Android/sdk/platform-tools:/opt/homebrew/bin:/opt/homebrew/sbin:/usr/local/bin:/usr/bin:/bin:/usr/sbin:/sbin:/Library/Apple/usr/bin"
                        + System.getenv("PATH"));
        environment.put("ANDROID_HOME", "/Users/" + UsernameUtils.getUserName() + "/Library/Android/sdk");
        return AppiumDriverLocalService.buildService(new AppiumServiceBuilder()
                .usingDriverExecutable(new File("/usr/local/bin/node"))
                .withAppiumJS(new File("/usr/local/lib/node_modules/appium/build/lib/main.js")).usingAnyFreePort()
                .withArgument(GeneralServerFlag.SESSION_OVERRIDE).withEnvironment(environment).withLogFile(new File(
                        params.getPlatformName() + "_" + params.getDeviceName() + File.separator + "Server.log")));
    }
}
