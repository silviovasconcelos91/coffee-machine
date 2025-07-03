import java.util.Arrays;

public enum DRINK {
    COFFEE("coffee","C", 0.6),
    CHOCOLATE("chocolate","H", 0.5),
    TEA("tea","T", 0.4),
    ORANGE("orange","O", 0.6);

    private final String value;
    private final String drinkMakerValue;
    private final double drinkAmount;

    DRINK(String value, String drinkMakerValue, double drinkAmount){
        this.value = value;
        this.drinkMakerValue = drinkMakerValue;
        this.drinkAmount = drinkAmount;
    }

    public String getDrinkMakerValue() {
        return drinkMakerValue;
    }

    public String getValue() {
        return value;
    }

    public double getDrinkAmount() {
        return drinkAmount;
    }

    public static DRINK fromValue(String value){
        return Arrays.stream(DRINK.values())
                .filter(drink -> drink.getValue().equals(value))
                .findFirst()
                .orElseThrow();
    }

    public boolean isNotEnoughMoney(double drinkAmount) {
        return this.getDrinkAmount() > drinkAmount;
    }

    public boolean isHotDrink() {
        return !this.equals(DRINK.ORANGE);
    }
}
