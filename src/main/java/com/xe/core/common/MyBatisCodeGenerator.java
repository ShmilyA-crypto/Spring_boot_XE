/**
 * 
 */
/**
 * @author DELL
 *
 */
package com.xe.core.common;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

/**
 * 自动生成代码
 * 
 * @author DELL
 *
 */
//https://mp.baomidou.com/config/generator-config.html#strategy
public class MyBatisCodeGenerator {


	public static void generateByTables(String... tableNames) {
		// 代码生成器
		AutoGenerator mpg = new AutoGenerator();

		String projectPath = System.getProperty("user.dir"); // 当前项目目录
		// 全局配置
		GlobalConfig gc = new GlobalConfig();
		gc.setAuthor("DEXTER").setOutputDir(projectPath + "/src/main/java").setFileOverride(true)// 覆盖现有文件
				.setServiceName("%sService")// .setSwagger2(true)
				.setOpen(false).setBaseResultMap(true);
		mpg.setGlobalConfig(gc);

		// 数据源配置
		String dbUrl = "jdbc:mysql://127.0.0.1:3306/spring_boot_xe?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true";
		DataSourceConfig dataSourceConfig = new DataSourceConfig();
		dataSourceConfig.setDbType(DbType.MYSQL).setUrl(dbUrl).setUsername("root").setPassword("root233")
				.setDriverName("com.mysql.jdbc.Driver");
		mpg.setDataSource(dataSourceConfig);

		// 包配置
		PackageConfig pc = new PackageConfig();
		// pc.setModuleName(scanner("模块名"));
		pc.setParent(null).setXml("mybatis").setParent("com.xe.core");
		mpg.setPackageInfo(pc);

		// 策略配置
		StrategyConfig strategy = new StrategyConfig();
		strategy.setNaming(NamingStrategy.underline_to_camel).setColumnNaming(NamingStrategy.underline_to_camel)
				// .setInclude(scanner("表名"))
				.setInclude(tableNames).setRestControllerStyle(true)
				.setSuperControllerClass("com.xe.core.controller.BaseController")
				.setControllerMappingHyphenStyle(true).setTablePrefix(pc.getModuleName() + "_");
		mpg.setStrategy(strategy);
		mpg.execute();

	}

	public static void main(String[] args) {
		generateByTables("db_datasource");

	}
}