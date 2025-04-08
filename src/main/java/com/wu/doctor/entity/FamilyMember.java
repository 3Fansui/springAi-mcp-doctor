package com.wu.doctor.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("family_member")
public class FamilyMember {
    @TableId(type = IdType.AUTO)
    private Long id;
    private String memberType;
    private String memberName;
}
