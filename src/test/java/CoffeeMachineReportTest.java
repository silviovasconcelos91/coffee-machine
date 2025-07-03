import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CoffeeMachineReportTest {

    private final CoffeeMachineCommandRepository coffeeMachineCommandRepository = mock(CoffeeMachineCommandRepository.class);
    private final CoffeeMachineReport coffeeMachineReport = new CoffeeMachineReport(coffeeMachineCommandRepository);

    @Test
    void should_generate_report_1_tea_2_coffees() {
        // Given
        String expectedRepoort = """
                Chocolate : 0
                Coffee : 2
                Orange : 0
                Tea : 1
                Total : 1.6""";

        //When
        List<Command> commandList = List.of(
                generateCommand("coffee", 0.6),
                generateCommand("coffee", 0.6),
                generateCommand("tea", 0.4)
        );

        when(coffeeMachineCommandRepository.getAllCommands()).thenReturn(commandList);
        String report = coffeeMachineReport.report();
        //Then
        Assertions.assertEquals(expectedRepoort, report);
    }

    private static Command generateCommand(String tea, double amount) {
        return new Command(tea, 0, amount, false);
    }

    @Test
    void should_generate_report_1_tea_1_coffees_1_orange_juice() {
        // Given
        String expectedRepoort = """
                Chocolate : 0
                Coffee : 1
                Orange : 1
                Tea : 1
                Total : 1.6""";
        List<Command> commandList = List.of(
                generateCommand("coffee", 0.6),
                generateCommand("orange", 0.6),
                generateCommand("tea", 0.4)
        );

        //When
        when(coffeeMachineCommandRepository.getAllCommands()).thenReturn(commandList);
        String report = coffeeMachineReport.report();

        //Then
        Assertions.assertEquals(expectedRepoort, report);
    }

}
