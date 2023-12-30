package me.rubix327.marketplacehelper;

@SuppressWarnings("unused")
public class MarketPlaceHelperCore {

    public static void logEmptyMessage(){
        logMessage("");
    }

    public static void logInfoMessage(String msg){
        logMessage(ConsoleColors.BLUE_BRIGHT + "[*] " + ConsoleColors.RESET + msg);
    }

    public static void logAddMessage(String msg){
        logMessage(ConsoleColors.YELLOW + "[+] " + ConsoleColors.RESET + msg);
    }

    public static void logSuccessMessage(String msg){
        logMessage(ConsoleColors.GREEN + "[✔] " + ConsoleColors.RESET + msg);
    }

    public static void logWarningMessage(String msg){
        logMessage(ConsoleColors.RED + "[〰] " + ConsoleColors.RESET + msg);
    }

    public static void logErrorMessage(String msg){
        logMessage(ConsoleColors.RED + "[✖] " + msg + ConsoleColors.RESET);
    }

    public static void logMessage(String msg){
        System.out.println(msg);
    }

}
