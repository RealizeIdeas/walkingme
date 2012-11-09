databaseChangeLog = {

    changeSet(author: "ami (generated)", id: "1352475953630-1") {
        createTable(tableName: "location") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "locationPK")
            }

            column(name: "version", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "additional", type: "varchar(255)")

            column(name: "city", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "country_code", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "latitude", type: "decimal(19,2)")

            column(name: "longitude", type: "decimal(19,2)")

            column(name: "postal_code", type: "varchar(255)")

            column(name: "province", type: "varchar(255)")

            column(name: "street", type: "varchar(255)")
        }
    }

    changeSet(author: "ami (generated)", id: "1352475953630-2") {
        createTable(tableName: "requestmap") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "requestmapPK")
            }

            column(name: "version", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "config_attribute", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "url", type: "varchar(255)") {
                constraints(nullable: "false", unique: "true")
            }
        }
    }

    changeSet(author: "ami (generated)", id: "1352475953630-3") {
        createTable(tableName: "role") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "rolePK")
            }

            column(name: "version", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "authority", type: "varchar(255)") {
                constraints(nullable: "false", unique: "true")
            }
        }
    }

    changeSet(author: "ami (generated)", id: "1352475953630-4") {
        createTable(tableName: "user") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "userPK")
            }

            column(name: "version", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "account_expired", type: "boolean") {
                constraints(nullable: "false")
            }

            column(name: "account_locked", type: "boolean") {
                constraints(nullable: "false")
            }

            column(name: "birthday", type: "timestamp")

            column(name: "user_connection_user_id", type: "varchar(255)")

            column(name: "user_connection_provider_id", type: "varchar(255)")

            column(name: "user_connection_provider_user_id", type: "varchar(255)")

            column(name: "email", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "enabled", type: "boolean") {
                constraints(nullable: "false")
            }

            column(name: "first_name", type: "varchar(255)")

            column(name: "gender", type: "varchar(255)")

            column(name: "last_name", type: "varchar(255)")

            column(name: "location_id", type: "bigint")

            column(name: "password", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "password_expired", type: "boolean") {
                constraints(nullable: "false")
            }

            column(name: "username", type: "varchar(255)") {
                constraints(nullable: "false", unique: "true")
            }
        }
    }

    changeSet(author: "ami (generated)", id: "1352475953630-5") {
        createTable(tableName: "user_role") {
            column(name: "role_id", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "user_id", type: "bigint") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "ami (generated)", id: "1352475953630-6") {
        createTable(tableName: "UserConnection") {
            column(name: "userId", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "providerId", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "providerUserId", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "accessToken", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "displayName", type: "varchar(255)")

            column(name: "expireTime", type: "bigint")

            column(name: "imageUrl", type: "varchar(255)")

            column(name: "profileUrl", type: "varchar(255)")

            column(name: "rank", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "refreshToken", type: "varchar(255)")

            column(name: "secret", type: "varchar(255)")
        }
    }

    changeSet(author: "ami (generated)", id: "1352475953630-7") {
        addPrimaryKey(columnNames: "userId, providerId, providerUserId", constraintName: "UserConnectioPK", tableName: "UserConnection")
    }

    changeSet(author: "ami (generated)", id: "1352475953630-8") {
        addPrimaryKey(columnNames: "role_id, user_id", constraintName: "user_rolePK", tableName: "user_role")
    }

    changeSet(author: "ami (generated)", id: "1352475953630-9") {
        createIndex(indexName: "url_unique_1352475953513", tableName: "requestmap", unique: "true") {
            column(name: "url")
        }
    }

    changeSet(author: "ami (generated)", id: "1352475953630-10") {
        createIndex(indexName: "authority_unique_1352475953519", tableName: "role", unique: "true") {
            column(name: "authority")
        }
    }

    changeSet(author: "ami (generated)", id: "1352475953630-11") {
        createIndex(indexName: "username_unique_1352475953535", tableName: "user", unique: "true") {
            column(name: "username")
        }
    }

    changeSet(author: "ami (generated)", id: "1352475953630-12") {
        addForeignKeyConstraint(baseColumnNames: "location_id", baseTableName: "user", constraintName: "FK36EBCB4A43DDFC", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "location", referencesUniqueColumn: "false")
    }

    changeSet(author: "ami (generated)", id: "1352475953630-13") {
        addForeignKeyConstraint(baseColumnNames: "user_connection_user_id, user_connection_provider_id, user_connection_provider_user_id", baseTableName: "user", constraintName: "FK36EBCBA2C81E27", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "userId, providerId, providerUserId", referencedTableName: "UserConnection", referencesUniqueColumn: "false")
    }

    changeSet(author: "ami (generated)", id: "1352475953630-14") {
        addForeignKeyConstraint(baseColumnNames: "role_id", baseTableName: "user_role", constraintName: "FK143BF46A5751B5C", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "role", referencesUniqueColumn: "false")
    }

    changeSet(author: "ami (generated)", id: "1352475953630-15") {
        addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "user_role", constraintName: "FK143BF46AAA9FDF3C", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", referencesUniqueColumn: "false")
    }
}
