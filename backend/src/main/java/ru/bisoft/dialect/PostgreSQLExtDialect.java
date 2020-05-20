package ru.bisoft.dialect;
import java.sql.Types;

import org.hibernate.dialect.PostgreSQL82Dialect;

public class PostgreSQLExtDialect extends PostgreSQL82Dialect {
	public PostgreSQLExtDialect() {
		registerColumnType(Types.JAVA_OBJECT, "jsonb");
	}
}
