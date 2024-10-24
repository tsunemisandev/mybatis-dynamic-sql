package com.example.mybatis_dynamic_sql

import com.example.mybatis_dynamic_sql.PersonDynamicSqlSupport.firstName
import com.example.mybatis_dynamic_sql.PersonDynamicSqlSupport.lastName
import com.example.mybatis_dynamic_sql.PersonDynamicSqlSupport.person
import com.example.mybatis_dynamic_sql.PersonDynamicSqlSupport2.person2
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Result
import org.apache.ibatis.annotations.Results
import org.apache.ibatis.annotations.SelectProvider
import org.mybatis.dynamic.sql.AliasableSqlTable
import org.mybatis.dynamic.sql.BindableColumn
import org.mybatis.dynamic.sql.render.RenderingContext
import org.mybatis.dynamic.sql.select.function.AbstractUniTypeFunction
import org.mybatis.dynamic.sql.select.render.SelectStatementProvider
import org.mybatis.dynamic.sql.util.FragmentAndParameters
import org.mybatis.dynamic.sql.util.SqlProviderAdapter
import org.mybatis.dynamic.sql.util.kotlin.elements.column
import org.mybatis.dynamic.sql.util.kotlin.model.select
import java.sql.JDBCType
import java.util.*

object PersonDynamicSqlSupport {
    val person = Person()
    val id = person.id
    val firstName = person.firstName
    val lastName = person.lastName
    val birthDate = person.birthDate
    val employed = person.employed
    val occupation = person.occupation

    class Person : AliasableSqlTable<Person>("Person", ::Person) {
        val id = column<Int>(name = "id", jdbcType = JDBCType.INTEGER)
        val firstName = column<String>(name = "first_name", jdbcType = JDBCType.VARCHAR)
        val lastName = column<String>(name = "last_name", jdbcType = JDBCType.VARCHAR)
        val birthDate = column<Date>(name = "birth_date", jdbcType = JDBCType.DATE)
        val employed = column<Boolean>(
            name = "employed",
            jdbcType = JDBCType.VARCHAR,
            typeHandler = "examples.kotlin.mybatis3.canonical.YesNoTypeHandler"
        )
        val occupation = column<String>(name = "occupation", jdbcType = JDBCType.VARCHAR)
        val addressId = column<Int>(name = "address_id", jdbcType = JDBCType.INTEGER)
    }
}

object PersonDynamicSqlSupport2 {
    val person2 = Person()
    val id = person2.id
    val firstName = person2.firstName
    val lastName = person2.lastName
    val birthDate = person2.birthDate
    val employed = person2.employed
    val occupation = person2.occupation

    class Person : AliasableSqlTable<Person>("Person", ::Person) {
        val id = column<Int>(name = "id", jdbcType = JDBCType.INTEGER)
        val firstName = column<String>(name = "first_name", jdbcType = JDBCType.VARCHAR)
        val lastName = column<String>(name = "last_name", jdbcType = JDBCType.VARCHAR)
        val birthDate = column<Date>(name = "birth_date", jdbcType = JDBCType.DATE)
        val employed = column<Boolean>(
            name = "employed",
            jdbcType = JDBCType.VARCHAR,
            typeHandler = "examples.kotlin.mybatis3.canonical.YesNoTypeHandler"
        )
        val occupation = column<String>(name = "occupation", jdbcType = JDBCType.VARCHAR)
        val addressId = column<Int>(name = "address_id", jdbcType = JDBCType.INTEGER)
    }
}


val selectStatement = select(GetUserName(firstName), lastName) {
    from(person, "p")
    join(person2, "g") {
        on(person.id) equalTo person2.id
    }
    limit(3)
}

data class PersonRecord(var firstName: String? = null)

@Mapper
interface PersonMapper {
    @SelectProvider(type = SqlProviderAdapter::class, method = "select")
    @Results(
        id = "PersonRecordResult",
        value = [
            Result(column = "first_name", property = "firstName"),
        ]
    )
    fun selectMany(selectStatement: SelectStatementProvider): List<PersonRecord>
}

class GetUserName(column: BindableColumn<String>?) : AbstractUniTypeFunction<String, GetUserName>(column) {
    override fun render(renderingContext: RenderingContext?): FragmentAndParameters {
//        val renderedColumn = column.render(renderingContext);
//        return FragmentAndParameters.withFragment("username(" + renderedColumn.fragment() + ")") //$NON-NLS-1$ //$NON-NLS-2$
//            .withParameters(renderedColumn.parameters()).build();
        return column.render(renderingContext).mapFragment { f-> "username($f)" }
    }

    override fun copy(): GetUserName {
        return GetUserName(column)
    }


}