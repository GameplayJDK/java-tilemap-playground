/*
 * The MIT License (MIT)
 * Copyright (c) 2019 GameplayJDK
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 * Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package de.gameplayjdk.jwfcimage.extension.access;

import de.gameplayjdk.jwfcimage.extension.ExtensionInterface;
import de.gameplayjdk.jwfcimage.utility.Extension;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.stream.Collectors;

public final class ApplicationExtensionManager implements ApplicationExtensionManagerInterface {

    // TODO: Test the class.

    private static final String EXTENSION_PATH = "./extension";
    private static final String EXTENSION_CLASS = "Extension-Class";

    private final String path;

    private final List<ExtensionInterface> extensionList;

    public ApplicationExtensionManager() {
        this(ApplicationExtensionManager.EXTENSION_PATH);
    }

    public ApplicationExtensionManager(String path) {
        this.path = path;

        this.extensionList = new ArrayList<ExtensionInterface>();
    }

    @Override
    public boolean attachAvailableExtension(Application application) {
        File path = this.getFile();
        if (!path.exists()) {
            return false;
        }

        File[] list = this.getList(path);
        if (null == list) {
            return false;
        }

        final List<String> listExtensionClassName = new ArrayList<String>();
        final List<URL> listUrl = Arrays.stream(list)
                .filter(file -> {
                    try {
                        Manifest manifest = (new JarFile(file)).getManifest();

                        if (null == manifest) {
                            return false;
                        }

                        String extensionClassName = manifest.getMainAttributes()
                                .getValue(ApplicationExtensionManager.EXTENSION_CLASS);

                        if (extensionClassName == null) {
                            return false;
                        }

                        listExtensionClassName.add(extensionClassName);

                        return true;
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }

                    return false;
                })
                .map(File::toURI)
                .map(uri -> {
                    try {
                        return uri.toURL();
                    } catch (MalformedURLException ex) {
                        ex.printStackTrace();
                    }

                    return null;
                })
                .filter(Objects::isNull)
                .collect(Collectors.toList());

        if (0 == listExtensionClassName.size() || 0 == listUrl.size()) {
            return false;
        }

        URLClassLoader classLoader = new URLClassLoader(listUrl.toArray(new URL[0]));

        int count = 0;

        for (String extensionClassName : listExtensionClassName) {
            if (!this.attach(application, classLoader, extensionClassName)) {
                continue;
            }

            count++;
        }

        return (count > 0);
    }

    private boolean attach(Application application, URLClassLoader classLoader, String extensionClassName) {
        try {
            Class<?> extensionClass = classLoader.loadClass(extensionClassName);

            boolean isExtension = Arrays.stream(extensionClass.getInterfaces())
                    .anyMatch(interfaceClass -> interfaceClass.getName().equals(ExtensionInterface.class.getName()));

            if (isExtension) {
                ExtensionInterface extension = (ExtensionInterface) extensionClass.newInstance();
                extension.attach(application);

                this.extensionList.add(extension);

                return true;
            }
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | ClassCastException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    private File[] getList(File path) {
        return path.listFiles(new ApplicationExtensionManager.FileFilterJar());
    }

    private File getFile() {
        return new File(this.path);
    }

    public static class FileFilterJar implements FileFilter {

        private static final String EXTENSION_JAR = "jar";

        public FileFilterJar() {
        }

        @Override
        public boolean accept(File file) {
            if (file.isDirectory()) {
                return true;
            }

            String extension = Extension.getExtension(file);

            if (null != extension) {
                return extension.equals(ApplicationExtensionManager.FileFilterJar.EXTENSION_JAR);
            }

            return false;
        }
    }
}
