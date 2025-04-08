package com.wu.doctor.service.impl;

import com.wu.doctor.entity.FamilyMember;
import com.wu.doctor.mapper.FamilyMemberMapper;
import com.wu.doctor.service.FamilyMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class FamilyMemberServiceImpl extends ServiceImpl<FamilyMemberMapper, FamilyMember> implements FamilyMemberService {
    // 实现自定义业务方法
}
