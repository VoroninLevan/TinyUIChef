package provider;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;


public class Driver {

    private final String mProfile;
    private final int mWidth, mHeight;
    private final boolean mIsFullscreen, mHeadless;

    private final String GECKO_DRIVER = "src/test/resources/drivers/geckodriver.exe";
    private final String CHROME_DRIVER = "src/test/resources/drivers/chromedriver.exe";
    private final String EDGE_DRIVER = "src/test/resources/drivers/msedgedriver.exe";

    private WebDriver mDriver;

    public Driver(ParameterReader parameterReader){
        mProfile = parameterReader.getProfile();
        mIsFullscreen = parameterReader.getFullscreen();
        mWidth = parameterReader.getWidth();
        mHeight = parameterReader.getHeight();
        mHeadless = parameterReader.getHeadless();
    }

    /**
     * Sets up and starts browser depends on the parameters provided in 'parameters.xml'
     * NOTE: Currently supports 2 browsers: Firefox and Chrome
     * If 'fullscreen' value is false -> will set width and height of window depends on respective
     * values from 'parameters.xml'
     */
    protected void startBrowser(){
        switch (mProfile){
            case "firefox":
                System.setProperty("webdriver.gecko.driver", GECKO_DRIVER);
                if (mHeadless){
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    firefoxOptions.addArguments("-headless");
                    mDriver = new FirefoxDriver(firefoxOptions);
                } else {
                    mDriver = new FirefoxDriver();
                    if (mIsFullscreen) {
                        mDriver.manage().window().fullscreen();
                    } else {
                        Dimension dimension = new Dimension(mWidth, mHeight);
                        mDriver.manage().window().setSize(dimension);
                    }
                }
                break;
            case "chrome":
                System.setProperty("webdriver.chrome.driver", CHROME_DRIVER);
                if (mHeadless){
                    ChromeOptions chromeOptions = new ChromeOptions();
                    chromeOptions.addArguments("--headless");
                    mDriver = new ChromeDriver(chromeOptions);
                } else {
                    mDriver = new ChromeDriver();
                    if (mIsFullscreen) {
                        mDriver.manage().window().fullscreen();
                    } else {
                        Dimension dimension = new Dimension(mWidth, mHeight);
                        mDriver.manage().window().setSize(dimension);
                    }
                }
                break;
            case "edge":
                System.setProperty("webdriver.edge.driver", EDGE_DRIVER);
                if (mHeadless){
                    EdgeOptions edgeOptions = new EdgeOptions();
                    edgeOptions.addArguments("headless");
                    edgeOptions.addArguments("disable-gpu");
                    mDriver = new EdgeDriver(edgeOptions);
                } else {
                    mDriver = new EdgeDriver();
                    if (mIsFullscreen) {
                        mDriver.manage().window().fullscreen();
                    } else {
                        Dimension dimension = new Dimension(mWidth, mHeight);
                        mDriver.manage().window().setSize(dimension);
                    }
                }
                break;
        }
    }

    /**
     * Getter for WebDriver object
     *
     * @return WebDriver -> WebDriver object
     */
    public WebDriver getDriver(){
        startBrowser();
        return mDriver;
    }

    /**
     * Getter for headless value
     *
     * @return boolean
     */
    public boolean getHeadless(){
        return mHeadless;
    }
}
