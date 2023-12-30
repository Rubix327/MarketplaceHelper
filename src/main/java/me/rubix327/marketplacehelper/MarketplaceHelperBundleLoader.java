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

public class MarketplaceHelperBundleLoader {

    // TODO Calculated at runtime
    private static final String basePath = "C:\\Users\\Rubix327\\Desktop\\MarketPlaceHelper-v1.0";

    public static void main(String[] args) {
        test();
    }

    public static void test(){
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
            logErrorMessage("Не найдена папка modules по пути " + modulesFolder);
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

        List<URL> urls = new ArrayList<>();
        for (File foundJarFile : foundJarFiles) {
            URL url = getUrlFromFile(foundJarFile);
            if (url == null) continue;
            urls.add(url);
        }

        URL[] urlsArray = new URL[foundJarFiles.size()];
        urls.toArray(urlsArray);

        try (URLClassLoader urlClassLoader = new URLClassLoader(
                urlsArray, MarketplaceHelperBundleLoader.class.getClassLoader()
        )) {
            for (File foundJarFile : foundJarFiles) {
                loadModule(foundJarFile, urlClassLoader);
            }
        } catch (IOException e) {
            logErrorMessage("Не удалось инициализировать загрузчик классов. Детали: " + e.getMessage());
            return;
        }

        MarketPlaceHelperLogger.logSuccessMessage("Загружены следующие модули:");
        for (MarketPlaceBundle loadedBundle : loadedBundles) {
            System.out.println(loadedBundle.getName() + " - " + loadedBundle.getDescription());
        }

    }

    private static URL getUrlFromFile(File file){
        try {
            URL url = file.toURI().toURL();
            System.out.println(url);
            return url;
        } catch (MalformedURLException e) {
            return null;
        }
    }

    public static void loadModule(File file, ClassLoader loader){
        String moduleName = file.getName().replace(".jar", "");
        MarketPlaceHelperLogger.logAddMessage("Загружаем модуль " + moduleName + "...");
        String pkg;
        String main;
        try (JarFile jarFile = new JarFile(file)){
            Manifest manifest = jarFile.getManifest();
            if (manifest == null){
                // This file is not a module
                return;
            }
            Attributes attr = manifest.getMainAttributes();
            pkg = attr.getValue("Package");
            main = attr.getValue("Main-Class");
        }
        catch (IOException | SecurityException e){
            logErrorMessage("Не удалось открыть модуль " + moduleName + ". Детали: " + e.getMessage());
            return;
        }

        if (pkg == null){
            // This file is not a module
            return;
        }

        if (main == null){
            // This file is not a module
            return;
        }

        String mainClassName = pkg + "." + main;
        MarketPlaceHelperLogger.logInfoMessage("Main class name: " + mainClassName);

        try{
            Class<?> classToLoad = Class.forName(mainClassName, true, loader);
            MarketPlaceBundle instance = (MarketPlaceBundle) classToLoad.getDeclaredConstructor().newInstance();
            // TODO remove in production
            instance.init();
//            Method method = classToLoad.getDeclaredMethod("init");
//            method.invoke(instance);

            loadedBundles.add(instance);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static final List<MarketPlaceBundle> loadedBundles = new ArrayList<>();

}
