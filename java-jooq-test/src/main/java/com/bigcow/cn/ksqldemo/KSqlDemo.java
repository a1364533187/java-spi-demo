package com.bigcow.cn.ksqldemo;

import static com.kuaishou.datarch.themis.sdk.HiveSQL.APP_KEY_CANDIDATE;
import static com.kuaishou.datarch.themis.sdk.HiveSQL.APP_SECRET_CANDIDATE;

import java.util.List;
import java.util.Map;

import com.kuaishou.datarch.themis.sdk.KwaiSQL;
import com.kuaishou.datarch.themis.sdk.enums.EmEnv;
import com.kuaishou.datarch.themis.sdk.enums.EmSchema;
import com.kuaishou.datarch.themis.sdk.facade.IJobHandler;

public class KSqlDemo {

    public static void main(String[] args) throws Exception {
        // 同步 adhoc 提交
        List<Map<String, Object>> result = KwaiSQL.builder()
                //不设置默认为 EmSchema.kwaisql
                .schema(EmSchema.hive)
                /** 生产环境请按以下述参数设置*/
                //                .env(EmEnv.prod.getEnv())// 设置生产环境; 其他环境参考枚举
                //                .appKey("xxxxx") // 设置生产环境 appKey; 其他环境设置对应的appKey
                //                .appSecret("xxxxx") // 设置生产环境 appSecret; 其他环境设置对应的appSecret
                /** 以下为测试环境 sdk demo 使用的 参数  生产环境请安上述参数设置*/
                .env(EmEnv.candidate.getEnv()).appKey(APP_KEY_CANDIDATE)
                .appSecret(APP_SECRET_CANDIDATE)
                .sql("SELECT `a`.`logId`, `a`.`appKey`, `b`.`user_id`, `b`.`phone`\n"
                        + "FROM (SELECT *\n"
                        + "FROM `clickHouse`.`ks_dataarch`.`data_arch_themis_gateway_monitor`\n"
                        + "WHERE `timestamp` >= 1606752000000 limit 20000) AS `a`\n"
                        + "LEFT JOIN `mysql`.`themis`.`app_auth` AS `b` ON `a`.`appKey` = `b`.`app_key` limit 20000\n")
                .user("user").groupId(3).build()
                // 如果关联hive 或者复杂的联合查询 建议使用 异步提交
                //.asyncSubmitSQL()
                .submitSQL();
    }

    /**
     * 异步提交SQL 只关心结果
     * 1.底层使用异步提交SQL
     * 2.底层轮询 查询SQL 执行的 状态 (每x秒一次 默认每五秒一次 可以通过pollingTime(xx) 设置)
     * 3.成功时 分页查询 多次调用 IJobHandler.onNewResultArrive 方法
     * <p>
     * 参数说明
     * result 是每次分页的结果；
     * isAllResultArrived 是否所有结果都查询完了；
     * resultRows 结果总行数
     * e 执行过程中 的异常信息 （很多阶段都可能出现异常）
     * queryInfo 查询的jobId和jobInstanceId 等信息
     * <p>
     * 4.默认的 分页大小 pageSize = 1000 (可以通过pageSize(xx)方法设置该参数)
     * 5.失败时 IJobHandler.onNewResultArrive 方法Exception e 会有失败信息
     * 6.有可能中间某次查询错误 导致查询失败
     */
    public static void asyncSubmitSQL() throws Exception {
        KwaiSQL.builder()
                //不设置默认为 EmSchema.kwaisql
                .schema(EmSchema.kwaisql)
                /** 生产环境请按以下述参数设置*/
//                .env(EmEnv.prod.getEnv())// 设置生产环境; 其他环境参考枚举
//                .appKey("xxxxx") // 设置生产环境 appKey; 其他环境设置对应的appKey
//                .appSecret("xxxxx") // 设置生产环境 appSecret; 其他环境设置对应的appSecret
                /** 以下为测试环境 sdk demo 使用的 参数  生产环境请安上述参数设置*/
                .env(EmEnv.candidate.getEnv())
                .appKey(APP_KEY_CANDIDATE)
                .appSecret(APP_SECRET_CANDIDATE)
                .sql("sql")
                .user("dp")
                .groupId(123)
                .pollingTime(3)
                .handler((IJobHandler) (result, isAllResultArrived, resultRows, e, queryInfo) -> {
                    // 输出 SQL 的jobId 和jobInstanceId 相关信息
                    System.out.println(queryInfo);
                    if (e != null) {
                        // 输出执行失败的原因
                        System.out.println(e.getMessage());
                    } else {
                        // 输出查询结果 分页 会多次调用该方法
                        System.out.println(result);
                        if (isAllResultArrived) {
                            System.out.println("所有结果都查询成功");
                        }
                    }
                })
                .build()
                .asyncSubmitSQL();
    }

}
