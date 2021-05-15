package com.dbtechprojects.pokemonApp.util.testsuites

import com.dbtechprojects.gamedeals.api.ApiIsolationTests

import org.junit.runner.RunWith
import org.junit.runners.Suite
// test suite to test Room database and API functionality

@RunWith(Suite::class)
@Suite.SuiteClasses(
    ApiIsolationTests::class
)
class DataIsolationTests