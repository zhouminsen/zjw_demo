package zjw.cat.producer;

import com.baomidou.mybatisplus.extension.service.IService;
import zjw.cat.producer.entity.CalcCount;

/**
 * @author Administrator
 */
public interface CalcCountService extends IService<CalcCount> {

    void add(CalcCount s);

    Integer count(Integer i, Integer j);
}
