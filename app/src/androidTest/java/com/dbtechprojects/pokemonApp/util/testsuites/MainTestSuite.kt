package com.dbtechprojects.pokemonApp.util.testsuites

import com.dbtechprojects.pokemonApp.api.ApiIsolationTests
import com.dbtechprojects.pokemonApp.database.RoomDatabaseTest

import org.junit.runner.RunWith
import org.junit.runners.Suite
// test suite to test Room database and API functionality

@RunWith(Suite::class)
@Suite.SuiteClasses(
    ApiIsolationTests::class,
    RoomDatabaseTest::class,
    UtilityMethodsTest::class
)
class MainTestSuite