databaseChangeLog = {

	changeSet(author: "paulm (generated)", id: "1321683883102-1") {
		createTable(tableName: "answer") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "answerPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "text", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "value", type: "integer") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "paulm (generated)", id: "1321683883102-2") {
		createTable(tableName: "evaluation") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "evaluationPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "comments", type: "varchar(1000)")

			column(name: "complete", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "responder_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "results_gathered", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "review_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "paulm (generated)", id: "1321683883102-3") {
		createTable(tableName: "question") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "questionPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "text", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "paulm (generated)", id: "1321683883102-4") {
		createTable(tableName: "response") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "responsePK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "answer_id", type: "bigint")

			column(name: "evaluation_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "question_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "responses_idx", type: "integer")
		}
	}

	changeSet(author: "paulm (generated)", id: "1321683883102-5") {
		createTable(tableName: "review") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "reviewPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "complete", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "reviewee_id", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "team_review_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "paulm (generated)", id: "1321683883102-6") {
		createTable(tableName: "role") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "rolePK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "paulm (generated)", id: "1321683883102-7") {
		createTable(tableName: "team_member") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "team_memberPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "email", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "password", type: "varchar(255)") {
				constraints(nullable: "false")
			}

			column(name: "role_id", type: "bigint") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "paulm (generated)", id: "1321683883102-8") {
		createTable(tableName: "team_review") {
			column(autoIncrement: "true", name: "id", type: "bigint") {
				constraints(nullable: "false", primaryKey: "true", primaryKeyName: "team_reviewPK")
			}

			column(name: "version", type: "bigint") {
				constraints(nullable: "false")
			}

			column(name: "complete", type: "bit") {
				constraints(nullable: "false")
			}

			column(name: "name", type: "varchar(255)") {
				constraints(nullable: "false")
			}
		}
	}

	changeSet(author: "paulm (generated)", id: "1321683883102-9") {
		addForeignKeyConstraint(baseColumnNames: "responder_id", baseTableName: "evaluation", constraintName: "FK332C073C77CF1253", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "team_member", referencesUniqueColumn: "false")
	}

	changeSet(author: "paulm (generated)", id: "1321683883102-10") {
		addForeignKeyConstraint(baseColumnNames: "review_id", baseTableName: "evaluation", constraintName: "FK332C073C9C72A93E", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "review", referencesUniqueColumn: "false")
	}

	changeSet(author: "paulm (generated)", id: "1321683883102-11") {
		addForeignKeyConstraint(baseColumnNames: "answer_id", baseTableName: "response", constraintName: "FKEBB71441C77C077E", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "answer", referencesUniqueColumn: "false")
	}

	changeSet(author: "paulm (generated)", id: "1321683883102-12") {
		addForeignKeyConstraint(baseColumnNames: "evaluation_id", baseTableName: "response", constraintName: "FKEBB71441C3950CBE", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "evaluation", referencesUniqueColumn: "false")
	}

	changeSet(author: "paulm (generated)", id: "1321683883102-13") {
		addForeignKeyConstraint(baseColumnNames: "question_id", baseTableName: "response", constraintName: "FKEBB71441FCF1257E", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "question", referencesUniqueColumn: "false")
	}

	changeSet(author: "paulm (generated)", id: "1321683883102-14") {
		addForeignKeyConstraint(baseColumnNames: "reviewee_id", baseTableName: "review", constraintName: "FKC84EF7588CEDF49D", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "team_member", referencesUniqueColumn: "false")
	}

	changeSet(author: "paulm (generated)", id: "1321683883102-15") {
		addForeignKeyConstraint(baseColumnNames: "team_review_id", baseTableName: "review", constraintName: "FKC84EF758CAA2A899", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "team_review", referencesUniqueColumn: "false")
	}

	changeSet(author: "paulm (generated)", id: "1321683883102-16") {
		addForeignKeyConstraint(baseColumnNames: "role_id", baseTableName: "team_member", constraintName: "FKA29B52BC26837D7E", deferrable: "false", initiallyDeferred: "false", referencedColumnNames: "id", referencedTableName: "role", referencesUniqueColumn: "false")
	}
}
