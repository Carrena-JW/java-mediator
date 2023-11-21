package carrena.mediator.command;

import carrena.mediator.mediator.RequestCommand;

public record Some1Command(String name, Long age) implements RequestCommand<Long> {
}
