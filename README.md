How to create own data base function

```kotlin
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

class GetUserName(column: BindableColumn<String>?) : AbstractUniTypeFunction<String, GetUserName>(column) {
    override fun render(renderingContext: RenderingContext?): FragmentAndParameters {
        val renderedColumn = column.render(renderingContext);
        return FragmentAndParameters.withFragment("username(" + renderedColumn.fragment() + ")") //$NON-NLS-1$ //$NON-NLS-2$
            .withParameters(renderedColumn.parameters()).build();
    }

    override fun copy(): GetUserName {
        return GetUserName(column)
    }


}
```
Output example
```sql
 Preparing: select username(p.first_name), p.last_name from Person p join Person g on p.id = g.id limit ?
```