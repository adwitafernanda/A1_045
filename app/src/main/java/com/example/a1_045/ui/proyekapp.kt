package com.example.a1_045.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import com.example.a1_045.ProyekApplications
import com.example.a1_045.ui.navigation.PengelolaHalaman

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProyekApp(

) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val appContainerProyek = (LocalContext.current.applicationContext as ProyekApplications).containerproyek
    Scaffold(
//        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        //topBar = {TopAppBar(scrollBehavior = scrollBehavior)},
    ){
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ){
            PengelolaHalaman(appContainerProyek = appContainerProyek)
        }
    }
}
