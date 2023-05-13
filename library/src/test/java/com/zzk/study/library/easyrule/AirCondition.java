package com.zzk.study.library.easyrule;

import org.jeasy.rules.api.*;
import org.jeasy.rules.core.InferenceRulesEngine;
import org.jeasy.rules.core.RuleBuilder;
import org.junit.jupiter.api.Test;

public class AirCondition {

    @Test
    public void name(){
        Rule airConditioningRule = new RuleBuilder()
                .name("air conditioning rule")
                .when(HighTemperatureCondition.itIsHot())
                .then(DecreaseTemperatureAction.decreaseTemperature())
                .build();

        // define facts
        Facts facts = new Facts();
        facts.put("temperature", 30);

        Rules rules = new Rules();
        rules.register(airConditioningRule);

        // fire rules on known facts
        RulesEngine rulesEngine = new InferenceRulesEngine();
        rulesEngine.fire(rules, facts);
    }

    public static class HighTemperatureCondition implements Condition {
        @Override
        public boolean evaluate(Facts facts) {
            Integer temperature = facts.get("temperature");
            return temperature > 25;
        }

        static HighTemperatureCondition itIsHot() {
            return new HighTemperatureCondition();
        }
    }

    public static class DecreaseTemperatureAction implements Action {
        @Override
        public void execute(Facts facts) throws Exception {
            System.out.println("It is hot! cooling air..");
            Integer temperature = facts.get("temperature");
            facts.put("temperature", temperature - 1);
        }

        static DecreaseTemperatureAction decreaseTemperature() {
            return new DecreaseTemperatureAction();
        }
    }
}
