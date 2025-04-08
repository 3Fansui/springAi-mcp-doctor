package com.wu.doctor.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("health_data")
public class HealthData {
    @TableId(type = IdType.AUTO)
    private Long id;
    private Long familyMemberId;
    private String dataType; // blood_pressure, blood_sugar, weight
    private String value;
    private String unit;
    private LocalDateTime recordTime;
    private String notes;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdAt;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedAt;
}
