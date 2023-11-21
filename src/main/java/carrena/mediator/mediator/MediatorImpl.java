package carrena.mediator.mediator;


import carrena.mediator.command.Some1Command;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Stream;

@Component
public class MediatorImpl implements Mediator {
    private final String PACKAGE_TARGET_PATH = "carrena.mediator.command";
    private List<Class<?>> classes;

    public MediatorImpl() {
        classes = findClassesImplementing(RequestCommandHandler.class, PACKAGE_TARGET_PATH);
    }

    public List<Class<?>> findClassesImplementing(Class<?> interfaceClass, String packageName) {
        List<Class<?>> classes = new ArrayList<>();
        try {
            String path = packageName.replace('.', '/');
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            Enumeration<URL> resources = classLoader.getResources(path);

            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();
                File directory = new File(resource.getFile());
                findClasses(directory, packageName, classes, interfaceClass);
            }
        } catch (IOException e) {
            //예외 무시
        }

        return classes;
    }

    private void findClasses(File directory, String packageName, List<Class<?>> classes, Class<?> interfaceClass) {

        if (!directory.exists()) return;

        File[] files = directory.listFiles();
        if (files == null) return;

        for (File file : files) {
            try {
                if (file.isDirectory()) {
                    findClasses(file, packageName + "." + file.getName(), classes, interfaceClass);
                } else if (file.getName().endsWith(".class")) {
                    String className = packageName + '.' + file.getName().replace(".class", "");
                    Class<?> clazz = Class.forName(className);

                    //handler 함수에 command 파람이 존재하는 경우대상으로 봄
                    Method handleMethod = clazz.getMethod("handle", RequestCommand.class);

                    if (interfaceClass.isAssignableFrom(clazz) && !clazz.isInterface()) {
                        classes.add(clazz);
                    }
                }
            } catch (ClassNotFoundException | NoSuchMethodException e) {
                //예외 무시
            }
        }
    }

    @Override
    public <TResponse> TResponse send(RequestCommand<TResponse> command) {

        Method foundMethod = classes.stream()
                .flatMap(clazz -> Stream.of(clazz.getDeclaredMethods()))
                .filter(method -> method.getName().equals("handle"))
                .filter(method -> method.getParameterCount() == 1)
                .filter(method -> method.getParameterTypes()[0].equals(command.getClass()))
                .findFirst().get();
        Class<?> foundClass = foundMethod.getDeclaringClass();
        try {
            Object instance = foundClass.getConstructor().newInstance();
            return (TResponse) foundMethod.invoke(instance, command);
        } catch (NoSuchMethodException e) {
            // 예외 무시
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            // 다양한 예외 상황에 대한 예외 처리
            e.printStackTrace();
        }

        return null;
    }
}
