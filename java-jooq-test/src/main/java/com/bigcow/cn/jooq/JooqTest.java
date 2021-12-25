package com.bigcow.cn.jooq;

import static org.jooq.impl.DSL.count;
import static org.jooq.impl.DSL.countDistinct;
import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.inline;
import static org.jooq.impl.DSL.sum;
import static org.jooq.impl.DSL.table;

import org.jooq.DSLContext;
import org.jooq.Query;
import org.jooq.SQLDialect;
import org.jooq.conf.ParamType;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;

public class JooqTest {

    public static void main(String[] args) {
        DSLContext create = DSL.using(SQLDialect.MYSQL); //指定SQLDialect
// Fetch a SQL string from a jOOQ Query in order to manually execute it with another tool.
// For simplicity reasons, we're using the API to construct case-insensitive object references, here.
//dp_data_factory.sub_dw_app_ptc_dc_stat_web_service_di_v2  clickhouse 表
        Query query = create.select(field("user_id").as(field("userId")), field("pv").div(3), countDistinct(field("ac_type")), sum(sum(field("pv", SQLDataType.INTEGER))))
                .from(table("dp_data_factory.sub_dw_app_ptc_dc_stat_web_service_di_v2"))
                .where(field("p_date").eq("20200328").and(field("p").in(1,2,3)))
                .groupBy(field("user_id"))
                .orderBy(field("kk"))
                .limit(inline(3))
                .offset(7);

// your inlined bind values will be properly escaped to avoid SQL syntax errors and SQL injection.
        String sql = query.getSQL(ParamType.INLINED);

        System.out.println("sql: " + sql);
    }

//    public static void main(String[] args) {
//        DSLContext create = DSL.using(SQLDialect.MYSQL); //指定SQLDialect
//        // Fetch a SQL string from a jOOQ Query in order to manually execute it with another tool.
//        // For simplicity reasons, we're using the API to construct case-insensitive object references, here.
//        //dp_data_factory.sub_dw_app_ptc_dc_stat_web_service_di_v2  clickhouse 表
//        Query query = create.selectDistinct(field("pv"), field("uv"))
//                .from(table("dp_data_factory.sub_dw_app_ptc_dc_stat_web_service_di_v2"))
//                .where(field("p_date").eq("20200328"))
//                .groupBy(field("user_id"))
//                .orderBy(field("user_id"))
//                .limit(inline(3))
//                .offset(7);
//
//        // your inlined bind values will be properly escaped to avoid SQL syntax errors and SQL injection.
//        String sql = query.getSQL(ParamType.INLINED);
//
//        System.out.println("sql: " + sql);
//    }


}
