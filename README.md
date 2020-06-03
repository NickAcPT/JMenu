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
    
    @Option(ordinal = 1, key = '2', description = "Exit")
    @ExitOption
    public void exit() {}
}
```


