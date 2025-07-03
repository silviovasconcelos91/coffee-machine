import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CoffeeMachineReport {

    public static final String TOTAL_LINE = "Total";
    public static final String LINE_BREAK = "\n";
    public static final String SEPARATOR = " : ";
    public static final long INITIAL_DRINK_NUMBER = 0L;
    private final CoffeeMachineCommandRepository coffeeMachineCommandRepository;

    public CoffeeMachineReport(CoffeeMachineCommandRepository coffeeMachineCommandRepository) {
        this.coffeeMachineCommandRepository = coffeeMachineCommandRepository;
    }

    public String report() {
        List<Command> allCommands = coffeeMachineCommandRepository.getAllCommands();
        Map<String, Long> numberOfDrinkByName = generateNumberOfDrinkByNameMap();
        allCommands.forEach(command -> {
            Long drinkCount = numberOfDrinkByName.get(command.drink());
            drinkCount++;
            numberOfDrinkByName.put(command.drink(), drinkCount);
        });

        Double total = allCommands.stream()
                .map(Command::amount)
                .reduce(Double::sum)
                .orElse(0.0);

        return generateReportFrom(numberOfDrinkByName, total);
    }

    private static Map<String, Long> generateNumberOfDrinkByNameMap() {
        return Arrays.stream(DRINK.values())
                .map(DRINK::getValue)
                .collect(Collectors.toMap(
                        Function.identity(),
                        drink -> INITIAL_DRINK_NUMBER));
    }

    private static String generateReportFrom(Map<String, Long> numberOfDrinkByName, Double total) {
        String drinkReportWithoutTotal = numberOfDrinkByName.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .map(numberOfDrinkByDrinkName -> generateReportLine(numberOfDrinkByDrinkName.getKey(), numberOfDrinkByDrinkName.getValue()))
                .collect(Collectors.joining(LINE_BREAK));
        String totalLine = String.join(SEPARATOR, TOTAL_LINE, String.valueOf(total));
        return String.join(LINE_BREAK, drinkReportWithoutTotal, totalLine);
    }

    private static String generateReportLine(String drinkName, Long numberOfDrink) {
        return StringUtils.capitalize(drinkName)
                .concat(SEPARATOR)
                .concat(String.valueOf(numberOfDrink));
    }

}
