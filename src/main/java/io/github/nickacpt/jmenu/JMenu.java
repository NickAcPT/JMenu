package io.github.nickacpt.jmenu;

import io.github.nickacpt.jmenu.annotations.Option;

import java.io.PrintStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

public class JMenu {
    public static JMenuLanguage menuLanguage = new JMenuLanguage();
    private final PrintStream writer = System.out;
    private final Scanner reader = new Scanner(System.in);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private ArrayList<MenuActionDefinition> definitions;

    public PrintStream writer() {
        return writer;
    }

    public Scanner reader() {
        return reader;
    }

    public <T> T read(String text, Function<Scanner, T> func) {
        writer().print(text);
        T apply = func.apply(reader());
        writer().println();
        return apply;
    }

    public <T> T pick(List<T> list, String text, BiFunction<Integer, T, String> display) {
        for (int i = 0; i < list.size(); i++) {
            T t = list.get(i);

            writer().println(display.apply(i + 1, t));
        }

        int index = readInteger(text);
        return list.get(index - 1);
    }


    public String line(String text) {
        return insert(text, Scanner::nextLine);
    }


    public String readLine(String text) {
        return read(text, Scanner::nextLine);
    }

    public int readInteger(String text) {
        return read(text, scanner -> {
            int returnVal = scanner.nextInt();
            scanner.nextLine();
            return returnVal;
        });
    }

    public int integer(String text) {
        return insert(text, scanner -> {
            int returnVal = scanner.nextInt();
            scanner.nextLine();
            return returnVal;
        });
    }

    public <T> T insert(String text, Function<Scanner, T> func) {
        return read(menuLanguage.INSERT_PREFIX + " " + text + ": ", func);
    }

    public LocalDate date(String text) {
        return insert(text + " " + menuLanguage.DAY_MONTH_YEAR, scanner -> {
            LocalDate date1 = null;
            do {
                try {
                    date1 = LocalDate.parse(scanner.nextLine(), formatter);
                } catch (DateTimeParseException e) {
                    System.out.println(menuLanguage.INCORRECT_DATE);
                }
            } while (date1 == null);

            return date1;
        });
    }

    public boolean bool(String text) {
        return insert(text + " (s/sim/y/yes n/nao/n/no)", boolScanner());
    }
    public boolean readBool(String text) {
        return read(text + " (s/sim/y/yes n/nao/n/no)", boolScanner());
    }

    private Function<Scanner, Boolean> boolScanner() {
        return scanner -> {
            do {
                String input = scanner.nextLine();
                switch (input) {
                    case "s":
                    case "sim":
                    case "y":
                    case "yes":
                        return true;
                    case "n":
                    case "nao":
                    case "não":
                    case "no":
                        return false;
                    default:
                        System.out.println(menuLanguage.INCORRECT_BOOL);
                }
            } while (true);
        };
    }

    boolean isExit = false;
    boolean isLooping = false;
    int loopingCount = 0;

    public void runLooping() {
        isLooping = true;
        writeSplitter();
        while (!isExit) {
            run();
            loopingCount++;
        }
        writeSplitter();
        isLooping = false;
    }

    public void run() {
        loadDefinitions();

        if (!isLooping) writeSplitter();
        definitions.forEach(MenuActionDefinition::printDescription);

        String input = readLine(menuLanguage.INSERT_THE_OPTION_PREFIX);
        Optional<MenuActionDefinition> selectedOption =
                definitions.stream().filter(it -> it.getOption().key() == input.charAt(0)).findFirst();

        selectedOption.ifPresent(MenuActionDefinition::run);
        if (!isLooping) writeSplitter();
    }

    private void writeSplitter() {
        writer().println("===============================================================");
    }

    private void loadDefinitions() {
        if (definitions == null) {
            definitions = Arrays.stream(getClass().getDeclaredMethods())
                    .filter(it -> it.isAnnotationPresent(Option.class))
                    .map(method -> new MenuActionDefinition(this, method))
                    .sorted(Comparator.comparing(c -> c.getOption().ordinal()))
                    .collect(Collectors.toCollection(ArrayList::new));
        }
    }

}
