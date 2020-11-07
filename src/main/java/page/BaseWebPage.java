package page;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BaseWebPage {

    private final WebDriverWait mWait;
    protected WebDriver mDriver;
    private final Logger logger = Logger.getLogger(BaseWebPage.class.getName());

    public static final int XPATH = 1;
    public static final int ID = 2;
    public static final int CLASS_NAME = 3;

    public BaseWebPage(WebDriver driver){
        mWait = new WebDriverWait(driver, 30);
        mDriver = driver;
    }

    /**
     * Clears text field
     *
     * @param by int -> identifier:
     *           1 - xPath,
     *           2 - Id,
     *           3 - Class name
     * @param identifier String -> Element unique identifier
     */
    protected void clearTextField(int by, String identifier){
        WebElement element = findElement(by, identifier);
        if (element != null) {
            element.clear();
        }
    }

    /**
     * Clicks element by xPath
     *
     * @param xPath String -> Element xPath
     */
    protected void clickByXPath(String xPath){
        try {
            mWait.until(ExpectedConditions.elementToBeClickable(By.xpath(xPath))).click();
            logger.log(Level.INFO, "Clicked element with xPath: " + xPath);
        } catch (NoSuchElementException e){
            logger.log(Level.WARNING, "Element not found by xPath: " + xPath);
        }
    }

    /**
     * Clicks element by id
     *
     * @param id String -> Element id attribute
     */
    protected void clickById(String id){
        try {
            mWait.until(ExpectedConditions.elementToBeClickable(By.id(id))).click();
            logger.log(Level.INFO, "Clicked element with id: " + id);
        } catch (NoSuchElementException e){
            logger.log(Level.WARNING, "Element not found by id: " + id);
        }
    }

    /**
     * Clicks element by class name
     *
     * @param className String -> Element class attribute
     */
    protected void clickByClassName(String className){
        try {
            mWait.until(ExpectedConditions.elementToBeClickable(By.className(className))).click();
            logger.log(Level.INFO, "Clicked element with class name: " + className);
        } catch (NoSuchElementException e){
            logger.log(Level.WARNING, "Element not found by class name: " + className);
        }
    }

    /**
     * Sets text into text field
     *
     * @param by int -> identifier:
     *           1 - xPath,
     *           2 - Id,
     *           3 - Class name
     * @param identifier String -> Element unique identifier
     * @param text String -> desired string
     */
    protected void enterText(int by, String identifier, String text){
        WebElement element = findElement(by, identifier);
        if (element != null) {
            element.sendKeys(text);
        }
    }

    /**
     * Finds and returns list of web elements
     *
     * @param by int -> identifier:
     *           1 - xPath,
     *           2 - Id,
     *           3 - Class name
     * @param identifier String -> Element unique identifier
     * @return List -> list of web elements
     */
    protected List<WebElement> getElements(int by, String identifier){
        switch (by){
            case XPATH:
                try {
                    return mDriver.findElements(By.xpath(identifier));
                } catch (NoSuchElementException e){
                    logger.log(Level.WARNING, "Elements not found by xPath: " + identifier);
                }
            case ID:
                try {
                    return mDriver.findElements(By.id(identifier));
                } catch (NoSuchElementException e){
                    logger.log(Level.WARNING, "Elements not found by id: " + identifier);
                }
            case CLASS_NAME:
                try {
                    return mDriver.findElements(By.className(identifier));
                } catch (NoSuchElementException e){
                    logger.log(Level.WARNING, "Elements not found by class name: " + identifier);
                }
            default:
                return null;
        }
    }

    /**
     * Retrieves and returns elements attribute
     *
     * @param by int -> identifier:
     *           1 - xPath,
     *           2 - Id,
     *           3 - Class name
     * @param identifier String -> Element unique identifier
     * @return String -> attribute
     */
    protected String getElementsAttribute (int by, String identifier, String attribute){
        WebElement element = findElement(by, identifier);
        return (element != null) ? element.getAttribute(attribute) : "";
    }

    /**
     * Retrieves and returns text of web element
     *
     * @param by int -> identifier:
     *           1 - xPath,
     *           2 - Id,
     *           3 - Class name
     * @param identifier String -> Element unique identifier
     * @return String -> text
     */
    protected String getElementText(int by, String identifier){
        WebElement element = findElement(by, identifier);
        if (element != null) return element.getText();
        return null;
    }

    /**
     * Returns 'true' in case if element can be located
     *
     * @param by int -> identifier:
     *           1 - xPath,
     *           2 - Id,
     *           3 - Class name
     * @param identifier String -> Element unique identifier
     * @return boolean
     */
    protected boolean isElementPresent(int by, String identifier){
        switch (by){
            case XPATH:
                try {
                    mDriver.findElement(By.xpath(identifier));
                    return true;
                } catch (NoSuchElementException e){
                    return false;
                }
            case ID:
                try {
                    mDriver.findElement(By.id(identifier));
                    return true;
                } catch (NoSuchElementException e){
                    return false;
                }
            case CLASS_NAME:
                try {
                    mDriver.findElement(By.className(identifier));
                    return true;
                } catch (NoSuchElementException e){
                    return false;
                }
            default:
                return false;
        }
    }

    /**
     * Selects from drop-down by visible text
     *
     * @param by int -> identifier:
     *           1 - xPath,
     *           2 - Id,
     *           3 - Class name
     * @param identifier String -> Element unique identifier
     * @param text String -> desired option
     */
    protected void selectByText(int by, String identifier, String text){
        WebElement element = findElement(by, identifier);
        if (element != null) {
            Select select = new Select(element);
            select.selectByValue(text);
        }
    }

    /**
     * Waits until element be visible by provided xPath
     *
     * @param identifier String -> element xPath
     */
    protected void waitForVisibilityOfElementByXpath(String identifier){
        mWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(identifier)));
    }

    /**
     * Waits until element be invisible by provided xPath
     *
     * @param identifier String -> element xPath
     */
    protected void waitForInvisibilityOfElementByXpath(String identifier){
        mWait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(identifier)));
    }

    /**
     * Finds and returns web element on the page
     * Can be used from child classes
     *
     * @param by int -> identifier:
     *           1 - xPath,
     *           2 - Id,
     *           3 - Class name
     * @param identifier String -> Element unique identifier
     * @return WebElement
     */
    protected WebElement getElement(int by, String identifier){
        return findElement(by, identifier);
    }

    /**
     * Finds and returns web element on the page
     *
     * @param by int -> identifier:
     *           1 - xPath,
     *           2 - Id,
     *           3 - Class name
     * @param identifier String -> Element unique identifier
     * @return WebElement
     */
    private WebElement findElement(int by, String identifier){
        switch (by){
            case XPATH:
                try {
                    return mWait.until(ExpectedConditions.elementToBeClickable(By.xpath(identifier))).findElement(By.xpath(identifier));
                } catch (NoSuchElementException e){
                    logger.log(Level.WARNING, "Element not found by xPath: " + identifier);
                }
            case ID:
                try {
                    return mWait.until(ExpectedConditions.elementToBeClickable(By.xpath(identifier))).findElement(By.xpath(identifier));
                } catch (NoSuchElementException e){
                    logger.log(Level.WARNING, "Element not found by id: " + identifier);
                }
            case CLASS_NAME:
                try {
                    return mWait.until(ExpectedConditions.elementToBeClickable(By.xpath(identifier))).findElement(By.xpath(identifier));
                } catch (NoSuchElementException e){
                    logger.log(Level.WARNING, "Element not found by class name: " + identifier);
                }
            default:
                return null;
        }
    }
}
