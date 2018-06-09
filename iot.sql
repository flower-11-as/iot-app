/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.18 : Database - iot
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`iot` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `iot`;

/*Table structure for table `t_iot_account` */

DROP TABLE IF EXISTS `t_iot_account`;

CREATE TABLE `t_iot_account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `server_id` varchar(64) NOT NULL COMMENT '用户名',
  `password` varchar(64) NOT NULL COMMENT '密码',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '登录状态 1：成功 0：失败',
  `token` varchar(100) DEFAULT NULL COMMENT 'token',
  `last_login_time` datetime DEFAULT NULL COMMENT '登录时间',
  `subscribe_url` varchar(256) DEFAULT NULL COMMENT '订阅地址',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_manager` int(11) NOT NULL COMMENT '创建人',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_manager` int(11) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_server_id` (`server_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='iot-账户表';

/*Data for the table `t_iot_account` */

insert  into `t_iot_account`(`id`,`server_id`,`password`,`status`,`token`,`last_login_time`,`subscribe_url`,`create_time`,`create_manager`,`update_time`,`update_manager`) values (2,'jiayingdev01','jsj2018',1,'e0512b24dbb5345923531951996b9d6a','2018-06-10 01:06:39','http://104.224.159.254/iot-manage/iot/callback/account2','2018-04-21 01:11:44',1,'2018-06-10 01:32:51',1);

/*Table structure for table `t_iot_dev_type` */

DROP TABLE IF EXISTS `t_iot_dev_type`;

CREATE TABLE `t_iot_dev_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `server_id` varchar(64) NOT NULL COMMENT 'iot用户名',
  `dev_type` varchar(64) NOT NULL COMMENT '产品类型',
  `del_flag` tinyint(2) NOT NULL DEFAULT '0' COMMENT '删除状态 0：未删除 1：已删除',
  `alarm_service` varchar(128) DEFAULT NULL COMMENT '告警接口',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `unq_serverid_devtype` (`server_id`,`dev_type`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COMMENT='iot-产品型号';

/*Data for the table `t_iot_dev_type` */

insert  into `t_iot_dev_type`(`id`,`server_id`,`dev_type`,`del_flag`,`alarm_service`,`create_time`) values (11,'jiayingdev01','MyLamp',0,'lampAlarmService','2018-04-26 04:16:33');

/*Table structure for table `t_iot_dev_type_command` */

DROP TABLE IF EXISTS `t_iot_dev_type_command`;

CREATE TABLE `t_iot_dev_type_command` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dev_type_id` int(11) NOT NULL COMMENT '产品型号',
  `command_id` varchar(64) DEFAULT NULL COMMENT '指令id',
  `command_name` varchar(64) NOT NULL COMMENT '显示名称',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='iot-设备指令';

/*Data for the table `t_iot_dev_type_command` */

insert  into `t_iot_dev_type_command`(`id`,`dev_type_id`,`command_id`,`command_name`,`create_time`) values (7,11,'DeviceCmd','设备指令','2018-04-26 04:16:33');

/*Table structure for table `t_iot_dev_type_command_param` */

DROP TABLE IF EXISTS `t_iot_dev_type_command_param`;

CREATE TABLE `t_iot_dev_type_command_param` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `command_id` int(11) NOT NULL COMMENT '指令id',
  `param_type` tinyint(4) NOT NULL DEFAULT '1' COMMENT '1、指令参数 2、指令响应参数',
  `param_name` varchar(64) NOT NULL COMMENT '参数名称',
  `data_type` varchar(32) DEFAULT NULL COMMENT '数据类型',
  `show_name` varchar(64) DEFAULT NULL COMMENT '显示名称',
  `pos` int(11) DEFAULT NULL,
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8 COMMENT='iot-设备指令参数';

/*Data for the table `t_iot_dev_type_command_param` */

insert  into `t_iot_dev_type_command_param`(`id`,`command_id`,`param_type`,`param_name`,`data_type`,`show_name`,`pos`,`create_time`) values (34,7,1,'Switch1','bool','开关一',1,'2018-04-26 04:16:33'),(35,7,1,'Period1','binary','开关一时段',2,'2018-04-26 04:16:33'),(36,7,1,'Switch2','bool','开关二',10,'2018-04-26 04:16:33'),(37,7,1,'Period2','binary','开关二时段',11,'2018-04-26 04:16:33'),(38,7,1,'SwitchMode1','bool','开关一控制模式',19,'2018-04-26 04:16:33'),(39,7,1,'SwitchMode2','bool','开关二控制模式',20,'2018-04-26 04:16:33'),(40,7,1,'GPS','binary','GPS定位',21,'2018-04-26 04:16:33'),(41,7,1,'GeneralDataBuffer','binary','通用数据通道',43,'2018-04-26 04:16:33'),(42,7,2,'result',NULL,'指令结果',1,'2018-04-26 04:16:33');

/*Table structure for table `t_iot_dev_type_command_param_ext` */

DROP TABLE IF EXISTS `t_iot_dev_type_command_param_ext`;

CREATE TABLE `t_iot_dev_type_command_param_ext` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `command_param_id` int(11) NOT NULL COMMENT '指令参数id',
  `json` varchar(128) NOT NULL COMMENT '指令参数json',
  `create_manager` int(11) NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_manager` int(11) DEFAULT NULL COMMENT '修改人',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='iot-设备指令扩展';

/*Data for the table `t_iot_dev_type_command_param_ext` */

/*Table structure for table `t_iot_dev_type_message` */

DROP TABLE IF EXISTS `t_iot_dev_type_message`;

CREATE TABLE `t_iot_dev_type_message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dev_type_id` int(11) NOT NULL COMMENT '产品型号',
  `message_id` varchar(64) DEFAULT NULL COMMENT '消息id',
  `message_name` varchar(64) NOT NULL COMMENT '显示名称',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COMMENT='iot-设备消息';

/*Data for the table `t_iot_dev_type_message` */

insert  into `t_iot_dev_type_message`(`id`,`dev_type_id`,`message_id`,`message_name`,`create_time`) values (7,11,'DeviceMessage','设备消息','2018-04-26 04:16:33');

/*Table structure for table `t_iot_dev_type_message_param` */

DROP TABLE IF EXISTS `t_iot_dev_type_message_param`;

CREATE TABLE `t_iot_dev_type_message_param` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `message_id` int(11) DEFAULT NULL COMMENT '消息id',
  `param_name` varchar(64) NOT NULL COMMENT '参数名称',
  `data_type` varchar(32) DEFAULT NULL COMMENT '数据类型',
  `show_name` varchar(64) DEFAULT NULL COMMENT '显示名称',
  `pos` int(11) DEFAULT NULL,
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8 COMMENT='iot-设备消息参数';

/*Data for the table `t_iot_dev_type_message_param` */

insert  into `t_iot_dev_type_message_param`(`id`,`message_id`,`param_name`,`data_type`,`show_name`,`pos`,`create_time`) values (36,7,'Switch1','bool','开关一',1,'2018-04-26 04:16:33'),(37,7,'Period1','binary','开关一时段',2,'2018-04-26 04:16:33'),(38,7,'Switch2','bool','开关二',10,'2018-04-26 04:16:33'),(39,7,'Period2','binary','开关二时段',11,'2018-04-26 04:16:33'),(40,7,'SwitchMode1','bool','开关一控制模式',19,'2018-04-26 04:16:33'),(41,7,'SwitchMode2','bool','开关二控制模式',20,'2018-04-26 04:16:33'),(42,7,'Power','short','功率',21,'2018-04-26 04:16:33'),(43,7,'Voltage','short','电压',23,'2018-04-26 04:16:33'),(44,7,'Current','short','电流',25,'2018-04-26 04:16:33'),(45,7,'GPS','binary','GPS定位',27,'2018-04-26 04:16:33'),(46,7,'GeneralDataBuffer','binary','通用数据通道',49,'2018-04-26 04:16:33');

/*Table structure for table `t_iot_dev_type_message_param_ext` */

DROP TABLE IF EXISTS `t_iot_dev_type_message_param_ext`;

CREATE TABLE `t_iot_dev_type_message_param_ext` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `message_param_id` int(11) NOT NULL COMMENT '指令参数id',
  `json` varchar(128) NOT NULL COMMENT '指令参数json',
  `create_manager` int(11) NOT NULL COMMENT '创建人',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_manager` int(11) DEFAULT NULL COMMENT '修改人',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='iot-设备消息扩展';

/*Data for the table `t_iot_dev_type_message_param_ext` */

/*Table structure for table `t_iot_device` */

DROP TABLE IF EXISTS `t_iot_device`;

CREATE TABLE `t_iot_device` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `server_id` varchar(64) NOT NULL COMMENT '创建与IoT用户',
  `dev_type` varchar(64) NOT NULL COMMENT '产品类型',
  `dev_serial` varchar(64) NOT NULL COMMENT '设备唯一标识',
  `name` varchar(64) NOT NULL COMMENT '设备名称',
  `connect_point_id` varchar(64) NOT NULL COMMENT '连接平台',
  `service_mode` varchar(64) DEFAULT NULL COMMENT '服务模式',
  `is_published` int(11) NOT NULL DEFAULT '0' COMMENT '是否公开： 0、不公开 1、公开',
  `location` varchar(128) DEFAULT NULL COMMENT '设备位置',
  `longitude` double(18,6) DEFAULT NULL COMMENT '经度',
  `latitude` double(18,6) DEFAULT NULL COMMENT '维度',
  `end_user_info` varchar(32) DEFAULT NULL COMMENT '终端用户信息',
  `end_user_name` varchar(32) DEFAULT NULL COMMENT '终端用户名称',
  `industry_name` varchar(32) DEFAULT NULL COMMENT '产品类别（前）',
  `category_name` varchar(64) DEFAULT NULL COMMENT '产品类别（后）',
  `display_icon_id` int(11) DEFAULT NULL COMMENT 'iot icon',
  `client_id` varchar(128) DEFAULT NULL COMMENT 'clientID',
  `protocol_type` varchar(32) DEFAULT NULL COMMENT 'CoAP',
  `has_sim_Card` varchar(32) DEFAULT NULL COMMENT '是否有SIM卡',
  `sim_num` varchar(128) DEFAULT NULL COMMENT 'sim号',
  `del_flag` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0、未删除 1、已删除',
  `extend_type` varchar(64) DEFAULT NULL COMMENT '扩展类型',
  `area_code` varchar(256) DEFAULT NULL COMMENT '设备地址编号',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_manager` int(11) NOT NULL COMMENT '创建人',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_manager` int(11) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_dev_serial` (`dev_serial`)
) ENGINE=InnoDB AUTO_INCREMENT=192 DEFAULT CHARSET=utf8 COMMENT='iot-设备';

/*Data for the table `t_iot_device` */

insert  into `t_iot_device`(`id`,`server_id`,`dev_type`,`dev_serial`,`name`,`connect_point_id`,`service_mode`,`is_published`,`location`,`longitude`,`latitude`,`end_user_info`,`end_user_name`,`industry_name`,`category_name`,`display_icon_id`,`client_id`,`protocol_type`,`has_sim_Card`,`sim_num`,`del_flag`,`extend_type`,`area_code`,`create_time`,`create_manager`,`update_time`,`update_manager`) values (132,'jiayingdev01','MyLamp','863703036535206','YF0106-5206','ctc-nanjing-iot-137',NULL,0,'五华撤回',116.122379,24.297720,'13670852182','zengjy','交通','自定义路灯类别',1003,'126c7a0789a64c0ebcd669978f77199d',NULL,'1',NULL,0,NULL,NULL,'2018-06-07 00:24:02',1,'2018-06-10 01:17:09',NULL),(133,'jiayingdev01','MyLamp','863703036585193','YF0101_85193','ctc-nanjing-iot-137',NULL,0,'五华撤回',115.789503,23.932011,'13670852182','zengjy','交通','自定义路灯类别',1003,'126c7a0789a64c0ebcd669978f77199d',NULL,'1',NULL,0,NULL,NULL,'2018-06-06 17:54:40',1,'2018-06-10 01:17:09',NULL),(134,'jiayingdev01','MyLamp','863703036569551','YF0006_69551','ctc-nanjing-iot-137',NULL,0,'五华撤回',115.789503,23.940466,'13670852182','zengjy','交通','自定义路灯类别',1003,'126c7a0789a64c0ebcd669978f77199d',NULL,'1',NULL,0,NULL,NULL,'2018-06-06 17:45:53',1,'2018-06-10 01:17:09',NULL),(135,'jiayingdev01','MyLamp','863703036557721','wh_edrx_0077-13','ctc-nanjing-iot-137',NULL,0,'五华县水寨镇沿江中路13杆',115.784586,23.940353,'13670852182','zengjy','交通','自定义路灯类别',1003,'126c7a0789a64c0ebcd669978f77199d',NULL,'1',NULL,0,NULL,NULL,'2018-06-03 02:34:03',1,'2018-06-10 01:17:09',NULL),(136,'jiayingdev01','MyLamp','863703036533599','wh_edrx_0057-12','ctc-nanjing-iot-137',NULL,0,'五华县水寨镇沿江中路12杆',115.784622,23.940336,'13670852182','zengjy','交通','自定义路灯类别',1003,'126c7a0789a64c0ebcd669978f77199d',NULL,'1',NULL,0,NULL,NULL,'2018-06-03 02:33:00',1,'2018-06-10 01:17:09',NULL),(137,'jiayingdev01','MyLamp','863703036557663','wh_edrx_0133-11','ctc-nanjing-iot-137',NULL,0,'五华县水寨镇沿江中路11杆',115.784568,23.940320,'13670852182','zengjy','交通','自定义路灯类别',1003,'126c7a0789a64c0ebcd669978f77199d',NULL,'1',NULL,0,NULL,NULL,'2018-06-03 02:31:46',1,'2018-06-10 01:17:10',NULL),(138,'jiayingdev01','MyLamp','863703037549867','wh_edrx_0066-09','ctc-nanjing-iot-137',NULL,0,'五华县水寨镇沿江中路09杆',115.784488,23.940212,'13670852182','zengjy','交通','自定义路灯类别',1003,'126c7a0789a64c0ebcd669978f77199d',NULL,'1',NULL,0,NULL,NULL,'2018-06-03 02:24:52',1,'2018-06-10 01:17:10',NULL),(139,'jiayingdev01','MyLamp','863703036534555','wh_edrx_0042-08','ctc-nanjing-iot-137',NULL,0,'五华县水寨镇沿江中路08杆',115.784506,23.940237,'13670852182','zengjy','交通','自定义路灯类别',1003,'126c7a0789a64c0ebcd669978f77199d',NULL,'1',NULL,0,NULL,NULL,'2018-06-03 02:22:01',1,'2018-06-10 01:17:10',NULL),(140,'jiayingdev01','MyLamp','863703036533698','wh_edrx_0150-06','ctc-nanjing-iot-137',NULL,0,'五华县水寨镇沿江中路06号杆',115.784586,23.940353,'13670852182','zengjy','交通','自定义路灯类别',1003,'126c7a0789a64c0ebcd669978f77199d',NULL,'1',NULL,0,NULL,NULL,'2018-06-03 02:16:04',1,'2018-06-10 01:17:10',NULL),(141,'jiayingdev01','MyLamp','863703036536691','wh_edrx_0070-04','ctc-nanjing-iot-137',NULL,0,'五华县水寨镇沿江中路04号杆',115.784550,23.940295,'13670852182','zengjy','交通','自定义路灯类别',1003,'126c7a0789a64c0ebcd669978f77199d',NULL,'1',NULL,0,NULL,NULL,'2018-06-03 02:09:02',1,'2018-06-10 01:17:10',NULL),(142,'jiayingdev01','MyLamp','863703036555451','wh_edrx_0046-03','ctc-nanjing-iot-137',NULL,0,'五华县水寨镇沿江中路03号杆',115.784559,23.940320,'13670852182','zengjy','交通','自定义路灯类别',1003,'126c7a0789a64c0ebcd669978f77199d',NULL,'1',NULL,0,NULL,NULL,'2018-06-03 02:05:54',1,'2018-06-10 01:17:10',NULL),(143,'jiayingdev01','MyLamp','863703037547978','wh_edrx_0058-02','ctc-nanjing-iot-137',NULL,0,'五华县水寨镇没江中路02号杆',115.784532,23.940270,'13670852182','zengjy','交通','自定义路灯类别',1003,'126c7a0789a64c0ebcd669978f77199d',NULL,'1',NULL,0,NULL,NULL,'2018-06-03 02:01:18',1,'2018-06-10 01:17:10',NULL),(144,'jiayingdev01','MyLamp','863703036589575','wh_edrx_0064','ctc-nanjing-iot-137',NULL,0,'五华县水寨镇沿江中路',115.784506,23.940254,'13670852182','zengjy','交通','自定义路灯类别',1003,'126c7a0789a64c0ebcd669978f77199d',NULL,'1',NULL,0,NULL,NULL,'2018-06-03 01:45:12',1,'2018-06-10 01:17:10',NULL),(145,'jiayingdev01','MyLamp','863703036584758','wh_0006-07','ctc-nanjing-iot-137',NULL,0,'五华县水寨镇沿江中路',115.784401,23.940092,'13670852182','zengjy','交通','自定义路灯类别',1003,'126c7a0789a64c0ebcd669978f77199d',NULL,'1',NULL,0,NULL,NULL,'2018-06-02 23:26:01',1,'2018-06-10 01:17:10',NULL),(146,'jiayingdev01','MyLamp','863703036534498','wh0112','ctc-nanjing-iot-137',NULL,0,'五华县水寨镇沿江中路',115.784320,23.940017,'13670852182','zengjy','交通','自定义路灯类别',1003,'126c7a0789a64c0ebcd669978f77199d',NULL,'1',NULL,0,NULL,NULL,'2018-06-02 17:57:43',1,'2018-06-10 01:17:10',NULL),(147,'jiayingdev01','MyLamp','863703036568751','wh0039','ctc-nanjing-iot-137',NULL,0,'五华县水寨镇',115.782604,23.930425,'13670852182','zengjy','交通','自定义路灯类别',1003,'126c7a0789a64c0ebcd669978f77199d',NULL,'1',NULL,0,NULL,NULL,'2018-06-01 21:28:31',1,'2018-06-10 01:17:10',NULL),(148,'jiayingdev01','MyLamp','863703036585052','wh0104','ctc-nanjing-iot-137',NULL,0,'13670852182',116.476527,24.220761,'13670852182','zengjy','交通','自定义路灯类别',1003,'126c7a0789a64c0ebcd669978f77199d',NULL,'1',NULL,0,NULL,NULL,'2018-05-31 20:13:35',1,'2018-06-10 01:17:10',NULL),(149,'jiayingdev01','MyLamp','863703036535115','wh0063','ctc-nanjing-iot-137',NULL,0,'五华县水寨镇',116.124103,24.302562,'13670852182','zengjy','交通','自定义路灯类别',1003,'126c7a0789a64c0ebcd669978f77199d',NULL,'1',NULL,0,NULL,NULL,'2018-05-31 20:12:55',1,'2018-06-10 01:17:10',NULL),(150,'jiayingdev01','MyLamp','863703037549784','wh0176','ctc-nanjing-iot-137',NULL,0,'五华县水寨镇',115.781052,23.948317,'13670852182','zengjy','交通','自定义路灯类别',1003,'126c7a0789a64c0ebcd669978f77199d',NULL,'1',NULL,0,NULL,NULL,'2018-05-31 20:10:14',1,'2018-06-10 01:17:10',NULL),(151,'jiayingdev01','MyLamp','863703036590458','wh0035','ctc-nanjing-iot-137',NULL,0,'五华县水寨镇',116.126906,24.302298,'13670852182','zengjy','交通','自定义路灯类别',1003,'126c7a0789a64c0ebcd669978f77199d',NULL,'1',NULL,0,NULL,NULL,'2018-05-31 20:09:55',1,'2018-06-10 01:17:10',NULL),(152,'jiayingdev01','MyLamp','863703036566771','wh0161','ctc-nanjing-iot-137',NULL,0,'五华县水寨镇',116.124894,24.303813,'13670852182','zengjy','交通','自定义路灯类别',1003,'126c7a0789a64c0ebcd669978f77199d',NULL,'1',NULL,0,NULL,NULL,'2018-05-31 20:08:10',1,'2018-06-10 01:17:10',NULL),(153,'jiayingdev01','MyLamp','863703037511990','wh0158','ctc-nanjing-iot-137',NULL,0,'五华县水寨镇',116.126978,24.303681,'13670852182','zengjy','交通','自定义路灯类别',1003,'126c7a0789a64c0ebcd669978f77199d',NULL,'1',NULL,0,NULL,NULL,'2018-05-31 20:06:58',1,'2018-06-10 01:17:10',NULL),(154,'jiayingdev01','MyLamp','863703037549800','wh0174','ctc-nanjing-iot-137',NULL,0,'五华县水寨镇',116.127625,24.303484,'13670852182','zengjy','交通','自定义路灯类别',1003,'126c7a0789a64c0ebcd669978f77199d',NULL,'1',NULL,0,NULL,NULL,'2018-05-31 20:02:50',1,'2018-06-10 01:17:10',NULL),(155,'jiayingdev01','MyLamp','863703036569668','wh0032','ctc-nanjing-iot-137',NULL,0,'五华县水寨镇',116.124535,24.303780,'13670852182','zengjy','交通','自定义路灯类别',1003,'126c7a0789a64c0ebcd669978f77199d',NULL,'1',NULL,0,NULL,NULL,'2018-05-31 19:58:34',1,'2018-06-10 01:17:10',NULL),(156,'jiayingdev01','MyLamp','863703036561731','wh0071','ctc-nanjing-iot-137',NULL,0,'五华县水寨镇',116.125181,24.304175,'13670852182','zengjy','交通','自定义路灯类别',1003,'126c7a0789a64c0ebcd669978f77199d',NULL,'1',NULL,0,NULL,NULL,'2018-05-31 19:56:04',1,'2018-06-10 01:17:10',NULL),(157,'jiayingdev01','MyLamp','863703036533540','wh0078','ctc-nanjing-iot-137',NULL,0,'五华县水寨镇',116.128272,24.305229,'13670852182','zengjy','交通','自定义路灯类别',1003,'126c7a0789a64c0ebcd669978f77199d',NULL,'1',NULL,0,NULL,NULL,'2018-05-31 19:55:28',1,'2018-06-10 01:17:10',NULL),(158,'jiayingdev01','MyLamp','863703036535032','wh0020','ctc-nanjing-iot-137',NULL,0,'五华县水寨镇',116.127265,24.303846,'13670852182','zengjy','交通','自定义路灯类别',1003,'126c7a0789a64c0ebcd669978f77199d',NULL,'1',NULL,0,NULL,NULL,'2018-05-31 19:54:04',1,'2018-06-10 01:17:10',NULL),(159,'jiayingdev01','MyLamp','863703037547820','wh0045','ctc-nanjing-iot-137',NULL,0,'五华县水寨镇',116.126978,24.303385,'13670852182','zengjy','交通','自定义路灯类别',1003,'126c7a0789a64c0ebcd669978f77199d',NULL,'1',NULL,0,NULL,NULL,'2018-05-31 19:51:40',1,'2018-06-10 01:17:10',NULL),(160,'jiayingdev01','MyLamp','863703036535180','wh0151','ctc-nanjing-iot-137',NULL,0,'五华县水寨镇',116.125397,24.304834,'13670852182','zengjy','交通','自定义路灯类别',1003,'126c7a0789a64c0ebcd669978f77199d',NULL,'1',NULL,0,NULL,NULL,'2018-05-31 19:50:37',1,'2018-06-10 01:17:10',NULL),(161,'jiayingdev01','MyLamp','863703036550155','wh0019','ctc-nanjing-iot-137',NULL,0,'五华县水寨镇',116.125325,24.305163,'13670852182','zengjy','交通','自定义路灯类别',1003,'126c7a0789a64c0ebcd669978f77199d',NULL,'1',NULL,0,NULL,NULL,'2018-05-31 19:48:57',1,'2018-06-10 01:17:10',NULL),(162,'jiayingdev01','MyLamp','863703036535081','wh0193','ctc-nanjing-iot-137',NULL,0,'五华县水寨镇',116.125613,24.304900,'13670852182','zengjy','交通','自定义路灯类别',1003,'126c7a0789a64c0ebcd669978f77199d',NULL,'1',NULL,0,NULL,NULL,'2018-05-31 19:48:07',1,'2018-06-10 01:17:10',NULL),(163,'jiayingdev01','MyLamp','863703036590342','wh0125','ctc-nanjing-iot-137',NULL,0,'五华县水寨镇',116.127912,24.305163,'13670852182','zengjy','交通','自定义路灯类别',1003,'126c7a0789a64c0ebcd669978f77199d',NULL,'1',NULL,0,NULL,NULL,'2018-05-31 19:46:41',1,'2018-06-10 01:17:10',NULL),(164,'jiayingdev01','MyLamp','863703036534704','wh0027','ctc-nanjing-iot-137',NULL,0,'五华县水寨镇',116.130428,24.305624,'13670852182','zengjy','交通','自定义路灯类别',1003,'126c7a0789a64c0ebcd669978f77199d',NULL,'1',NULL,0,NULL,NULL,'2018-05-31 19:43:21',1,'2018-06-10 01:17:10',NULL),(165,'jiayingdev01','MyLamp','863703036566946','wh_0293-10','ctc-nanjing-iot-137',NULL,0,'五华县水寨镇',115.775705,23.934125,'13670852182','zengjy','交通','自定义路灯类别',1003,'126c7a0789a64c0ebcd669978f77199d',NULL,'1',NULL,0,NULL,NULL,'2018-05-31 17:24:20',1,'2018-06-10 01:17:10',NULL),(166,'jiayingdev01','MyLamp','863703036531684','wh0032','ctc-nanjing-iot-137',NULL,0,'五 华县水寨镇',115.778004,23.927783,'13670852182','zengjy','交通','自定义路灯类别',1003,'126c7a0789a64c0ebcd669978f77199d',NULL,'1',NULL,0,NULL,NULL,'2018-05-31 17:14:32',1,'2018-06-10 01:17:10',NULL),(167,'jiayingdev01','MyLamp','863703037547895','wh0149','ctc-nanjing-iot-137',NULL,0,'五华县水寨镇',115.786053,23.940466,'13670852182','zengjy','交通','自定义路灯类别',1003,'126c7a0789a64c0ebcd669978f77199d',NULL,'1',NULL,0,NULL,NULL,'2018-05-31 10:57:36',1,'2018-06-10 01:17:10',NULL),(168,'jiayingdev01','MyLamp','863703036589534','wh0018','ctc-nanjing-iot-137',NULL,0,'五华县水寨镇',115.788928,23.938881,'13670852182','zengjy','交通','自定义路灯类别',1003,'126c7a0789a64c0ebcd669978f77199d',NULL,'1',NULL,0,NULL,NULL,'2018-05-31 08:34:35',1,'2018-06-10 01:17:10',NULL),(169,'jiayingdev01','MyLamp','863703037547424','qx_7424','ctc-nanjing-iot-137',NULL,0,'qxhy',116.128703,24.306678,'13670852182','zengjy','交通','自定义路灯类别',1003,'126c7a0789a64c0ebcd669978f77199d',NULL,'1',NULL,0,NULL,NULL,'2018-05-18 00:04:46',1,'2018-06-10 01:17:10',NULL),(170,'jiayingdev01','MyLamp','863703036590433','db_lamp0433','ctc-nanjing-iot-137',NULL,0,'',116.596684,24.405173,'13670852182','zengjy','交通','自定义路灯类别',1003,'126c7a0789a64c0ebcd669978f77199d',NULL,'1',NULL,0,NULL,NULL,'2018-05-09 00:22:12',1,'2018-06-10 01:17:10',NULL),(171,'jiayingdev01','MyLamp','863703037547671','qx_K99_7671','ctc-nanjing-iot-137',NULL,0,'',116.105131,24.290343,'13670852182','zengjy','交通','自定义路灯类别',1003,'126c7a0789a64c0ebcd669978f77199d',NULL,'1',NULL,0,NULL,NULL,'2018-05-03 00:29:26',1,'2018-06-10 01:17:10',NULL),(172,'jiayingdev01','MyLamp','863703037512931','qx_K13_2931','ctc-nanjing-iot-137',NULL,0,'',116.122954,24.300882,'13670852182','zengjy','交通','自定义路灯类别',1003,'126c7a0789a64c0ebcd669978f77199d',NULL,'1',NULL,0,NULL,NULL,'2018-05-02 23:41:03',1,'2018-06-10 01:17:10',NULL),(173,'jiayingdev01','MyLamp','863703037548018','qx_K28_8018','ctc-nanjing-iot-137',NULL,0,'',116.118354,24.324065,'13670852182','zengjy','交通','自定义路灯类别',1003,'126c7a0789a64c0ebcd669978f77199d',NULL,'1',NULL,0,NULL,NULL,'2018-05-02 13:01:37',1,'2018-06-10 01:17:10',NULL),(174,'jiayingdev01','MyLamp','863703036575186','qx_5186','ctc-nanjing-iot-137',NULL,0,'',115.778579,23.956847,'13670852182','zengjy','交通','自定义路灯类别',1003,'126c7a0789a64c0ebcd669978f77199d',NULL,'1',NULL,0,NULL,NULL,'2018-04-27 22:22:30',1,'2018-06-10 01:17:11',NULL),(175,'jiayingdev01','MyLamp','863703036549033','db_err_9033','ctc-nanjing-iot-137',NULL,0,'',115.768231,23.953677,'13670852182','zengjy','交通','自定义路灯类别',1003,'126c7a0789a64c0ebcd669978f77199d',NULL,'1',NULL,0,NULL,NULL,'2018-04-27 22:20:32',1,'2018-06-10 01:17:11',NULL),(176,'jiayingdev01','MyLamp','863703036548985','qx_8985','ctc-nanjing-iot-137',NULL,0,'',115.764206,23.952620,'13670852182','zengjy','交通','自定义路灯类别',1003,'126c7a0789a64c0ebcd669978f77199d',NULL,'1',NULL,0,NULL,NULL,'2018-04-27 22:17:02',1,'2018-06-10 01:17:11',NULL),(177,'jiayingdev01','MyLamp','863703036549009','qx_9009','ctc-nanjing-iot-137',NULL,0,'',115.772830,23.949978,'13670852182','zengjy','交通','自定义路灯类别',1003,'126c7a0789a64c0ebcd669978f77199d',NULL,'1',NULL,0,NULL,NULL,'2018-04-27 13:40:57',1,'2018-06-10 01:17:11',NULL),(178,'jiayingdev01','MyLamp','863703036533466','xk604_3466','ctc-nanjing-iot-137',NULL,0,'',115.773980,23.942580,'13670852182','zengjy','交通','自定义路灯类别',1003,'126c7a0789a64c0ebcd669978f77199d',NULL,'1',NULL,0,NULL,NULL,'2018-04-27 10:02:02',1,'2018-06-10 01:17:11',NULL),(179,'jiayingdev01','MyLamp','863703036590250','qx_0250','ctc-nanjing-iot-137',NULL,0,'',115.756732,23.930425,'13670852182','zengjy','交通','自定义路灯类别',1003,'126c7a0789a64c0ebcd669978f77199d',NULL,'1',NULL,0,NULL,NULL,'2018-04-27 08:39:36',1,'2018-06-10 01:17:11',NULL),(180,'jiayingdev01','MyLamp','863703037531113','db_lamp1113','ctc-nanjing-iot-137',NULL,0,'',115.738335,23.932539,'13670852182','zengjy','交通','自定义路灯类别',1003,'126c7a0789a64c0ebcd669978f77199d',NULL,'1',NULL,0,NULL,NULL,'2018-04-27 08:25:34',1,'2018-06-10 01:17:11',NULL),(181,'jiayingdev01','MyLamp','863703036557986','db_7986','ctc-nanjing-iot-137',NULL,0,'',115.782604,23.948393,'13670852182','zengjy','交通','自定义路灯类别',1003,'126c7a0789a64c0ebcd669978f77199d',NULL,'1',NULL,0,NULL,NULL,'2018-04-27 00:49:42',1,'2018-06-10 01:17:11',NULL),(182,'jiayingdev01','MyLamp','460111176771394','lamp1394','ctc-nanjing-iot-137',NULL,0,'',116.131577,24.324065,'13670852182','zengjy','交通','自定义路灯类别',1003,'126c7a0789a64c0ebcd669978f77199d',NULL,'1',NULL,0,NULL,NULL,'2018-04-26 00:16:35',1,'2018-06-10 01:17:11',NULL),(183,'jiayingdev01','MyLamp','863703036590375','db_err_0375','ctc-nanjing-iot-137',NULL,0,'',116.128703,24.317216,'13670852182','zengjy','交通','自定义路灯类别',1003,'126c7a0789a64c0ebcd669978f77199d',NULL,'1',NULL,0,NULL,NULL,'2018-04-23 23:21:55',1,'2018-06-10 01:17:11',NULL),(184,'jiayingdev01','MyLamp','863703037550444','db_0444','ctc-nanjing-iot-137',NULL,0,'',116.134452,24.313001,'13670852182','zengjy','交通','自定义路灯类别',1003,'126c7a0789a64c0ebcd669978f77199d',NULL,'1',NULL,0,NULL,NULL,'2018-04-23 23:08:58',1,'2018-06-10 01:17:11',NULL),(185,'jiayingdev01','MyLamp','863703037547739','qx_7739','ctc-nanjing-iot-137',NULL,0,'',116.127553,24.315636,'13670852182','zengjy','交通','自定义路灯类别',1003,'126c7a0789a64c0ebcd669978f77199d',NULL,'1',NULL,0,NULL,NULL,'2018-04-23 21:08:28',1,'2018-06-10 01:17:11',NULL),(186,'jiayingdev01','MyLamp','863703036549231','db_9231','ctc-nanjing-iot-137',NULL,0,'',116.131577,24.318270,'13670852182','zengjy','交通','自定义路灯类别',1003,'126c7a0789a64c0ebcd669978f77199d',NULL,'1',NULL,0,NULL,NULL,'2018-04-23 20:46:57',1,'2018-06-10 01:17:11',NULL),(187,'jiayingdev01','MyLamp','863703036534829','db_4829','ctc-nanjing-iot-137',NULL,0,'',116.131002,24.321431,'13670852182','zengjy','交通','自定义路灯类别',1003,'126c7a0789a64c0ebcd669978f77199d',NULL,'1',NULL,0,NULL,NULL,'2018-04-23 19:29:26',1,'2018-06-10 01:17:11',NULL),(188,'jiayingdev01','MyLamp','863703036557291','db_lamp7291','ctc-nanjing-iot-137',NULL,0,'',116.136752,24.316162,'13670852182','zengjy','交通','自定义路灯类别',1003,'126c7a0789a64c0ebcd669978f77199d',NULL,'1',NULL,0,NULL,NULL,'2018-04-23 17:41:33',1,'2018-06-10 01:17:11',NULL),(189,'jiayingdev01','MyLamp','863703036557655','qx_7655','ctc-nanjing-iot-137',NULL,0,'',116.131002,24.305624,'13670852182','zengjy','交通','自定义路灯类别',1003,'126c7a0789a64c0ebcd669978f77199d',NULL,'1',NULL,0,NULL,NULL,'2018-04-23 16:31:01',1,'2018-06-10 01:17:11',NULL),(190,'jiayingdev01','MyLamp','863703036549124','db_9124','ctc-nanjing-iot-137',NULL,0,'',116.136752,24.310894,'13670852182','zengjy','交通','自定义路灯类别',1003,'126c7a0789a64c0ebcd669978f77199d',NULL,'1',NULL,0,NULL,NULL,'2018-04-19 13:09:32',1,'2018-06-10 01:17:11',NULL),(191,'jiayingdev01','MyLamp','863703036528656','qxpl_lamp8656','ctc-nanjing-iot-137',NULL,0,'',116.136752,24.309840,'13670852182','zengjy','交通','自定义路灯类别',1003,'126c7a0789a64c0ebcd669978f77199d',NULL,'1',NULL,0,NULL,NULL,'2018-04-18 22:16:24',1,'2018-06-10 01:17:11',NULL);

/*Table structure for table `t_iot_device_alarm_config` */

DROP TABLE IF EXISTS `t_iot_device_alarm_config`;

CREATE TABLE `t_iot_device_alarm_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `device_id` int(11) NOT NULL COMMENT '设备id',
  `param_key` varchar(64) NOT NULL COMMENT '参数key',
  `param_value` varchar(128) NOT NULL COMMENT '参数值',
  `description` varchar(256) DEFAULT NULL COMMENT '描述',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_manager` int(11) NOT NULL COMMENT '创建人',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_manager` int(11) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_device_id_param_key` (`device_id`,`param_key`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;

/*Data for the table `t_iot_device_alarm_config` */

/*Table structure for table `t_iot_device_alarm_record` */

DROP TABLE IF EXISTS `t_iot_device_alarm_record`;

CREATE TABLE `t_iot_device_alarm_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `device_id` int(11) NOT NULL COMMENT '设备id',
  `alarm_desc` varchar(512) NOT NULL COMMENT '预警描述',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3357 DEFAULT CHARSET=utf8 COMMENT='iot-设备预警描述';

/*Data for the table `t_iot_device_alarm_record` */

/*Table structure for table `t_iot_device_basic_detail` */

DROP TABLE IF EXISTS `t_iot_device_basic_detail`;

CREATE TABLE `t_iot_device_basic_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `device_id` int(11) NOT NULL COMMENT '设备id',
  `sync_id` int(11) NOT NULL COMMENT '设备消息同步id',
  `param_name` varchar(64) NOT NULL COMMENT '参数名称',
  `param_value` varchar(512) NOT NULL COMMENT '参数值',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4189 DEFAULT CHARSET=utf8 COMMENT='iot-设备基本信息（电量、信号）';

/*Data for the table `t_iot_device_basic_detail` */

insert  into `t_iot_device_basic_detail`(`id`,`device_id`,`sync_id`,`param_name`,`param_value`,`create_time`) values (4069,132,2040,'batteryLevel','66','2018-06-10 01:17:09'),(4070,132,2040,'signalStrength','15','2018-06-10 01:17:09'),(4071,133,2041,'batteryLevel','24','2018-06-10 01:17:09'),(4072,133,2041,'signalStrength','27','2018-06-10 01:17:09'),(4073,134,2042,'batteryLevel','62','2018-06-10 01:17:09'),(4074,134,2042,'signalStrength','99','2018-06-10 01:17:09'),(4075,135,2043,'batteryLevel','100','2018-06-10 01:17:09'),(4076,135,2043,'signalStrength','23','2018-06-10 01:17:09'),(4077,136,2044,'batteryLevel','100','2018-06-10 01:17:09'),(4078,136,2044,'signalStrength','18','2018-06-10 01:17:09'),(4079,137,2045,'batteryLevel','100','2018-06-10 01:17:10'),(4080,137,2045,'signalStrength','23','2018-06-10 01:17:10'),(4081,138,2046,'batteryLevel','26','2018-06-10 01:17:10'),(4082,138,2046,'signalStrength','25','2018-06-10 01:17:10'),(4083,139,2047,'batteryLevel','100','2018-06-10 01:17:10'),(4084,139,2047,'signalStrength','19','2018-06-10 01:17:10'),(4085,140,2048,'batteryLevel','100','2018-06-10 01:17:10'),(4086,140,2048,'signalStrength','22','2018-06-10 01:17:10'),(4087,141,2049,'batteryLevel','100','2018-06-10 01:17:10'),(4088,141,2049,'signalStrength','16','2018-06-10 01:17:10'),(4089,142,2050,'batteryLevel','100','2018-06-10 01:17:10'),(4090,142,2050,'signalStrength','26','2018-06-10 01:17:10'),(4091,143,2051,'batteryLevel','100','2018-06-10 01:17:10'),(4092,143,2051,'signalStrength','21','2018-06-10 01:17:10'),(4093,144,2052,'batteryLevel','81','2018-06-10 01:17:10'),(4094,144,2052,'signalStrength','12','2018-06-10 01:17:10'),(4095,145,2053,'batteryLevel','100','2018-06-10 01:17:10'),(4096,145,2053,'signalStrength','23','2018-06-10 01:17:10'),(4097,146,2054,'batteryLevel','71','2018-06-10 01:17:10'),(4098,146,2054,'signalStrength','99','2018-06-10 01:17:10'),(4099,147,2055,'batteryLevel','61','2018-06-10 01:17:10'),(4100,147,2055,'signalStrength','99','2018-06-10 01:17:10'),(4101,148,2056,'batteryLevel','99','2018-06-10 01:17:10'),(4102,148,2056,'signalStrength','20','2018-06-10 01:17:10'),(4103,149,2057,'batteryLevel','39','2018-06-10 01:17:10'),(4104,149,2057,'signalStrength','15','2018-06-10 01:17:10'),(4105,150,2058,'batteryLevel','83','2018-06-10 01:17:10'),(4106,150,2058,'signalStrength','16','2018-06-10 01:17:10'),(4107,151,2059,'batteryLevel','66','2018-06-10 01:17:10'),(4108,151,2059,'signalStrength','14','2018-06-10 01:17:10'),(4109,152,2060,'batteryLevel','70','2018-06-10 01:17:10'),(4110,152,2060,'signalStrength','99','2018-06-10 01:17:10'),(4111,153,2061,'batteryLevel','0','2018-06-10 01:17:10'),(4112,153,2061,'signalStrength','20','2018-06-10 01:17:10'),(4113,154,2062,'batteryLevel','1','2018-06-10 01:17:10'),(4114,154,2062,'signalStrength','8','2018-06-10 01:17:10'),(4115,155,2063,'batteryLevel','70','2018-06-10 01:17:10'),(4116,155,2063,'signalStrength','99','2018-06-10 01:17:10'),(4117,156,2064,'batteryLevel','0','2018-06-10 01:17:10'),(4118,156,2064,'signalStrength','16','2018-06-10 01:17:10'),(4119,157,2065,'batteryLevel','44','2018-06-10 01:17:10'),(4120,157,2065,'signalStrength','99','2018-06-10 01:17:10'),(4121,158,2066,'batteryLevel','54','2018-06-10 01:17:10'),(4122,158,2066,'signalStrength','18','2018-06-10 01:17:10'),(4123,159,2067,'batteryLevel','91','2018-06-10 01:17:10'),(4124,159,2067,'signalStrength','99','2018-06-10 01:17:10'),(4125,160,2068,'batteryLevel','37','2018-06-10 01:17:10'),(4126,160,2068,'signalStrength','22','2018-06-10 01:17:10'),(4127,161,2069,'batteryLevel','63','2018-06-10 01:17:10'),(4128,161,2069,'signalStrength','99','2018-06-10 01:17:10'),(4129,162,2070,'batteryLevel','62','2018-06-10 01:17:10'),(4130,162,2070,'signalStrength','99','2018-06-10 01:17:10'),(4131,163,2071,'batteryLevel','','2018-06-10 01:17:10'),(4132,163,2071,'signalStrength','','2018-06-10 01:17:10'),(4133,164,2072,'batteryLevel','46','2018-06-10 01:17:10'),(4134,164,2072,'signalStrength','10','2018-06-10 01:17:10'),(4135,165,2073,'batteryLevel','100','2018-06-10 01:17:10'),(4136,165,2073,'signalStrength','24','2018-06-10 01:17:10'),(4137,166,2074,'batteryLevel','70','2018-06-10 01:17:10'),(4138,166,2074,'signalStrength','99','2018-06-10 01:17:10'),(4139,167,2075,'batteryLevel','73','2018-06-10 01:17:10'),(4140,167,2075,'signalStrength','19','2018-06-10 01:17:10'),(4141,168,2076,'batteryLevel','41','2018-06-10 01:17:10'),(4142,168,2076,'signalStrength','99','2018-06-10 01:17:10'),(4143,169,2077,'batteryLevel','36','2018-06-10 01:17:10'),(4144,169,2077,'signalStrength','28','2018-06-10 01:17:10'),(4145,170,2078,'batteryLevel','100','2018-06-10 01:17:10'),(4146,170,2078,'signalStrength','12','2018-06-10 01:17:10'),(4147,171,2079,'batteryLevel','71','2018-06-10 01:17:10'),(4148,171,2079,'signalStrength','19','2018-06-10 01:17:10'),(4149,172,2080,'batteryLevel','95','2018-06-10 01:17:10'),(4150,172,2080,'signalStrength','99','2018-06-10 01:17:10'),(4151,173,2081,'batteryLevel','72','2018-06-10 01:17:10'),(4152,173,2081,'signalStrength','31','2018-06-10 01:17:10'),(4153,174,2082,'batteryLevel','76','2018-06-10 01:17:11'),(4154,174,2082,'signalStrength','31','2018-06-10 01:17:11'),(4155,175,2083,'batteryLevel','86','2018-06-10 01:17:11'),(4156,175,2083,'signalStrength','13','2018-06-10 01:17:11'),(4157,176,2084,'batteryLevel','98','2018-06-10 01:17:11'),(4158,176,2084,'signalStrength','99','2018-06-10 01:17:11'),(4159,177,2085,'batteryLevel','99','2018-06-10 01:17:11'),(4160,177,2085,'signalStrength','22','2018-06-10 01:17:11'),(4161,178,2086,'batteryLevel','66','2018-06-10 01:17:11'),(4162,178,2086,'signalStrength','20','2018-06-10 01:17:11'),(4163,179,2087,'batteryLevel','73','2018-06-10 01:17:11'),(4164,179,2087,'signalStrength','31','2018-06-10 01:17:11'),(4165,180,2088,'batteryLevel','100','2018-06-10 01:17:11'),(4166,180,2088,'signalStrength','14','2018-06-10 01:17:11'),(4167,181,2089,'batteryLevel','100','2018-06-10 01:17:11'),(4168,181,2089,'signalStrength','14','2018-06-10 01:17:11'),(4169,182,2090,'batteryLevel','','2018-06-10 01:17:11'),(4170,182,2090,'signalStrength','','2018-06-10 01:17:11'),(4171,183,2091,'batteryLevel','100','2018-06-10 01:17:11'),(4172,183,2091,'signalStrength','16','2018-06-10 01:17:11'),(4173,184,2092,'batteryLevel','100','2018-06-10 01:17:11'),(4174,184,2092,'signalStrength','17','2018-06-10 01:17:11'),(4175,185,2093,'batteryLevel','0','2018-06-10 01:17:11'),(4176,185,2093,'signalStrength','99','2018-06-10 01:17:11'),(4177,186,2094,'batteryLevel','100','2018-06-10 01:17:11'),(4178,186,2094,'signalStrength','22','2018-06-10 01:17:11'),(4179,187,2095,'batteryLevel','95','2018-06-10 01:17:11'),(4180,187,2095,'signalStrength','10','2018-06-10 01:17:11'),(4181,188,2096,'batteryLevel','100','2018-06-10 01:17:11'),(4182,188,2096,'signalStrength','16','2018-06-10 01:17:11'),(4183,189,2097,'batteryLevel','28','2018-06-10 01:17:11'),(4184,189,2097,'signalStrength','31','2018-06-10 01:17:11'),(4185,190,2098,'batteryLevel','100','2018-06-10 01:17:11'),(4186,190,2098,'signalStrength','18','2018-06-10 01:17:11'),(4187,191,2099,'batteryLevel','56','2018-06-10 01:17:11'),(4188,191,2099,'signalStrength','28','2018-06-10 01:17:11');

/*Table structure for table `t_iot_device_batch` */

DROP TABLE IF EXISTS `t_iot_device_batch`;

CREATE TABLE `t_iot_device_batch` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `task_id` varchar(64) NOT NULL COMMENT 'iot批量任务id',
  `client_id` varchar(64) DEFAULT NULL COMMENT '类似流水号',
  `progress` decimal(18,2) NOT NULL DEFAULT '0.00' COMMENT '进度',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0、处理中 1、处理成功 2、处理失败',
  `data_list` text NOT NULL COMMENT '数据集',
  `result_list` text COMMENT '结果集',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_manager` int(11) NOT NULL COMMENT '创建人',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uniq_task_id` (`task_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='iot-批量注册';

/*Data for the table `t_iot_device_batch` */

/*Table structure for table `t_iot_device_command` */

DROP TABLE IF EXISTS `t_iot_device_command`;

CREATE TABLE `t_iot_device_command` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `device_id` int(11) NOT NULL COMMENT '设备id',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0、处理中 1、处理成功 2、处理失败',
  `req_command_id` varchar(64) NOT NULL COMMENT '请求指令id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='iot-设备指令';

/*Data for the table `t_iot_device_command` */

/*Table structure for table `t_iot_device_command_detail` */

DROP TABLE IF EXISTS `t_iot_device_command_detail`;

CREATE TABLE `t_iot_device_command_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `command_id` int(11) NOT NULL COMMENT '设备指令',
  `param_name` varchar(64) NOT NULL COMMENT '参数名称',
  `param_value` varchar(512) NOT NULL COMMENT '参数值',
  `date_type` varchar(32) DEFAULT NULL COMMENT '数据类型',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='iot-指令详情';

/*Data for the table `t_iot_device_command_detail` */

/*Table structure for table `t_iot_device_ext` */

DROP TABLE IF EXISTS `t_iot_device_ext`;

CREATE TABLE `t_iot_device_ext` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `device_id` int(11) NOT NULL COMMENT '设备id',
  `alarm_status` tinyint(2) NOT NULL DEFAULT '0' COMMENT '预警状态 0、未预警 1、预警中',
  `alarm_desc` varchar(512) DEFAULT NULL COMMENT '预警描述',
  `alarm_time` datetime DEFAULT NULL COMMENT '预警时间',
  `create_time` datetime NOT NULL COMMENT '创建日期',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改日期',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_device_id` (`device_id`)
) ENGINE=InnoDB AUTO_INCREMENT=124 DEFAULT CHARSET=utf8 COMMENT='iot-设备扩展';

/*Data for the table `t_iot_device_ext` */

insert  into `t_iot_device_ext`(`id`,`device_id`,`alarm_status`,`alarm_desc`,`alarm_time`,`create_time`,`update_time`) values (64,132,1,NULL,NULL,'2018-06-07 00:24:02','2018-06-10 01:57:01'),(65,133,0,NULL,NULL,'2018-06-06 17:54:40','2018-06-06 17:54:40'),(66,134,0,NULL,NULL,'2018-06-06 17:45:53','2018-06-06 17:45:53'),(67,135,0,NULL,NULL,'2018-06-03 02:34:03','2018-06-03 02:34:03'),(68,136,0,NULL,NULL,'2018-06-03 02:33:00','2018-06-03 02:33:00'),(69,137,0,NULL,NULL,'2018-06-03 02:31:46','2018-06-03 02:31:46'),(70,138,0,NULL,NULL,'2018-06-03 02:24:52','2018-06-03 02:24:52'),(71,139,0,NULL,NULL,'2018-06-03 02:22:01','2018-06-03 02:22:01'),(72,140,0,NULL,NULL,'2018-06-03 02:16:04','2018-06-03 02:16:04'),(73,141,0,NULL,NULL,'2018-06-03 02:09:02','2018-06-03 02:09:02'),(74,142,0,NULL,NULL,'2018-06-03 02:05:54','2018-06-03 02:05:54'),(75,143,0,NULL,NULL,'2018-06-03 02:01:18','2018-06-03 02:01:18'),(76,144,0,NULL,NULL,'2018-06-03 01:45:12','2018-06-03 01:45:12'),(77,145,0,NULL,NULL,'2018-06-02 23:26:01','2018-06-02 23:26:01'),(78,146,0,NULL,NULL,'2018-06-02 17:57:43','2018-06-02 17:57:43'),(79,147,0,NULL,NULL,'2018-06-01 21:28:31','2018-06-01 21:28:31'),(80,148,0,NULL,NULL,'2018-05-31 20:13:35','2018-05-31 20:13:35'),(81,149,0,NULL,NULL,'2018-05-31 20:12:55','2018-05-31 20:12:55'),(82,150,0,NULL,NULL,'2018-05-31 20:10:14','2018-05-31 20:10:14'),(83,151,0,NULL,NULL,'2018-05-31 20:09:55','2018-05-31 20:09:55'),(84,152,0,NULL,NULL,'2018-05-31 20:08:10','2018-05-31 20:08:10'),(85,153,0,NULL,NULL,'2018-05-31 20:06:58','2018-05-31 20:06:58'),(86,154,0,NULL,NULL,'2018-05-31 20:02:50','2018-05-31 20:02:50'),(87,155,0,NULL,NULL,'2018-05-31 19:58:34','2018-05-31 19:58:34'),(88,156,0,NULL,NULL,'2018-05-31 19:56:04','2018-05-31 19:56:04'),(89,157,0,NULL,NULL,'2018-05-31 19:55:28','2018-05-31 19:55:28'),(90,158,0,NULL,NULL,'2018-05-31 19:54:04','2018-05-31 19:54:04'),(91,159,0,NULL,NULL,'2018-05-31 19:51:40','2018-05-31 19:51:40'),(92,160,0,NULL,NULL,'2018-05-31 19:50:37','2018-05-31 19:50:37'),(93,161,0,NULL,NULL,'2018-05-31 19:48:57','2018-05-31 19:48:57'),(94,162,0,NULL,NULL,'2018-05-31 19:48:07','2018-05-31 19:48:07'),(95,163,0,NULL,NULL,'2018-05-31 19:46:41','2018-05-31 19:46:41'),(96,164,0,NULL,NULL,'2018-05-31 19:43:21','2018-05-31 19:43:21'),(97,165,0,NULL,NULL,'2018-05-31 17:24:20','2018-05-31 17:24:20'),(98,166,0,NULL,NULL,'2018-05-31 17:14:32','2018-05-31 17:14:32'),(99,167,0,NULL,NULL,'2018-05-31 10:57:36','2018-05-31 10:57:36'),(100,168,0,NULL,NULL,'2018-05-31 08:34:35','2018-05-31 08:34:35'),(101,169,0,NULL,NULL,'2018-05-18 00:04:46','2018-05-18 00:04:46'),(102,170,0,NULL,NULL,'2018-05-09 00:22:12','2018-05-09 00:22:12'),(103,171,0,NULL,NULL,'2018-05-03 00:29:26','2018-05-03 00:29:26'),(104,172,0,NULL,NULL,'2018-05-02 23:41:03','2018-05-02 23:41:03'),(105,173,0,NULL,NULL,'2018-05-02 13:01:37','2018-05-02 13:01:37'),(106,174,0,NULL,NULL,'2018-04-27 22:22:30','2018-04-27 22:22:30'),(107,175,0,NULL,NULL,'2018-04-27 22:20:32','2018-04-27 22:20:32'),(108,176,0,NULL,NULL,'2018-04-27 22:17:02','2018-04-27 22:17:02'),(109,177,0,NULL,NULL,'2018-04-27 13:40:57','2018-04-27 13:40:57'),(110,178,0,NULL,NULL,'2018-04-27 10:02:02','2018-04-27 10:02:02'),(111,179,0,NULL,NULL,'2018-04-27 08:39:36','2018-04-27 08:39:36'),(112,180,0,NULL,NULL,'2018-04-27 08:25:34','2018-04-27 08:25:34'),(113,181,0,NULL,NULL,'2018-04-27 00:49:42','2018-04-27 00:49:42'),(114,182,0,NULL,NULL,'2018-04-26 00:16:35','2018-04-26 00:16:35'),(115,183,0,NULL,NULL,'2018-04-23 23:21:55','2018-04-23 23:21:55'),(116,184,0,NULL,NULL,'2018-04-23 23:08:58','2018-04-23 23:08:58'),(117,185,0,NULL,NULL,'2018-04-23 21:08:28','2018-04-23 21:08:28'),(118,186,0,NULL,NULL,'2018-04-23 20:46:57','2018-04-23 20:46:57'),(119,187,0,NULL,NULL,'2018-04-23 19:29:26','2018-04-23 19:29:26'),(120,188,0,NULL,NULL,'2018-04-23 17:41:33','2018-04-23 17:41:33'),(121,189,0,NULL,NULL,'2018-04-23 16:31:01','2018-04-23 16:31:01'),(122,190,0,NULL,NULL,'2018-04-19 13:09:32','2018-04-19 13:09:32'),(123,191,0,NULL,NULL,'2018-04-18 22:16:24','2018-04-18 22:16:24');

/*Table structure for table `t_iot_device_message` */

DROP TABLE IF EXISTS `t_iot_device_message`;

CREATE TABLE `t_iot_device_message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `device_id` int(11) NOT NULL COMMENT '设备id',
  `sync_id` int(11) NOT NULL COMMENT '同步id',
  `dev_type_message_id` varchar(64) NOT NULL COMMENT '设备消息模板id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_uq_sync_id_message_id` (`sync_id`,`dev_type_message_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3749 DEFAULT CHARSET=utf8 COMMENT='iot-设备消息';

/*Data for the table `t_iot_device_message` */

insert  into `t_iot_device_message`(`id`,`device_id`,`sync_id`,`dev_type_message_id`,`create_time`) values (3689,132,2040,'DeviceMessage','2018-06-10 01:17:09'),(3690,133,2041,'DeviceMessage','2018-06-10 01:17:09'),(3691,134,2042,'DeviceMessage','2018-06-10 01:17:09'),(3692,135,2043,'DeviceMessage','2018-06-10 01:17:09'),(3693,136,2044,'DeviceMessage','2018-06-10 01:17:09'),(3694,137,2045,'DeviceMessage','2018-06-10 01:17:10'),(3695,138,2046,'DeviceMessage','2018-06-10 01:17:10'),(3696,139,2047,'DeviceMessage','2018-06-10 01:17:10'),(3697,140,2048,'DeviceMessage','2018-06-10 01:17:10'),(3698,141,2049,'DeviceMessage','2018-06-10 01:17:10'),(3699,142,2050,'DeviceMessage','2018-06-10 01:17:10'),(3700,143,2051,'DeviceMessage','2018-06-10 01:17:10'),(3701,144,2052,'DeviceMessage','2018-06-10 01:17:10'),(3702,145,2053,'DeviceMessage','2018-06-10 01:17:10'),(3703,146,2054,'DeviceMessage','2018-06-10 01:17:10'),(3704,147,2055,'DeviceMessage','2018-06-10 01:17:10'),(3705,148,2056,'DeviceMessage','2018-06-10 01:17:10'),(3706,149,2057,'DeviceMessage','2018-06-10 01:17:10'),(3707,150,2058,'DeviceMessage','2018-06-10 01:17:10'),(3708,151,2059,'DeviceMessage','2018-06-10 01:17:10'),(3709,152,2060,'DeviceMessage','2018-06-10 01:17:10'),(3710,153,2061,'DeviceMessage','2018-06-10 01:17:10'),(3711,154,2062,'DeviceMessage','2018-06-10 01:17:10'),(3712,155,2063,'DeviceMessage','2018-06-10 01:17:10'),(3713,156,2064,'DeviceMessage','2018-06-10 01:17:10'),(3714,157,2065,'DeviceMessage','2018-06-10 01:17:10'),(3715,158,2066,'DeviceMessage','2018-06-10 01:17:10'),(3716,159,2067,'DeviceMessage','2018-06-10 01:17:10'),(3717,160,2068,'DeviceMessage','2018-06-10 01:17:10'),(3718,161,2069,'DeviceMessage','2018-06-10 01:17:10'),(3719,162,2070,'DeviceMessage','2018-06-10 01:17:10'),(3720,163,2071,'DeviceMessage','2018-06-10 01:17:10'),(3721,164,2072,'DeviceMessage','2018-06-10 01:17:10'),(3722,165,2073,'DeviceMessage','2018-06-10 01:17:10'),(3723,166,2074,'DeviceMessage','2018-06-10 01:17:10'),(3724,167,2075,'DeviceMessage','2018-06-10 01:17:10'),(3725,168,2076,'DeviceMessage','2018-06-10 01:17:10'),(3726,169,2077,'DeviceMessage','2018-06-10 01:17:10'),(3727,170,2078,'DeviceMessage','2018-06-10 01:17:10'),(3728,171,2079,'DeviceMessage','2018-06-10 01:17:10'),(3729,172,2080,'DeviceMessage','2018-06-10 01:17:10'),(3730,173,2081,'DeviceMessage','2018-06-10 01:17:10'),(3731,174,2082,'DeviceMessage','2018-06-10 01:17:11'),(3732,175,2083,'DeviceMessage','2018-06-10 01:17:11'),(3733,176,2084,'DeviceMessage','2018-06-10 01:17:11'),(3734,177,2085,'DeviceMessage','2018-06-10 01:17:11'),(3735,178,2086,'DeviceMessage','2018-06-10 01:17:11'),(3736,179,2087,'DeviceMessage','2018-06-10 01:17:11'),(3737,180,2088,'DeviceMessage','2018-06-10 01:17:11'),(3738,181,2089,'DeviceMessage','2018-06-10 01:17:11'),(3739,182,2090,'DeviceMessage','2018-06-10 01:17:11'),(3740,183,2091,'DeviceMessage','2018-06-10 01:17:11'),(3741,184,2092,'DeviceMessage','2018-06-10 01:17:11'),(3742,185,2093,'DeviceMessage','2018-06-10 01:17:11'),(3743,186,2094,'DeviceMessage','2018-06-10 01:17:11'),(3744,187,2095,'DeviceMessage','2018-06-10 01:17:11'),(3745,188,2096,'DeviceMessage','2018-06-10 01:17:11'),(3746,189,2097,'DeviceMessage','2018-06-10 01:17:11'),(3747,190,2098,'DeviceMessage','2018-06-10 01:17:11'),(3748,191,2099,'DeviceMessage','2018-06-10 01:17:11');

/*Table structure for table `t_iot_device_message_detail` */

DROP TABLE IF EXISTS `t_iot_device_message_detail`;

CREATE TABLE `t_iot_device_message_detail` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `message_id` int(11) NOT NULL COMMENT '设备消息',
  `param_name` varchar(64) NOT NULL COMMENT '参数名称',
  `param_value` varchar(512) NOT NULL COMMENT '参数值',
  `data_type` varchar(32) DEFAULT NULL COMMENT '数据类型',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=29613 DEFAULT CHARSET=utf8 COMMENT='iot-消息详情';

/*Data for the table `t_iot_device_message_detail` */

insert  into `t_iot_device_message_detail`(`id`,`message_id`,`param_name`,`param_value`,`data_type`,`create_time`) values (28975,3689,'Period2','3138333032323333','binary','2018-06-10 01:17:09'),(28976,3689,'Period1','3138333032323333','binary','2018-06-10 01:17:09'),(28977,3689,'Voltage','0','short','2018-06-10 01:17:09'),(28978,3689,'GeneralDataBuffer','30303030303042A2303036703030303030303030303030303030303030303030','binary','2018-06-10 01:17:09'),(28979,3689,'GPS','3130322E393739343733343131382E37333231343733','binary','2018-06-10 01:17:09'),(28980,3689,'Current','0','short','2018-06-10 01:17:09'),(28981,3689,'Switch2','off','bool','2018-06-10 01:17:09'),(28982,3689,'Power','0','short','2018-06-10 01:17:09'),(28983,3689,'Switch1','off','bool','2018-06-10 01:17:09'),(28984,3689,'SwitchMode2','off','bool','2018-06-10 01:17:09'),(28985,3689,'SwitchMode1','off','bool','2018-06-10 01:17:09'),(28986,3690,'Period2','3138303032313030','binary','2018-06-10 01:17:09'),(28987,3690,'Period1','3138303032313030','binary','2018-06-10 01:17:09'),(28988,3690,'Voltage','0','short','2018-06-10 01:17:09'),(28989,3690,'GeneralDataBuffer','30303030303033F7303035B03030303030303030303030303030303030303030','binary','2018-06-10 01:17:09'),(28990,3690,'GPS','3130322E393739343733343131382E37333231343733','binary','2018-06-10 01:17:09'),(28991,3690,'Current','0','short','2018-06-10 01:17:09'),(28992,3690,'Switch2','off','bool','2018-06-10 01:17:09'),(28993,3690,'Power','0','short','2018-06-10 01:17:09'),(28994,3690,'Switch1','off','bool','2018-06-10 01:17:09'),(28995,3690,'SwitchMode2','off','bool','2018-06-10 01:17:09'),(28996,3690,'SwitchMode1','off','bool','2018-06-10 01:17:09'),(28997,3691,'Period2','3138333030363030','binary','2018-06-10 01:17:09'),(28998,3691,'Period1','3138333032323330','binary','2018-06-10 01:17:09'),(28999,3691,'Voltage','0','short','2018-06-10 01:17:09'),(29000,3691,'GeneralDataBuffer','3030303030303086303036593030303030303030303030303030303030303030','binary','2018-06-10 01:17:09'),(29001,3691,'GPS','3130322E393739343733343131382E37333231343733','binary','2018-06-10 01:17:09'),(29002,3691,'Current','0','short','2018-06-10 01:17:09'),(29003,3691,'Switch2','off','bool','2018-06-10 01:17:09'),(29004,3691,'Power','0','short','2018-06-10 01:17:09'),(29005,3691,'Switch1','off','bool','2018-06-10 01:17:09'),(29006,3691,'SwitchMode2','off','bool','2018-06-10 01:17:09'),(29007,3691,'SwitchMode1','off','bool','2018-06-10 01:17:09'),(29008,3692,'Period2','3138303032313030','binary','2018-06-10 01:17:09'),(29009,3692,'Period1','3138303032313030','binary','2018-06-10 01:17:09'),(29010,3692,'Voltage','226','short','2018-06-10 01:17:09'),(29011,3692,'GeneralDataBuffer','3030303030306341303036103230313030303030313030303030303032303230','binary','2018-06-10 01:17:09'),(29012,3692,'GPS','3130322E393739343733343131382E37333231343733','binary','2018-06-10 01:17:09'),(29013,3692,'Current','16','short','2018-06-10 01:17:09'),(29014,3692,'Switch2','off','bool','2018-06-10 01:17:09'),(29015,3692,'Power','2','short','2018-06-10 01:17:09'),(29016,3692,'Switch1','off','bool','2018-06-10 01:17:09'),(29017,3692,'SwitchMode2','off','bool','2018-06-10 01:17:09'),(29018,3692,'SwitchMode1','off','bool','2018-06-10 01:17:09'),(29019,3693,'Period2','3138333032323333','binary','2018-06-10 01:17:09'),(29020,3693,'Period1','3138333032323333','binary','2018-06-10 01:17:09'),(29021,3693,'Voltage','220','short','2018-06-10 01:17:09'),(29022,3693,'GeneralDataBuffer','3030303030306033303036123030333031303030303030303030303030303030','binary','2018-06-10 01:17:09'),(29023,3693,'GPS','3130322E393739343733343131382E37333231343733','binary','2018-06-10 01:17:09'),(29024,3693,'Current','24','short','2018-06-10 01:17:09'),(29025,3693,'Switch2','off','bool','2018-06-10 01:17:09'),(29026,3693,'Power','0','short','2018-06-10 01:17:09'),(29027,3693,'Switch1','off','bool','2018-06-10 01:17:10'),(29028,3693,'SwitchMode2','off','bool','2018-06-10 01:17:10'),(29029,3693,'SwitchMode1','off','bool','2018-06-10 01:17:10'),(29030,3694,'Period2','3138303032313030','binary','2018-06-10 01:17:10'),(29031,3694,'Period1','3138303032313030','binary','2018-06-10 01:17:10'),(29032,3694,'Voltage','221','short','2018-06-10 01:17:10'),(29033,3694,'GeneralDataBuffer','3030303030305AD2303036183130303030303030303031303030303032303230','binary','2018-06-10 01:17:10'),(29034,3694,'GPS','3130322E393739343733343131382E37333231343733','binary','2018-06-10 01:17:10'),(29035,3694,'Current','35','short','2018-06-10 01:17:10'),(29036,3694,'Switch2','off','bool','2018-06-10 01:17:10'),(29037,3694,'Power','2','short','2018-06-10 01:17:10'),(29038,3694,'Switch1','off','bool','2018-06-10 01:17:10'),(29039,3694,'SwitchMode2','off','bool','2018-06-10 01:17:10'),(29040,3694,'SwitchMode1','off','bool','2018-06-10 01:17:10'),(29041,3695,'Period2','3138303032313030','binary','2018-06-10 01:17:10'),(29042,3695,'Period1','3138303032313030','binary','2018-06-10 01:17:10'),(29043,3695,'Voltage','0','short','2018-06-10 01:17:10'),(29044,3695,'GeneralDataBuffer','3030303030304D9E303035C13030303030303030303030303030303030303030','binary','2018-06-10 01:17:10'),(29045,3695,'GPS','3130322E393739343733343131382E37333231343733','binary','2018-06-10 01:17:10'),(29046,3695,'Current','0','short','2018-06-10 01:17:10'),(29047,3695,'Switch2','off','bool','2018-06-10 01:17:10'),(29048,3695,'Power','0','short','2018-06-10 01:17:10'),(29049,3695,'Switch1','off','bool','2018-06-10 01:17:10'),(29050,3695,'SwitchMode2','off','bool','2018-06-10 01:17:10'),(29051,3695,'SwitchMode1','off','bool','2018-06-10 01:17:10'),(29052,3696,'Period2','3138303032313030','binary','2018-06-10 01:17:10'),(29053,3696,'Period1','3138303032313030','binary','2018-06-10 01:17:10'),(29054,3696,'Voltage','216','short','2018-06-10 01:17:10'),(29055,3696,'GeneralDataBuffer','30303030303063A1303036203030303031303030303030303030303030303030','binary','2018-06-10 01:17:10'),(29056,3696,'GPS','3130322E393739343733343131382E37333231343733','binary','2018-06-10 01:17:10'),(29057,3696,'Current','23','short','2018-06-10 01:17:10'),(29058,3696,'Switch2','off','bool','2018-06-10 01:17:10'),(29059,3696,'Power','0','short','2018-06-10 01:17:10'),(29060,3696,'Switch1','off','bool','2018-06-10 01:17:10'),(29061,3696,'SwitchMode2','off','bool','2018-06-10 01:17:10'),(29062,3696,'SwitchMode1','off','bool','2018-06-10 01:17:10'),(29063,3697,'Period2','3138333032323333','binary','2018-06-10 01:17:10'),(29064,3697,'Period1','3138333032323333','binary','2018-06-10 01:17:10'),(29065,3697,'Voltage','213','short','2018-06-10 01:17:10'),(29066,3697,'GeneralDataBuffer','3030303030306475303036183030303030303130303030303030303030303030','binary','2018-06-10 01:17:10'),(29067,3697,'GPS','3130322E393739343733343131382E37333231343733','binary','2018-06-10 01:17:10'),(29068,3697,'Current','29','short','2018-06-10 01:17:10'),(29069,3697,'Switch2','off','bool','2018-06-10 01:17:10'),(29070,3697,'Power','0','short','2018-06-10 01:17:10'),(29071,3697,'Switch1','off','bool','2018-06-10 01:17:10'),(29072,3697,'SwitchMode2','off','bool','2018-06-10 01:17:10'),(29073,3697,'SwitchMode1','off','bool','2018-06-10 01:17:10'),(29074,3698,'Period2','3138333032323333','binary','2018-06-10 01:17:10'),(29075,3698,'Period1','3138333032323333','binary','2018-06-10 01:17:10'),(29076,3698,'Voltage','214','short','2018-06-10 01:17:10'),(29077,3698,'GeneralDataBuffer','3030303030305829303036293030303030303030303030303130303030303030','binary','2018-06-10 01:17:10'),(29078,3698,'GPS','3130322E393739343733343131382E37333231343733','binary','2018-06-10 01:17:10'),(29079,3698,'Current','28','short','2018-06-10 01:17:10'),(29080,3698,'Switch2','off','bool','2018-06-10 01:17:10'),(29081,3698,'Power','0','short','2018-06-10 01:17:10'),(29082,3698,'Switch1','off','bool','2018-06-10 01:17:10'),(29083,3698,'SwitchMode2','off','bool','2018-06-10 01:17:10'),(29084,3698,'SwitchMode1','off','bool','2018-06-10 01:17:10'),(29085,3699,'Period2','3138303032313030','binary','2018-06-10 01:17:10'),(29086,3699,'Period1','3138303032313030','binary','2018-06-10 01:17:10'),(29087,3699,'Voltage','215','short','2018-06-10 01:17:10'),(29088,3699,'GeneralDataBuffer','30303030303037CF303037303030303030303030303034303130303030303030','binary','2018-06-10 01:17:10'),(29089,3699,'GPS','3130322E393739343733343131382E37333231343733','binary','2018-06-10 01:17:10'),(29090,3699,'Current','30','short','2018-06-10 01:17:10'),(29091,3699,'Switch2','off','bool','2018-06-10 01:17:10'),(29092,3699,'Power','0','short','2018-06-10 01:17:10'),(29093,3699,'Switch1','off','bool','2018-06-10 01:17:10'),(29094,3699,'SwitchMode2','off','bool','2018-06-10 01:17:10'),(29095,3699,'SwitchMode1','off','bool','2018-06-10 01:17:10'),(29096,3700,'Period2','3138333032323330','binary','2018-06-10 01:17:10'),(29097,3700,'Period1','3138333032323333','binary','2018-06-10 01:17:10'),(29098,3700,'Voltage','213','short','2018-06-10 01:17:10'),(29099,3700,'GeneralDataBuffer','3030303030305B98303036183030303030303030303030303230313030303030','binary','2018-06-10 01:17:10'),(29100,3700,'GPS','3130322E393739343733343131382E37333231343733','binary','2018-06-10 01:17:10'),(29101,3700,'Current','35','short','2018-06-10 01:17:10'),(29102,3700,'Switch2','off','bool','2018-06-10 01:17:10'),(29103,3700,'Power','0','short','2018-06-10 01:17:10'),(29104,3700,'Switch1','off','bool','2018-06-10 01:17:10'),(29105,3700,'SwitchMode2','off','bool','2018-06-10 01:17:10'),(29106,3700,'SwitchMode1','off','bool','2018-06-10 01:17:10'),(29107,3701,'Period2','3138333032323330','binary','2018-06-10 01:17:10'),(29108,3701,'Period1','3138333032323238','binary','2018-06-10 01:17:10'),(29109,3701,'Voltage','0','short','2018-06-10 01:17:10'),(29110,3701,'GeneralDataBuffer','303030303030304E303036B13230313030303030303030303030303031303130','binary','2018-06-10 01:17:10'),(29111,3701,'GPS','3130322E393739343733343131382E37333231343733','binary','2018-06-10 01:17:10'),(29112,3701,'Current','35','short','2018-06-10 01:17:10'),(29113,3701,'Switch2','off','bool','2018-06-10 01:17:10'),(29114,3701,'Power','1','short','2018-06-10 01:17:10'),(29115,3701,'Switch1','off','bool','2018-06-10 01:17:10'),(29116,3701,'SwitchMode2','off','bool','2018-06-10 01:17:10'),(29117,3701,'SwitchMode1','off','bool','2018-06-10 01:17:10'),(29118,3702,'Period2','3138333032323330','binary','2018-06-10 01:17:10'),(29119,3702,'Period1','3139303032323330','binary','2018-06-10 01:17:10'),(29120,3702,'Voltage','220','short','2018-06-10 01:17:10'),(29121,3702,'GeneralDataBuffer','30303030303043BE303036103030303030303130303030303030303037B037B0','binary','2018-06-10 01:17:10'),(29122,3702,'GPS','3130322E393739343733343131382E37333231343733','binary','2018-06-10 01:17:10'),(29123,3702,'Current','26','short','2018-06-10 01:17:10'),(29124,3702,'Switch2','off','bool','2018-06-10 01:17:10'),(29125,3702,'Power','-32761','short','2018-06-10 01:17:10'),(29126,3702,'Switch1','off','bool','2018-06-10 01:17:10'),(29127,3702,'SwitchMode2','off','bool','2018-06-10 01:17:10'),(29128,3702,'SwitchMode1','off','bool','2018-06-10 01:17:10'),(29129,3703,'Period2','3138333030363030','binary','2018-06-10 01:17:10'),(29130,3703,'Period1','3139303032323330','binary','2018-06-10 01:17:10'),(29131,3703,'Voltage','0','short','2018-06-10 01:17:10'),(29132,3703,'GeneralDataBuffer','3030303030303030303036853030303031303130303030303030303030303030','binary','2018-06-10 01:17:10'),(29133,3703,'GPS','3130322E393739343733343131382E37333231343733','binary','2018-06-10 01:17:10'),(29134,3703,'Current','34','short','2018-06-10 01:17:10'),(29135,3703,'Switch2','off','bool','2018-06-10 01:17:10'),(29136,3703,'Power','0','short','2018-06-10 01:17:10'),(29137,3703,'Switch1','off','bool','2018-06-10 01:17:10'),(29138,3703,'SwitchMode2','off','bool','2018-06-10 01:17:10'),(29139,3703,'SwitchMode1','off','bool','2018-06-10 01:17:10'),(29140,3704,'Period2','3138303030363030','binary','2018-06-10 01:17:10'),(29141,3704,'Period1','3139303032323330','binary','2018-06-10 01:17:10'),(29142,3704,'Voltage','0','short','2018-06-10 01:17:10'),(29143,3704,'GeneralDataBuffer','3030303030303030303036563030303030303030303030303030303030303030','binary','2018-06-10 01:17:10'),(29144,3704,'GPS','3130322E393739343733343131382E37333231343733','binary','2018-06-10 01:17:10'),(29145,3704,'Current','28','short','2018-06-10 01:17:10'),(29146,3704,'Switch2','off','bool','2018-06-10 01:17:10'),(29147,3704,'Power','0','short','2018-06-10 01:17:10'),(29148,3704,'Switch1','off','bool','2018-06-10 01:17:10'),(29149,3704,'SwitchMode2','off','bool','2018-06-10 01:17:10'),(29150,3704,'SwitchMode1','off','bool','2018-06-10 01:17:10'),(29151,3705,'Period2','3138303030363030','binary','2018-06-10 01:17:10'),(29152,3705,'Period1','3139303032323330','binary','2018-06-10 01:17:10'),(29153,3705,'Voltage','211','short','2018-06-10 01:17:10'),(29154,3705,'GeneralDataBuffer','30303030303042E0303036023030303030303030323031303030303030303030','binary','2018-06-10 01:17:10'),(29155,3705,'GPS','3130322E393739343733343131382E37333231343733','binary','2018-06-10 01:17:10'),(29156,3705,'Current','31','short','2018-06-10 01:17:10'),(29157,3705,'Switch2','off','bool','2018-06-10 01:17:10'),(29158,3705,'Power','0','short','2018-06-10 01:17:10'),(29159,3705,'Switch1','off','bool','2018-06-10 01:17:10'),(29160,3705,'SwitchMode2','off','bool','2018-06-10 01:17:10'),(29161,3705,'SwitchMode1','off','bool','2018-06-10 01:17:10'),(29162,3706,'Period2','3138303030363030','binary','2018-06-10 01:17:10'),(29163,3706,'Period1','3138333030353330','binary','2018-06-10 01:17:10'),(29164,3706,'Voltage','0','short','2018-06-10 01:17:10'),(29165,3706,'GeneralDataBuffer','3030313130303613303035F53030303030303030303030303030303030303030','binary','2018-06-10 01:17:10'),(29166,3706,'GPS','3130322E393739343733343131382E37333231343733','binary','2018-06-10 01:17:10'),(29167,3706,'Current','0','short','2018-06-10 01:17:10'),(29168,3706,'Switch2','off','bool','2018-06-10 01:17:10'),(29169,3706,'Power','0','short','2018-06-10 01:17:10'),(29170,3706,'Switch1','off','bool','2018-06-10 01:17:10'),(29171,3706,'SwitchMode2','on','bool','2018-06-10 01:17:10'),(29172,3706,'SwitchMode1','on','bool','2018-06-10 01:17:10'),(29173,3707,'Period2','3138333030363030','binary','2018-06-10 01:17:10'),(29174,3707,'Period1','3138333032333030','binary','2018-06-10 01:17:10'),(29175,3707,'Voltage','0','short','2018-06-10 01:17:10'),(29176,3707,'GeneralDataBuffer','3131313130303032303036BD3030303030303330313030303030303030303030','binary','2018-06-10 01:17:10'),(29177,3707,'GPS','3130322E393739343733343131382E37333231343733','binary','2018-06-10 01:17:10'),(29178,3707,'Current','34','short','2018-06-10 01:17:10'),(29179,3707,'Switch2','on','bool','2018-06-10 01:17:10'),(29180,3707,'Power','0','short','2018-06-10 01:17:10'),(29181,3707,'Switch1','on','bool','2018-06-10 01:17:10'),(29182,3707,'SwitchMode2','on','bool','2018-06-10 01:17:10'),(29183,3707,'SwitchMode1','on','bool','2018-06-10 01:17:10'),(29184,3708,'Period2','3138303030363030','binary','2018-06-10 01:17:10'),(29185,3708,'Period1','3138333030353330','binary','2018-06-10 01:17:10'),(29186,3708,'Voltage','0','short','2018-06-10 01:17:10'),(29187,3708,'GeneralDataBuffer','30303131303038B3303036713030303030303030303030303030303030303030','binary','2018-06-10 01:17:10'),(29188,3708,'GPS','3130322E393739343733343131382E37333231343733','binary','2018-06-10 01:17:10'),(29189,3708,'Current','0','short','2018-06-10 01:17:10'),(29190,3708,'Switch2','off','bool','2018-06-10 01:17:10'),(29191,3708,'Power','0','short','2018-06-10 01:17:10'),(29192,3708,'Switch1','off','bool','2018-06-10 01:17:10'),(29193,3708,'SwitchMode2','on','bool','2018-06-10 01:17:10'),(29194,3708,'SwitchMode1','on','bool','2018-06-10 01:17:10'),(29195,3709,'Period2','3138303030363030','binary','2018-06-10 01:17:10'),(29196,3709,'Period1','3139303032323330','binary','2018-06-10 01:17:10'),(29197,3709,'Voltage','0','short','2018-06-10 01:17:10'),(29198,3709,'GeneralDataBuffer','30303030303030303030367F3030303030303030303030303030303030303030','binary','2018-06-10 01:17:10'),(29199,3709,'GPS','3130322E393739343733343131382E37333231343733','binary','2018-06-10 01:17:10'),(29200,3709,'Current','31','short','2018-06-10 01:17:10'),(29201,3709,'Switch2','off','bool','2018-06-10 01:17:10'),(29202,3709,'Power','0','short','2018-06-10 01:17:10'),(29203,3709,'Switch1','off','bool','2018-06-10 01:17:10'),(29204,3709,'SwitchMode2','off','bool','2018-06-10 01:17:10'),(29205,3709,'SwitchMode1','off','bool','2018-06-10 01:17:10'),(29206,3710,'Period2','3138303030363030','binary','2018-06-10 01:17:10'),(29207,3710,'Period1','3138333030353330','binary','2018-06-10 01:17:10'),(29208,3710,'Voltage','0','short','2018-06-10 01:17:10'),(29209,3710,'GeneralDataBuffer','3030313130303DDD303034273030303030303030303030303030303030303030','binary','2018-06-10 01:17:10'),(29210,3710,'GPS','3130322E393739343733343131382E37333231343733','binary','2018-06-10 01:17:10'),(29211,3710,'Current','0','short','2018-06-10 01:17:10'),(29212,3710,'Switch2','off','bool','2018-06-10 01:17:10'),(29213,3710,'Power','0','short','2018-06-10 01:17:10'),(29214,3710,'Switch1','off','bool','2018-06-10 01:17:10'),(29215,3710,'SwitchMode2','on','bool','2018-06-10 01:17:10'),(29216,3710,'SwitchMode1','on','bool','2018-06-10 01:17:10'),(29217,3711,'Period2','3138333032323333','binary','2018-06-10 01:17:10'),(29218,3711,'Period1','3138333032323333','binary','2018-06-10 01:17:10'),(29219,3711,'Voltage','0','short','2018-06-10 01:17:10'),(29220,3711,'GeneralDataBuffer','3030303030303A593030354B3030303030303030303030303030303030303030','binary','2018-06-10 01:17:10'),(29221,3711,'GPS','3130322E393739343733343131382E37333231343733','binary','2018-06-10 01:17:10'),(29222,3711,'Current','0','short','2018-06-10 01:17:10'),(29223,3711,'Switch2','off','bool','2018-06-10 01:17:10'),(29224,3711,'Power','0','short','2018-06-10 01:17:10'),(29225,3711,'Switch1','off','bool','2018-06-10 01:17:10'),(29226,3711,'SwitchMode2','off','bool','2018-06-10 01:17:10'),(29227,3711,'SwitchMode1','off','bool','2018-06-10 01:17:10'),(29228,3712,'Period2','3138303030363030','binary','2018-06-10 01:17:10'),(29229,3712,'Period1','3139303032323330','binary','2018-06-10 01:17:10'),(29230,3712,'Voltage','2','short','2018-06-10 01:17:10'),(29231,3712,'GeneralDataBuffer','3030303030303030303036843030303030303230313030303030303030303030','binary','2018-06-10 01:17:10'),(29232,3712,'GPS','3130322E393739343733343131382E37333231343733','binary','2018-06-10 01:17:10'),(29233,3712,'Current','27','short','2018-06-10 01:17:10'),(29234,3712,'Switch2','off','bool','2018-06-10 01:17:10'),(29235,3712,'Power','0','short','2018-06-10 01:17:10'),(29236,3712,'Switch1','off','bool','2018-06-10 01:17:10'),(29237,3712,'SwitchMode2','off','bool','2018-06-10 01:17:10'),(29238,3712,'SwitchMode1','off','bool','2018-06-10 01:17:10'),(29239,3713,'Period2','3138303030363030','binary','2018-06-10 01:17:10'),(29240,3713,'Period1','3138333030353330','binary','2018-06-10 01:17:10'),(29241,3713,'Voltage','0','short','2018-06-10 01:17:10'),(29242,3713,'GeneralDataBuffer','30303131303037813030342C3030303030303030303030303030303030303030','binary','2018-06-10 01:17:10'),(29243,3713,'GPS','3130322E393739343733343131382E37333231343733','binary','2018-06-10 01:17:10'),(29244,3713,'Current','0','short','2018-06-10 01:17:10'),(29245,3713,'Switch2','off','bool','2018-06-10 01:17:10'),(29246,3713,'Power','0','short','2018-06-10 01:17:10'),(29247,3713,'Switch1','off','bool','2018-06-10 01:17:10'),(29248,3713,'SwitchMode2','on','bool','2018-06-10 01:17:10'),(29249,3713,'SwitchMode1','on','bool','2018-06-10 01:17:10'),(29250,3714,'Period2','3138303030363030','binary','2018-06-10 01:17:10'),(29251,3714,'Period1','3139303032323330','binary','2018-06-10 01:17:10'),(29252,3714,'Voltage','2','short','2018-06-10 01:17:10'),(29253,3714,'GeneralDataBuffer','3030303030303030303030303030303030303030303030303030303030303030','binary','2018-06-10 01:17:10'),(29254,3714,'GPS','3130322E393739343733343131382E37333231343733','binary','2018-06-10 01:17:10'),(29255,3714,'Current','34','short','2018-06-10 01:17:10'),(29256,3714,'Switch2','off','bool','2018-06-10 01:17:10'),(29257,3714,'Power','0','short','2018-06-10 01:17:10'),(29258,3714,'Switch1','off','bool','2018-06-10 01:17:10'),(29259,3714,'SwitchMode2','off','bool','2018-06-10 01:17:10'),(29260,3714,'SwitchMode1','off','bool','2018-06-10 01:17:10'),(29261,3715,'Period2','3138303030363030','binary','2018-06-10 01:17:10'),(29262,3715,'Period1','3139303032323330','binary','2018-06-10 01:17:10'),(29263,3715,'Voltage','0','short','2018-06-10 01:17:10'),(29264,3715,'GeneralDataBuffer','3030303030303030303036363030303030303230313030303030303030303030','binary','2018-06-10 01:17:10'),(29265,3715,'GPS','3130322E393739343733343131382E37333231343733','binary','2018-06-10 01:17:10'),(29266,3715,'Current','23','short','2018-06-10 01:17:10'),(29267,3715,'Switch2','off','bool','2018-06-10 01:17:10'),(29268,3715,'Power','0','short','2018-06-10 01:17:10'),(29269,3715,'Switch1','off','bool','2018-06-10 01:17:10'),(29270,3715,'SwitchMode2','off','bool','2018-06-10 01:17:10'),(29271,3715,'SwitchMode1','off','bool','2018-06-10 01:17:10'),(29272,3716,'Period2','3138333030363030','binary','2018-06-10 01:17:10'),(29273,3716,'Period1','3139303032323330','binary','2018-06-10 01:17:10'),(29274,3716,'Voltage','4','short','2018-06-10 01:17:10'),(29275,3716,'GeneralDataBuffer','3030303030303031303036E03030303033303130303032303130313030303030','binary','2018-06-10 01:17:10'),(29276,3716,'GPS','3130322E393739343733343131382E37333231343733','binary','2018-06-10 01:17:10'),(29277,3716,'Current','34','short','2018-06-10 01:17:10'),(29278,3716,'Switch2','off','bool','2018-06-10 01:17:10'),(29279,3716,'Power','0','short','2018-06-10 01:17:10'),(29280,3716,'Switch1','off','bool','2018-06-10 01:17:10'),(29281,3716,'SwitchMode2','off','bool','2018-06-10 01:17:10'),(29282,3716,'SwitchMode1','off','bool','2018-06-10 01:17:10'),(29283,3717,'Period2','3138303030363030','binary','2018-06-10 01:17:10'),(29284,3717,'Period1','3138333030353330','binary','2018-06-10 01:17:10'),(29285,3717,'Voltage','0','short','2018-06-10 01:17:10'),(29286,3717,'GeneralDataBuffer','303031313030312E303035EB3030303030303030303030303030303030303030','binary','2018-06-10 01:17:10'),(29287,3717,'GPS','3130322E393739343733343131382E37333231343733','binary','2018-06-10 01:17:10'),(29288,3717,'Current','0','short','2018-06-10 01:17:10'),(29289,3717,'Switch2','off','bool','2018-06-10 01:17:10'),(29290,3717,'Power','0','short','2018-06-10 01:17:10'),(29291,3717,'Switch1','off','bool','2018-06-10 01:17:10'),(29292,3717,'SwitchMode2','on','bool','2018-06-10 01:17:10'),(29293,3717,'SwitchMode1','on','bool','2018-06-10 01:17:10'),(29294,3718,'Period2','3138303030363030','binary','2018-06-10 01:17:10'),(29295,3718,'Period1','3138333030353330','binary','2018-06-10 01:17:10'),(29296,3718,'Voltage','0','short','2018-06-10 01:17:10'),(29297,3718,'GeneralDataBuffer','3030303130303030303036603030303030303030303030303030343031303130','binary','2018-06-10 01:17:10'),(29298,3718,'GPS','3130322E393739343733343131382E37333231343733','binary','2018-06-10 01:17:10'),(29299,3718,'Current','35','short','2018-06-10 01:17:10'),(29300,3718,'Switch2','off','bool','2018-06-10 01:17:10'),(29301,3718,'Power','1','short','2018-06-10 01:17:10'),(29302,3718,'Switch1','off','bool','2018-06-10 01:17:10'),(29303,3718,'SwitchMode2','on','bool','2018-06-10 01:17:10'),(29304,3718,'SwitchMode1','off','bool','2018-06-10 01:17:10'),(29305,3719,'Period2','3138303030363030','binary','2018-06-10 01:17:10'),(29306,3719,'Period1','3139303032323330','binary','2018-06-10 01:17:10'),(29307,3719,'Voltage','0','short','2018-06-10 01:17:10'),(29308,3719,'GeneralDataBuffer','30303030303030543030365D3030303030303030303030303030313030303030','binary','2018-06-10 01:17:10'),(29309,3719,'GPS','3332312E3937393437333438392E3733323134373330','binary','2018-06-10 01:17:10'),(29310,3719,'Current','16','short','2018-06-10 01:17:10'),(29311,3719,'Switch2','off','bool','2018-06-10 01:17:10'),(29312,3719,'Power','0','short','2018-06-10 01:17:10'),(29313,3719,'Switch1','off','bool','2018-06-10 01:17:10'),(29314,3719,'SwitchMode2','off','bool','2018-06-10 01:17:10'),(29315,3719,'SwitchMode1','off','bool','2018-06-10 01:17:10'),(29316,3721,'Period2','3138303030363030','binary','2018-06-10 01:17:10'),(29317,3721,'Period1','3138333030353330','binary','2018-06-10 01:17:10'),(29318,3721,'Voltage','0','short','2018-06-10 01:17:10'),(29319,3721,'GeneralDataBuffer','30303131303039D0303035143030303030303030303030303030303030303030','binary','2018-06-10 01:17:10'),(29320,3721,'GPS','3130322E393739343733343131382E37333231343733','binary','2018-06-10 01:17:10'),(29321,3721,'Current','0','short','2018-06-10 01:17:10'),(29322,3721,'Switch2','off','bool','2018-06-10 01:17:10'),(29323,3721,'Power','0','short','2018-06-10 01:17:10'),(29324,3721,'Switch1','off','bool','2018-06-10 01:17:10'),(29325,3721,'SwitchMode2','on','bool','2018-06-10 01:17:10'),(29326,3721,'SwitchMode1','on','bool','2018-06-10 01:17:10'),(29327,3722,'Period2','3138303030363030','binary','2018-06-10 01:17:10'),(29328,3722,'Period1','3139303032323330','binary','2018-06-10 01:17:10'),(29329,3722,'Voltage','220','short','2018-06-10 01:17:10'),(29330,3722,'GeneralDataBuffer','30303030303043F93030362030303330313031303030303030303030C0B7C0B7','binary','2018-06-10 01:17:10'),(29331,3722,'GPS','3130322E393739343733343131382E37333231343733','binary','2018-06-10 01:17:10'),(29332,3722,'Current','34','short','2018-06-10 01:17:10'),(29333,3722,'Switch2','off','bool','2018-06-10 01:17:10'),(29334,3722,'Power','-30832','short','2018-06-10 01:17:10'),(29335,3722,'Switch1','off','bool','2018-06-10 01:17:10'),(29336,3722,'SwitchMode2','off','bool','2018-06-10 01:17:10'),(29337,3722,'SwitchMode1','off','bool','2018-06-10 01:17:10'),(29338,3723,'Period2','3138303030363030','binary','2018-06-10 01:17:10'),(29339,3723,'Period1','3139303032323330','binary','2018-06-10 01:17:10'),(29340,3723,'Voltage','0','short','2018-06-10 01:17:10'),(29341,3723,'GeneralDataBuffer','3030303030303030303036843030303030303030303032303130313030303030','binary','2018-06-10 01:17:10'),(29342,3723,'GPS','3130322E393739343733343131382E37333231343733','binary','2018-06-10 01:17:10'),(29343,3723,'Current','29','short','2018-06-10 01:17:10'),(29344,3723,'Switch2','off','bool','2018-06-10 01:17:10'),(29345,3723,'Power','0','short','2018-06-10 01:17:10'),(29346,3723,'Switch1','off','bool','2018-06-10 01:17:10'),(29347,3723,'SwitchMode2','off','bool','2018-06-10 01:17:10'),(29348,3723,'SwitchMode1','off','bool','2018-06-10 01:17:10'),(29349,3724,'Period2','3138333032323330','binary','2018-06-10 01:17:10'),(29350,3724,'Period1','3139303032323330','binary','2018-06-10 01:17:10'),(29351,3724,'Voltage','0','short','2018-06-10 01:17:10'),(29352,3724,'GeneralDataBuffer','30303030303030303030368D3030303030303030303030303130303030303030','binary','2018-06-10 01:17:10'),(29353,3724,'GPS','3130322E393739343733343131382E37333231343733','binary','2018-06-10 01:17:10'),(29354,3724,'Current','27','short','2018-06-10 01:17:10'),(29355,3724,'Switch2','off','bool','2018-06-10 01:17:10'),(29356,3724,'Power','0','short','2018-06-10 01:17:10'),(29357,3724,'Switch1','off','bool','2018-06-10 01:17:10'),(29358,3724,'SwitchMode2','off','bool','2018-06-10 01:17:10'),(29359,3724,'SwitchMode1','off','bool','2018-06-10 01:17:10'),(29360,3725,'Period2','3138303030363030','binary','2018-06-10 01:17:10'),(29361,3725,'Period1','3139303032323330','binary','2018-06-10 01:17:10'),(29362,3725,'Voltage','0','short','2018-06-10 01:17:10'),(29363,3725,'GeneralDataBuffer','3030303030303031303035F93030303030303130313030303030303030303030','binary','2018-06-10 01:17:10'),(29364,3725,'GPS','3130322E393739343733343131382E37333231343733','binary','2018-06-10 01:17:10'),(29365,3725,'Current','28','short','2018-06-10 01:17:10'),(29366,3725,'Switch2','off','bool','2018-06-10 01:17:10'),(29367,3725,'Power','0','short','2018-06-10 01:17:10'),(29368,3725,'Switch1','off','bool','2018-06-10 01:17:10'),(29369,3725,'SwitchMode2','off','bool','2018-06-10 01:17:10'),(29370,3725,'SwitchMode1','off','bool','2018-06-10 01:17:10'),(29371,3726,'Period2','3138303030363030','binary','2018-06-10 01:17:10'),(29372,3726,'Period1','3138333030363330','binary','2018-06-10 01:17:10'),(29373,3726,'Voltage','0','short','2018-06-10 01:17:10'),(29374,3726,'GeneralDataBuffer','303031313030429E303035E93030303030303030303030303030303030303030','binary','2018-06-10 01:17:10'),(29375,3726,'GPS','3130322E393739343733343131382E37333231343733','binary','2018-06-10 01:17:10'),(29376,3726,'Current','0','short','2018-06-10 01:17:10'),(29377,3726,'Switch2','off','bool','2018-06-10 01:17:10'),(29378,3726,'Power','0','short','2018-06-10 01:17:10'),(29379,3726,'Switch1','off','bool','2018-06-10 01:17:10'),(29380,3726,'SwitchMode2','on','bool','2018-06-10 01:17:10'),(29381,3726,'SwitchMode1','on','bool','2018-06-10 01:17:10'),(29382,3727,'Period2','3138303030363030','binary','2018-06-10 01:17:10'),(29383,3727,'Period1','3138303030363030','binary','2018-06-10 01:17:10'),(29384,3727,'Voltage','215','short','2018-06-10 01:17:10'),(29385,3727,'GeneralDataBuffer','31313131313830303036303031383030303630303130322E393739343733343131382E37333231343733383633373033303336353930343333343630313131313736373731333933120609','binary','2018-06-10 01:17:10'),(29386,3727,'GPS','3130322E393739343733343131382E37333231343733','binary','2018-06-10 01:17:10'),(29387,3727,'Current','1140','short','2018-06-10 01:17:10'),(29388,3727,'Switch2','on','bool','2018-06-10 01:17:10'),(29389,3727,'Power','243','short','2018-06-10 01:17:10'),(29390,3727,'Switch1','on','bool','2018-06-10 01:17:10'),(29391,3727,'SwitchMode2','on','bool','2018-06-10 01:17:10'),(29392,3727,'SwitchMode1','on','bool','2018-06-10 01:17:10'),(29393,3728,'Period2','3138303030363030','binary','2018-06-10 01:17:10'),(29394,3728,'Period1','3138333030363330','binary','2018-06-10 01:17:10'),(29395,3728,'Voltage','0','short','2018-06-10 01:17:10'),(29396,3728,'GeneralDataBuffer','303031313030464C303036823030303030303030303030303030303030303030','binary','2018-06-10 01:17:10'),(29397,3728,'GPS','3130322E393739343733343131382E37333231343733','binary','2018-06-10 01:17:10'),(29398,3728,'Current','0','short','2018-06-10 01:17:10'),(29399,3728,'Switch2','off','bool','2018-06-10 01:17:10'),(29400,3728,'Power','0','short','2018-06-10 01:17:10'),(29401,3728,'Switch1','off','bool','2018-06-10 01:17:10'),(29402,3728,'SwitchMode2','on','bool','2018-06-10 01:17:10'),(29403,3728,'SwitchMode1','on','bool','2018-06-10 01:17:10'),(29404,3729,'Period2','3138333030363030','binary','2018-06-10 01:17:10'),(29405,3729,'Period1','3138333032323330','binary','2018-06-10 01:17:10'),(29406,3729,'Voltage','0','short','2018-06-10 01:17:10'),(29407,3729,'GeneralDataBuffer','3030303030303036303036F03030303030303030303030303030303030303030','binary','2018-06-10 01:17:10'),(29408,3729,'GPS','3130322E393739343733343131382E37333231343733','binary','2018-06-10 01:17:10'),(29409,3729,'Current','0','short','2018-06-10 01:17:10'),(29410,3729,'Switch2','off','bool','2018-06-10 01:17:10'),(29411,3729,'Power','0','short','2018-06-10 01:17:10'),(29412,3729,'Switch1','off','bool','2018-06-10 01:17:10'),(29413,3729,'SwitchMode2','off','bool','2018-06-10 01:17:10'),(29414,3729,'SwitchMode1','off','bool','2018-06-10 01:17:10'),(29415,3730,'Period2','3138303030363030','binary','2018-06-10 01:17:10'),(29416,3730,'Period1','3138333030363330','binary','2018-06-10 01:17:10'),(29417,3730,'Voltage','0','short','2018-06-10 01:17:10'),(29418,3730,'GeneralDataBuffer','303031313030450E3030368A3030303030303030303030303030303030303030','binary','2018-06-10 01:17:10'),(29419,3730,'GPS','3130322E393739343733343131382E37333231343733','binary','2018-06-10 01:17:10'),(29420,3730,'Current','0','short','2018-06-10 01:17:10'),(29421,3730,'Switch2','off','bool','2018-06-10 01:17:10'),(29422,3730,'Power','0','short','2018-06-10 01:17:10'),(29423,3730,'Switch1','off','bool','2018-06-10 01:17:10'),(29424,3730,'SwitchMode2','on','bool','2018-06-10 01:17:10'),(29425,3730,'SwitchMode1','on','bool','2018-06-10 01:17:10'),(29426,3731,'Period2','3138303030363030','binary','2018-06-10 01:17:11'),(29427,3731,'Period1','3138333032323330','binary','2018-06-10 01:17:11'),(29428,3731,'Voltage','0','short','2018-06-10 01:17:11'),(29429,3731,'GeneralDataBuffer','30303030303045783030369B3030303030303030303030303030303030303030','binary','2018-06-10 01:17:11'),(29430,3731,'GPS','3130322E393739343733343131382E37333231343733','binary','2018-06-10 01:17:11'),(29431,3731,'Current','0','short','2018-06-10 01:17:11'),(29432,3731,'Switch2','off','bool','2018-06-10 01:17:11'),(29433,3731,'Power','0','short','2018-06-10 01:17:11'),(29434,3731,'Switch1','off','bool','2018-06-10 01:17:11'),(29435,3731,'SwitchMode2','off','bool','2018-06-10 01:17:11'),(29436,3731,'SwitchMode1','off','bool','2018-06-10 01:17:11'),(29437,3732,'Period2','3138303030363030','binary','2018-06-10 01:17:11'),(29438,3732,'Period1','3138303030363030','binary','2018-06-10 01:17:11'),(29439,3732,'Voltage','0','short','2018-06-10 01:17:11'),(29440,3732,'GeneralDataBuffer','31313131313830303036303031383030303630303130322E393739343733343131382E373332313437333836333730333033363534393033333436303131313137363737313339361205060921','binary','2018-06-10 01:17:11'),(29441,3732,'GPS','3130322E393739343733343131382E37333231343733','binary','2018-06-10 01:17:11'),(29442,3732,'Current','0','short','2018-06-10 01:17:11'),(29443,3732,'Switch2','on','bool','2018-06-10 01:17:11'),(29444,3732,'Power','0','short','2018-06-10 01:17:11'),(29445,3732,'Switch1','on','bool','2018-06-10 01:17:11'),(29446,3732,'SwitchMode2','on','bool','2018-06-10 01:17:11'),(29447,3732,'SwitchMode1','on','bool','2018-06-10 01:17:11'),(29448,3733,'Period2','3138303030363030','binary','2018-06-10 01:17:11'),(29449,3733,'Period1','3138333030363330','binary','2018-06-10 01:17:11'),(29450,3733,'Voltage','0','short','2018-06-10 01:17:11'),(29451,3733,'GeneralDataBuffer','3030313130303033303036FC3030303030303030303030303030303030303030','binary','2018-06-10 01:17:11'),(29452,3733,'GPS','3130322E393739343733343131382E37333231343733','binary','2018-06-10 01:17:11'),(29453,3733,'Current','0','short','2018-06-10 01:17:11'),(29454,3733,'Switch2','off','bool','2018-06-10 01:17:11'),(29455,3733,'Power','0','short','2018-06-10 01:17:11'),(29456,3733,'Switch1','off','bool','2018-06-10 01:17:11'),(29457,3733,'SwitchMode2','on','bool','2018-06-10 01:17:11'),(29458,3733,'SwitchMode1','on','bool','2018-06-10 01:17:11'),(29459,3734,'Period2','3138333032323333','binary','2018-06-10 01:17:11'),(29460,3734,'Period1','3136333030353330','binary','2018-06-10 01:17:11'),(29461,3734,'Voltage','235','short','2018-06-10 01:17:11'),(29462,3734,'GeneralDataBuffer','3030303030304D493030360430303130303030303030303030303030A381A381','binary','2018-06-10 01:17:11'),(29463,3734,'GPS','3130322E393739343733343131382E37333231343733','binary','2018-06-10 01:17:11'),(29464,3734,'Current','29','short','2018-06-10 01:17:11'),(29465,3734,'Switch2','off','bool','2018-06-10 01:17:11'),(29466,3734,'Power','20851','short','2018-06-10 01:17:11'),(29467,3734,'Switch1','off','bool','2018-06-10 01:17:11'),(29468,3734,'SwitchMode2','off','bool','2018-06-10 01:17:11'),(29469,3734,'SwitchMode1','off','bool','2018-06-10 01:17:11'),(29470,3735,'Period2','3138303030363030','binary','2018-06-10 01:17:11'),(29471,3735,'Period1','3139303032323330','binary','2018-06-10 01:17:11'),(29472,3735,'Voltage','0','short','2018-06-10 01:17:11'),(29473,3735,'GeneralDataBuffer','3030303030303E8F303036703030303030303030303030303030303030303030','binary','2018-06-10 01:17:11'),(29474,3735,'GPS','3130322E393739343733343131382E37333231343733','binary','2018-06-10 01:17:11'),(29475,3735,'Current','0','short','2018-06-10 01:17:11'),(29476,3735,'Switch2','off','bool','2018-06-10 01:17:11'),(29477,3735,'Power','0','short','2018-06-10 01:17:11'),(29478,3735,'Switch1','off','bool','2018-06-10 01:17:11'),(29479,3735,'SwitchMode2','off','bool','2018-06-10 01:17:11'),(29480,3735,'SwitchMode1','off','bool','2018-06-10 01:17:11'),(29481,3736,'Period2','3138303030363030','binary','2018-06-10 01:17:11'),(29482,3736,'Period1','3138333030363330','binary','2018-06-10 01:17:11'),(29483,3736,'Voltage','0','short','2018-06-10 01:17:11'),(29484,3736,'GeneralDataBuffer','30303131303047D1303036903030303030303030303030303030303030303030','binary','2018-06-10 01:17:11'),(29485,3736,'GPS','3130322E393739343733343131382E37333231343733','binary','2018-06-10 01:17:11'),(29486,3736,'Current','0','short','2018-06-10 01:17:11'),(29487,3736,'Switch2','off','bool','2018-06-10 01:17:11'),(29488,3736,'Power','0','short','2018-06-10 01:17:11'),(29489,3736,'Switch1','off','bool','2018-06-10 01:17:11'),(29490,3736,'SwitchMode2','on','bool','2018-06-10 01:17:11'),(29491,3736,'SwitchMode1','on','bool','2018-06-10 01:17:11'),(29492,3737,'Period2','3138303030363030','binary','2018-06-10 01:17:11'),(29493,3737,'Period1','3138303030363030','binary','2018-06-10 01:17:11'),(29494,3737,'Voltage','210','short','2018-06-10 01:17:11'),(29495,3737,'GeneralDataBuffer','31313131313830303036303031383030303630303130322E393739343733343131382E37333231343733383633373033303337353331313133343630313131313736373639353837120608','binary','2018-06-10 01:17:11'),(29496,3737,'GPS','3130322E393739343733343131382E37333231343733','binary','2018-06-10 01:17:11'),(29497,3737,'Current','1240','short','2018-06-10 01:17:11'),(29498,3737,'Switch2','on','bool','2018-06-10 01:17:11'),(29499,3737,'Power','256','short','2018-06-10 01:17:11'),(29500,3737,'Switch1','on','bool','2018-06-10 01:17:11'),(29501,3737,'SwitchMode2','on','bool','2018-06-10 01:17:11'),(29502,3737,'SwitchMode1','on','bool','2018-06-10 01:17:11'),(29503,3738,'Period2','3138303030363030','binary','2018-06-10 01:17:11'),(29504,3738,'Period1','3138333030363030','binary','2018-06-10 01:17:11'),(29505,3738,'Voltage','210','short','2018-06-10 01:17:11'),(29506,3738,'GeneralDataBuffer','30303131313833303036303031383030303630303130322E393739343733343131382E37333231343733383633373033303336353537393836343630313131313736373639353931120609','binary','2018-06-10 01:17:11'),(29507,3738,'GPS','3130322E393739343733343131382E37333231343733','binary','2018-06-10 01:17:11'),(29508,3738,'Current','1069','short','2018-06-10 01:17:11'),(29509,3738,'Switch2','off','bool','2018-06-10 01:17:11'),(29510,3738,'Power','224','short','2018-06-10 01:17:11'),(29511,3738,'Switch1','on','bool','2018-06-10 01:17:11'),(29512,3738,'SwitchMode2','on','bool','2018-06-10 01:17:11'),(29513,3738,'SwitchMode1','on','bool','2018-06-10 01:17:11'),(29514,3740,'Period2','3138303030363030','binary','2018-06-10 01:17:11'),(29515,3740,'Period1','3138333030363030','binary','2018-06-10 01:17:11'),(29516,3740,'Voltage','221','short','2018-06-10 01:17:11'),(29517,3740,'GeneralDataBuffer','31313131313833303036303031383030303630303130322E393739343733343131382E3733323134373338363337303330333635393033373534363031313131373637373236343412041E150F','binary','2018-06-10 01:17:11'),(29518,3740,'GPS','3130322E393739343733343131382E37333231343733','binary','2018-06-10 01:17:11'),(29519,3740,'Current','1085','short','2018-06-10 01:17:11'),(29520,3740,'Switch2','on','bool','2018-06-10 01:17:11'),(29521,3740,'Power','239','short','2018-06-10 01:17:11'),(29522,3740,'Switch1','on','bool','2018-06-10 01:17:11'),(29523,3740,'SwitchMode2','on','bool','2018-06-10 01:17:11'),(29524,3740,'SwitchMode1','on','bool','2018-06-10 01:17:11'),(29525,3741,'Period2','3138303030363030','binary','2018-06-10 01:17:11'),(29526,3741,'Period1','3138333030363330','binary','2018-06-10 01:17:11'),(29527,3741,'Voltage','215','short','2018-06-10 01:17:11'),(29528,3741,'GeneralDataBuffer','30303131313833303036333031383030303630303130322E393739343733343131382E37333231343733383633373033303337353530343434343630313131313736373639353930120507','binary','2018-06-10 01:17:11'),(29529,3741,'GPS','3130322E393739343733343131382E37333231343733','binary','2018-06-10 01:17:11'),(29530,3741,'Current','1160','short','2018-06-10 01:17:11'),(29531,3741,'Switch2','off','bool','2018-06-10 01:17:11'),(29532,3741,'Power','246','short','2018-06-10 01:17:11'),(29533,3741,'Switch1','on','bool','2018-06-10 01:17:11'),(29534,3741,'SwitchMode2','on','bool','2018-06-10 01:17:11'),(29535,3741,'SwitchMode1','on','bool','2018-06-10 01:17:11'),(29536,3742,'Period2','3138303030363030','binary','2018-06-10 01:17:11'),(29537,3742,'Period1','3138333030363330','binary','2018-06-10 01:17:11'),(29538,3742,'Voltage','0','short','2018-06-10 01:17:11'),(29539,3742,'GeneralDataBuffer','3030313130303033303034243030303030303030303030303030303030303030','binary','2018-06-10 01:17:11'),(29540,3742,'GPS','3130322E393739343733343131382E37333231343733','binary','2018-06-10 01:17:11'),(29541,3742,'Current','0','short','2018-06-10 01:17:11'),(29542,3742,'Switch2','off','bool','2018-06-10 01:17:11'),(29543,3742,'Power','0','short','2018-06-10 01:17:11'),(29544,3742,'Switch1','off','bool','2018-06-10 01:17:11'),(29545,3742,'SwitchMode2','on','bool','2018-06-10 01:17:11'),(29546,3742,'SwitchMode1','on','bool','2018-06-10 01:17:11'),(29547,3743,'Period2','3138303030363030','binary','2018-06-10 01:17:11'),(29548,3743,'Period1','3138333030363330','binary','2018-06-10 01:17:11'),(29549,3743,'Voltage','210','short','2018-06-10 01:17:11'),(29550,3743,'GeneralDataBuffer','30303131313833303036333031383030303630303130322E393739343733343131382E37333231343733383633373033303336353439323331343630313131313736373639353839120609','binary','2018-06-10 01:17:11'),(29551,3743,'GPS','3130322E393739343733343131382E37333231343733','binary','2018-06-10 01:17:11'),(29552,3743,'Current','1189','short','2018-06-10 01:17:11'),(29553,3743,'Switch2','off','bool','2018-06-10 01:17:11'),(29554,3743,'Power','248','short','2018-06-10 01:17:11'),(29555,3743,'Switch1','on','bool','2018-06-10 01:17:11'),(29556,3743,'SwitchMode2','on','bool','2018-06-10 01:17:11'),(29557,3743,'SwitchMode1','on','bool','2018-06-10 01:17:11'),(29558,3744,'Period2','3138303030363030','binary','2018-06-10 01:17:11'),(29559,3744,'Period1','3138333030363030','binary','2018-06-10 01:17:11'),(29560,3744,'Voltage','211','short','2018-06-10 01:17:11'),(29561,3744,'GeneralDataBuffer','30303131313833303036303031383030303630303130322E393739343733343131382E37333231343733383633373033303336353334383239343630313131313736373639343839120609','binary','2018-06-10 01:17:11'),(29562,3744,'GPS','3130322E393739343733343131382E37333231343733','binary','2018-06-10 01:17:11'),(29563,3744,'Current','1216','short','2018-06-10 01:17:11'),(29564,3744,'Switch2','off','bool','2018-06-10 01:17:11'),(29565,3744,'Power','254','short','2018-06-10 01:17:11'),(29566,3744,'Switch1','on','bool','2018-06-10 01:17:11'),(29567,3744,'SwitchMode2','on','bool','2018-06-10 01:17:11'),(29568,3744,'SwitchMode1','on','bool','2018-06-10 01:17:11'),(29569,3745,'Period2','3138303030363030','binary','2018-06-10 01:17:11'),(29570,3745,'Period1','3138303030363030','binary','2018-06-10 01:17:11'),(29571,3745,'Voltage','212','short','2018-06-10 01:17:11'),(29572,3745,'GeneralDataBuffer','31313131313830303036303031383030303630303130322E393739343733343131382E37333231343733383633373033303336353537323931343630313131313736373639353932120602','binary','2018-06-10 01:17:11'),(29573,3745,'GPS','3130322E393739343733343131382E37333231343733','binary','2018-06-10 01:17:11'),(29574,3745,'Current','1155','short','2018-06-10 01:17:11'),(29575,3745,'Switch2','on','bool','2018-06-10 01:17:11'),(29576,3745,'Power','241','short','2018-06-10 01:17:11'),(29577,3745,'Switch1','on','bool','2018-06-10 01:17:11'),(29578,3745,'SwitchMode2','on','bool','2018-06-10 01:17:11'),(29579,3745,'SwitchMode1','on','bool','2018-06-10 01:17:11'),(29580,3746,'Period2','3138303030363030','binary','2018-06-10 01:17:11'),(29581,3746,'Period1','3138333030363330','binary','2018-06-10 01:17:11'),(29582,3746,'Voltage','0','short','2018-06-10 01:17:11'),(29583,3746,'GeneralDataBuffer','3030313130304290303035C33030303030303030303030303030303030303030','binary','2018-06-10 01:17:11'),(29584,3746,'GPS','3130322E393739343733343131382E37333231343733','binary','2018-06-10 01:17:11'),(29585,3746,'Current','0','short','2018-06-10 01:17:11'),(29586,3746,'Switch2','off','bool','2018-06-10 01:17:11'),(29587,3746,'Power','0','short','2018-06-10 01:17:11'),(29588,3746,'Switch1','off','bool','2018-06-10 01:17:11'),(29589,3746,'SwitchMode2','on','bool','2018-06-10 01:17:11'),(29590,3746,'SwitchMode1','on','bool','2018-06-10 01:17:11'),(29591,3747,'Period2','3138303030363030','binary','2018-06-10 01:17:11'),(29592,3747,'Period1','3138303030363030','binary','2018-06-10 01:17:11'),(29593,3747,'Voltage','213','short','2018-06-10 01:17:11'),(29594,3747,'GeneralDataBuffer','31313131313830303036303031383030303630303130322E393739343733343131382E3733323134373338363337303330333635343931323434363031313131373637363935383812051F0118','binary','2018-06-10 01:17:11'),(29595,3747,'GPS','3130322E393739343733343131382E37333231343733','binary','2018-06-10 01:17:11'),(29596,3747,'Current','1185','short','2018-06-10 01:17:11'),(29597,3747,'Switch2','on','bool','2018-06-10 01:17:11'),(29598,3747,'Power','249','short','2018-06-10 01:17:11'),(29599,3747,'Switch1','on','bool','2018-06-10 01:17:11'),(29600,3747,'SwitchMode2','on','bool','2018-06-10 01:17:11'),(29601,3747,'SwitchMode1','on','bool','2018-06-10 01:17:11'),(29602,3748,'Period2','3138333032323333','binary','2018-06-10 01:17:11'),(29603,3748,'Period1','3138333032323333','binary','2018-06-10 01:17:11'),(29604,3748,'Voltage','0','short','2018-06-10 01:17:11'),(29605,3748,'GeneralDataBuffer','30303030303030D6303036413030303030303030303030303030303030303030','binary','2018-06-10 01:17:11'),(29606,3748,'GPS','3130322E393739343733343131382E37333231343733','binary','2018-06-10 01:17:11'),(29607,3748,'Current','0','short','2018-06-10 01:17:11'),(29608,3748,'Switch2','off','bool','2018-06-10 01:17:11'),(29609,3748,'Power','0','short','2018-06-10 01:17:11'),(29610,3748,'Switch1','off','bool','2018-06-10 01:17:11'),(29611,3748,'SwitchMode2','off','bool','2018-06-10 01:17:11'),(29612,3748,'SwitchMode1','off','bool','2018-06-10 01:17:11');

/*Table structure for table `t_iot_device_sync` */

DROP TABLE IF EXISTS `t_iot_device_sync`;

CREATE TABLE `t_iot_device_sync` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `device_id` int(11) NOT NULL COMMENT '设备id',
  `type` tinyint(4) NOT NULL DEFAULT '1' COMMENT '同步类型：1、后台同步 2、job同步 3、iot同步',
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2100 DEFAULT CHARSET=utf8 COMMENT='iot-设备消息同步记录';

/*Data for the table `t_iot_device_sync` */

insert  into `t_iot_device_sync`(`id`,`device_id`,`type`,`create_time`) values (2040,132,1,'2018-06-10 01:17:09'),(2041,133,1,'2018-06-10 01:17:09'),(2042,134,1,'2018-06-10 01:17:09'),(2043,135,1,'2018-06-10 01:17:09'),(2044,136,1,'2018-06-10 01:17:09'),(2045,137,1,'2018-06-10 01:17:10'),(2046,138,1,'2018-06-10 01:17:10'),(2047,139,1,'2018-06-10 01:17:10'),(2048,140,1,'2018-06-10 01:17:10'),(2049,141,1,'2018-06-10 01:17:10'),(2050,142,1,'2018-06-10 01:17:10'),(2051,143,1,'2018-06-10 01:17:10'),(2052,144,1,'2018-06-10 01:17:10'),(2053,145,1,'2018-06-10 01:17:10'),(2054,146,1,'2018-06-10 01:17:10'),(2055,147,1,'2018-06-10 01:17:10'),(2056,148,1,'2018-06-10 01:17:10'),(2057,149,1,'2018-06-10 01:17:10'),(2058,150,1,'2018-06-10 01:17:10'),(2059,151,1,'2018-06-10 01:17:10'),(2060,152,1,'2018-06-10 01:17:10'),(2061,153,1,'2018-06-10 01:17:10'),(2062,154,1,'2018-06-10 01:17:10'),(2063,155,1,'2018-06-10 01:17:10'),(2064,156,1,'2018-06-10 01:17:10'),(2065,157,1,'2018-06-10 01:17:10'),(2066,158,1,'2018-06-10 01:17:10'),(2067,159,1,'2018-06-10 01:17:10'),(2068,160,1,'2018-06-10 01:17:10'),(2069,161,1,'2018-06-10 01:17:10'),(2070,162,1,'2018-06-10 01:17:10'),(2071,163,1,'2018-06-10 01:17:10'),(2072,164,1,'2018-06-10 01:17:10'),(2073,165,1,'2018-06-10 01:17:10'),(2074,166,1,'2018-06-10 01:17:10'),(2075,167,1,'2018-06-10 01:17:10'),(2076,168,1,'2018-06-10 01:17:10'),(2077,169,1,'2018-06-10 01:17:10'),(2078,170,1,'2018-06-10 01:17:10'),(2079,171,1,'2018-06-10 01:17:10'),(2080,172,1,'2018-06-10 01:17:10'),(2081,173,1,'2018-06-10 01:17:10'),(2082,174,1,'2018-06-10 01:17:11'),(2083,175,1,'2018-06-10 01:17:11'),(2084,176,1,'2018-06-10 01:17:11'),(2085,177,1,'2018-06-10 01:17:11'),(2086,178,1,'2018-06-10 01:17:11'),(2087,179,1,'2018-06-10 01:17:11'),(2088,180,1,'2018-06-10 01:17:11'),(2089,181,1,'2018-06-10 01:17:11'),(2090,182,1,'2018-06-10 01:17:11'),(2091,183,1,'2018-06-10 01:17:11'),(2092,184,1,'2018-06-10 01:17:11'),(2093,185,1,'2018-06-10 01:17:11'),(2094,186,1,'2018-06-10 01:17:11'),(2095,187,1,'2018-06-10 01:17:11'),(2096,188,1,'2018-06-10 01:17:11'),(2097,189,1,'2018-06-10 01:17:11'),(2098,190,1,'2018-06-10 01:17:11'),(2099,191,1,'2018-06-10 01:17:11');

/*Table structure for table `t_iot_server` */

DROP TABLE IF EXISTS `t_iot_server`;

CREATE TABLE `t_iot_server` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `server_id` varchar(64) NOT NULL COMMENT '连接平台id',
  `description` varchar(256) NOT NULL COMMENT '描述',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 COMMENT='iot-连接平台';

/*Data for the table `t_iot_server` */

insert  into `t_iot_server`(`id`,`server_id`,`description`,`create_time`) values (10,'HW-test-iot-117','华为深圳实验室自助测试平台。','2018-06-07 10:34:26'),(11,'ctc-nanjing-iot-137','中国电信物联网开放平台','2018-06-07 10:34:26'),(12,'HW-test-iot-112','华为深圳实验室测试平台。','2018-06-07 10:34:26');

/*Table structure for table `t_iot_service_mode` */

DROP TABLE IF EXISTS `t_iot_service_mode`;

CREATE TABLE `t_iot_service_mode` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `server_id` varchar(64) NOT NULL COMMENT '连接平台id',
  `service_mode` varchar(64) NOT NULL COMMENT '业务模式',
  `is_default` int(11) NOT NULL COMMENT '0 1',
  `description` varchar(256) DEFAULT NULL COMMENT '业务描述',
  `create_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='iot-业务模式';

/*Data for the table `t_iot_service_mode` */

insert  into `t_iot_service_mode`(`id`,`server_id`,`service_mode`,`is_default`,`description`,`create_time`) values (5,'HW-test-iot-117','PSM',1,'低功耗模式。UE在进入空闲态一段时间后，关闭信号的收发和AS（接入层）相关功能，相当于部分关机，从而减少天线、射频、信令处理等的功耗消耗，增强电池使用时间。适用于对下行指令延时不敏感的设备','2018-06-07 10:34:13'),(6,'ctc-nanjing-iot-137','PSM',1,'低功耗模式。UE在进入空闲态一段时间后，关闭信号的收发和AS（接入层）相关功能，相当于部分关机，从而减少天线、射频、信令处理等的功耗消耗，增强电池使用时间。适用于对下行指令延时不敏感的设备','2018-06-07 10:34:13'),(7,'ctc-nanjing-iot-137','eDrx',0,'扩展非连续接收模式。UE在空闲态时，一个寻呼时间窗口（Paging Time Window，PTW），UE只在PTW内按DRX周期监听寻呼信道，以便接收下行业务，PTW外的时间处于睡眠态，不监听寻呼信道，不能接收下行业务。设备对指令接收实时性以及电池电量消耗介乎于psm和Drx之间。适用于对指令的延时非常敏感的设备','2018-06-07 10:34:13'),(8,'HW-test-iot-112','PSM',1,'低功耗模式。UE在进入空闲态一段时间后，关闭信号的收发和AS（接入层）相关功能，相当于部分关机，从而减少天线、射频、信令处理等的功耗消耗，增强电池使用时间。适用于对下行指令延时不敏感的设备','2018-06-07 10:34:13');

/*Table structure for table `t_sys_manager` */

DROP TABLE IF EXISTS `t_sys_manager`;

CREATE TABLE `t_sys_manager` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(64) NOT NULL COMMENT '用户名',
  `password` varchar(64) NOT NULL COMMENT '密码',
  `name` varchar(128) DEFAULT NULL COMMENT '姓名',
  `mobile` varchar(32) DEFAULT NULL COMMENT '手机号',
  `status` tinyint(2) DEFAULT '1' COMMENT '状态 0:禁用，1:正常',
  `salt` varchar(64) NOT NULL DEFAULT '' COMMENT '盐',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_manager` int(11) NOT NULL COMMENT '创建人',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_manager` int(11) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='管理员表';

/*Data for the table `t_sys_manager` */

insert  into `t_sys_manager`(`id`,`username`,`password`,`name`,`mobile`,`status`,`salt`,`create_time`,`create_manager`,`update_time`,`update_manager`) values (1,'admin','4280d89a5a03f812751f504cc10ee8a5','超级管理员','15707351117',1,'','2018-04-01 23:45:46',1,'2018-06-10 01:26:33',1),(3,'wuhuaxian','4280d89a5a03f812751f504cc10ee8a5','五华县管理员','',1,'','2018-06-10 01:05:39',1,'2018-06-10 01:31:56',1);

/*Table structure for table `t_sys_manager_account` */

DROP TABLE IF EXISTS `t_sys_manager_account`;

CREATE TABLE `t_sys_manager_account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `manager_id` int(11) NOT NULL COMMENT '管理员id',
  `account_id` int(11) NOT NULL COMMENT 'iot账户id',
  `end_user_name` varchar(1024) DEFAULT NULL COMMENT '终端账户名(逗号分隔)',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_manager` int(11) NOT NULL COMMENT '创建人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `idx_manager_account` (`manager_id`,`account_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COMMENT='管理员绑定iot账户';

/*Data for the table `t_sys_manager_account` */

insert  into `t_sys_manager_account`(`id`,`manager_id`,`account_id`,`end_user_name`,`create_time`,`create_manager`) values (13,1,2,'zengjy','2018-06-10 01:26:33',1),(16,3,2,'zengjy','2018-06-10 01:31:56',1);

/*Table structure for table `t_sys_manager_role` */

DROP TABLE IF EXISTS `t_sys_manager_role`;

CREATE TABLE `t_sys_manager_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `manager_id` int(11) NOT NULL COMMENT '管理员ID',
  `role_id` int(11) NOT NULL COMMENT '角色ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_manager` int(11) NOT NULL COMMENT '创建人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_manager_role` (`manager_id`,`role_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='管理员与角色对应关系表';

/*Data for the table `t_sys_manager_role` */

insert  into `t_sys_manager_role`(`id`,`manager_id`,`role_id`,`create_time`,`create_manager`) values (1,1,1,'2018-04-09 00:11:43',1),(8,3,2,'2018-06-10 01:05:39',1);

/*Table structure for table `t_sys_menu` */

DROP TABLE IF EXISTS `t_sys_menu`;

CREATE TABLE `t_sys_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) NOT NULL COMMENT '父菜单ID，一级菜单为0',
  `name` varchar(64) NOT NULL COMMENT '菜单名称',
  `url` varchar(200) DEFAULT NULL COMMENT '菜单URL',
  `permission` varchar(500) DEFAULT NULL COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
  `type` int(11) NOT NULL DEFAULT '1' COMMENT '类型   1：目录   2：菜单   3：按钮',
  `icon` varchar(50) DEFAULT NULL COMMENT '菜单图标',
  `order_num` int(11) NOT NULL DEFAULT '1' COMMENT '排序',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_manager` int(11) NOT NULL COMMENT '创建人',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_manager` int(11) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8 COMMENT='菜单表';

/*Data for the table `t_sys_menu` */

insert  into `t_sys_menu`(`id`,`parent_id`,`name`,`url`,`permission`,`type`,`icon`,`order_num`,`create_time`,`create_manager`,`update_time`,`update_manager`) values (1,0,'基础管理','','',0,'fa fa-bars',0,'2018-04-09 00:09:58',1,'2018-06-08 10:42:05',1),(2,0,'系统管理',NULL,NULL,0,'fa fa-desktop',1,'2018-04-09 00:56:06',1,'2018-04-09 00:56:51',1),(3,2,'员工管理','sys/manager/','sys:manager',1,'fa fa-user',2,'2018-04-09 00:57:41',1,'2018-04-22 00:35:32',1),(4,2,'菜单管理','sys/menu/','sys:menu',1,'fa fa-th-list',0,'2018-04-09 23:33:02',1,'2018-04-22 00:35:18',1),(12,2,'角色管理','sys/role','sys:role',1,'fa fa-paw',1,'2018-04-19 02:26:46',2,'2018-04-22 00:35:25',1),(13,0,'IoT基础管理','','',0,'fa fa-cloud',5,'2018-04-20 01:08:58',1,'2018-05-01 17:12:53',1),(14,13,'连接平台','iot/server','iot:server',1,'fa fa-bluetooth',1,'2018-04-20 01:10:04',1,'2018-04-21 16:48:29',1),(15,13,'业务模式','iot/serviceMode','iot:serviceMode',1,'fa fa-asterisk',2,'2018-04-20 01:10:48',1,'2018-04-22 01:36:56',1),(16,0,'IoT账户管理','','',0,'fa fa-user',4,'2018-04-20 01:11:53',1,'2018-05-01 17:12:52',1),(17,0,'IoT产品型号管理','','',0,'fa fa-bicycle',6,'2018-04-20 01:12:15',1,'2018-05-01 17:12:53',1),(18,0,'IoT设备管理','','',0,'fa fa-cogs',7,'2018-04-20 01:12:35',1,'2018-05-01 17:12:56',1),(19,0,'系统工具','','',0,'fa fa-cog',2,'2018-04-20 01:13:04',1,'2018-05-01 11:19:19',1),(20,16,'账户管理','iot/account','iot:account',1,'fa fa-user-circle',1,'2018-04-20 01:17:56',1,'2018-04-20 23:40:11',1),(23,17,'产品型号','iot/devType','iot:devType',1,'fa fa-lightbulb-o',0,'2018-04-22 13:55:42',1,'2018-04-22 13:55:42',NULL),(24,18,'设备管理','iot/device','',1,'fa fa-bug',0,'2018-04-23 22:38:14',1,'2018-06-10 01:38:17',1),(25,19,'计划任务','sys/job','sys:job',1,'fa fa-calendar',1,'2018-05-01 11:19:08',1,'2018-05-01 11:19:08',NULL),(26,1,'配置管理','sys/param','sys:param',1,'fa fa-key',1,'2018-05-01 12:11:55',1,'2018-05-01 13:02:02',1),(27,18,'设备管理-预警','iot/device/alarm','',1,'fa fa-warning',1,'2018-05-01 15:45:27',1,'2018-06-10 01:47:48',1),(28,0,'通知管理','','',0,'fa fa-envelope',3,'2018-05-01 17:11:20',1,'2018-05-01 17:12:51',NULL),(29,28,'通知管理','sys/notice','sys:notice',1,'fa fa-commenting',1,'2018-05-01 17:11:54',1,'2018-05-07 23:56:46',1),(30,28,'我的通知','sys/noticeRecord','sys:noticeRecord',1,'fa fa-comments-o',2,'2018-05-09 23:22:15',1,'2018-05-09 23:24:27',1),(31,18,'设备批量','iot/deviceBatch','iot:deviceBatch',1,'fa fa-cubes',2,'2018-06-08 15:46:36',1,'2018-06-08 15:55:05',1),(32,24,'同步IoT设备','iot/device/syncDevices','iot:device:syncDevices',2,'',1,'2018-06-10 01:35:53',1,'2018-06-10 02:03:15',1),(33,24,'添加设备','iot/device/add','iot:device:add',2,'',2,'2018-06-10 01:36:45',1,'2018-06-10 01:46:59',1),(34,24,'列表','iot/device/list','',2,'',0,'2018-06-10 01:46:44',1,'2018-06-10 01:46:44',NULL),(35,24,'查看','','iot:device:view',2,'',3,'2018-06-10 01:49:22',1,'2018-06-10 01:59:04',1),(36,24,'编辑','','iot:device:edit',2,'',4,'2018-06-10 01:50:24',1,'2018-06-10 01:50:24',NULL),(37,24,'配置','','iot:device:alarmConfig',2,'',5,'2018-06-10 01:52:57',1,'2018-06-10 01:52:57',NULL),(38,24,'报表','','iot:device:report',2,'',6,'2018-06-10 01:53:22',1,'2018-06-10 01:53:22',NULL),(39,24,'同步','','iot:device:syncDevice',2,'',7,'2018-06-10 01:53:54',1,'2018-06-10 01:53:54',NULL),(40,24,'指令','','iot:device:command',2,'',8,'2018-06-10 01:54:52',1,'2018-06-10 01:54:52',NULL),(41,24,'重启','','iot:device:reboot',2,'',9,'2018-06-10 01:55:14',1,'2018-06-10 01:55:14',NULL),(42,24,'上报','','iot:device:upload',2,'',10,'2018-06-10 01:55:31',1,'2018-06-10 01:55:31',NULL),(43,24,'删除','','iot:device:remove',2,'',11,'2018-06-10 01:55:57',1,'2018-06-10 01:55:57',NULL);

/*Table structure for table `t_sys_notice` */

DROP TABLE IF EXISTS `t_sys_notice`;

CREATE TABLE `t_sys_notice` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` tinyint(4) NOT NULL DEFAULT '1' COMMENT '通知类型 1、通知 2、预警',
  `title` varchar(128) NOT NULL COMMENT '通知标题',
  `content` varchar(512) NOT NULL COMMENT '通知内容',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_manager` int(11) NOT NULL COMMENT '创建人',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_manager` int(11) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='系统通知';

/*Data for the table `t_sys_notice` */

/*Table structure for table `t_sys_notice_record` */

DROP TABLE IF EXISTS `t_sys_notice_record`;

CREATE TABLE `t_sys_notice_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `manager_id` int(11) NOT NULL COMMENT '管理员id',
  `type` tinyint(4) NOT NULL DEFAULT '1' COMMENT '通知类型 1、通知 2、告警',
  `title` varchar(128) NOT NULL COMMENT '通知标题',
  `content` varchar(512) NOT NULL COMMENT '通知内容',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '状态 0、未读 1、已读',
  `read_time` datetime DEFAULT NULL COMMENT '阅读时间',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=189 DEFAULT CHARSET=utf8 COMMENT='系统通知记录';

/*Data for the table `t_sys_notice_record` */

/*Table structure for table `t_sys_param` */

DROP TABLE IF EXISTS `t_sys_param`;

CREATE TABLE `t_sys_param` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `param_key` varchar(64) NOT NULL COMMENT 'key',
  `param_value` varchar(128) NOT NULL COMMENT 'value',
  `description` varchar(256) DEFAULT NULL COMMENT '描述',
  `group` varchar(64) NOT NULL COMMENT '分组',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `create_manager` int(11) NOT NULL COMMENT '创建人',
  `update_manager` int(11) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_param_key` (`param_key`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8 COMMENT='系统配置';

/*Data for the table `t_sys_param` */

insert  into `t_sys_param`(`id`,`param_key`,`param_value`,`description`,`group`,`create_time`,`update_time`,`create_manager`,`update_manager`) values (7,'minPower','180','预警最小功率（W）','dev-type-alarm-11','2018-05-01 16:17:30','2018-05-13 13:04:27',1,1),(10,'maxPower','220','预警最大功率（W）','dev-type-alarm-11','2018-05-01 16:23:16','2018-05-13 13:04:42',1,1),(11,'maxNoReportTime','2','未上报时长（h）','dev-type-alarm-11','2018-05-13 04:41:38','2018-05-13 04:41:38',1,NULL),(12,'stepSize','2','报表步长','report','2018-05-13 18:20:36','2018-05-13 18:36:42',1,1),(13,'width','12','报表宽度','report','2018-05-13 18:37:56','2018-05-13 18:37:56',1,NULL);

/*Table structure for table `t_sys_role` */

DROP TABLE IF EXISTS `t_sys_role`;

CREATE TABLE `t_sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) NOT NULL COMMENT '角色名称',
  `remark` varchar(100) DEFAULT NULL COMMENT '备注',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_manager` int(11) NOT NULL COMMENT '创建人',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `update_manager` int(11) DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='角色表';

/*Data for the table `t_sys_role` */

insert  into `t_sys_role`(`id`,`name`,`remark`,`create_time`,`create_manager`,`update_time`,`update_manager`) values (1,'超级管理员','超级管理员','2018-04-09 00:11:14',1,'2018-06-10 02:02:40',1),(2,'地区管理员','地区管理员','2018-04-15 00:29:46',1,'2018-06-10 02:03:58',1);

/*Table structure for table `t_sys_role_menu` */

DROP TABLE IF EXISTS `t_sys_role_menu`;

CREATE TABLE `t_sys_role_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_id` int(11) NOT NULL COMMENT '管理员ID',
  `menu_id` int(11) NOT NULL COMMENT '角色ID',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `create_manager` int(11) NOT NULL COMMENT '创建人',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_role_menu` (`role_id`,`menu_id`)
) ENGINE=InnoDB AUTO_INCREMENT=92 DEFAULT CHARSET=utf8 COMMENT='角色与菜单对应关系表';

/*Data for the table `t_sys_role_menu` */

insert  into `t_sys_role_menu`(`id`,`role_id`,`menu_id`,`create_time`,`create_manager`) values (21,1,1,'2018-04-19 04:19:08',1),(22,1,2,'2018-04-19 04:19:39',1),(23,1,3,'2018-04-19 04:19:39',1),(24,1,4,'2018-04-19 04:19:39',1),(25,1,12,'2018-04-19 04:19:39',1),(37,1,13,'2018-04-20 01:09:09',1),(38,1,14,'2018-04-20 01:10:59',1),(39,1,15,'2018-04-20 01:10:59',1),(40,1,16,'2018-04-20 01:13:11',1),(41,1,17,'2018-04-20 01:13:11',1),(42,1,18,'2018-04-20 01:13:11',1),(43,1,19,'2018-04-20 01:13:11',1),(44,1,20,'2018-04-20 01:18:13',1),(56,2,18,'2018-04-22 01:50:23',1),(60,1,23,'2018-04-22 13:56:16',1),(61,1,24,'2018-04-23 22:38:25',1),(62,1,25,'2018-05-01 11:19:43',1),(63,1,26,'2018-05-01 12:12:05',1),(64,1,27,'2018-05-01 15:47:48',1),(65,1,28,'2018-05-01 17:12:07',1),(66,1,29,'2018-05-01 17:12:07',1),(67,2,27,'2018-05-07 00:43:33',1),(68,1,30,'2018-05-09 23:22:29',1),(69,1,31,'2018-06-08 15:46:44',1),(70,2,30,'2018-06-10 00:51:59',1),(73,2,28,'2018-06-10 00:51:59',1),(74,1,32,'2018-06-10 01:37:01',1),(75,1,33,'2018-06-10 01:37:01',1),(76,2,34,'2018-06-10 01:47:15',1),(77,2,24,'2018-06-10 01:47:15',1),(78,2,35,'2018-06-10 01:49:43',1),(79,1,34,'2018-06-10 01:56:06',1),(80,1,35,'2018-06-10 01:56:06',1),(81,1,36,'2018-06-10 01:56:06',1),(82,1,37,'2018-06-10 01:56:06',1),(83,1,38,'2018-06-10 01:56:06',1),(84,1,39,'2018-06-10 01:56:06',1),(85,1,40,'2018-06-10 01:56:06',1),(86,1,41,'2018-06-10 01:56:06',1),(87,1,42,'2018-06-10 01:56:06',1),(88,1,43,'2018-06-10 01:56:06',1),(89,2,40,'2018-06-10 01:56:34',1),(90,2,37,'2018-06-10 01:56:34',1),(91,2,38,'2018-06-10 01:56:34',1);

/*Table structure for table `t_sys_task` */

DROP TABLE IF EXISTS `t_sys_task`;

CREATE TABLE `t_sys_task` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `cron_expression` varchar(255) NOT NULL COMMENT 'cron表达式',
  `job_name` varchar(255) NOT NULL COMMENT '任务名',
  `job_group` varchar(255) NOT NULL COMMENT '任务分组',
  `job_status` varchar(255) NOT NULL DEFAULT '''0''' COMMENT '任务状态 0、关闭 1、启动',
  `spring_bean` varchar(255) DEFAULT NULL COMMENT 'Spring bean',
  `bean_class` varchar(255) NOT NULL COMMENT '任务执行时调用哪个类的方法 包名+类名',
  `method_name` varchar(255) DEFAULT NULL COMMENT '任务调用的方法名',
  `is_concurrent` tinyint(2) NOT NULL DEFAULT '0' COMMENT '任务是否并发 0:不并发 1：并发',
  `description` varchar(255) DEFAULT NULL COMMENT '任务描述',
  `create_manager` int(11) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_manager` int(11) DEFAULT NULL COMMENT '更新者',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

/*Data for the table `t_sys_task` */

insert  into `t_sys_task`(`id`,`cron_expression`,`job_name`,`job_group`,`job_status`,`spring_bean`,`bean_class`,`method_name`,`is_concurrent`,`description`,`create_manager`,`create_time`,`update_manager`,`update_time`) values (14,'0 10 * * * ?','预警job','alarmJobGroup','0',NULL,'com.scrawl.iot.web.task.DeviceAlarmJob',NULL,0,'预警job',NULL,NULL,NULL,'2018-06-10 00:53:02');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
