/*
 Navicat MySQL Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 50724
 Source Host           : 127.0.0.1:3306
 Source Schema         : ndnu-oj-1

 Target Server Type    : MySQL
 Target Server Version : 50724
 File Encoding         : 65001

 Date: 23/07/2024 23:11:44
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for answer
-- ----------------------------
DROP TABLE IF EXISTS `answer`;
CREATE TABLE `answer`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '题解标题',
  `code` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '题解代码',
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '题解内容',
  `questionId` bigint(20) NOT NULL COMMENT '题目id',
  `userId` mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '创建用户id',
  `createTime` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '最终更改时间',
  `isDelete` tinyint(4) NULL DEFAULT 0 COMMENT '是否逻辑删除',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `question_answer_id_uindex`(`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '题解表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for exam
-- ----------------------------
DROP TABLE IF EXISTS `exam`;
CREATE TABLE `exam`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `questionGroupId` bigint(20) NOT NULL COMMENT '题目集id',
  `userId` bigint(20) NOT NULL COMMENT '提交用户id',
  `examSubmitIdList` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '代码集合',
  `score` double NULL DEFAULT NULL COMMENT '成绩',
  `status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '状态（0未批阅， 1已批阅）',
  `actionTime` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '开始作答时间',
  `submitTime` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '提交时间',
  `isDelete` tinyint(4) NULL DEFAULT 0 COMMENT '是否逻辑删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1786092389414445058 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '考试提交表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for exam_submit
-- ----------------------------
DROP TABLE IF EXISTS `exam_submit`;
CREATE TABLE `exam_submit`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `examId` bigint(20) NOT NULL COMMENT '考试id',
  `language` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '语言',
  `code` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '代码',
  `questionId` bigint(20) NOT NULL COMMENT '问题id',
  `updateTime` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最终提交时间',
  `score` double NULL DEFAULT 0 COMMENT '本题得分',
  `status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '批阅状态（0未批阅 ，1已批阅）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '考试答案提交表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for quesitongroup_usergroup
-- ----------------------------
DROP TABLE IF EXISTS `quesitongroup_usergroup`;
CREATE TABLE `quesitongroup_usergroup`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `questionGroupId` bigint(20) NOT NULL COMMENT '题目集id',
  `userGroupId` bigint(20) NOT NULL COMMENT '用户集Id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户集和题目集联查表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for question
-- ----------------------------
DROP TABLE IF EXISTS `question`;
CREATE TABLE `question`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `title` varchar(512) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '标题',
  `content` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL COMMENT '内容',
  `tags` varchar(1024) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '标签列表（json 数组）',
  `answer` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL COMMENT '题目答案',
  `submitNum` int(11) NOT NULL DEFAULT 0 COMMENT '题目提交数',
  `acceptedNum` int(11) NOT NULL DEFAULT 0 COMMENT '题目通过数',
  `judgeCase` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL COMMENT '判题用例（json 数组）',
  `judgeConfig` text CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL COMMENT '判题配置（json 对象）',
  `thumbNum` int(11) NOT NULL DEFAULT 0 COMMENT '点赞数',
  `favourNum` int(11) NOT NULL DEFAULT 0 COMMENT '收藏数',
  `answerNumber` int(11) NOT NULL DEFAULT 0 COMMENT '题解数量',
  `questionDegree` int(11) NOT NULL DEFAULT 0 COMMENT '问题难度 简单（0） 中等（1） 困难（2）',
  `userId` bigint(20) NOT NULL COMMENT '创建用户 id',
  `createTime` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `isDelete` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_userId`(`userId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1786085218354155523 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '题目' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for question_group
-- ----------------------------
DROP TABLE IF EXISTS `question_group`;
CREATE TABLE `question_group`  (
  `id` bigint(20) NOT NULL COMMENT '题目組id',
  `groupName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '组名',
  `questionIdList` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '分组中的题目id',
  `userId` bigint(20) NOT NULL COMMENT '创建分组用户id',
  `createTime` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `isDelete` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否逻辑删除',
  `userGroupIdList` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '用户分组id(text类型）可为多值',
  `actionTime` datetime(0) NULL DEFAULT NULL COMMENT '开始时间',
  `endTime` datetime(0) NULL DEFAULT NULL COMMENT '结束时间',
  `status` tinyint(4) NULL DEFAULT 0 COMMENT '开放状态(0草稿，1已开放,   2已关闭）',
  `limitTime` int(11) NULL DEFAULT NULL COMMENT '限制考试时间',
  `latestTime` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后访问时间',
  `score` int(11) NULL DEFAULT NULL,
  `announcement` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `explanation` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for question_submit
-- ----------------------------
DROP TABLE IF EXISTS `question_submit`;
CREATE TABLE `question_submit`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `language` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '编程语言',
  `code` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户代码',
  `judgeInfo` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '判题信息（json 对象）',
  `status` int(11) NOT NULL DEFAULT 0 COMMENT '判题状态（0 - 待判题、1 - 判题中、2 - 成功、3 - 失败）',
  `questionId` bigint(20) NOT NULL COMMENT '题目 id',
  `userId` bigint(20) NOT NULL COMMENT '创建用户 id',
  `createTime` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `isDelete` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否删除',
  `solveStatus` int(11) NULL DEFAULT 0 COMMENT '是否解决该题目 未解决（0） 已解决（1）',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_questionId`(`questionId`) USING BTREE,
  INDEX `idx_userId`(`userId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1786092043136892930 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '题目提交' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for questiongroup_question
-- ----------------------------
DROP TABLE IF EXISTS `questiongroup_question`;
CREATE TABLE `questiongroup_question`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `questionGroupId` bigint(20) NOT NULL COMMENT '问题集id',
  `questionId` bigint(20) NOT NULL COMMENT '题目id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 16 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '题目集和题目联查表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `userAccount` varchar(256) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '账号',
  `userPassword` varchar(512) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL COMMENT '密码',
  `unionId` varchar(256) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '微信开放平台id',
  `mpOpenId` varchar(256) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '公众号openId',
  `userName` varchar(256) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `userAvatar` varchar(1024) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '用户头像',
  `userProfile` varchar(512) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL DEFAULT NULL COMMENT '用户简介',
  `userRole` varchar(256) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL DEFAULT 'user' COMMENT '用户角色：user/admin/ban',
  `solveList` text CHARACTER SET utf8 COLLATE utf8_bin NULL COMMENT '已完成题目id集合',
  `createTime` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `isDelete` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否删除',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_unionId`(`unionId`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1786086579053481987 CHARACTER SET = utf8 COLLATE = utf8_unicode_ci COMMENT = '用户' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_group
-- ----------------------------
DROP TABLE IF EXISTS `user_group`;
CREATE TABLE `user_group`  (
  `id` bigint(20) NOT NULL COMMENT '用戶組id',
  `groupName` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '组名',
  `userIdList` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '分组中的用户',
  `userId` bigint(20) NOT NULL COMMENT '创建分组用户id',
  `createTime` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updateTime` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '修改时间',
  `isDelete` tinyint(4) NOT NULL DEFAULT 0 COMMENT '是否逻辑删除',
  `latestTime` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后访问时间',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户组描述',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for user_usergroup
-- ----------------------------
DROP TABLE IF EXISTS `user_usergroup`;
CREATE TABLE `user_usergroup`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `userId` bigint(20) NOT NULL COMMENT '用户id',
  `userGroupId` bigint(20) NOT NULL COMMENT '用户集Id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户和用户集联查表' ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
