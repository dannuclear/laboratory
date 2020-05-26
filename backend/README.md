# API

## Авторизация
http://192.168.23.129:8080/laboratory/api/v1/auth/login
```json
{ "username": "admin", "password": "pass" }
```

## Обновление токена
http://192.168.23.129:8080/laboratory/api/v1/auth/refresh-token
```json
{ "refreshToken": "uuid" }
```

## Отборы
http://192.168.23.129:8080/laboratory/api/v1/selections

## Субъекты права (Заявители и организации)
http://192.168.23.129:8080/laboratory/api/v1/legalEntities

## Заявки
http://192.168.23.129:8080/laboratory/api/v1/requests

## Единицы измерения
http://192.168.23.129:8080/laboratory/api/v1/units

## Пользователи
http://192.168.23.129:8080/laboratory/api/v1/users

## Оборудование
http://192.168.23.129:8080/laboratory/api/v1/equipments

## Пробы
http://192.168.23.129:8080/laboratory/api/v1/samples

## Нормативные документы
http://192.168.23.129:8080/laboratory/api/v1/documents

## Классы объектов
http://192.168.23.129:8080/laboratory/api/v1/objectClasses

## Документы классов объекта
http://192.168.23.129:8080/laboratory/api/v1/objectClasses/{objectClass}/documents

## Показатели классов объекта
http://192.168.23.129:8080/laboratory/api/v1/objectClasses/{objectClass}/properties

## Справочник показателей (цветность, мутность, БГКП, ...). Показатель имеет тип (микробиологический, количественный, ...) и ед. изм.
http://192.168.23.129:8080/laboratory/api/v1/properties

## Справочник типов показателей (микробиологический, количественный, ...)
http://192.168.23.129:8080/laboratory/api/v1/propertyTypes

## Справочник отчетов
http://192.168.23.129:8080/laboratory/api/v1/reports

---------------------------------------------------------

## Подразделения
http://192.168.23.129:8080/laboratory/api/v1/organizations/{organization}/departments
Требуемые роли: ROLE_DEPARTMENT (DEPARTMENT_READ, DEPARTMENT_WRITE) или ROLE_DEPARTMENT_ADMIN или ROLE_ORGANIZATION_ADMIN или ROLE_ADMIN

## Показатели пробы
http://192.168.23.129:8080/laboratory/api/v1/samples/{sample}/properties
Требуемые роли: ROLE_SAMPLE_PROPERTY (SAMPLE_PROPERTY_READ, SAMPLE_PROPERTY_WRITE) или ROLE_SAMPLE_PROPERTY_ADMIN или ROLE_ADMIN

## Поверки оборудования
http://192.168.23.129:8080/laboratory/api/v1/equipments/{equipment}/verifications
Требуемые роли: ROLE_EQUIPMENT_VERIFICATION (EQUIPMENT_VERIFICATION_READ, EQUIPMENT_VERIFICATION_WRITE) или ROLE_EQUIPMENT_VERIFICATION_ADMIN или ROLE_ADMIN

## Т.О. оборудования
http://192.168.23.129:8080/laboratory/api/v1/equipments/{equipment}/maintenances
Требуемые роли: ROLE_EQUIPMENT_MAINTENANCE (EQUIPMENT_MAINTENANCE_READ, EQUIPMENT_MAINTENANCE_WRITE) или ROLE_EQUIPMENT_MAINTENANCE_ADMIN или ROLE_ADMIN

---------------------------------------------------------
Принцип ролей и разрешений следующий:
Если пользователь имеет роль ROLE_ADMIN то соответсвенноон может все
Если пользователь имеет роль ROLE_UNIT_ADMIN то может читать и писать единицы измерения
Если пользователь имеет роль ROLE_UNIT и разрешение UNIT_READ то может только читать единицы измерения, попытка записать вернет DENY

Например если после авторизации получен ответ:
```json
{
   "refreshExp": "12.02.2020 22:59:28",
   "accessExp": "12.02.2020 22:59:28",
   "perms": "ROLE_SAMPLE_ADMIN",
   "accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGVzIjoiUk9MRV9BRE1JTiIsImV4cCI6MTU4MTUzMDM2OH0.0ozk2ZjXwlZbaBt838DJMcybR7gHNmzbnfIRoN4bq9o",
   "username": "admin",
   "refreshToken": "6600c053-dc1b-4e15-9dec-ae597896c454"
}
```

то пользователь может работать с пробами и показателями пробы. По иерархии ролей: ROLE_SAMPLE_ADMIN > ROLE_SAMPLE_PROPERTY_ADMIN (администратор проб является администратором и показателей проб)

```json
{
   "refreshExp": "12.02.2020 22:59:28",
   "accessExp": "12.02.2020 22:59:28",
   "perms": "ROLE_UNIT, UNIT_READ, ROLE_DOCUMENT, DOCUMENT_READ",
   "accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbiIsInJvbGVzIjoiUk9MRV9BRE1JTiIsImV4cCI6MTU4MTUzMDM2OH0.0ozk2ZjXwlZbaBt838DJMcybR7gHNmzbnfIRoN4bq9o",
   "username": "admin",
   "refreshToken": "6600c053-dc1b-4e15-9dec-ae597896c454"
}
```

строка ROLE_UNIT, UNIT_READ, ROLE_DOCUMENT, DOCUMENT_READ позволяет пользователю читать единицы изм и документы, следовательно и отрисовывать остальные разделы ему не нужно

```text
ROLE_ADMIN						Администратор

ROLE_DOCUMENT_ADMIN			Администратор документов (может и читать и писать)
	ROLE_DOCUMENT				Пользователь документов
		DOCUMENT_WRITE			Разрешение записи
		DOCUMENT_READ			Разрешение чтения

ROLE_EQUIPMENT_ADMIN			Администратор оборудования (может и читать и писать)
	ROLE_EQUIPMENT				Пользователь оборудования
		EQUIPMENT_WRITE		Разрешение записи
		EQUIPMENT_READ			Разрешение чтения

ROLE_PROPERTY_ADMIN			Администратор показателей (может и читать и писать)
	ROLE_PROPERTY				Пользователь показателей
		PROPERTY_WRITE			Разрешение записи
		PROPERTY_READ			Разрешение чтения
		
ROLE_SAMPLE_ADMIN				Администратор проб (может и читать и писать)
	ROLE_SAMPLE				Пользователь проб
		SAMPLE_WRITE			Разрешение записи
		SAMPLE_READ			Разрешение чтения

ROLE_UNIT_ADMIN				Администратор единиц измерения (может и читать и писать)
	ROLE_UNIT					Пользователь единиц измерения
		UNIT_WRITE				Разрешение записи
		UNIT_READ				Разрешение чтения
		
ROLE_SAMPLE_PROPERTY_ADMIN	Администратор показателей пробы (может и читать и писать)
	ROLE_SAMPLE_PROPERTY		Пользователь показателей пробы
		SAMPLE_PROPERTY_WRITE	Разрешение записи
		SAMPLE_PROPERTY_READ	Разрешение чтения
		
ROLE_ORGANIZATION_ADMIN		Администратор организаций (может и читать и писать)
	ROLE_ORGANIZATION			Пользователь организаций
		ORGANIZATION_WRITE	Разрешение записи
		ORGANIZATION_READ		Разрешение чтения

ROLE_PROPERTY_TYPE_ADMIN		Администратор типов показателей (может и читать и писать)
	ROLE_PROPERTY_TYPE		Пользователь типов показателей
		PROPERTY_TYPE_WRITE	Разрешение записи
		PROPERTY_TYPE_READ	Разрешение чтения
		
ROLE_DEPARTMENT_ADMIN			Администратор подразделений (может и читать и писать)
	ROLE_DEPARTMENT			Пользователь подразделений
		DEPARTMENT_TYPE_WRITE	Разрешение записи
		DEPARTMENT_TYPE_READ	Разрешение чтения

```
Иерархия ролей

				ROLE_ADMIN > ROLE_DOCUMENT_ADMIN
				ROLE_ADMIN > ROLE_USER_ADMIN
				ROLE_ADMIN > ROLE_EQUIPMENT_ADMIN
				ROLE_ADMIN > ROLE_PROPERTY_ADMIN
				ROLE_ADMIN > ROLE_SAMPLE_ADMIN
				ROLE_ADMIN > ROLE_UNIT_ADMIN
				ROLE_ADMIN > ROLE_SAMPLE_PROPERTY_ADMIN
				ROLE_ADMIN > ROLE_ORGANIZATION_ADMIN

				ROLE_PROPERTY_ADMIN > ROLE_PROPERTY_TYPE_ADMIN
				ROLE_PROPERTY_ADMIN > ROLE_UNIT_ADMIN

				ROLE_SAMPLE_ADMIN > ROLE_SAMPLE_PROPERTY_ADMIN

				ROLE_ORGANIZATION_ADMIN > ROLE_DEPARTMENT_ADMIN