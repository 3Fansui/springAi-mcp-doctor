package com.wu.doctor.controller;

import com.wu.doctor.entity.HealthData;
import com.wu.doctor.service.HealthDataService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/health-data")
@RequiredArgsConstructor
public class HealthDataController {

    private final HealthDataService healthDataService;

    // 获取指定家庭成员的健康数据
    @GetMapping("/member/{memberId}")
    public List<HealthData> getDataByMember(
            @PathVariable Long memberId,
            @RequestParam(required = false) String dataType // 可选参数，按类型筛选
    ) {
        QueryWrapper<HealthData> queryWrapper = new QueryWrapper<HealthData>()
                .eq("family_member_id", memberId)
                .orderByDesc("record_time");
        if (dataType != null && !dataType.isEmpty()) {
            queryWrapper.eq("data_type", dataType);
        }
        return healthDataService.list(queryWrapper);
    }

    @GetMapping("/{id}")
    public HealthData getDataById(@PathVariable Long id) {
        return healthDataService.getById(id);
    }

    @PostMapping
    public boolean addData(@RequestBody HealthData healthData) {
        if (healthData.getFamilyMemberId() == null) {
            return false;
        }
        return healthDataService.save(healthData);
    }

     @PutMapping("/{id}")
     public boolean updateData(@PathVariable Long id, @RequestBody HealthData healthData) {
         healthData.setId(id);
         return healthDataService.updateById(healthData);
     }

     @DeleteMapping("/{id}")
     public boolean deleteData(@PathVariable Long id) {
         return healthDataService.removeById(id);
     }
}
