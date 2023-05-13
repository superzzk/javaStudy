package com.zzk.study.library.mvel;

import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.Test;
import org.mvel2.MVEL;
import org.mvel2.integration.VariableResolverFactory;
import org.mvel2.integration.impl.MapVariableResolverFactory;
import org.mvel2.templates.CompiledTemplate;
import org.mvel2.templates.TemplateCompiler;
import org.mvel2.templates.TemplateRuntime;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mvel2.templates.TemplateCompiler.compileTemplate;

public class TemplateDemo {

    @Test
    public void testPassThru() {
        String s = "foobar!";
        CompiledTemplate compiled = compileTemplate(s);
        assertEquals("foobar!", TemplateRuntime.execute(compiled));
    }

    @Test
    public void testBasicParsing() {
        String s = "foo: @{_foo_}--@{_bar_}!";
        CompiledTemplate compiled = compileTemplate(s);
        VariableResolverFactory vrf = new MapVariableResolverFactory(ImmutableMap.of("_foo_", "Foo", "_bar_", "Bar"));
        assertEquals("foo: Foo--Bar!", TemplateRuntime.execute(compiled, null, vrf));
    }

    @Test
    public void testIfStatement() {
        String s = "@if{_foo_=='Foo'}Hello@end{}";
        CompiledTemplate compiled = compileTemplate(s);
        VariableResolverFactory vrf = new MapVariableResolverFactory(ImmutableMap.of("_foo_", "Foo"));
        assertEquals("Hello", TemplateRuntime.execute(compiled, null, vrf));
    }

    @Test
    public void ifElse() {
        String s = "@if{_foo_=='Bar'}Hello@else{_foo_=='Foo'}Goodbye@end{}";
        CompiledTemplate compiled = compileTemplate(s);
        VariableResolverFactory vrf = new MapVariableResolverFactory(ImmutableMap.of("_foo_", "Foo"));
        assertEquals("Goodbye", TemplateRuntime.execute(compiled, null, vrf));
    }

    @Test
    public void test_code() {
        String s = "@code{a = 'foo'; b = 'bar'}@{a}@{b}";
        CompiledTemplate compiled = compileTemplate(s);
        assertEquals("foobar", TemplateRuntime.execute(compiled, null, new MapVariableResolverFactory()));

        final String template = "@code{sumText = 0}@{sumText}";
        assertEquals("0", TemplateRuntime.eval(template, new HashMap()));
    }


    @Test
    public void testEval() {
        Map<String, Object> vars = new LinkedHashMap<String, Object>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("list", "john,paul,ringo,george");
        vars.put("map", map);

        String expr = "map[\"foundIt\"] = !(map['list'].contains(\"john\"))";
        System.out.println("Evaluating '" + expr + "': ......");
        assertFalse((boolean) MVEL.eval(expr, vars));
        assertFalse((boolean)map.get("foundIt"));
    }

    @Test
    public void test_compile_and_execute() {
        Map<String, Object> vars = new LinkedHashMap<String, Object>();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("foo", "bar");
        map.put("one", 1);
        vars.put("map", map);

        String[] testCasesMVEL219 = {
                "map['foo']==map['foo']", // ok
                "(map['one'] > 0)", // ok
                "(map['one'] > 0) && (map['foo'] == map['foo'])", // ok
                "(map['one'] > 0) && (map['foo']==map['foo'])", // broken
        };

        for (String expr : testCasesMVEL219) {
            Serializable compiled = MVEL.compileExpression(expr);
            Boolean ret = (Boolean) MVEL.executeExpression(compiled, vars);
            assertTrue(ret);
        }

        String[] templateTestCasesMVEL219 = {
                "@{map['foo']==map['foo']}", // ok
                "@{(map['one'] > 0)}", // ok
                "@{(map['one'] > 0) && (map['foo'] == map['foo'])}", // ok
                "@{(map['one'] > 0) && (map['foo']==map['foo'])}" // broken
        };

        for (String expr : templateTestCasesMVEL219) {
            boolean ret = (boolean) TemplateRuntime.eval(expr, vars);
            assertTrue(ret);
        }
    }

    @Test
    public void testStringCoercion() {
        String expr = " buffer = new StringBuilder(); i = 10; buffer.append( i + \"blah\" ); buffer.toString()";
        Serializable s = MVEL.compileExpression(expr);
        assertEquals("10blah", MVEL.executeExpression(s,new HashMap()).toString());

        expr = "@code{ buffer = new StringBuilder(); i = 10; buffer.append( i + \"blah\" );}@{buffer.toString()}";
        Object ret = TemplateRuntime.eval(expr, new HashMap());
        assertEquals("10blah", ret.toString());
    }

    @Test
    public void testMVEL197() {
        String template = "${(args[0].name=='name1') ? 'a' : 'b'}";
        Object[] args = new Object[1];
        Demo.Person test = new Demo.Person();
        test.setName("name1");
        args[0] = test;
        Map<String, Object> context = new HashMap<String, Object>();
        context.put("args", args);

        assertEquals("a", TemplateRuntime.eval(template, context));
    }

    @Test
    public void testEscaping() {
        String template = "@@{'foo'}ABC";
        assertEquals("@{'foo'}ABC", TemplateRuntime.eval(template, new Object()));
    }


    @Test
    public void testOutputStream1() {
        final StringBuilder sb = new StringBuilder();
        OutputStream os = new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                sb.append((char) b);
            }
        };

        String template = "@foreach{item:['foo','far']}@{item}@end{}";
        CompiledTemplate compiled = TemplateCompiler.compileTemplate(template);

        TemplateRuntime.execute(compiled, new HashMap(), os);
        assertEquals("foofar", sb.toString());
    }
}
