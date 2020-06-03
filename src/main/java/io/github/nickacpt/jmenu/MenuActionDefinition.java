package io.github.nickacpt.jmenu;

import io.github.nickacpt.jmenu.annotations.ExitOption;
import io.github.nickacpt.jmenu.annotations.Option;

import java.lang.reflect.Method;

public class MenuActionDefinition {
    private final JMenu menu;
    private final Method method;
    private final Option option;
    public MenuActionDefinition(JMenu menu, Method method) {
        this.menu = menu;
        this.method = method;
        option = method.getAnnotation(Option.class);
    }

    @Override
    public String toString() {
        return "MenuActionDefinition{" +
                "method=" + method.getName() +
                ", option=(" + option.key() + "," + option.description() + ")}";
    }

    public Option getOption() {
        return option;
    }

    public void run() {
        try {
            if (method.isAnnotationPresent(ExitOption.class)) {
                menu.isExit = true;
                return;
            }
            method.invoke(menu);
        } catch (Exception e) {
            //Do nothing
        }
    }

    public void printDescription() {
        menu.writer().println(option.key() + " - " + option.description());
    }
}
