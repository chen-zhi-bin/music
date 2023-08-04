package com.chen.music;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.LikeTable;
import com.baomidou.mybatisplus.generator.config.querys.MySqlQuery;
import com.baomidou.mybatisplus.generator.keywords.MySqlKeyWordsHandler;


import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;

public class CodeGenerator {
    public static void main(String[] args) {
        // 全局策略配置
        GlobalConfig globalConfig = new GlobalConfig.Builder()
                // 覆盖已生成的文件
                .fileOverride()
                // 指定输出路径
                .outputDir("D:\\GraduationDesign\\music\\MusicSystem\\src\\main\\java")
                // 开启swagger注解
                .enableSwagger()
                // 作者
                .author("chen")
                //生成之后是否打开文件夹
                .openDir(false)
                // 时间策略
//                .dateType(DateType.TIME_PACK)
                // 注释日期格式
//                .commentDate("yyyy-MM-dd")
                .build();

        //数据源配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig
                .Builder("jdbc:mysql://192.168.64.133:3306/music_mysql?characterEncoding=utf-8&useSSL=false&useUnicode=true"
                , "root", "123456")
                //数据库类型转换
                .typeConvert(new MySqlTypeConvert())
                //数据库关键字处理
                .keyWordsHandler(new MySqlKeyWordsHandler())
                //数据库查询
                .dbQuery(new MySqlQuery())
                //数据库部分
                // .schema("mybatis-plus")
                .build();

        //包配置
        PackageConfig packageConfig = new PackageConfig.Builder()
                //父包名
                .parent("com.chan.music")
                //父包模块名
                .moduleName("")
                //Controller 包名
                .controller("controller")
                //Mapper 包名
                .mapper("mapper")
                .xml("mapper.xml")
                //Service 包名
                .service("service")
                .serviceImpl("service.impl")
                //Entity 包名
                .entity("pojo")
                .build();

        //策略配置
        StrategyConfig builder = new StrategyConfig.Builder()
                //开启大写命名
                .enableCapitalMode()
                //开启跳过视图
                .enableSkipView()
                //禁用 sql 过滤
                .disableSqlFilter()
                //模糊表匹配(sql 过滤)
                .likeTable(new LikeTable("user"))
                //增加表匹配(内存过滤)
//                .addInclude("t_simple")
                //增加过滤表前缀
                .addTablePrefix("tb_", "c_")
                //增加过滤表后缀
//                .addFieldSuffix("_flag")
                // mapper 策略配置
                .mapperBuilder()
                //设置父类
                .superClass(BaseMapper.class)
                //启用 BaseResultMap 生成
                .enableBaseResultMap()
                //启用 BaseColumnList
                .enableBaseColumnList()
                //格式化 mapper 文件名称
                .formatMapperFileName("%sDao")
                //格式化 xml 实现类文件名称
                .formatXmlFileName("%s")
                .controllerBuilder()
//                .superClass(BaseController.class)
                .enableHyphenStyle()//驼峰
                .enableRestStyle()
//                .formatFileName("%sAction")
                .build();
        // 添加以上配置到AutoGenerator中
        AutoGenerator autoGenerator = new AutoGenerator(dataSourceConfig);
        autoGenerator.global(globalConfig); // 全局配置策略
        autoGenerator.packageInfo(packageConfig); // 包配置
        autoGenerator.strategy(builder);
        //生成代码
        autoGenerator.execute();
    }
}
