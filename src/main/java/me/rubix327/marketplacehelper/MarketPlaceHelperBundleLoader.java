package me.rubix327.marketplacehelper;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import static me.rubix327.marketplacehelper.MarketPlaceHelperLogger.logErrorMessage;

@SuppressWarnings("unused")
public class MarketPlaceHelperBundleLoader {

    public static List<MarketPlaceHelperBundle> loadBundles(File modulesFolder){
        if (!modulesFolder.exists()) {
            logErrorMessage("Папка " + modulesFolder + " не существует.");
            return new ArrayList<>();
        }

        if (!modulesFolder.isDirectory()) {
            logErrorMessage("Не найдена папка modules по пути " + modulesFolder + ".");
            return new ArrayList<>();
        }

        File[] innerFiles = modulesFolder.listFiles();
        if (innerFiles == null) {
            logErrorMessage("В папке modules не найдено ни одного модуля.");
            return new ArrayList<>();
        }

        List<File> foundJarFiles = new ArrayList<>();
        for (File file : innerFiles) {
            if (!file.isFile()) continue;
            if (!file.getName().endsWith(".jar")) continue;

            foundJarFiles.add(file);
        }

        if (foundJarFiles.isEmpty()){
            logErrorMessage("Не найдено ни одного модуля. Модули должны находится в папке modules.");
            return new ArrayList<>();
        }

        List<URL> urls = new ArrayList<>();
        for (File foundJarFile : foundJarFiles) {
            URL url = getUrlFromFile(foundJarFile);
            if (url == null) continue;
            urls.add(url);
        }

        URL[] urlsArray = new URL[foundJarFiles.size()];
        urls.toArray(urlsArray);

        URLClassLoader loader = new URLClassLoader(urlsArray, MarketPlaceHelperBundleLoader.class.getClassLoader());

        List<MarketPlaceHelperBundle> bundles = new ArrayList<>();
        for (File foundJarFile : foundJarFiles) {
            MarketPlaceHelperBundle bundle = loadModule(foundJarFile, loader);
            if (bundle != null){
                bundles.add(bundle);
            }
        }

        return bundles;
    }

    private static URL getUrlFromFile(File file){
        try {
            return file.toURI().toURL();
        } catch (MalformedURLException e) {
            return null;
        }
    }

    public static MarketPlaceHelperBundle loadModule(File file, ClassLoader loader){
        String moduleName = file.getName().replace(".jar", "");
        String mainClassPath;
        try (JarFile jarFile = new JarFile(file)){
            Manifest manifest = jarFile.getManifest();
            if (manifest == null){
                // This file is not a module
                return null;
            }
            Attributes attr = manifest.getMainAttributes();
            mainClassPath = attr.getValue("Main-Class");
        }
        catch (IOException | SecurityException e){
            logErrorMessage("Не удалось открыть модуль " + moduleName + ". Детали: " + e.getMessage());
            return null;
        }

        if (mainClassPath == null){
            // This file is not a module
            return null;
        }

        try{
            Class<?> classToLoad = Class.forName(mainClassPath, true, loader);
            return (MarketPlaceHelperBundle) classToLoad.getDeclaredConstructor().newInstance();
        } catch (InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException |
                 ClassNotFoundException e) {
            // TODO log into file
            logErrorMessage("Произошла ошибка при загрузке модуля " + moduleName + ": " + e.getClass().getName() + ": " + e.getMessage());
            return null;
        }
    }

}
