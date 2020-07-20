package zjw.cat.producer.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import zjw.cat.producer.CalcCountService;
import zjw.cat.producer.entity.CalcCount;
import zjw.cat.producer.mapper.CalcCountMapper;

/**
 * @author zhoujiawei
 * @ClassName: IfmApiConfigServiceImpl
 * @Description: api service 接口
 * @since 2019-06-24
 */
@Service
public class CalcCountServiceImpl extends ServiceImpl<CalcCountMapper, CalcCount> implements CalcCountService {
    @Override
    public void add(CalcCount s) {
        this.save(s);
    }

    @Override
    public Integer count(Integer i, Integer j) {
        return i + j;
    }
}
































