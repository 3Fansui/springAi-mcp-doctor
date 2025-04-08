package com.wu.doctor.controller;

import com.wu.doctor.entity.HealthRecord;
import com.wu.doctor.service.HealthRecordService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/health-records")
@RequiredArgsConstructor
public class HealthRecordController {

    private final HealthRecordService healthRecordService;

    // 获取指定家庭成员的健康记录
    @GetMapping("/member/{memberId}")
    public List<HealthRecord> getRecordsByMember(@PathVariable Long memberId) {
        return healthRecordService.list(new QueryWrapper<HealthRecord>().eq("family_member_id", memberId).orderByDesc("record_date"));
    }

    @GetMapping("/{id}")
    public HealthRecord getRecordById(@PathVariable Long id) {
        return healthRecordService.getById(id);
    }

    @PostMapping
    public boolean addRecord(@RequestBody HealthRecord record) {
        // 可以在此添加校验，确保 family_member_id 不为空
        if (record.getFamilyMemberId() == null) {
           return false; // 或抛出异常
        }
        return healthRecordService.save(record);
    }

     @PutMapping("/{id}")
     public boolean updateRecord(@PathVariable Long id, @RequestBody HealthRecord record) {
         record.setId(id);
         return healthRecordService.updateById(record);
     }

     @DeleteMapping("/{id}")
     public boolean deleteRecord(@PathVariable Long id) {
         return healthRecordService.removeById(id);
     }
}
