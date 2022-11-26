package com.dbtechprojects.pokemonApp.util.testsuites

import com.dbtechprojects.pokemonApp.api.ApiIsolationTests
import com.dbtechprojects.pokemonApp.database.RoomDatabaseTest
import com.dbtechprojects.pokemonApp.ui.MainActivityTest

import org.junit.runner.RunWith
import org.junit.runners.Suite
// test suite to test Room database and API functionality

@RunWith(Suite::class)
@Suite.SuiteClasses(
    //UnitTest
    ApiIsolationTests::class,
    RoomDatabaseTest::class,
    UtilityMethodsTest::class,
    // UI Tests
MainActivityTest::class
)
class MainTestSuite