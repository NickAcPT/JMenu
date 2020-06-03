# JMenu

A menu library created to aid with the creator of console menus.



## Usage

```java
import io.github.nickacpt.jmenu.JMenu;
import io.github.nickacpt.jmenu.annotations.ExitOption;
import io.github.nickacpt.jmenu.annotations.Option;

public class NiceMenu extends JMenu {

    @Option(ordinal = 0, key = '1', description = "Option One")
    public void optionOne() {
        // Implementation here
    }

    @Option(ordinal = 1, key = '2', description = "Option Two")
    public void optionTwo() {
        // Implementation here
    }
    
    @Option(ordinal = 100, key = '0', description = "Exit")
    @ExitOption
    public void exit() {}
}
```

Then, to invoke the menu, just create a new instance of the class.
```java
new NiceMenu().run();
```
or, if you want the menu to repeat until the user exits it, use
```java
new NiceMenu().runLooping();
```
Result:
```
===============================================================
1 - Option One
2 - Option Two
0 - Exit
Select the option:
===============================================================
```

