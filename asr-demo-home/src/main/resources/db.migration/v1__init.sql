SET FOREIGN_KEY_CHECKS=0;

CREATE TABLE upload_info (
  id bigint(20) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键',
  orgin_name varchar(100) NOT NULL COMMENT '视频文件原始名',
  file_name varchar(100) NOT NULL COMMENT '视频文件名',
  status tinyint(2) NOT NULL COMMENT '状态（0:待语音识别,1:语音识别中,2:语音识别成功,3:语音识别失败）',
  srt_name varchar(100) DEFAULT NULL COMMENT '字幕文件名',
  video_type varchar(10) NOT NULL COMMENT '视频类型(ENG:英语,OTHER:其他)',
  create_by varchar(10) NOT NULL COMMENT '创建人工号',
  ctime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  utime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;