package carrena.mediator.mediator;

public interface RequestCommandHandler<TRequest extends RequestCommand<TResponse>,TResponse> {
    TResponse handle(TRequest command);
}
