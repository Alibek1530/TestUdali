package com.ali.repository

import com.ali.model.Options
import com.ali.model.Tests
import com.ali.table.OptionTable
import com.ali.table.TestTable
import com.ali.repository.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.*


class Repo {

    suspend fun addOption(options: Options) {
        dbQuery {
            OptionTable.insert { ot ->
                ot[OptionTable.idTest] = options.id
                ot[OptionTable.name] = options.name
                ot[OptionTable.description] = options.description
            }
        }
    }

    suspend fun getAllOption(): List<Options> = dbQuery {
        OptionTable.selectAll().orderBy(OptionTable.idTest to SortOrder.ASC).mapNotNull { rowToOption(it) }
    }

    suspend fun updateOption(id: Long, options: Options) {
        dbQuery {
            OptionTable.update(
                where = {
                    OptionTable.idTest.eq(id)
                }
            ) { ot ->
                    ot[OptionTable.name] = options.name
                    ot[OptionTable.description] = options.description
            }
        }
    }

    suspend fun deleteOption(id: Long) {
        dbQuery {
            OptionTable.deleteWhere { OptionTable.idTest.eq(id) }
        }
    }

    ////////////////////////

    suspend fun addTest(tests: Tests) {
        dbQuery {
            TestTable.insert { tt ->
                tt[TestTable.idTest] = tests.id.toString()
                tt[TestTable.questionImage] = tests.questionImage
                tt[TestTable.aAnswer] = tests.aAnswer
                tt[TestTable.bAnswer] = tests.bAnswer
                tt[TestTable.cAnswer] = tests.cAnswer
                tt[TestTable.dAnswer] = tests.dAnswer
            }
        }
    }

    suspend fun isTestIdCount(testId: Long): Long {
        val count: Long = dbQuery {
            TestTable.select {
                TestTable.idTest.eq(testId.toString())
            }.count()
        }
        return count
    }

    suspend fun getAllTest(testId: String): List<Tests> = dbQuery {
        TestTable.select {
            TestTable.idTest.eq(testId)
        }.orderBy(TestTable.id to SortOrder.ASC)
            .mapNotNull { rowToTest(it) }
    }

    suspend fun updateTest(id: Long, tests: Tests) {
        dbQuery {
            TestTable.update(
                where = {
                    TestTable.id.eq(id)
                }
            ) { ut ->
                ut[TestTable.idTest] = tests.id.toString()
                ut[TestTable.questionImage] = tests.questionImage
                ut[TestTable.aAnswer] = tests.aAnswer
                ut[TestTable.bAnswer] = tests.bAnswer
                ut[TestTable.cAnswer] = tests.cAnswer
                ut[TestTable.dAnswer] = tests.dAnswer
            }
        }
    }

    suspend fun deleteTest(id: Long) {
        dbQuery {
            TestTable.deleteWhere { TestTable.id.eq(id) }
        }
    }

    private fun rowToTest(row: ResultRow?): Tests? {
        if (row == null) {
            return null
        }
        return Tests(
            id = row[TestTable.id].toLong(),
            questionImage = row[TestTable.questionImage],
            aAnswer = row[TestTable.aAnswer],
            bAnswer = row[TestTable.bAnswer],
            cAnswer = row[TestTable.cAnswer],
            dAnswer = row[TestTable.dAnswer],
        )
    }

    private fun rowToOption(row: ResultRow?): Options? {
        if (row == null) {
            return null
        }
        return Options(
            id = row[OptionTable.idTest],
            name = row[OptionTable.name],
            description = row[OptionTable.description]
        )
    }
}