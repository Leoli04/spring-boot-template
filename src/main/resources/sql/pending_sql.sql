create table ic_task
(
    id               bigint not null comment '主键' primary key,
    task_type        varchar(40) not null comment '任务类型',
    task_status      varchar(40) not null comment '任务状态,doing:处理中,done:已完成',
    completed_time   timestamp comment '完成时间',
    deleted          int comment '删除标志',
    create_user      varchar(40) comment '创建人',
    create_time      timestamp comment '创建时间',
    update_user varchar(40) comment '修改人',
    update_time timestamp comment '修改时间'
) comment '任务处理表';