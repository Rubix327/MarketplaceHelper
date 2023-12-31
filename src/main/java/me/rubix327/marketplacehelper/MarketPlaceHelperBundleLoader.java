package me.rubix327.marketplacehelper;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import static me.rubix327.marketplacehelper.MarketPlaceHelperLogger.*;

public class MarketPlaceHelperBundleLoader {
    private static final List<MarketPlaceHelperBundle> loadedBundles = new ArrayList<>();
    private static final boolean DEBUG = true;

    public static void main(String[] args) {
        try{
            execute();
        } catch (Exception e){
            logErrorMessage("Произошла ошибка. Детали: " + e.getMessage());
            // TODO log into file
            e.printStackTrace();
        }
    }

    public static String getCoreFolderPath(){
        if (DEBUG) return "C:\\Users\\Rubix327\\Desktop\\MarketPlaceHelper-v1.0";
        try{
            return new File(MarketPlaceHelperBundleLoader.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParent();
        } catch (URISyntaxException e){
            throw new RuntimeException(e);
        }
    }

    public static void execute(){
        final String basePath = getCoreFolderPath();
        File modulesFolder = new File(basePath, "modules");

        if (!modulesFolder.exists()) {
            boolean created = modulesFolder.mkdir();
            if (!created) {
                logErrorMessage("Не удалось создать папку modules по пути " + modulesFolder + ".");
                logErrorMessage("Пожалуйста, создайте её вручную и перезапустите программу.");
                return;
            }
        }

        if (!modulesFolder.isDirectory()) {
            logErrorMessage("Не найдена папка modules по пути " + modulesFolder + ".");
            return;
        }

        File[] innerFiles = modulesFolder.listFiles();
        if (innerFiles == null) {
            logErrorMessage("В папке modules не найдено ни одного модуля.");
            return;
        }

        List<File> foundJarFiles = new ArrayList<>();
        for (File file : innerFiles) {
            if (!file.isFile()) continue;
            if (!file.getName().endsWith(".jar")) continue;

            foundJarFiles.add(file);
        }

        if (foundJarFiles.isEmpty()){
            logErrorMessage("Не найдено ни одного модуля. Модули должны находится в папке modules.");
            return;
        }

        List<URL> urls = new ArrayList<>();
        for (File foundJarFile : foundJarFiles) {
            URL url = getUrlFromFile(foundJarFile);
            if (url == null) continue;
            urls.add(url);
        }

        URL[] urlsArray = new URL[foundJarFiles.size()];
        urls.toArray(urlsArray);

        try (URLClassLoader urlClassLoader = new URLClassLoader(
                urlsArray, MarketPlaceHelperBundleLoader.class.getClassLoader()
        )){
            for (File foundJarFile : foundJarFiles) {
                loadModule(foundJarFile, urlClassLoader);
            }

            if (loadedBundles.isEmpty()){
                logErrorMessage("Ни одного модуля не найдено. Они должны находится в папке modules.");
                urlClassLoader.close();
                return;
            }

            logSuccessMessage("Загружены следующие модули:");
            for (MarketPlaceHelperBundle loadedBundle : loadedBundles) {
                logNumberMessage(loadedBundles.indexOf(loadedBundle), loadedBundle.getName() + " (" + loadedBundle.getVersion() + ") - " + loadedBundle.getDescription());
            }

            boolean active = true;
            while (active){
                int min = 1;
                int max = loadedBundles.size();
                logQuestionMessageIn("Какой модуль вы хотите использовать? Введите число (" + min + "-" + max + "): ");

                try{
                    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                    String numberStr = reader.readLine();

                    int number;
                    try{
                        number = Integer.parseInt(numberStr);
                    } catch (NumberFormatException e){
                        continue;
                    }

                    if (number < 1 || number > loadedBundles.size()){
                        logWarningMessage("Введите число от " + min + " до " + max + ".");
                        continue;
                    }

                    active = false;
                    MarketPlaceHelperBundle chosenBundle = loadedBundles.get(number - 1);
                    logInfoMessage("Запускаем модуль " + chosenBundle.getName() + "...");
                    chosenBundle.init();

                } catch (Exception e){
                    logErrorMessage("Произошла ошибка при инициализации модуля. Детали: " + e.getMessage());
                    return;
                }
            }
        }
        catch (IOException e) {
            logErrorMessage("Не удалось закрыть загрузчик классов. Детали: " + e.getMessage());
        }
    }

    private static URL getUrlFromFile(File file){
        try {
            return file.toURI().toURL();
        } catch (MalformedURLException e) {
            return null;
        }
    }

    public static void loadModule(File file, ClassLoader loader){
        String moduleName = file.getName().replace(".jar", "");
        String mainClassPath;
        try (JarFile jarFile = new JarFile(file)){
            Manifest manifest = jarFile.getManifest();
            if (manifest == null){
                // This file is not a module
                return;
            }
            Attributes attr = manifest.getMainAttributes();
            mainClassPath = attr.getValue("Main-Class");
        }
        catch (IOException | SecurityException e){
            logErrorMessage("Не удалось открыть модуль " + moduleName + ". Детали: " + e.getMessage());
            return;
        }

        if (mainClassPath == null){
            // This file is not a module
            return;
        }

        try{
            Class<?> classToLoad = Class.forName(mainClassPath, true, loader);
            MarketPlaceHelperBundle instance = (MarketPlaceHelperBundle) classToLoad.getDeclaredConstructor().newInstance();
            loadedBundles.add(instance);
        } catch (InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException |
                 ClassNotFoundException e) {
            logErrorMessage("Произошла ошибка при загрузке модуля " + moduleName + ": " + e.getClass().getName() + ": " + e.getMessage());
            // TODO log into file
            e.printStackTrace();
        }
    }

}
