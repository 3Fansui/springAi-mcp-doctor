package com.wu.doctor.controller;

import com.wu.doctor.entity.Medication;
import com.wu.doctor.service.MedicationService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/medications")
@RequiredArgsConstructor
public class MedicationController {

    private final MedicationService medicationService;

    // 获取指定家庭成员的用药记录
    @GetMapping("/member/{memberId}")
    public List<Medication> getMedicationsByMember(@PathVariable Long memberId) {
        return medicationService.list(new QueryWrapper<Medication>().eq("family_member_id", memberId).orderByDesc("created_at"));
    }

    @GetMapping("/{id}")
    public Medication getMedicationById(@PathVariable Long id) {
        return medicationService.getById(id);
    }

    @PostMapping
    public boolean addMedication(@RequestBody Medication medication) {
        if (medication.getFamilyMemberId() == null) {
           return false;
        }
        return medicationService.save(medication);
    }

     @PutMapping("/{id}")
     public boolean updateMedication(@PathVariable Long id, @RequestBody Medication medication) {
         medication.setId(id);
         return medicationService.updateById(medication);
     }

     @DeleteMapping("/{id}")
     public boolean deleteMedication(@PathVariable Long id) {
         return medicationService.removeById(id);
     }
}
