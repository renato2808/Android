package com.example.arc

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

data class StoresList(val stores: List<Store>)

data class Store(
    val storeID: Long,
    val address: String,
    val city: String,
    val name: String,
    val zipcode: String,
    val storeLogoURL: String
)

interface StoresService {

    @GET("stores.json")
    suspend fun requestAllStores(): StoresList
}

open class CoroutineContextProvider {
    open val main: CoroutineContext = Dispatchers.Main
    open val io: CoroutineContext = Dispatchers.IO
}

// Adapter signature and it's interface
class StoresListAdapter(private val stores: List<Store>, private val actions: StoresListAdapter.Actions) {

    // ...

    interface Actions {
        fun onStoreSelected(storeId: Long)
    }
}

open class BaseActivity : AppCompatActivity(), StoresListAdapter.Actions {
    override fun onStoreSelected(storeId: Long) {
        Toast.makeText(this, "Pet Store $storeId selected. Au au!", Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()

        // ...
    }
}

class MainActivity : BaseActivity(), CoroutineScope {

    private lateinit var adapter: StoresListAdapter

    // coroutine stuff
    private val coroutineContextProvider = CoroutineContextProvider()
    private lateinit var mJob: Job
    override val coroutineContext: CoroutineContext
        get() = mJob + Dispatchers.Main
    // ---------------

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mJob = Job() // coroutines stuff

        val retrofit = Retrofit.Builder()
            .baseUrl("http://arctouch.api.codeStores")
            .addConverterFactory(MoshiConverterFactory.create(Moshi.Builder().build()))
            .build()
            .create(StoresService::class.java)

        setRecyclerView()

        val thisActivityContext = this

        launch {

            val storesResponse = retrofit.requestAllStores()

            adapter = StoresListAdapter(
                storesResponse.stores,
                object : StoresListAdapter.Actions {
                    override fun onStoreSelected(storeId: Long) {
                        Toast.makeText(thisActivityContext, "Pet Store $storeId selected. Au au!", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            )
            recyclerView.adapter = adapter
        }

        retryButton.setOnClickListener {
            if (adapter == null) {
                launch {
                    val storesResponse = retrofit.requestAllStores()
                    adapter = StoresListAdapter(
                        storesResponse.stores,
                        object : StoresListAdapter.Actions {
                            override fun onStoreSelected(storeId: Long) {
                                Toast.makeText(
                                    thisActivityContext,
                                    "Pet Store $storeId selected. Au au!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    )
                    recyclerView.adapter = adapter
                }
            }
        }
    }

    fun setRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Stores List"
    }

    override fun onDestroy() {
        super.onDestroy()
        mJob.cancel()
    }
}
