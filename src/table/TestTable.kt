package com.ali.table

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object TestTable : Table() {
    val id: Column<Long> = long("id").autoIncrement()
    val idTest = varchar("idTest", 512)
    val questionImage = text("questionImage")
    val aAnswer = text("aAnswer")
    val bAnswer = text("bAnswer")
    val cAnswer = text("cAnswer")
    val dAnswer = text("dAnswer")

    override val primaryKey: PrimaryKey = PrimaryKey(id)
}