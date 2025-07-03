//The drink maker should make the drinks only if the correct amount of money is given
//If not enough money is provided, we want to send a message to the drink maker. The message should contains at least the amount of money missing.


//"M:message-content" (Drink maker forwards any message received
//				onto the coffee machine interface
//				for the customer to see)

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class CoffeeMachineTest {

    private final CoffeeMachineCommandRepository coffeeMachineCommandRepository = mock(CoffeeMachineCommandRepository.class);
    private final BeverageQuantityChecker beverageQuantityChecker = mock(BeverageQuantityChecker.class);
    private final EmailNotifier emailNotifier = mock(EmailNotifier.class);
    private final DrinkMaker drinkMaker = mock(DrinkMaker.class);
    private final CoffeeMachine coffeeMachine = new CoffeeMachine(drinkMaker, coffeeMachineCommandRepository, beverageQuantityChecker, emailNotifier);

    @Test
    @DisplayName("appel de la méthode make de drinkMaker avec la commande coffee C::")
    void should_call_drinkMaker_make_coffee() {
        // GIVEN
        Command cmd = new Command("coffee", 0, 0.6, false);

        // WHEN
        coffeeMachine.processOrder(cmd);

        // THEN
        verify(drinkMaker, times(1)).make("C::");
    }

    @Test
    @DisplayName("appel de la méthode make de drinkMaker avec la commande tea T::")
    void should_call_drinkMaker_make_tea() {
        // GIVEN
        Command cmd = new Command("tea", 0, 0.4, false);

        // WHEN
        coffeeMachine.processOrder(cmd);

        // THEN
        verify(drinkMaker, times(1)).make("T::");
    }

    @Test
    @DisplayName("appel de la méthode make de drinkMaker avec la commande chocolate H::")
    void should_call_drinkMaker_make_chocolate() {
        // GIVEN
        Command cmd = new Command("chocolate", 0, 0.5, false);

        // WHEN
        coffeeMachine.processOrder(cmd);

        // THEN
        verify(drinkMaker, times(1)).make("H::");
    }

    @Test
    @DisplayName("appel de la méthode make de drinkMaker avec la commande coffee plus un sucre C:1:0")
    void should_call_drinkMaker_make_coffee_one_sugar() {
        // GIVEN
        Command cmd = new Command("coffee", 1, 0.6, false);

        // WHEN
        coffeeMachine.processOrder(cmd);

        // THEN
        verify(drinkMaker, times(1)).make(any());
        verify(drinkMaker).make("C:1:0");
    }

    @Test
    @DisplayName("appel de la méthode make de drinkMaker avec la commande coffee plus un sucre C:2:0")
    void should_call_drinkMaker_make_coffee_with_two_sugars_and_touillette() {
        // GIVEN
        Command cmd = new Command("coffee", 2, 0.6, false);

        // WHEN
        coffeeMachine.processOrder(cmd);

        // THEN
        verify(drinkMaker, times(1)).make(any());
        verify(drinkMaker).make("C:2:0");
    }

    @Test
    @DisplayName("appel de la méthode make de drinkMaker avec la commande coffee sans sucre C::")
    void should_call_drinkMaker_make_coffee_with_zero_sugars() {
        // GIVEN
        Command cmd = new Command("coffee", 0, 0.6, false);

        // WHEN
        coffeeMachine.processOrder(cmd);

        // THEN
        verify(drinkMaker, times(1)).make(any());
        verify(drinkMaker).make("C::");
    }

    @Test
    @DisplayName("appel de la méthode make de drinkMaker avec cmd C:: si le montant est bon")
    void should_call_drinkMaker_make_when_amount_is_ok() {
        // GIVEN
        Command cmd = new Command("coffee", 0, 0.6, false);

        // WHEN
        coffeeMachine.processOrder(cmd);

        // THEN
        verify(drinkMaker, times(1)).make(any());
        verify(drinkMaker).make("C::");
    }

    @Test
    @DisplayName("appel de la méthode make de drinkMaker avec cmd M:: si le montant est insufisant avec un montant de 0.2")
    void should_call_drinkMaker_make_with_M_when_amount_is_not_enough_with_ammount_0_2() {
        // GIVEN
        Command cmd = new Command("coffee", 0, 0.2, false);

        // WHEN
        coffeeMachine.processOrder(cmd);

        // THEN
        verify(drinkMaker, times(1)).make(any());
        verify(drinkMaker).make("M:il manque le montant : 0.4");
    }

    @Test
    @DisplayName("appel de la méthode make de drinkMaker avec cmd M:: si le montant est insufisant avec un montant de 0.4")
    void should_call_drinkMaker_make_with_M_when_amount_is_not_enough_with_ammount_0_4() {
        // GIVEN
        Command cmd = new Command("coffee", 0, 0.4, false);

        // WHEN
        coffeeMachine.processOrder(cmd);

        // THEN
        verify(drinkMaker, times(1)).make(any());
        verify(drinkMaker).make("M:il manque le montant : 0.2");
    }

    @Test
    @DisplayName("appel de la méthode make de drinkMaker avec la commande orange O::")
    void should_call_drinkMaker_make_orange_juice() {
        // GIVEN
        Command cmd = new Command("orange", 0, 0.6, false);

        // WHEN
        coffeeMachine.processOrder(cmd);

        // THEN
        verify(drinkMaker, times(1)).make("O::");
    }

    @Test
    @DisplayName("appel de la méthode make de drinkMaker avec la commande coffee hot Ch::")
    void should_call_drinkMaker_make_coffee_hot() {
        // GIVEN
        Command cmd = new Command("coffee", 0, 0.6,true);

        // WHEN
        coffeeMachine.processOrder(cmd);

        // THEN
        verify(drinkMaker, times(1)).make("Ch::");
    }

    @Test
    @DisplayName("appel de la méthode make de drinkMaker avec la commande tea hot Th::")
    void should_call_drinkMaker_make_tea_hot() {
        // GIVEN
        Command cmd = new Command("tea", 0, 0.4,true);

        // WHEN
        coffeeMachine.processOrder(cmd);

        // THEN
        verify(drinkMaker, times(1)).make("Th::");
    }

    @Test
    @DisplayName("appel de la méthode make de drinkMaker avec la commande orange hot O::")
    void should_call_drinkMaker_make_orange_not_hot() {
        // GIVEN
        Command cmd = new Command("orange", 0, 0.6,true);

        // WHEN
        coffeeMachine.processOrder(cmd);

        // THEN
        verify(drinkMaker, times(1)).make("O::");
    }

    @Test
    void should_call_repository_with_command() {
        // GIVEN
        Command cmd = new Command("orange", 0, 0.6,true);

        // WHEN
        coffeeMachine.processOrder(cmd);

        // THEN
        verify(coffeeMachineCommandRepository, times(1)).saveCommand(cmd);
    }

    @Test
    void should_not_call_repository_with_amount_not_enough() {
        // GIVEN
        Command cmd = new Command("orange", 0, 0.5,true);

        // WHEN
        coffeeMachine.processOrder(cmd);

        // THEN
        verify(coffeeMachineCommandRepository, times(0)).saveCommand(cmd);
    }

    @Test
    void should_call_email_notifier_when_water_is_empty() {
        // GIVEN
        Command cmd = new Command("tea", 0, 0.6,true);

        // WHEN
        when(beverageQuantityChecker.isEmpty(cmd.drink())).thenReturn(true);
        coffeeMachine.processOrder(cmd);

        // THEN
        verify(emailNotifier, times(1)).notifyMissingDrink(cmd.drink());
    }

    @Test
    void should_not_call_email_notifier_when_water_is_not_empty() {
        // GIVEN
        Command cmd = new Command("tea", 0, 0.6,true);

        // WHEN
        when(beverageQuantityChecker.isEmpty(cmd.drink())).thenReturn(false);
        coffeeMachine.processOrder(cmd);

        // THEN
        verify(emailNotifier, times(0)).notifyMissingDrink(cmd.drink());
    }

    @Test
    void should_not_call_coffee_maker_when_empty() {
        // GIVEN
        Command cmd = new Command("coffee", 0, 0.6,true);

        // WHEN
        when(beverageQuantityChecker.isEmpty(cmd.drink())).thenReturn(true);
        coffeeMachine.processOrder(cmd);

        // THEN
        verify(drinkMaker, times(0)).make("Ch::");
    }

}
