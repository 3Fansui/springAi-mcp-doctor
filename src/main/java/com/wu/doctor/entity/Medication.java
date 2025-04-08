package com.wu.doctor.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@TableName("medication")
public class Medication {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long familyMemberId;
    private String medicineName;
    private String dosage;
    private String frequency;
    private String remindTimes;
    private LocalDate startDate;
    private LocalDate endDate;
    private String notes;
    private Integer status; // 1: 启用, 0: 禁用

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}
