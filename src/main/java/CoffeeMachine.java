import java.math.BigDecimal;
import java.math.RoundingMode;

public class CoffeeMachine {

    public static final String STICK_VALUE = "0";
    public static final String EXTRA_HOT_VALUE = "h";
    private final DrinkMaker drinkMaker;
    private final CoffeeMachineCommandRepository coffeeMachineCommandRepository;
    private final BeverageQuantityChecker beverageQuantityChecker;
    private final EmailNotifier emailNotifier;

    public CoffeeMachine(DrinkMaker drinkMaker, CoffeeMachineCommandRepository coffeeMachineCommandRepository, BeverageQuantityChecker beverageQuantityChecker, EmailNotifier emailNotifier) {
        this.drinkMaker = drinkMaker;
        this.coffeeMachineCommandRepository = coffeeMachineCommandRepository;
        this.beverageQuantityChecker = beverageQuantityChecker;
        this.emailNotifier = emailNotifier;
    }

    public void processOrder(Command command) {
        DRINK drink =  DRINK.fromValue(command.drink());
        double drinkAmount = command.amount();
        if (drink.isNotEnoughMoney(drinkAmount)) {
            BigDecimal missingAmount = calculateMissingAmount(drink, drinkAmount);
            drinkMaker.make("M:il manque le montant : " + missingAmount);
            return;
        }

        if (isEmpty(command.drink())) {
            emailNotifier.notifyMissingDrink(command.drink());
            return;
        }

        String drinkMakerCommand = generateDrinkMakerCommand(command, drink);

        drinkMaker.make(drinkMakerCommand);
        coffeeMachineCommandRepository.saveCommand(command);
    }

    private boolean isEmpty(String drink) {
        return beverageQuantityChecker.isEmpty(drink);
    }

    private String generateDrinkMakerCommand(Command command, DRINK drink) {

        String drinkMakerValue = processDrink(drink);
        if(drink.isHotDrink() && command.extraHot()){
            drinkMakerValue += EXTRA_HOT_VALUE;
        }
        String drinkMakerSugar = processSugar(command);
        String drinkMakeStick = processStick(command);
        return String.join(":", drinkMakerValue, drinkMakerSugar, drinkMakeStick);
    }



    private static BigDecimal calculateMissingAmount(DRINK drink, double drinkAmount) {
        double missingAmount = drink.getDrinkAmount() - drinkAmount;
        return new BigDecimal(missingAmount).setScale(1, RoundingMode.HALF_UP);
    }

    private String processStick(Command command) {
        return command.sugar() > 0 ? STICK_VALUE : "";
    }

    private static String processDrink(DRINK drink) {
        return drink.getDrinkMakerValue();
    }

    private static String processSugar(Command command) {
        return command.sugar() > 0 ? String.valueOf(command.sugar()) : "";
    }
}
