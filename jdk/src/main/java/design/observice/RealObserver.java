package design.observice;

/**
 * Created by Administrator on 2018/8/24.
 * 监控者实现
 */
public class RealObserver implements Observer {
    private String name;

    public RealObserver(String name) {
        this.name = name;
    }

    @Override
    public void update(String message) {
        System.out.println(name + "收到消息:" + message);
    }
}
