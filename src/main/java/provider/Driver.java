package provider;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Driver {

    private final String mProfile;
    private final int mWidth, mHeight;
    private final boolean mIsFullscreen;

    private final String GECKO_DRIVER = "src/test/resources/drivers/geckodriver.exe";
    private final String CHROME_DRIVER = "src/test/resources/drivers/chromedriver.exe";

    private WebDriver mDriver;

    public Driver(ParameterReader parameterReader){
        mProfile = parameterReader.getProfile();
        mIsFullscreen = parameterReader.getFullscreen();
        mWidth = parameterReader.getWidth();
        mHeight = parameterReader.getHeight();
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
                mDriver = new FirefoxDriver();
                if (mIsFullscreen){
                    mDriver.manage().window().fullscreen();
                } else {
                    Dimension dimension = new Dimension(mWidth, mHeight);
                    mDriver.manage().window().setSize(dimension);
                }
                break;
            case "chrome":
                System.setProperty("webdriver.gecko.driver", CHROME_DRIVER);
                mDriver = new ChromeDriver();
                if (mIsFullscreen){
                    mDriver.manage().window().fullscreen();
                } else {
                    Dimension dimension = new Dimension(mWidth, mHeight);
                    mDriver.manage().window().setSize(dimension);
                }
                break;
            default:
                // Left for future
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
}
