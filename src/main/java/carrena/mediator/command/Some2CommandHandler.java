package carrena.mediator.command;

import carrena.mediator.mediator.RequestCommandHandler;

public class Some2CommandHandler implements RequestCommandHandler<Some2Command, Long> {
    @Override
    public Long handle(Some2Command command) {
        System.out.println("Some2CommanHandler handle() 함수가 호출됨");
        return null;
    }
}
