package com.greencrop.activities.HomeScreen

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import com.greencrop.singleton.toast
import com.greencrop.R
import com.greencrop.databinding.ActivityHomeBinding
import com.greencrop.activities.login.ui.login.LoginActivity
import okhttp3.*
import java.util.*

import android.widget.TextView
import androidx.core.content.ContextCompat
import com.greencrop.activities.FaqActivity
import com.greencrop.utils.MyApp
import kotlinx.android.synthetic.main.nav_header_home.view.*
import java.lang.Exception

class HomeActivity : AppCompatActivity(), LocationListener,
    NavigationView.OnNavigationItemSelectedListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityHomeBinding
    var locationManager: LocationManager? = null
    var location: Location? = null

    companion object {
        var lat = 0.0
        var lang = 0.0
        var season="rabi"
    }

    fun askpermission(){
        if (ContextCompat.checkSelfPermission(this@HomeActivity,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            == PackageManager.PERMISSION_DENIED){

        ActivityCompat.requestPermissions(
                this@HomeActivity, arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
//                Manifest.permission.ACCESS_NETWORK_STATE,
//                Manifest.permission.SEND_SMS,
//                Manifest.permission.READ_SMS,
//                Manifest.permission.RECEIVE_SMS,
                Manifest.permission.CALL_PHONE,
                ),
                1
            )
            }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarHome.toolbar)

        askpermission()

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_home)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_setting,
                R.id.navFertilizer, R.id.nav_Logout
            ), drawerLayout
        )
        var headerView = navView.getHeaderView(0)
        var txtName=headerView.findViewById<TextView>(R.id.txtName)
        txtName.text=MyApp.pref?.getemail()
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navView.setNavigationItemSelectedListener(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.home, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_home)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onLocationChanged(location: Location) {
        this.location = location
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        try {
            when (requestCode) {
                1 -> {

                    // If request is cancelled, the result arrays are empty.
                    if (grantResults.size > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    ) {
                        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
                        if (ActivityCompat.checkSelfPermission(
                                this,
                                Manifest.permission.ACCESS_FINE_LOCATION
                            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                this,
                                Manifest.permission.ACCESS_COARSE_LOCATION
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return
                        }
                        locationManager!!.requestLocationUpdates("network", 5000, 5.0f, this)
                        location = locationManager!!.getLastKnownLocation("network")
                        if (location != null) {
                            lat = java.lang.Double.valueOf(location!!.latitude)
                            lang = java.lang.Double.valueOf(location!!.longitude)
                            lat = round(lat, 4) // returns 200.35
                            lang = round(lang, 4) // returns 200.35
                        }

                        // permission was granted, yay! Do the
                        // contacts-related task you need to do.
                    } else {

                        // permission denied, boo! Disable the
                        // functionality that depends on this permission.

                        when (requestCode) {
                            1 -> {


                                // If request is cancelled, the result arrays are empty.
                                if (grantResults.size > 0
                                    && grantResults[0] == PackageManager.PERMISSION_GRANTED
                                ) {
                                    locationManager =
                                        getSystemService(LOCATION_SERVICE) as LocationManager
                                    if (ActivityCompat.checkSelfPermission(
                                            this,
                                            Manifest.permission.ACCESS_FINE_LOCATION
                                        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                                            this,
                                            Manifest.permission.ACCESS_COARSE_LOCATION
                                        ) != PackageManager.PERMISSION_GRANTED
                                    ) {
                                        // TODO: Consider calling
                                        //    ActivityCompat#requestPermissions
                                        // here to request the missing permissions, and then overriding
                                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                        //                                          int[] grantResults)
                                        // to handle the case where the user grants the permission. See the documentation
                                        // for ActivityCompat#requestPermissions for more details.
                                        return
                                    }
                                    locationManager!!.requestLocationUpdates(
                                        "network",
                                        5000,
                                        5.0f,
                                        this
                                    )
                                    location = locationManager!!.getLastKnownLocation("network")
                                    if (location != null) {
                                        lat = java.lang.Double.valueOf(location!!.latitude)
                                        lang = java.lang.Double.valueOf(location!!.longitude)
                                        lat = round(
                                            lat,
                                            4
                                        ) // returns 200.35
                                        lang = round(
                                            lang,
                                            4
                                        ) // returns 200.35
                                    }

                                    // permission was granted, yay! Do the
                                    // contacts-related task you need to do.
                                } else {

                                    // permission denied, boo! Disable the
                                    // functionality that depends on this permission.
                                    Toast.makeText(
                                        this@HomeActivity,
                                        "Permission denied to read your External storage",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                                return
                            }
                        }
                    }
                    return
                }
            }
        }catch (e:Exception){}
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    fun round(value: Double, places: Int): Double {
        var value = value
        require(places >= 0)
        val factor = Math.pow(10.0, places.toDouble()).toLong()
        value = value * factor
        val tmp = Math.round(value)
        return tmp.toDouble() / factor
    }

    fun logout() {

        MyApp.pref?.setemail("")
        var i = Intent(this@HomeActivity, LoginActivity::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(i)
        finish()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        val navController = findNavController(R.id.nav_host_fragment_content_home)
        if (id == R.id.nav_Logout) {
            //this.toast("logout clicked")
            AlertDialog.Builder(this@HomeActivity)
                .setTitle("Log Out")
                .setMessage("Are you sure you want to log out?")
                .setPositiveButton("yes") { dialog, which ->
            logout()
                    //  startActivity(new Intent(HomeActivity.this,LoginRegisterActivity.class));
                }
                .setNegativeButton(
                    "Abort"
                ) { dialog, which -> Log.d("MainActivity", "Aborting mission...") }
                .show()
        }else if (id==R.id.nav_setting){
            navController.navigate(R.id.action_nav_home_to_nav_settings)
        }else if (id==R.id.nav_home){
            navController.navigate(R.id.nav_home)
        }else if (id== R.id.navFertilizer){
//            navController.navigate(R.id.navFertilizerShopMap)

            val url = "https://www.google.com/maps/search/?api=1&query=fertilizer shops near me"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        }else if (id== R.id.navFertilizer_for_crop_menu){
            navController.navigate(R.id.navFertilizer_for_crop)
        }else if (id== R.id.rabi){
            season="rabi"
            navController.navigate(R.id.navSeasonal)
        }else if (id== R.id.kharif){
            season="kharif"
            navController.navigate(R.id.navSeasonal)
        }else if (id== R.id.zaid){
            season="zaid"
            navController.navigate(R.id.navSeasonal)
        }else if (id== R.id.nav_faq){
            var intent = Intent(this@HomeActivity, FaqActivity::class.java)
            startActivity(intent)
        }else if (id== R.id.nav_call){
            val callIntent = Intent(Intent.ACTION_CALL)
            callIntent.data = Uri.parse("tel:123456789")
            startActivity(callIntent)
        }
        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        drawer.closeDrawer(GravityCompat.START)
        return super.onOptionsItemSelected(item)
    }


}