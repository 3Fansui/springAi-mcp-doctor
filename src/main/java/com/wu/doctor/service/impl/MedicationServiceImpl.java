package com.wu.doctor.service.impl;

import com.wu.doctor.entity.Medication;
import com.wu.doctor.mapper.MedicationMapper;
import com.wu.doctor.service.MedicationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class MedicationServiceImpl extends ServiceImpl<MedicationMapper, Medication> implements MedicationService {
}
