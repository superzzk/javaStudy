package com.zzk.study.library.easyrule;

import lombok.Data;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.jeasy.rules.mvel.MVELRule;
import org.junit.jupiter.api.Test;

/**
 * TODO:
 *
 * @author zhangzhongkun02
 * @date 2023/5/7 10:44 AM
 */
public class Shop {

    @Test
    public void main(){
        Rule ageRule = new MVELRule()
                .name("age rule")
                .description("Check if person's age is > 18 and marks the person as adult")
                .priority(1)
                .when("person.age > 18")
                .then("person.setAdult(true);");

        Rule alcoholRule = new MVELRule()
                .name("alcohol rule")
                .description("children are not allowed buy alcohol")
                .priority(2)
                .when("person.isAdult() == false")
                .then("System.out.println(\"Shop: Sorry, you are not allowed to buy alcohol\");");

        Rule buyRule = new MVELRule()
                .name("buy rule")
                .description("allow buy alcohol")
                .priority(3)
                .when("person.isAdult() == true")
                .then("System.out.println(\"Shop: Ok, here you are\");");

        //create a person instance (fact)
        Person tom = new Person("Tom", 14);
        Facts facts = new Facts();
        facts.put("person", tom);

        // create a rule set
        Rules rules = new Rules();
        rules.register(ageRule);
        rules.register(alcoholRule);
        rules.register(buyRule);

        //create a default rules engine and fire rules on known facts
        RulesEngine rulesEngine = new DefaultRulesEngine();

        System.out.println("Tom: Hi! can I have some Vodka please?");
        rulesEngine.fire(rules, facts);

        facts.put("person", new Person("zzk", 19));
        System.out.println("zzk: Hi! can I have some Vodka please?");
        rulesEngine.fire(rules, facts);
    }

    @Data
    public class Person {
        private String name;
        private int age;
        private boolean adult;

        public Person(String tom, int i) {
            this.name = tom;
            this.age = i;
        }
    }
}
