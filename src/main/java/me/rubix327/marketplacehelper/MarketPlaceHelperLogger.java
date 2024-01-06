package me.rubix327.marketplacehelper;

import lombok.Setter;
import org.slf4j.Logger;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressWarnings("unused")
public class MarketPlaceHelperLogger {

    @Setter
    private Logger logger;

    public void logEmptyMessage(){
        logMessage("");
    }

    public void logInfoMessage(String msg){
        if (logger != null){
            logger.info(msg);
        } else {
            logMessage("[" + getTime() + " INFO]: " + ConsoleColors.GREEN + msg + ConsoleColors.RESET);
        }
    }

    public void logDebugMessage(String msg){
        if (logger != null){
            logger.debug(msg);
        } else {
            logMessage("[" + getTime() + " INFO]: " + ConsoleColors.RESET + msg);
        }
    }

    public void logSuccessMessage(String msg){
        if (logger != null){
            logger.info(msg);
        } else {
            logMessage("[" + getTime() + " INFO]: " + msg);
        }
    }

    public void logWarningMessage(String msg){
        if (logger != null){
            logger.warn(msg);
        } else {
            logMessage(ConsoleColors.YELLOW_BOLD + "[" + getTime() + " WARN]: " + msg);
        }
    }

    public void logErrorMessage(String msg){
        if (logger != null){
            logger.error(msg);
        } else {
            logMessage(ConsoleColors.RED_BRIGHT + "[" + getTime() + " ERROR]: " + msg + ConsoleColors.RESET);
        }
    }

    private void logMessage(String msg){
        System.out.println(msg);
    }

    public static String getTime(){
        DateFormat format = new SimpleDateFormat("HH:mm:ss");
        return format.format(new Date());
    }

}
