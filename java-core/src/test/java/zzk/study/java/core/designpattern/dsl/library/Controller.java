package zzk.study.java.core.designpattern.dsl.library;

import lombok.Data;

@Data
public class Controller {
    private State currentState;
    private StateMachine machine;
    private CommandChannel commandsChannel;

    public void handle(String eventCode) {
        if (currentState.hasTransition(eventCode))
            transitionTo(currentState.targetState(eventCode));
        else if (machine.isResetEvent(eventCode))
            transitionTo(machine.getStart());
        // ignore unknown events
    }
    private void transitionTo(State target) {
        currentState = target;
        currentState.executeActions(commandsChannel);
    }
}
