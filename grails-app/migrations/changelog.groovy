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

    changeSet(author: "ami (generated)", id: "1352492172160-1") {
        createTable(tableName: "category") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "categoryPK")
            }

            column(name: "version", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "title_id", type: "bigint") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "ami (generated)", id: "1352492172160-2") {
        createTable(tableName: "checkin") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "checkinPK")
            }

            column(name: "version", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "place_id", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "user_id", type: "bigint") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "ami (generated)", id: "1352492172160-3") {
        createTable(tableName: "keywords") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "keywordsPK")
            }

            column(name: "version", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "category_id", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "created_by", type: "varchar(255)")

            column(name: "date_created", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "last_updated", type: "datetime") {
                constraints(nullable: "false")
            }

            column(name: "title", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "user_id", type: "bigint") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "ami (generated)", id: "1352492172160-4") {
        createTable(tableName: "photo") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "photoPK")
            }

            column(name: "version", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "absoluteurl", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "name", type: "varchar(255)")

            column(name: "place_id", type: "bigint") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "ami (generated)", id: "1352492172160-5") {
        createTable(tableName: "place") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "placePK")
            }

            column(name: "version", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "description", type: "varchar(5000)")

            column(name: "location_id", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "ranking", type: "bigint")

            column(name: "telephone", type: "varchar(255)")

            column(name: "title", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "websiteurl", type: "varchar(255)")
        }
    }

    changeSet(author: "ami (generated)", id: "1352492172160-6") {
        createTable(tableName: "review") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "reviewPK")
            }

            column(name: "version", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "likes", type: "integer") {
                constraints(nullable: "false")
            }

            column(name: "message", type: "varchar(5000)") {
                constraints(nullable: "false")
            }

            column(name: "place_id", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "ranking", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "title", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "user_id", type: "bigint")
        }
    }

    changeSet(author: "ami (generated)", id: "1352492172160-7") {
        createTable(tableName: "translatable") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "translatablePK")
            }

            column(name: "version", type: "bigint") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "ami (generated)", id: "1352492172160-8") {
        createTable(tableName: "translatable_string") {
            column(autoIncrement: "true", name: "id", type: "bigint") {
                constraints(nullable: "false", primaryKey: "true", primaryKeyName: "translatable_PK")
            }

            column(name: "version", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "language_id", type: "varchar(255)") {
                constraints(nullable: "false")
            }

            column(name: "translatable_id", type: "bigint") {
                constraints(nullable: "false")
            }

            column(name: "value", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "ami (generated)", id: "1352492172160-9") {
        addColumn(tableName: "user") {
            column(name: "created_by", type: "varchar(255)")
        }
    }

    changeSet(author: "ami (generated)", id: "1352492172160-10") {
        addColumn(tableName: "user") {
            column(name: "date_created", type: "datetime") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "ami (generated)", id: "1352492172160-11") {
        addColumn(tableName: "user") {
            column(name: "last_updated", type: "datetime") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "ami (generated)", id: "1352492172160-12") {
        dropNotNullConstraint(columnDataType: "datetime", columnName: "birthday", tableName: "user")
    }

    changeSet(author: "ami (generated)", id: "1352492172160-13") {
        createIndex(indexName: "FK302BCFE894F901", tableName: "category") {
            column(name: "title_id")
        }
    }

    changeSet(author: "ami (generated)", id: "1352492172160-14") {
        createIndex(indexName: "FK2C3ED02D2A27920C", tableName: "checkin") {
            column(name: "place_id")
        }
    }

    changeSet(author: "ami (generated)", id: "1352492172160-15") {
        createIndex(indexName: "FK2C3ED02DAA9FDF3C", tableName: "checkin") {
            column(name: "user_id")
        }
    }

    changeSet(author: "ami (generated)", id: "1352492172160-16") {
        createIndex(indexName: "FK1F2E9FAAAA9FDF3C", tableName: "keywords") {
            column(name: "user_id")
        }
    }

    changeSet(author: "ami (generated)", id: "1352492172160-17") {
        createIndex(indexName: "FK1F2E9FAAD1D3016A", tableName: "keywords") {
            column(name: "category_id")
        }
    }

    changeSet(author: "ami (generated)", id: "1352492172160-18") {
        createIndex(indexName: "FK65B3E322A27920C", tableName: "photo") {
            column(name: "place_id")
        }
    }

    changeSet(author: "ami (generated)", id: "1352492172160-19") {
        createIndex(indexName: "FK65CD9073E6293E9", tableName: "place") {
            column(name: "location_id")
        }
    }

    changeSet(author: "ami (generated)", id: "1352492172160-20") {
        createIndex(indexName: "FKC84EF7582A27920C", tableName: "review") {
            column(name: "place_id")
        }
    }

    changeSet(author: "ami (generated)", id: "1352492172160-21") {
        createIndex(indexName: "FKC84EF758AA9FDF3C", tableName: "review") {
            column(name: "user_id")
        }
    }

    changeSet(author: "ami (generated)", id: "1352492172160-22") {
        createIndex(indexName: "FK9E3B903F5C7C6CE8", tableName: "translatable_string") {
            column(name: "translatable_id")
        }
    }

    changeSet(author: "ami (generated)", id: "1352492172160-23") {
        createIndex(indexName: "FK143BF46A5751B5C", tableName: "user_role") {
            column(name: "role_id")
        }
    }

    changeSet(author: "ami (generated)", id: "1352492172160-24") {
        addForeignKeyConstraint(baseColumnNames: "title_id", baseTableName: "category", constraintName: "FK302BCFE894F901", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "translatable", referencesUniqueColumn: "false")
    }

    changeSet(author: "ami (generated)", id: "1352492172160-25") {
        addForeignKeyConstraint(baseColumnNames: "place_id", baseTableName: "checkin", constraintName: "FK2C3ED02D2A27920C", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "place", referencesUniqueColumn: "false")
    }

    changeSet(author: "ami (generated)", id: "1352492172160-26") {
        addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "checkin", constraintName: "FK2C3ED02DAA9FDF3C", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", referencesUniqueColumn: "false")
    }

    changeSet(author: "ami (generated)", id: "1352492172160-27") {
        addForeignKeyConstraint(baseColumnNames: "category_id", baseTableName: "keywords", constraintName: "FK1F2E9FAAD1D3016A", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "category", referencesUniqueColumn: "false")
    }

    changeSet(author: "ami (generated)", id: "1352492172160-28") {
        addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "keywords", constraintName: "FK1F2E9FAAAA9FDF3C", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", referencesUniqueColumn: "false")
    }

    changeSet(author: "ami (generated)", id: "1352492172160-29") {
        addForeignKeyConstraint(baseColumnNames: "place_id", baseTableName: "photo", constraintName: "FK65B3E322A27920C", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "place", referencesUniqueColumn: "false")
    }

    changeSet(author: "ami (generated)", id: "1352492172160-30") {
        addForeignKeyConstraint(baseColumnNames: "location_id", baseTableName: "place", constraintName: "FK65CD9073E6293E9", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "location", referencesUniqueColumn: "false")
    }

    changeSet(author: "ami (generated)", id: "1352492172160-31") {
        addForeignKeyConstraint(baseColumnNames: "place_id", baseTableName: "review", constraintName: "FKC84EF7582A27920C", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "place", referencesUniqueColumn: "false")
    }

    changeSet(author: "ami (generated)", id: "1352492172160-32") {
        addForeignKeyConstraint(baseColumnNames: "user_id", baseTableName: "review", constraintName: "FKC84EF758AA9FDF3C", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "user", referencesUniqueColumn: "false")
    }

    changeSet(author: "ami (generated)", id: "1352492172160-33") {
        addForeignKeyConstraint(baseColumnNames: "translatable_id", baseTableName: "translatable_string", constraintName: "FK9E3B903F5C7C6CE8", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "translatable", referencesUniqueColumn: "false")
    }

    changeSet(author: "ami (generated)", id: "1352553888585-1") {
        addColumn(tableName: "place") {
            column(name: "public_id", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "ami (generated)", id: "1352555399018-1") {
        addColumn(tableName: "place") {
            column(name: "service", type: "varchar(255)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(author: "ami (generated)", id: "1352555399018-2") {
        createIndex(indexName: "unique-public_id", tableName: "place") {
            column(name: "service")

            column(name: "public_id")
        }
    }

    changeSet(author: "ami (generated)", id: "1352581864971-1") {
        createTable(tableName: "category_facebook_categories") {
            column(name: "category_id", type: "bigint")

            column(name: "facebook_categories_string", type: "varchar(255)")
        }
    }

    changeSet(author: "ami (generated)", id: "1352581864971-2") {
        createIndex(indexName: "FK59E48874D1D3016A", tableName: "category_facebook_categories") {
            column(name: "category_id")
        }
    }

    changeSet(author: "ami (generated)", id: "1352581864971-3") {
        addForeignKeyConstraint(baseColumnNames: "category_id", baseTableName: "category_facebook_categories", constraintName: "FK59E48874D1D3016A", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "category", referencesUniqueColumn: "false")
    }

    changeSet(author: "ami (generated)", id: "1352589729106-1") {
        addColumn(tableName: "place") {
            column(name: "reference", type: "varchar(255)")
        }
    }
}
