package provider;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ParameterReader {

    private String mProfile;
    private int mWidth, mHeight;
    private boolean mIsFullscreen, mHeadless;

    private XPath mXPath;
    private XPathExpression mExpression;
    private Document mDocument;

    private final Logger logger = Logger.getLogger(ParameterReader.class.getName());

    public ParameterReader() {
        setupXMLParser();
        readParameters();
    }

    /**
     * Getter for custom boolean value
     *
     * @return boolean
     */
    public boolean getCustomBoolean(String tag){
        return parseCustomBoolean(tag);
    }

    /**
     * Getter for custom int value
     *
     * @return int
     */
    public int getCustomInt(String tag){
        return parseCustomInt(tag);
    }

    /**
     * Getter for custom String value
     *
     * @return String
     */
    public String getCustomString(String tag){
        return parseCustomString(tag);
    }

    /**
     * Getter for 'fullscreen' value
     *
     * @return boolean -> 'fullscreen' value
     */
    public boolean getFullscreen(){
        return mIsFullscreen;
    }

    /**
     * Getter for 'headless' value
     *
     * @return boolean -> 'headless' value
     */
    public boolean getHeadless(){
        return mHeadless;
    }

    /**
     * Getter for 'window_height' value
     *
     * @return int -> 'window_height' value
     */
    public int getHeight(){
        return mHeight;
    }

    /**
     * Getter for 'profile' value
     *
     * @return String -> 'profile' value
     */
    public String getProfile(){
        return mProfile;
    }

    /**
     * Getter for 'window_width' value
     *
     * @return int -> 'window_width' value
     */
    public int getWidth(){
        return mWidth;
    }

    /**
     * Reads parameters from 'parameters.xml'
     */
    private void readParameters() {
        mHeadless = parseHeadless();
        mProfile = parseProfile();
        mIsFullscreen = parseFullscreen();
        mWidth = parseWidth();
        mHeight = parseHeight();
    }

    /**
     * Parses custom tag boolean value from
     *
     * @return boolean
     */
    private boolean parseCustomBoolean(String tag){
        try {
            mExpression = mXPath.compile("/parameters/custom/" + tag + "/text()");
            return  Boolean.parseBoolean((String) mExpression.evaluate(mDocument, XPathConstants.STRING));
        } catch (XPathExpressionException e){
            logger.log(Level.WARNING,
                    "There was an issue parsing fullscreen parameter. Please refer to the following error: " + e);
            return false;
        }
    }

    /**
     * Parses custom tag int value from
     *
     * @return int
     */
    private int parseCustomInt(String tag){
        try {
            mExpression = mXPath.compile("/parameters/custom/" + tag + "/text()");
            return  Integer.parseInt((String) mExpression.evaluate(mDocument, XPathConstants.STRING));
        } catch (XPathExpressionException e){
            logger.log(Level.WARNING,
                    "There was an issue parsing fullscreen parameter. Please refer to the following error: " + e);
            return -1;
        }
    }

    /**
     * Parses custom tag String value from
     *
     * @return String
     */
    private String parseCustomString(String tag){
        try {
            mExpression = mXPath.compile("/parameters/custom/" + tag + "/text()");
            return  (String) mExpression.evaluate(mDocument, XPathConstants.STRING);
        } catch (XPathExpressionException e){
            logger.log(Level.WARNING,
                    "There was an issue parsing fullscreen parameter. Please refer to the following error: " + e);
            return null;
        }
    }

    /**
     * Parses 'fullscreen' value from 'parameters.xml'
     *
     * @return boolean -> 'fullscreen' value
     */
    private boolean parseFullscreen(){
        try {
            mExpression = mXPath.compile("/parameters/browser/fullscreen/text()");
            return  Boolean.parseBoolean((String) mExpression.evaluate(mDocument, XPathConstants.STRING));
        } catch (XPathExpressionException e){
            logger.log(Level.WARNING,
                    "There was an issue parsing fullscreen parameter. Please refer to the following error: " + e);
            return false;
        }
    }

    /**
     * Parses 'headless' value from 'parameters.xml'
     *
     * @return boolean -> 'headless' value
     */
    private boolean parseHeadless(){
        try {
            mExpression = mXPath.compile("/parameters/browser/headless/text()");
            return  Boolean.parseBoolean((String) mExpression.evaluate(mDocument, XPathConstants.STRING));
        } catch (XPathExpressionException e){
            logger.log(Level.WARNING,
                    "There was an issue parsing headless parameter. Please refer to the following error: " + e);
            return false;
        }
    }

    /**
     * Parses 'window_height' value from 'parameters.xml'
     *
     * @return int -> 'window_height' value
     */
    private int parseHeight(){
        try {
            mExpression = mXPath.compile("/parameters/browser/window_height/text()");
            return  Integer.parseInt((String) mExpression.evaluate(mDocument, XPathConstants.STRING));
        } catch (XPathExpressionException e){
            logger.log(Level.WARNING,
                    "There was an issue parsing height parameter. Please refer to the following error: " + e);
            return -1;
        }
    }

    /**
     * Parses 'profile' value from 'parameters.xml'
     *
     * @return String -> 'profile' value
     */
    private String parseProfile(){
        try {
            mExpression = mXPath.compile("/parameters/browser/profile/text()");
            return (String) mExpression.evaluate(mDocument, XPathConstants.STRING);
        } catch (XPathExpressionException e){
            logger.log(Level.WARNING,
                    "There was an issue parsing profile parameter. Please refer to the following error: " + e);
            return null;
        }
    }

    /**
     * Parses 'window_width' value from 'parameters.xml'
     *
     * @return int -> 'window_width' value
     */
    private int parseWidth(){
        try {
            mExpression = mXPath.compile("/parameters/browser/window_width/text()");
            return  Integer.parseInt((String) mExpression.evaluate(mDocument, XPathConstants.STRING));
        } catch (XPathExpressionException e){
            logger.log(Level.WARNING,
                    "There was an issue parsing width parameter. Please refer to the following error: " + e);
            return -1;
        }
    }

    /**
     * Sets up xml parser
     */
    private void setupXMLParser(){
        try {
            File param = new File("src/test/resources/config/parameters/parameters.xml");
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            mDocument = builder.parse(param);
            XPathFactory xFactory = XPathFactory.newInstance();
            mXPath = xFactory.newXPath();
        } catch (ParserConfigurationException | SAXException | IOException e){
            logger.log(Level.WARNING,
                    "There was an issue with setting up parser. Please refer to the following error: " + e);
        }
    }
}
