package com.wu.doctor.controller;

import com.wu.doctor.entity.FamilyMember;
import com.wu.doctor.service.FamilyMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/family-members")
@RequiredArgsConstructor
public class FamilyMemberController {

    private final FamilyMemberService familyMemberService;

    @GetMapping
    public List<FamilyMember> getAllMembers() {
        return familyMemberService.list(); // 获取所有成员
    }

    @GetMapping("/{id}")
    public FamilyMember getMemberById(@PathVariable Long id) {
        return familyMemberService.getById(id);
    }

     @PostMapping
     public boolean addMember(@RequestBody FamilyMember member) {
         return familyMemberService.save(member);
     }

     @PutMapping("/{id}")
     public boolean updateMember(@PathVariable Long id, @RequestBody FamilyMember member) {
         member.setId(id); // 确保ID设置正确
         return familyMemberService.updateById(member);
     }

     @DeleteMapping("/{id}")
     public boolean deleteMember(@PathVariable Long id) {
         // 注意：删除成员会级联删除相关记录，需要谨慎操作或添加逻辑
         return familyMemberService.removeById(id);
     }
}
