package com.ewt360.asr.common.pool;

import com.ewt360.asr.common.constant.APIConstant;
import com.ewt360.asr.dal.dao.UploadInfoDAO;
import com.ewt360.asr.dal.model.UploadInfoDO;
import com.ewt360.asr.facade.base.dto.AsrDTO;
import lombok.extern.slf4j.Slf4j;

import java.io.*;

/**
 * @author: wangchao
 * @create: 2019/7/12 15:48
 */
@Slf4j
public class AsrTask implements Runnable {
    private AsrDTO asrDTO;
    private String command;
    private String logPath;
    private UploadInfoDAO uploadInfoDAO;

    public AsrTask(AsrDTO asrDTO,String fileDir,String logDir,UploadInfoDAO uploadInfoDAO) {
        this.asrDTO = asrDTO;
        StringBuilder sb = new StringBuilder();
        command = sb.append("sh "+APIConstant.SHELL_NAME+" ")
                .append(fileDir).append(asrDTO.getFileName()+" ").append(asrDTO.getProductId()+" ")
                .append(asrDTO.getExt()).toString();

        logPath = logDir + String.format(APIConstant.LOG_FORMAT_NAME,asrDTO.getPre());
        this.uploadInfoDAO = uploadInfoDAO;
        log.info(String.format("<<-------command:%s------->>",command));
        log.info(String.format("<<-------logPath:%s------->>",logPath));
    }

    @Override
    public void run() {
        Process process = null;
        InputStream pIn = null;
        InputStream pErr = null;
        try {
            Long startTime = System.currentTimeMillis();
            String[] cmds = { "/bin/sh", "-c", command};
            /**
             * 参数1 cmdarray：执行调用的命令及参数；
             * 参数2 envp：环境变量，如path=c:\java\bin，若未使用则为null；
             * 参数3 dir ：执行命令的工作目录；
             */
            //执行命令, 返回一个子进程对象（命令在子进程中执行）
            process = Runtime.getRuntime().exec(cmds, null, new File(asrDTO.getAppDir()));
            log.info(String.format("<<-------正在努力识别语音中[id=%s]...------->>", asrDTO.getUploadId()));
            //方法阻塞, 等待命令执行完成（成功会返回0）
            process.waitFor();
            Long endTime = System.currentTimeMillis();
            log.info(String.format("<<-------识别完成耗时%ds[id=%s]------->>", (endTime - startTime) / 1000, asrDTO.getUploadId()));

            //获取命令执行结果,有两个结果:正常的输出和错误的输出（PS:子进程的输出就是主进程的输入）
            pIn = process.getInputStream();
            pErr = process.getErrorStream();
            BufferedReader bufrIn = new BufferedReader(new InputStreamReader(pIn, "UTF-8"));
            BufferedReader bufrError = new BufferedReader(new InputStreamReader(pErr, "UTF-8"));
            //将shell的执行情况输出到日志文件中
            OutputStream os = new FileOutputStream(logPath);
            PrintWriter pw = new PrintWriter(os);
            String line = null;
            while ((line = bufrIn.readLine()) != null) {
                pw.println(line);
            }
            while ((line = bufrError.readLine()) != null) {
                pw.println(line);
            }
            if (pw != null)
                pw.flush();
        }catch (Exception e){
          log.error("语音识别异常------->>",e);
        } finally {
            UploadInfoDO up = new UploadInfoDO();
            up.setId(asrDTO.getUploadId());
            if(process!=null && process.exitValue()==0){
                up.setStatus(2);
                up.setSrtName(asrDTO.getFileName().replace("."+asrDTO.getExt(),".srt"));
            }else {
                up.setStatus(3);
                up.setSrtName("");
            }
            //更新识别状态为识别成功或识别失败
            uploadInfoDAO.update(up);

            if(pIn != null) {
                try {
                    pIn.close();
                } catch (IOException e) {
                    log.error("输入流关闭异常------->>",e);
                }
            }
            if(pErr != null) {
                try {
                    pErr.close();
                } catch (IOException e) {
                    log.error("错误流关闭异常------->>",e);
                }
            }
            if(process!=null)
                process.destroy();
        }
    }
}
