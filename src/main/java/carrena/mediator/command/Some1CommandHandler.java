package carrena.mediator.command;

import carrena.mediator.mediator.RequestCommand;
import carrena.mediator.mediator.RequestCommandHandler;

public class Some1CommandHandler implements RequestCommandHandler<Some1Command, Long> {
    @Override
    public Long handle(Some1Command command) {
        System.out.println("Some1CommanHandler handle() 함수가 호출됨");
        System.out.println("Some1CommanHandler 파라메터" + command.toString());
        return 333L;
    }
}
