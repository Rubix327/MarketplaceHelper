package me.rubix327.marketplacehelper;

@SuppressWarnings("unused")
public class MarketPlaceHelperLogger {

    public static void logEmptyMessage(){
        logMessage("");
    }

    public static void logInfoMessage(String msg){
        logMessage(ConsoleColors.BLUE_BRIGHT + "[i] " + ConsoleColors.RESET + msg);
    }

    public static void logAddMessage(String msg){
        logMessage(ConsoleColors.YELLOW + "[+] " + ConsoleColors.RESET + msg);
    }

    public static void logSuccessMessage(String msg){
        logMessage(ConsoleColors.GREEN + "[OK] " + ConsoleColors.RESET + msg);
    }

    public static void logWarningMessage(String msg){
        logMessage(ConsoleColors.YELLOW_BOLD + "[!] " + ConsoleColors.YELLOW + msg);
    }

    public static void logErrorMessage(String msg){
        logMessage(ConsoleColors.RED_BRIGHT + "[!] " + msg + ConsoleColors.RESET);
    }

    public static void logNumberMessage(Number number, String msg){
        logMessage(ConsoleColors.GREEN_BRIGHT + "[" + number + "] " + ConsoleColors.RESET + msg);
    }

    public static void logQuestionMessageIn(String msg){
        logMessageIn(ConsoleColors.YELLOW + "[?] " + msg + ConsoleColors.RESET);
    }

    public static void logMessage(String msg){
        System.out.println(msg);
    }

    public static void logMessageIn(String msg){
        System.out.print(msg);
    }

}
