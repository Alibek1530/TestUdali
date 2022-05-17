package com.ali.table

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object OptionTable : Table() {
    val idTest: Column<Long> = long("idTest")
    val name = text("name")
    val description = text("description")

    override val primaryKey: PrimaryKey = PrimaryKey(idTest)
}