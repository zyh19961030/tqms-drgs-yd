# 2022.9.25
ALTER TABLE `tqmsn`.`question`
ADD COLUMN `question_version` int(255) NULL COMMENT '问卷版本' AFTER `write_frequency`;

UPDATE `tqmsn`.`question` SET `question_version` = 100 WHERE `question_version` is null;

ALTER TABLE `tqmsn`.`answer_check`
ADD COLUMN `question_version` int(255) NULL DEFAULT NULL COMMENT '问卷版本' AFTER `del`;

CREATE TABLE `tqmsn`.`question_version`  (
                                     `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
                                     `qu_id` bigint(20) NULL DEFAULT NULL COMMENT '问卷id',
                                     `qu_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '问卷名称',
                                     `qu_desc` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '问卷描述',
                                     `qu_status` int(11) NULL DEFAULT NULL COMMENT '0:草稿箱 1:已发布',
                                     `qu_stop` int(11) NULL DEFAULT NULL COMMENT '0:正常1:已停用',
                                     `dept_ids` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '科室编辑(填报)权限，科室id逗号分割',
                                     `see_dept_ids` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '科室查看权限，科室id逗号分割',
                                     `table_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '答案对应数据库名',
                                     `del` tinyint(4) NULL DEFAULT NULL COMMENT '0:正常1:已删除',
                                     `creater` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建者',
                                     `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
                                     `updater` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新者',
                                     `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
                                     `category_id` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '分类id',
                                     `category_type` int(255) NULL DEFAULT NULL COMMENT '0其他 1单病种 2检查表',
                                     `icon` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图标',
                                     `write_frequency` int(11) NULL DEFAULT NULL COMMENT '填报频次 0患者登记表 1月度汇总表 2季度汇总表 3年度汇总表',
                                     `question_version` int(255) NULL DEFAULT NULL COMMENT '问卷版本',
                                     `current_create_time` datetime(0) NULL DEFAULT NULL COMMENT '当前创建时间',
                                     PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '问卷版本表' ROW_FORMAT = Dynamic;

CREATE TABLE `tqmsn`.`qsubject_version`  (
                                     `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
                                     `question_version_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '问卷版本表id',
                                     `qu_id` bigint(20) NULL DEFAULT NULL COMMENT '问卷id',
                                     `subject_id` int(11) NULL DEFAULT NULL COMMENT '题目id',
                                     `order_num` int(11) NULL DEFAULT NULL COMMENT '序号',
                                     `sub_name` varchar(2000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '题目名称',
                                     `sub_type` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '题目类型 1.单选   2.多选   3.日期   4.时间  5.下拉框    6.单行文本   7.多行文本  8.分组框  9.提示标题 10.日期选择器-月 11.日期选择器-季度 12.日期选择器-年 13.单选题（带分值、带附件） 14.下拉单选题（带分值、带附件） 15.分值型分组框 16.满意度评价题 17.科室选择改为数据源下拉单选 18.医院人员选择 19.数量统计题型 20.选择题分数求和题型 21.结果评价题型 22.数值题求和题型 23.预设题目数量题型',
                                     `required` int(11) NULL DEFAULT 0 COMMENT '是否为必填 0:非必填 1:必填',
                                     `display` int(11) NULL DEFAULT NULL COMMENT '默认是否显示 0:不显示 1:显示',
                                     `limit_words` int(11) NULL DEFAULT NULL COMMENT '最多字数限制',
                                     `text_check` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文本框校验 integer:整数  decimal:小数 email:电子邮箱 ch:中文 en:英文 idcard:身份证号码 qqno:QQ号 phone:手机号码(仅支持大陆地区) tel:电话号码',
                                     `text_hight` int(11) NULL DEFAULT NULL COMMENT '多行文本框高度',
                                     `section` int(11) NULL DEFAULT NULL COMMENT '时间是否为区间 0:非区间 1:区间',
                                     `def_value` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '默认值',
                                     `def_display` int(11) NULL DEFAULT NULL COMMENT '时间显示方式 0:时分 1:时分秒',
                                     `remark` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注',
                                     `jump_logic` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '跳题逻辑',
                                     `group_ids` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '分组题包含题号',
                                     `bind_value` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '绑定值',
                                     `grab` int(11) NULL DEFAULT NULL COMMENT '是否抓取0:不是抓取  1:抓取',
                                     `grab_type` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '抓取类型',
                                     `column_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '数据库列名',
                                     `column_type` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建数据库列名的数据类型中文名称,反显用',
                                     `column_type_database` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建数据库列名的数据类型英文名称',
                                     `del` tinyint(4) NULL DEFAULT NULL COMMENT '0:正常1:已删除',
                                     `creater` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建者',
                                     `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
                                     `updater` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新者',
                                     `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
                                     `special_jump_logic` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '特殊跳题逻辑,前端使用',
                                     `value_min` decimal(10, 4) NULL DEFAULT NULL COMMENT '数字类型最小值',
                                     `value_max` decimal(10, 4) NULL DEFAULT NULL COMMENT '数字类型最大值',
                                     `tips` varchar(5000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '提示属性',
                                     `mark` int(10) NULL DEFAULT NULL COMMENT '是否开启痕迹 改0:不开启1:开启 ',
                                     `score` decimal(10, 4) NULL DEFAULT NULL COMMENT '分值',
                                     `score_type` int(10) NULL DEFAULT NULL COMMENT '计算类型  0加分  1减分',
                                     `arrangement` int(10) NULL DEFAULT NULL COMMENT '排列方式 0每行1列  1每行2列 2每行3列 3每行4列  4每行5列  5每行6列',
                                     `view_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '视图名称',
                                     `condition_type` int(255) NULL DEFAULT NULL COMMENT '针对 19.数量统计题型 的统计的答案值的条件类型  0等于 1大于 2大于等于 3小于 4小于等于',
                                     `condition_value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '针对 19.数量统计题型 的统计的答案值的条件的值',
                                     `choice_subject_id` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '针对 19.数量统计题型 20.选择题分数求和题型 21.结果评价题型 22.数值题求和题型 选择统计题目的id集合',
                                     `subject_count` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '针对  23.预设题目数量题型 的项目总数量',
                                     `relation_subject_id` bigint(20) NULL DEFAULT NULL COMMENT '针对  17.数据源下拉单选 的关联题目id',
                                     `statistical_rules` int(10) NULL DEFAULT NULL COMMENT '统计规则(针对 19.数量统计题型) 0按答案 1按分值',
                                     `current_create_time` datetime(0) NULL DEFAULT NULL COMMENT '当前创建时间',
                                     PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '题目版本表' ROW_FORMAT = Dynamic;

CREATE TABLE `tqmsn`.`qoption_version`  (
                                     `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '主键',
                                     `question_version_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '问卷版本表id',
                                     `question_id` bigint(20) NULL DEFAULT NULL COMMENT '问卷id',
                                     `subject_version_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '题目版本表id',
                                     `subject_id` bigint(20) NULL DEFAULT NULL COMMENT '题目id',
                                     `option_id` bigint(20) NULL DEFAULT NULL COMMENT '选项id',
                                     `op_name` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '选项名称',
                                     `op_order` int(11) NULL DEFAULT NULL COMMENT '选项顺序',
                                     `jump_logic` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '跳题逻辑',
                                     `others` int(11) NULL DEFAULT NULL COMMENT '是否其他  0:否 1:是',
                                     `bind_value` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '绑定值',
                                     `del` tinyint(4) NULL DEFAULT NULL COMMENT '0:正常1:已删除',
                                     `creater` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '创建者',
                                     `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
                                     `updater` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '更新者',
                                     `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
                                     `op_value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '选项值',
                                     `special_jump_logic` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '特殊跳题逻辑,前端使用',
                                     `option_score` decimal(10, 4) NULL DEFAULT NULL COMMENT '选项分值',
                                     `answer_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '答案',
                                     `answer_value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '答案值',
                                     `greater_than_value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '大于的值',
                                     `less_than_value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '小于的值',
                                     `current_create_time` datetime(0) NULL DEFAULT NULL COMMENT '当前创建时间',
                                     PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '选项版本表' ROW_FORMAT = Dynamic;

#2022.9.29
ALTER TABLE `tqmsn`.`question`
    ADD COLUMN `template_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '关联模板id_查检表文件' AFTER `question_version`;

INSERT INTO `tqmsn`.`tb_data`(`id`, `code`, `creator`, `createTime`, `dataType`, `keyValue`, `parientId`, `remark`, `status`, `updator`, `updateTIme`, `value`) VALUES ('ed7b8ed104af4963a55c359e4929106e', NULL, 'admin', '2022-09-29 23:56:07', 'questionCheckCategory', '0', NULL, '查检表问卷设置分类', 'open', NULL, NULL, '医疗');
INSERT INTO `tqmsn`.`tb_data`(`id`, `code`, `creator`, `createTime`, `dataType`, `keyValue`, `parientId`, `remark`, `status`, `updator`, `updateTIme`, `value`) VALUES ('3801840aa1ce4912bc9fb0efef58bd47', NULL, 'admin', '2022-09-29 23:56:07', 'questionCheckCategory', '0', NULL, '查检表问卷设置分类', 'open', NULL, NULL, '护理');
INSERT INTO `tqmsn`.`tb_data`(`id`, `code`, `creator`, `createTime`, `dataType`, `keyValue`, `parientId`, `remark`, `status`, `updator`, `updateTIme`, `value`) VALUES ('abcb99878b8545a9a57d6bd274c18937', NULL, 'admin', '2022-09-29 23:56:07', 'questionCheckCategory', '0', NULL, '查检表问卷设置分类', 'open', NULL, NULL, '院感');
INSERT INTO `tqmsn`.`tb_data`(`id`, `code`, `creator`, `createTime`, `dataType`, `keyValue`, `parientId`, `remark`, `status`, `updator`, `updateTIme`, `value`) VALUES ('3830bde219db494ab018c4ba5c1df4b1', NULL, 'admin', '2022-09-29 23:56:07', 'questionCheckCategory', '0', NULL, '查检表问卷设置分类', 'open', NULL, NULL, '药事-护理');
INSERT INTO `tqmsn`.`tb_data`(`id`, `code`, `creator`, `createTime`, `dataType`, `keyValue`, `parientId`, `remark`, `status`, `updator`, `updateTIme`, `value`) VALUES ('64fd224ca0314317819fd0f05c246191', NULL, 'admin', '2022-09-29 23:56:07', 'questionCheckCategory', '0', NULL, '查检表问卷设置分类', 'open', NULL, NULL, '药事-医疗');

-- 2022.11.3
ALTER TABLE `tqmsn`.`question`
ADD COLUMN `traceability_status` int(10) NULL DEFAULT NULL COMMENT '溯源状态 1未生成 2已生成' AFTER `template_id`;