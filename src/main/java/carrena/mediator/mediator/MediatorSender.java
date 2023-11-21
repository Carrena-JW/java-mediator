package carrena.mediator.mediator;

public interface MediatorSender {

    <TResponse> TResponse send(RequestCommand<TResponse> command);

}
