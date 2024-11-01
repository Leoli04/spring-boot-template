package com.leoli04.springboottemplate.repository.dao.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Description: 基础实体类
 * @Author: LeoLi04
 * @CreateDate: 2024/11/1 10:35
 * @Version: 1.0
 */
@Data
@Slf4j
public class BasicEntity implements Serializable {
    private static final long serialVersionUID = 140529130738311446L;
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @TableField(value = "deleted",fill = FieldFill.INSERT)
    private Integer deleted;

    @TableField(value = "create_time",fill = FieldFill.INSERT)
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @TableField(value = "create_user",fill = FieldFill.INSERT)
    private String createUser;

    @TableField(value = "update_user",fill = FieldFill.INSERT_UPDATE)
    private String updateUser;
}
