package com.uhi5d.spotibud


/*
@RunWith(AndroidJUnit4::class)
class DbTest {

    private lateinit var appDatabase: AppDatabase
    private lateinit var searchHistoryDao: SearchHistoryDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        searchHistoryDao = appDatabase.shDao()
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        appDatabase.close()
    }


    @Test
    @Throws(Exception::class)
    fun writeAndGetSH() = runBlocking {
        val list = listOf(
            SearchHistory(
                name = "Do I Wanna Know",
                artistName = "Arctic Monkeys",
                uid = 0,
                type = "",
                sId = "",
                artistId = "",
                cImage = ""
            ),
            SearchHistory(
                name = "Tame Impala",
                artistName = "Tame Impala",
                uid = 1,
                type = "",
                sId = "",
                artistId = "",
                cImage = ""
            )
        )

        searchHistoryDao.insertSH(list[0])
        searchHistoryDao.insertSH(list[1])

        val all = searchHistoryDao.getAllSH()

        assertThat(all.first()[0].name, equalTo(list[0].name))
    }
}*/
