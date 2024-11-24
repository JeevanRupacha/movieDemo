package com.demo.moviedemo

import com.demo.moviedemo.core.utils.GetImageURLTest
import com.demo.moviedemo.data.utils.DataRepositoryTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    GetImageURLTest::class,
    DataRepositoryTest::class
)
class RunTestSuit