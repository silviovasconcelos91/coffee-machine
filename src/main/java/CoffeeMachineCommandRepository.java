import java.util.List;

public interface CoffeeMachineCommandRepository {

    void saveCommand(Command command);

    List<Command> getAllCommands();

}
