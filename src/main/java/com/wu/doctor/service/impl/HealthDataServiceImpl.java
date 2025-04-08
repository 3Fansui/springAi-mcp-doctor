package com.wu.doctor.service.impl;

import com.wu.doctor.entity.HealthData;
import com.wu.doctor.mapper.HealthDataMapper;
import com.wu.doctor.service.HealthDataService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class HealthDataServiceImpl extends ServiceImpl<HealthDataMapper, HealthData> implements HealthDataService {
}
