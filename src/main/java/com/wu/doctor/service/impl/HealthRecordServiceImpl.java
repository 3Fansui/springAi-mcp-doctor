package com.wu.doctor.service.impl;

import com.wu.doctor.entity.HealthRecord;
import com.wu.doctor.mapper.HealthRecordMapper;
import com.wu.doctor.service.HealthRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class HealthRecordServiceImpl extends ServiceImpl<HealthRecordMapper, HealthRecord> implements HealthRecordService {
}
