package com.progress.soft;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by inshaf on 10/20/17.
 */
public class FileUploaderUtils {

    private static Log logger = LogFactory.getLog(FileUploaderUtils.class);

    /**
     * Checks whether csv file has any empty or nul rows
     * @param fromCurrency
     * @param toCurrency
     * @param timestamp
     * @param amount
     * @param fileName
     * @return
     */
    public static boolean validateNullAndEmpty(String fromCurrency, String toCurrency, String timestamp, String amount, String fileName){
        boolean isValid = true;


        if(fromCurrency == null || fromCurrency.isEmpty()){
            if (logger.isDebugEnabled()) {
                logger.debug("fromCurrency is null or empty");
            }
            isValid = false;
        }else if(toCurrency == null || toCurrency.isEmpty()){
            if (logger.isDebugEnabled()) {
                logger.debug("toCurrency is null or empty");
            }
            isValid = false;
        }
        else if(timestamp == null || timestamp.isEmpty() || !isTimeStampValid(timestamp)){
            if (logger.isDebugEnabled()) {
                logger.debug("timestamp is null or empty");
            }
            isValid = false;
        }
        else if(amount == null || amount.isEmpty() || !isAmountValid(amount)){
            if (logger.isDebugEnabled()) {
                logger.debug("amount is null or empty");
            }
            isValid = false;
        }
        else if(fileName == null || fileName.isEmpty()){
            if (logger.isDebugEnabled()) {
                logger.debug("fileName is null or empty");
            }
            isValid = false;
        }

        return isValid;
    }

    /*
    Check whether records having valid timestamp
     */
    public static boolean isTimeStampValid(String inputString)
    {
        SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try{
            format.parse(inputString);
            return true;
        }
        catch(ParseException e)
        {
            logger.error("Timestamp not valid "+ e.toString());
            return false;
        }
    }

    /*
    Check whether records having valid amount
     */
    public static boolean isAmountValid(String inputString)
    {
        try
        {
            Double.parseDouble(inputString);
            return true;
        }
        catch(NumberFormatException e)
        {
            logger.error("Amount not valid "+ e.toString());
            return false;
        }
        catch(Exception e)
        {
            logger.error("Amount not valid "+ e.toString());
            return false;
        }
    }
}
