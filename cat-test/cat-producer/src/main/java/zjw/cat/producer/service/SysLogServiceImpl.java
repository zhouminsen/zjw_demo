package zjw.cat.producer.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import zjw.cat.producer.entity.SysLog;
import zjw.cat.producer.mapper.SysLogMapper;


@Service
public class SysLogServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements SysLogService {


}

