package fr.dawanAndroidMap.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import fr.dawanAndroidMap.R
import fr.dawanAndroidMap.data.AppDatabase
import fr.dawanAndroidMap.data.Center
import fr.dawanAndroidMap.net.Api
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity(private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO) : AppCompatActivity(), OnMapReadyCallback {

    // Défini la carte google map, est init à l'event onMapReady
    private var map: GoogleMap? = null

    // Trigger l'event onCenterClicked pour les élements de CenterAdapter (RecyclerView)
    private val adapter = CentersAdapter(::onCenterClicked)
    /**
     * Event appelé quand un élement du recyclerView est clické
     */
    private fun onCenterClicked(c: Center) {
        map?.animateCamera(
            CameraUpdateFactory.newLatLngZoom(LatLng(c.latitude, c.longitude), 10f)
        )
    }
    private val dao by lazy { AppDatabase.get(this).centerDao() }

    private var latestCenters: List<Center> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val existing = supportFragmentManager.findFragmentByTag("map") as? SupportMapFragment
        val mapFragment = existing ?: SupportMapFragment.newInstance().also {
            supportFragmentManager.beginTransaction()
                .replace(R.id.mapContainer, it, "map")
                .commitNow()
        }
        mapFragment.getMapAsync(this)

        findViewById<RecyclerView>(R.id.centersRecyclerView).apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = this@MainActivity.adapter
        }

        loadDatas()

    }

    /**
     * Essaie de load les données des centres, si vide les appels depuis l'API Dawan
     * puis set la maps avec les points gps des différents centres
     */
    private fun loadDatas(){
        lifecycleScope.launch {
            try {
                // At first we try to load the database (centers)
                val centers = withContext(ioDispatcher) { dao.getAllOnce() }
                if (centers.isNotEmpty()) {
                    latestCenters = centers
                    adapter.submitList(centers)
                    map?.let { updateMarkers(it, centers) }
                } else {
                    // if empty we load data from Dawan API
                    val net = withContext(ioDispatcher) { Api.service.getCenters() }
                    latestCenters = net
                    adapter.submitList(net)
                    map?.let { updateMarkers(it, net) }

                    // set data in db
                    withContext(ioDispatcher) {
                        dao.clear()
                        dao.insertAll(net)
                    }
                }
            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, "Hors ligne / erreur réseau", Toast.LENGTH_LONG).show()

                // Si exception on retry de se connecter à la base de donnée
                val fallback = withContext(ioDispatcher) { dao.getAllOnce() }
                if (fallback.isNotEmpty()) {
                    latestCenters = fallback
                    adapter.submitList(fallback)
                    map?.let { updateMarkers(it, fallback) }
                }
            }
        }
    }

    /**
     * La Map est prête → on peut dessiner (si on a déjà des données)
     */
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap.apply { uiSettings.isZoomControlsEnabled = true }

        map?.setOnMarkerClickListener { marker ->
            val c = marker.tag as? Center
            if (c != null) {
                DetailDialogFragment.newInstance(c.name, c.address)
                    .show(supportFragmentManager, "detail")
            }
            true
        }

        if (latestCenters.isNotEmpty()) {
            updateMarkers(googleMap, latestCenters)
        }

        // Coordonnées de la France
        val france = LatLng(46.603354, 1.888334)

        map?.animateCamera(CameraUpdateFactory.newLatLngZoom(france, 5f))
    }

    // Dessine les marqueurs sur la carte
    private fun updateMarkers(gm: GoogleMap, centers: List<Center>) {
        gm.clear()
        centers.forEach { c ->
            gm.addMarker(
                MarkerOptions()
                    .position(LatLng(c.latitude, c.longitude))
                    .title(c.name)
            )?.tag = c
        }
        centers.firstOrNull()?.let {
            gm.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(it.latitude, it.longitude),
                    5f
                )
            )
        }
    }
}
