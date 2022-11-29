import java.text.SimpleDateFormat;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.RemoteWebElement;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

public class DesCap {
	
	//***** Define capabilities variable ***********
	static AndroidDriver <MobileElement> driverApp;
	static AppiumDriver <MobileElement> driverApp1;
	static String callingClass;	    
	static DesiredCapabilities dc = new DesiredCapabilities();
	static String methodToCall = "startScreen";
	
	
	public static void setDesCap(final String deviceName, final String platformName, final String appPackage, final String appActivity) throws MalformedURLException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
		dc.setCapability("deviceName", "AS6NVB2309002938");    
		//dc.setCapability("deviceName", "3RP4C18418001857");
        dc.setCapability("appPackage", "com.droid4you.application.wallet");
        dc.setCapability("platformName", "Android");        
        dc.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");           
        System.out.println(dc);
        //dc.setCapability("appActivity", "com.droid4you.application.wallet.component.onboarding.OnboardingActivity");    
        dc.setCapability("appActivity", "com.droid4you.application.wallet.SplashScreenActivity");    
        dc.setCapability("autoGrantPermissions", false);
        dc.setCapability("noReset", "true");
        dc.setCapability("resetKeyboard", true);
        driverApp = new AndroidDriver<MobileElement>(new URL("http://127.0.0.1:4723/wd/hub"),dc);  
        //The following is to pass variables declared here back to the class which called this class
        callingClass = new Exception().getStackTrace()[1].getClassName(); 
        Class<?> cls = Class.forName(callingClass);
        Object obj = cls.newInstance();	
        String class_name = cls.getName();
        System.out.println("This is " + class_name + " calling");	
        Class[] arg = new Class[2];
	    arg[0] = DesiredCapabilities.class;
	    arg[1] =  AndroidDriver.class;
	    Method method = cls.getDeclaredMethod(methodToCall, arg[0], arg[1]);
		method.invoke(obj, dc, driverApp);	  
	} //end setDesCap method
}//end public class DesCap




