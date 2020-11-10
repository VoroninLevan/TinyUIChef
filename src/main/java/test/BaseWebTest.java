package test;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import provider.Driver;
import provider.ParameterReader;

public class BaseWebTest {

    protected WebDriver mDriver;

    public BaseWebTest(){
    }

    @BeforeMethod
    protected void beforeMethod(){
        ParameterReader reader = new ParameterReader();
        Driver driver = new Driver(reader);
        mDriver = driver.getDriver();
        System.out.println("Web exec");
    }

    @AfterMethod
    protected void afterMethod(){
        mDriver.close();
    }
}
