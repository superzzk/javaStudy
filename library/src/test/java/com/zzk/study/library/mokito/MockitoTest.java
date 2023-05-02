package com.zzk.study.library.mokito;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.*;
import org.mockito.exceptions.verification.TooFewActualInvocations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

//@RunWith(MockitoJUnitRunner.class)
public class MockitoTest {
    @Mock
    private List<String> mockedList;
    @Spy
    private List<String> spiedList = new ArrayList<>();
    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void mock_verify() {
        final List<String> mockList = Mockito.mock(List.class);
        mockList.add("one");
        verify(mockList).add("one");
        assertEquals(0, mockList.size());

        when(mockList.size()).thenReturn(100);
        assertEquals(100, mockList.size());
    }

    @Test
    public final void when_doReturn() {
        final MyList listMock = Mockito.mock(MyList.class);
        doReturn(false).when(listMock).add(anyString());

        final boolean added = listMock.add(randomAlphabetic(6));
        assertThat(added, is(false));
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void mock_with_name() {
        MyList listMock = mock(MyList.class, "myMock");
        when(listMock.add(anyString())).thenReturn(false);
        // 少传一个参数
        listMock.add(randomAlphabetic(6));

        thrown.expect(TooFewActualInvocations.class);
        thrown.expectMessage(containsString("myMock.add"));

        verify(listMock, times(2)).add(anyString());
    }

    @Test(expected = IllegalStateException.class)
    public final void when_throw() {
        final MyList listMock = Mockito.mock(MyList.class);
        when(listMock.add(anyString())).thenThrow(IllegalStateException.class);

        listMock.add(randomAlphabetic(6));
    }

    @Test(expected = NullPointerException.class)
    public final void when_doThrow() {
        final MyList listMock = Mockito.mock(MyList.class);
        doThrow(NullPointerException.class).when(listMock).clear();

        listMock.clear();
    }

    @Test
    public final void when_then_then() {
        final MyList listMock = Mockito.mock(MyList.class);
        when(listMock.add(anyString())).thenReturn(false).thenThrow(IllegalStateException.class);

        listMock.add(randomAlphabetic(6));
    }

    @Test(expected = IllegalStateException.class)
    public final void when_then_thenThrow() {
        final MyList listMock = Mockito.mock(MyList.class);
        when(listMock.add(anyString())).thenReturn(false).thenThrow(IllegalStateException.class);

        listMock.add(randomAlphabetic(6));
        listMock.add(randomAlphabetic(6));
    }

    @Test
    public final void when_call_real_method() {
        final MyList listMock = Mockito.mock(MyList.class);
        when(listMock.size()).thenCallRealMethod();

        assertThat(listMock.size(), equalTo(1));
    }

    @Test
    public final void answer() {
        final MyList listMock = Mockito.mock(MyList.class);
        doAnswer(invocation -> "Always the same").when(listMock).get(anyInt());

        final String element = listMock.get(1);
        assertThat(element, is(equalTo("Always the same")));
    }

    private static class CustomAnswer implements Answer<Boolean> {
        @Override
        public Boolean answer(InvocationOnMock invocation) throws Throwable {
            return false;
        }
    }

    @Test
    public void mock_with_answer() {
        MyList listMock = mock(MyList.class, new CustomAnswer());
        boolean added = listMock.add(randomAlphabetic(6));

        verify(listMock).add(anyString());
        assertThat(added, is(false));
    }

    @Test
    public void mock_with_setting() {
        MockSettings customSettings = withSettings().defaultAnswer(new CustomAnswer());
        MyList listMock = mock(MyList.class, customSettings);
        boolean added = listMock.add(randomAlphabetic(6));

        verify(listMock).add(anyString());
        assertThat(added, is(false));
    }
    @Test
    public void mock_with_annotation() {
        mockedList.add("one");
        Mockito.verify(mockedList).add("one");
        assertEquals(0, mockedList.size());

        Mockito.when(mockedList.size()).thenReturn(100);
        assertEquals(100, mockedList.size());
    }

    @Test
    public void spy() {
        final List<String> spyList = Mockito.spy(new ArrayList<String>());
        spyList.add("one");
        spyList.add("two");

        verify(spyList).add("one");
        verify(spyList).add("two");

        assertEquals(2, spyList.size());

        doReturn(100).when(spyList).size();
        assertEquals(100, spyList.size());
    }

    @Test
    public void spy_with_annotation() {
        spiedList.add("one");
        spiedList.add("two");

        Mockito.verify(spiedList).add("one");
        Mockito.verify(spiedList).add("two");

        assertEquals(2, spiedList.size());

        Mockito.doReturn(100).when(spiedList).size();
        assertEquals(100, spiedList.size());
    }
    
    @Test
    public void captor() {
        final List<String> mockList = Mockito.mock(List.class);
        final ArgumentCaptor<String> arg = ArgumentCaptor.forClass(String.class);
        mockList.add("one");
        Mockito.verify(mockList).add(arg.capture());

        assertEquals("one", arg.getValue());
    }

    @Captor
    private
    ArgumentCaptor<String> argCaptor;

    @Test
    public void captor_with_annotation() {
        mockedList.add("one");
        Mockito.verify(mockedList).add(argCaptor.capture());

        assertEquals("one", argCaptor.getValue());
    }

    @Mock
    private Map<String, String> wordMap;

    @InjectMocks
    private MyDictionary dic = new MyDictionary();

    @Test
    public void inject_mock() {
        Mockito.when(wordMap.get("aWord")).thenReturn("aMeaning");

        assertEquals("aMeaning", dic.getMeaning("aWord"));
    }

}
