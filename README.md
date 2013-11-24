Before Java 8, concrete methods could only exist on classes, so the default JUnit test runner doesn't look for tests on interfaces. The purpose of the Java8Runner is to find and run tests defined in virtual extension methods.

The example code below shows how to use the test runner. This example would have worked just as well using an abstract class instead of an interface. The real benefit comes from implementing two or more interfaces that both include tests. Usually you'd do this when you want to test a class that also implements two or more interfaces.

```java
public interface ListTestCase
{
    <T> List<T> newWith(T... elements);

    @Test
    default void get()
    {
        List<Integer> list = this.newWith(1, 2, 3);
        Assert.assertEquals(Integer.valueOf(1), list.get(0));
        Assert.assertEquals(Integer.valueOf(2), list.get(1));
        Assert.assertEquals(Integer.valueOf(3), list.get(2));
    }

    @Test
    default void set()
    {
        List<Integer> list = this.newWith(1, 2, 3);
        list.set(1, 4);
        Assert.assertEquals(Arrays.asList(1, 4, 3), list);
    }
}

@RunWith(Java8Runner.class)
public class ArrayListTest implements ListTestCase
{
    @Override
    public <T> List<T> newWith(T... elements)
    {
        return new ArrayList<>(Arrays.asList(elements));
    }
}
```
