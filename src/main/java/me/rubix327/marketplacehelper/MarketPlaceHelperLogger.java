package me.rubix327.marketplacehelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressWarnings("unused")
public class MarketPlaceHelperLogger {

    public static void logEmptyMessage(){
        logMessage("");
    }

    public static void logInfoMessage(String msg){
        logMessage("[" + getTime() + " INFO]: " + ConsoleColors.GREEN + msg + ConsoleColors.RESET);
    }

    public static void logAddMessage(String msg){
        logMessage("[" + getTime() + " INFO]: " + ConsoleColors.RESET + msg);
    }

    public static void logSuccessMessage(String msg){
        logMessage("[" + getTime() + " INFO]: " + msg);
    }

    public static void logWarningMessage(String msg){
        logMessage(ConsoleColors.YELLOW_BOLD + "[" + getTime() + " WARN]: " + msg);
    }

    public static void logErrorMessage(String msg){
        logMessage(ConsoleColors.RED_BRIGHT + "[" + getTime() + " ERROR]: " + msg + ConsoleColors.RESET);
    }

    public static void logNumberMessage(Number number, String msg){
        logMessage("[" + getTime() + " INFO]: " + ConsoleColors.GREEN_BRIGHT + "[" + number + "] " + ConsoleColors.RESET + msg);
    }

    public static void logQuestionMessageIn(String msg){
        logMessageIn(ConsoleColors.YELLOW + "[" + getTime() + " QSTN]: " + msg + ConsoleColors.RESET);
    }

    public static void logMessage(String msg){
        System.out.println(msg);
    }

    public static void logMessageIn(String msg){
        System.out.print(msg);
    }

    public static String getTime(){
        DateFormat format = new SimpleDateFormat("HH:mm:ss");
        return format.format(new Date());
    }

}
