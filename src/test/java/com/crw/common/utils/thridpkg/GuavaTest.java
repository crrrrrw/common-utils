package com.crw.common.utils.thridpkg;

import com.google.common.base.*;
import com.google.common.base.Optional;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.*;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.common.io.Files;
import com.google.common.util.concurrent.*;
import com.sun.istack.internal.Nullable;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * google guava测试
 */
public class GuavaTest {
    //-----------------------------------------------Optional-------------------------------------------------------------

    /**
     * guava的Optional类似于Java 8新增的Optional类，都是用来处理null的，不过guava的是抽象类，其实现类为Absent和Present，
     * 而java.util的是final类。其中一部分方法名是相同的。
     * <p>
     * Guava用Optional表示可能为null的T类型引用。一个Optional实例可能包含非null的引用（我们称之为引用存在），也可能什么也不包括（称之为引用缺失）。
     * 它从不说包含的是null值，而是用存在或缺失来表示。但Optional从不会包含null值引用。
     */
    @Test
    public void testOptional() {
        Integer value1 = null;
        Integer value2 = 10;
       /*创建指定引用的Optional实例，若引用为null则快速失败返回absent()
         absent()创建引用缺失的Optional实例
        */
        Optional<Integer> a = Optional.fromNullable(value1);
        Optional<Integer> b = Optional.of(value2); //返回包含给定的非空引用Optional实例
        System.out.println(sum(a, b));
    }

    private Integer sum(Optional<Integer> a, Optional<Integer> b) {
        //isPresent():如果Optional包含非null的引用（引用存在），返回true
        System.out.println("First param is present: " + a.isPresent());
        System.out.println("Second param is present: " + b.isPresent());
        Integer value1 = a.or(0);  //返回Optional所包含的引用,若引用缺失,返回指定的值
        Integer value2 = b.get(); //返回所包含的实例,它必须存在,通常在调用该方法时会调用isPresent()判断是否为null
        return value1 + value2;
    }

    //-----------------------------------------------Preconditions-------------------------------------------------------------

    /**
     * 前置条件Preconditions提供静态方法来检查方法或构造函数，被调用是否给定适当的参数。
     * 它检查的先决条件。其方法失败抛出IllegalArgumentException。
     */
    @Test
    public void testPreconditions() {
        try {
            getValue(5);
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }

        try {
            sum(4, null);
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }

        try {
            sqrt(-1);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private double sqrt(double input) {
        Preconditions.checkArgument(input > 0.0,
                "Illegal Argument passed: Negative value %s.", input);
        return Math.sqrt(input);
    }

    private int sum(Integer a, Integer b) {
        a = Preconditions.checkNotNull(a,
                "Illegal Argument passed: First parameter is Null.");
        b = Preconditions.checkNotNull(b,
                "Illegal Argument passed: Second parameter is Null.");
        return a + b;
    }

    private int getValue(int input) {
        int[] data = {1, 2, 3, 4, 5};
        int index = Preconditions.checkElementIndex(input, data.length,
                "Illegal Argument passed: Invalid index.");
        return data[index];
    }


    //-----------------------------------------------Joiner-------------------------------------------------------------

    /**
     * Joiner 提供了各种方法来处理字符串加入操作，对象等。
     * <p>
     * Joiner的实例不可变的，因此是线程安全的。
     */
    @Test
    public void testJoiner() {
        /*
         on:制定拼接符号，如：test1-test2-test3 中的 “-“ 符号
         skipNulls()：忽略NULL,返回一个新的Joiner实例
         useForNull(“Hello”)：NULL的地方都用字符串”Hello”来代替
        */
        StringBuilder sb = new StringBuilder();
        Joiner.on(",").skipNulls().appendTo(sb, "Hello", "guava");
        System.out.println(sb);
        System.out.println(Joiner.on(",").useForNull("none").join(1, null, 3));
        System.out.println(Joiner.on(",").skipNulls().join(Arrays.asList(1, 2, 3, 4, null, 6)));
        Map<String, String> map = new HashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        map.put("key3", "value3");
        System.out.println(Joiner.on(" , ").withKeyValueSeparator("->").join(map));
    }


    //-----------------------------------------------Splitter-------------------------------------------------------------

    /**
     * Splitter 能够将一个字符串按照指定的分隔符拆分成可迭代遍历的字符串集合，Iterable
     */
    @Test
    public void testSplitter() {
        /*
         on():指定分隔符来分割字符串
         limit():当分割的子字符串达到了limit个时则停止分割
         fixedLength():根据长度来拆分字符串
         trimResults():去掉子串中的空格
         omitEmptyStrings():去掉空的子串
         withKeyValueSeparator():要分割的字符串中key和value间的分隔符,分割后的子串中key和value间的分隔符默认是=
        */
        System.out.println(Splitter.on(",").limit(3).trimResults().split(" a,  b,  c,  d"));//[ a, b, c,d]
        System.out.println(Splitter.fixedLength(3).split("1 2 3"));//[1 2,  3]
        System.out.println(Splitter.on(" ").omitEmptyStrings().splitToList("1  2 3"));
        System.out.println(Splitter.on(",").omitEmptyStrings().split("1,,,,2,,,3"));//[1, 2, 3]
        System.out.println(Splitter.on(" ").trimResults().split("1 2 3")); //[1, 2, 3],默认的连接符是,
        System.out.println(Splitter.on(";").withKeyValueSeparator(":").split("a:1;b:2;c:3"));//{a=1, b=2, c=3}
    }

    //-----------------------------------------------EventBus-------------------------------------------------------------

    /**
     * Guava为我们提供了事件总线EventBus库，它是事件发布-订阅模式的实现，让我们能在领域驱动设计(DDD)中以事件的弱引用本质对我们的模块和领域边界很好的解耦设计。
     * <p>
     * Guava为我们提供了同步事件EventBus和异步实现AsyncEventBus两个事件总线，他们都不是单例的。
     * <p>
     * Guava发布的事件默认不会处理线程安全的，但我们可以标注@AllowConcurrentEvents来保证其线程安全
     * <p>
     * 如果Listener A监听Event A, 而Event A有一个子类Event B, 此时Listener A将同时接收Event A和B消息
     */
    @Test
    public void testEventBus() {
        EventBus eventBus = new EventBus("jack");
       /*
         如果多个subscriber订阅了同一个事件,那么每个subscriber都将收到事件通知
         并且收到事件通知的顺序跟注册的顺序保持一致
        */
        eventBus.register(new EventListener()); //注册订阅者
        eventBus.register(new MultiEventListener());
        eventBus.post(new OrderEvent("hello")); //发布事件
        eventBus.post(new OrderEvent("world"));
        eventBus.post("!");
    }

    /**
     * Guava 发布-订阅模式中传递的事件,是一个普通的POJO类
     */
    class OrderEvent {  //事件
        private String message;

        public OrderEvent(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }
    }

    /**
     * 订阅者
     */
    class EventListener {

        //@Subscribe保证有且只有一个输入参数,如果你需要订阅某种类型的消息,只需要在指定的方法上加上@Subscribe注解即可
        @Subscribe
        public void listen(OrderEvent event) {
            System.out.println("receive message: " + event.getMessage());
        }

        /*
          一个subscriber也可以同时订阅多个事件
          Guava会通过事件类型来和订阅方法的形参来决定到底调用subscriber的哪个订阅方法
         */
        @Subscribe
        public void listen(String message) {
            System.out.println("receive message: " + message);
        }
    }

    /**
     * 多个订阅者
     */
    class MultiEventListener {

        @Subscribe
        public void listen(OrderEvent event) {
            System.out.println("receive msg: " + event.getMessage());
        }

        @Subscribe
        public void listen(String message) {
            System.out.println("receive msg: " + message);
        }
    }


    //-----------------------------------------------Collection-------------------------------------------------------------

    /**
     * 不可变集合
     * 创建不可变集合方法：
     * <p>
     * - copyOf方法，如ImmutableSet.copyOf(set);
     * - of方法，如ImmutableSet.of(“a”, “b”, “c”)或 ImmutableMap.of(“a”, 1, “b”, 2);
     * - Builder工具
     */
    @Test
    public void testImmutableSet() {
        ImmutableSet<String> set = ImmutableSet.of("a", "b", "c", "d");
        ImmutableSet<String> set1 = ImmutableSet.copyOf(set);
        ImmutableSet<String> set2 = ImmutableSet.<String>builder().addAll(set).add("e").build();
        ImmutableList<String> list = set.asList();
        System.out.println(set);
        System.out.println(set1);
        System.out.println(set2);
        System.out.println(list);
    }

    /**
     * 新型集合类
     * Multiset 可统计一个词在文档中出现了多少次
     */
    @Test
    public void testMultiset() {
        Multiset<String> set = LinkedHashMultiset.create();
        set.add("a");
        set.add("a");
        set.add("a");
        set.add("a");
        set.setCount("a", 5); //添加或删除指定元素使其在集合中的数量是count
        System.out.println(set.count("a")); //给定元素在Multiset中的计数
        System.out.println(set);
        System.out.println(set.size()); //所有元素计数的总和,包括重复元素
        System.out.println(set.elementSet().size()); //所有元素计数的总和,不包括重复元素
        set.clear(); //清空集合
        System.out.println(set);
    }

    /**
     * Multimap 可以很容易地把一个键映射到多个值。换句话说，Multimap是把键映射到任意多个值的一般方式。
     */
    @Test
    public void testMultimap() {
        Multimap<String, Integer> map = HashMultimap.create(); //Multimap是把键映射到任意多个值的一般方式
        map.put("a", 1); //key相同时不会覆盖原value
        map.put("a", 2);
        map.put("a", 3);
        System.out.println(map); //{a=[1, 2, 3]}
        System.out.println(map.get("a")); //返回的是集合
        System.out.println(map.size()); //返回所有”键-单个值映射”的个数,而非不同键的个数
        System.out.println(map.keySet().size()); //返回不同key的个数
        Map<String, Collection<Integer>> mapView = map.asMap();
        System.out.println(mapView);
    }

    /**
     * BiMap
     */
    @Test
    public void testBiMap() {
        BiMap<String, String> biMap = HashBiMap.create();
        biMap.put("sina", "sina.com");
        biMap.put("qq", "qq.com");
        biMap.put("sina", "sina.cn"); //会覆盖原来的value
        /*
         在BiMap中,如果你想把键映射到已经存在的值，会抛出IllegalArgumentException异常
         如果对特定值,你想要强制替换它的键，请使用 BiMap.forcePut(key, value)
        */
        try {
            biMap.put("tecent", "qq.com"); //抛出异常
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        biMap.forcePut("tecent", "qq.com"); //强制替换key
        System.out.println(biMap);
        System.out.println(biMap.inverse().get("sina.com")); //通过value找key
        System.out.println(biMap.inverse().inverse() == biMap); //true
    }

    /**
     * Table
     * Table它有两个支持所有类型的键：“行” 和 “列”。
     */
    @Test
    public void testTable() {
        //记录学生在某门课上的成绩
        Table<String, String, Integer> table = HashBasedTable.create();
        table.put("jack", "java", 100);
        table.put("jack", "c", 90);
        table.put("mike", "java", 93);
        table.put("mike", "c", 100);
        Set<Table.Cell<String, String, Integer>> cells = table.cellSet();
        for (Table.Cell<String, String, Integer> cell : cells) {
            System.out.println(cell.getRowKey() + " " + cell.getColumnKey() + " " + cell.getValue());
        }
        System.out.println(table.row("jack"));
        System.out.println(table);
        System.out.println(table.rowKeySet());
        System.out.println(table.columnKeySet());
        System.out.println(table.values());
    }


    //-----------------------------------------------Collections2-------------------------------------------------------------

    /**
     * filter（）：只保留集合中满足特定要求的元素
     */
    @Test
    public void testFilter() {
        List<String> list = Lists.newArrayList("moon", "dad", "refer", "son");
        Collection<String> palindromeList = Collections2.filter(list, input -> {
            return new StringBuilder(input).reverse().toString().equals(input); //找回文串
        });
        System.out.println(palindromeList);
    }

    /**
     * transform（）：类型转换
     */
    @Test
    public void testTransform() {
        Set<Long> times = Sets.newHashSet();
        times.add(91299990701L);
        times.add(9320001010L);
        times.add(9920170621L);
        Collection<String> timeStrCol = Collections2.transform(times, input -> new SimpleDateFormat("yyyy-MM-dd").format(input));
        System.out.println(timeStrCol);


        // 多个 Function 组合
        List<String> list = Lists.newArrayList("abcde", "good", "happiness");
        //确保容器中的字符串长度不超过5
        Function<String, String> f1 = input -> input.length() > 5 ? input.substring(0, 5) : input;
        //转成大写
        Function<String, String> f2 = input -> input.toUpperCase();
        Function<String, String> function = Functions.compose(f1, f2);
        Collection<String> results = Collections2.transform(list, function);
        System.out.println(results);
    }

    /**
     * 集合操作：交集、差集、并集
     */
    @Test
    public void testCollectionOperation() {
        Set<Integer> set1 = Sets.newHashSet(1, 2, 3, 4, 5);
        Set<Integer> set2 = Sets.newHashSet(3, 4, 5, 6);
        Sets.SetView<Integer> inter = Sets.intersection(set1, set2); //交集
        System.out.println(inter);
        Sets.SetView<Integer> diff = Sets.difference(set1, set2); //差集,在A中不在B中
        System.out.println(diff);
        Sets.SetView<Integer> union = Sets.union(set1, set2); //并集
        System.out.println(union);
    }


    //-----------------------------------------------Cache-------------------------------------------------------------

    /**
     * Guava Cache与ConcurrentMap很相似，但也不完全一样。最基本的区别是ConcurrentMap会一直保存所有添加的元素，直到显式地移除。
     * 相对地，Guava Cache为了限制内存占用，通常都设定为自动回收元素。在某些场景下，尽管LoadingCache 不回收元素，它也是很有用的，因为它会自动加载缓存。
     * <p>
     * Guava Cache是一个全内存的本地缓存实现，它提供了线程安全的实现机制。
     * <p>
     * Guava Cache有两种创建方式：
     * 1. cacheLoader
     * 2. callable callback
     * <p>
     * refresh机制：
     * - LoadingCache.refresh(K) 在生成新的value的时候，旧的value依然会被使用。
     * - CacheLoader.reload(K, V) 生成新的value过程中允许使用旧的value
     * - CacheBuilder.refreshAfterWrite(long, TimeUnit) 自动刷新cache
     */
    @Test
    public void testLoadingCache() {
        LoadingCache<String, String> cache = CacheBuilder.newBuilder()
                .maximumSize(100) //最大缓存数目
                .expireAfterAccess(1, TimeUnit.SECONDS) //缓存1秒后过期
                .build(new CacheLoader<String, String>() {
                    @Override
                    public String load(String key) throws Exception {
                        return key;
                    }
                });
        cache.put("j", "java");
        cache.put("c", "cpp");
        cache.put("s", "scala");
        cache.put("g", "go");
        try {
            System.out.println(cache.get("j"));
            TimeUnit.SECONDS.sleep(2);
            System.out.println(cache.get("s")); //输出s
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test() {
        Cache<String, String> cache = CacheBuilder.newBuilder()
                .maximumSize(100)
                .expireAfterAccess(1, TimeUnit.SECONDS)
                .build();
        try {
            String result = cache.get("java", () -> "hello java");
            System.out.println(result);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


    //-----------------------------------------------并发-------------------------------------------------------------

    /**
     * ListenableFuture
     * 传统JDK中的Future通过异步的方式计算返回结果:在多线程运算中可能或者可能在没有结束返回结果，Future是运行中的多线程的一个引用句柄，确保在服务执行返回一个Result。
     * <p>
     * ListenableFuture可以允许你注册回调方法(callbacks)，在运算（多线程执行）完成的时候进行调用, 或者在运算（多线程执行）完成后立即执行。
     * 这样简单的改进，使得可以明显的支持更多的操作，这样的功能在JDK concurrent中的Future是不支持的。
     */
    @Test
    public void testListenableFuture() {
        //将ExecutorService装饰成ListeningExecutorService
        ListeningExecutorService service = MoreExecutors.listeningDecorator(
                Executors.newCachedThreadPool());

        //通过异步的方式计算返回结果
        ListenableFuture<String> future = service.submit(() -> {
            System.out.println("call execute..");
            return "task success!";
        });

        //有两种方法可以执行此Future并执行Future完成之后的回调函数
        future.addListener(() -> {  //该方法会在多线程运算完的时候,指定的Runnable参数传入的对象会被指定的Executor执行
            try {
                System.out.println("result: " + future.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }, service);

        Futures.addCallback(future, new FutureCallback<String>() {
            @Override
            public void onSuccess(@Nullable String result) {
                System.out.println("callback result: " + result);
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println(t.getMessage());
            }
        }, service);
    }


    //-----------------------------------------------IO-------------------------------------------------------------
    @Test
    public void testIO() {
    }

    //写文件
    private void writeFile(String content, File file) throws IOException {
        if (!file.exists()) {
            file.createNewFile();
        }
        Files.write(content.getBytes(Charsets.UTF_8), file);
    }

    //读文件
    private List<String> readFile(File file) throws IOException {
        if (!file.exists()) {
            return ImmutableList.of(); //避免返回null
        }
        return Files.readLines(file, Charsets.UTF_8);
    }

    //文件复制
    private void copyFile(File from, File to) throws IOException {
        if (!from.exists()) {
            return;
        }
        if (!to.exists()) {
            to.createNewFile();
        }
        Files.copy(from, to);
    }
}

