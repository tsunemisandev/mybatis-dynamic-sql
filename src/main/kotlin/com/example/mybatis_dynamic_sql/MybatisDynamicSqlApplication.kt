package com.example.mybatis_dynamic_sql

import org.mybatis.dynamic.sql.render.RenderingStrategies
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MybatisDynamicSqlApplication(val mapper: PersonMapper):CommandLineRunner {
	override fun run(vararg args: String?) {
		mapper.selectMany(selectStatement.render(RenderingStrategies.MYBATIS3))
	}
}

fun main(args: Array<String>) {
	runApplication<MybatisDynamicSqlApplication>(*args)
}
