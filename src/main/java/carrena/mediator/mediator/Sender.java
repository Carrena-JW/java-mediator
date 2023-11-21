package carrena.mediator.mediator;

public interface Sender {

    <TResponse> TResponse send(RequestCommand<TResponse> command);

}
