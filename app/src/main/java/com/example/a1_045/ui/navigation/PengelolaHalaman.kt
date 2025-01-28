package com.example.a1_045.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.a1_045.dependenciesinjection.AppContainerProyek
import com.example.a1_045.ui.page.DestinasiHomeUtama
import com.example.a1_045.ui.page.HomeScreen
import com.example.a1_045.ui.page.anggota.view.DestinasiDetailAnggota
import com.example.a1_045.ui.page.anggota.view.DestinasiHomeAnggota
import com.example.a1_045.ui.page.anggota.view.DestinasiInsertAnggota
import com.example.a1_045.ui.page.anggota.view.DestinasiUpdateAnggota
import com.example.a1_045.ui.page.anggota.view.DetailAnggotaScreen
import com.example.a1_045.ui.page.anggota.view.HomeAnggotaScreen
import com.example.a1_045.ui.page.anggota.view.InsertAnggotaScreen
import com.example.a1_045.ui.page.anggota.view.UpdateAnggotaScreen
import com.example.a1_045.ui.page.proyek.view.DestinasiDetailProyek
import com.example.a1_045.ui.page.proyek.view.DestinasiHomeProyek
import com.example.a1_045.ui.page.proyek.view.DestinasiInsertProyek
import com.example.a1_045.ui.page.proyek.view.DestinasiUpdate
import com.example.a1_045.ui.page.proyek.view.DetailProyekScreen
import com.example.a1_045.ui.page.proyek.view.HomeProyekScreen
import com.example.a1_045.ui.page.proyek.view.InsertProyek
import com.example.a1_045.ui.page.proyek.view.UpdateScreen
import com.example.a1_045.ui.page.team.view.DestinasiDetailTim
import com.example.a1_045.ui.page.team.view.DestinasiHomeTim
import com.example.a1_045.ui.page.team.view.DestinasiInsertTim
import com.example.a1_045.ui.page.team.view.DestinasiUpdateTim
import com.example.a1_045.ui.page.team.view.DetailTimScreen
import com.example.a1_045.ui.page.team.view.HomeTimScreen
import com.example.a1_045.ui.page.team.view.InsertTimScreen
import com.example.a1_045.ui.page.team.view.UpdateTimScreen
import com.example.a1_045.ui.page.tugas.view.DestinasiDetailTugas
import com.example.a1_045.ui.page.tugas.view.DestinasiHomeTugas
import com.example.a1_045.ui.page.tugas.view.DestinasiInsertTugas
import com.example.a1_045.ui.page.tugas.view.DestinasiUpdateTugas
import com.example.a1_045.ui.page.tugas.view.DetailTugasScreen
import com.example.a1_045.ui.page.tugas.view.HomeTugasScreen
import com.example.a1_045.ui.page.tugas.view.InsertTugasScreen
import com.example.a1_045.ui.page.tugas.view.UpdateTugasScreen

@Composable
fun PengelolaHalaman(
    appContainerProyek: AppContainerProyek,
    navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = DestinasiHomeUtama.route,
        modifier = Modifier,
    ){


        composable(DestinasiHomeUtama.route) {
            HomeScreen(
                navigateToTim = { navController.navigate(DestinasiHomeTim.route)},
                navigateToTugas = { navController.navigate(DestinasiHomeTugas.route) },
                navigateToProyek = { navController.navigate(DestinasiHomeProyek.route) },
                navigateToAnggotaTim = { navController.navigate((DestinasiHomeAnggota.route)) },
            )
        }


        composable(DestinasiHomeTim.route){
            HomeTimScreen(
                navigateToItemEntry = { navController.navigate(DestinasiInsertTim.route)},
                onDetailClick = {idtim -> navController.navigate("${DestinasiDetailTim.route}/$idtim")},
                navigateToEdit = {idtim -> navController.navigate("${DestinasiUpdateTim.route}/$idtim")},
                navigateBack = {
                    navController.navigate(DestinasiHomeUtama.route){
                        popUpTo(DestinasiHomeUtama.route){
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(DestinasiDetailTim.route + "/{idtim}") { backStackEntry ->
            val idtim = backStackEntry.arguments?.getString("idtim")
            if (idtim != null) {
                DetailTimScreen(idtim = idtim , repository = appContainerProyek.timRepository,navigateBack = {
                    navController.navigate(DestinasiHomeTim.route) {
                        popUpTo(DestinasiHomeTim.route) {
                            inclusive = true
                        }
                    }}) // Use the passed repository
            } else {
                // Handle error jika NIM null
            }
        }
        composable(DestinasiInsertTim.route) {
            InsertTimScreen(navigateBack = {
                navController.navigate(DestinasiHomeTim.route){
                    popUpTo(DestinasiHomeTim.route){
                        inclusive = true
                    }
                }
            })
        }
        composable(
            DestinasiUpdateTim.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdateTim.IDTim) {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString(DestinasiUpdateTim.IDTim)
            id?.let { idValue ->
                UpdateTimScreen(
                    onBack = {
                        navController.popBackStack()
                    },
                    onNavigate = {
                        navController.popBackStack()
                    }
                )
            }
        }















        composable(DestinasiHomeProyek.route){
            HomeProyekScreen(
                navigateBack = {
                    navController.navigate(DestinasiHomeUtama.route){
                        popUpTo(DestinasiHomeUtama.route){
                            inclusive = true
                        }
                    }
                },

                navigateToItemEntry = { navController.navigate(DestinasiInsertProyek.route)},
                onDetailClick = {idProyek -> navController.navigate("${DestinasiDetailProyek.route}/$idProyek")},
                navigateToEdit = {idProyek -> navController.navigate("${DestinasiUpdate.route}/$idProyek")},

            )
        }
        composable(DestinasiDetailProyek.route + "/{idProyek}") { backStackEntry ->
            val idProyek = backStackEntry.arguments?.getString("idProyek")
            if (idProyek != null) {
                DetailProyekScreen(idProyek = idProyek , repository = appContainerProyek.proyekRepository,navigateBack = {
                    navController.navigate(DestinasiHomeProyek.route) {
                        popUpTo(DestinasiHomeProyek.route) {
                            inclusive = true
                        }
                    }}) // Use the passed repository
            } else {
                // Handle error jika NIM null
            }
        }
        composable(DestinasiInsertProyek.route) {
            InsertProyek(navigateBack = {
                navController.navigate(DestinasiHomeProyek.route){
                    popUpTo(DestinasiHomeProyek.route){
                        inclusive = true
                    }
                }
            })
        }
        composable(
            DestinasiUpdate.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdate.ID) {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString(DestinasiUpdate.ID)
            id?.let { idValue ->
                UpdateScreen(
                    onBack = {
                        navController.popBackStack()
                    },
                    onNavigate = {
                        navController.popBackStack()
                    }
                )
            }
        }









        composable(DestinasiHomeTugas.route){
            HomeTugasScreen(
                navigateToItemEntry = { navController.navigate(DestinasiInsertTugas.route)},
                onDetailClick = {idtugas -> navController.navigate("${DestinasiDetailTugas.route}/$idtugas")},
                navigateToEdit = { idtugas ->
                    navController.navigate("${DestinasiUpdateTugas.route}/$idtugas")
                },
                navigateBack = {
                    navController.navigate(DestinasiHomeUtama.route){
                        popUpTo(DestinasiHomeUtama.route){
                            inclusive = true
                        }
                    }
                },
            )
        }
        composable(DestinasiDetailTugas.route + "/{idtugas}") { backStackEntry ->
            val idtugas = backStackEntry.arguments?.getString("idtugas")
            if (idtugas != null) {
                DetailTugasScreen(idtugas = idtugas , repository = appContainerProyek.tugasRepository,navigateBack = {
                    navController.navigate(DestinasiHomeTugas.route) {
                        popUpTo(DestinasiHomeTugas.route) {
                            inclusive = true
                        }
                    }}) // Use the passed repository
            } else {
                // Handle error jika NIM null
            }
        }
        composable(DestinasiInsertTugas.route) {
            InsertTugasScreen(navigateBack = {
                navController.navigate(DestinasiHomeTugas.route){
                    popUpTo(DestinasiHomeTugas.route){
                        inclusive = true
                    }
                }
            })
        }
        composable(
            DestinasiUpdateTugas.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdateTugas.IDTugas) {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString(DestinasiUpdateTugas.IDTugas)
            id?.let { idValue ->
                UpdateTugasScreen(
                    onBack = { navController.popBackStack() },
                    onNavigate = { navController.popBackStack() }
                )
            }
        }









        composable(DestinasiHomeAnggota.route){
            HomeAnggotaScreen(
                navigateToItemEntry = { navController.navigate(DestinasiInsertAnggota.route)},
                onDetailClick = {idanggota -> navController.navigate("${DestinasiDetailAnggota.route}/$idanggota")},
                navigateToEdit = { idanggota ->
                    navController.navigate("${DestinasiUpdateAnggota.route}/$idanggota")
                },
                navigateBack = {
                    navController.navigate(DestinasiHomeUtama.route){
                        popUpTo(DestinasiHomeUtama.route){
                            inclusive = true
                        }
                    }
                },
            )
        }
        composable(DestinasiDetailAnggota.route + "/{idanggota}") { backStackEntry ->
            val idanggota = backStackEntry.arguments?.getString("idanggota")
            if (idanggota != null) {
                DetailAnggotaScreen(idanggota = idanggota , repository = appContainerProyek.anggotaRepository,navigateBack = {
                    navController.navigate(DestinasiHomeAnggota.route) {
                        popUpTo(DestinasiHomeAnggota.route) {
                            inclusive = true
                        }
                    }}) // Use the passed repository
            } else {
                // Handle error jika NIM null
            }
        }
        composable(DestinasiInsertAnggota.route) {
            InsertAnggotaScreen(navigateBack = {
                navController.navigate(DestinasiHomeAnggota.route){
                    popUpTo(DestinasiHomeAnggota.route){
                        inclusive = true
                    }
                }
            })
        }
        composable(
            DestinasiUpdateAnggota.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdateAnggota.IDAnggota) {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString(DestinasiUpdateAnggota.IDAnggota)
            id?.let { idValue ->
                UpdateAnggotaScreen(
                    onBack = { navController.popBackStack() },
                    onNavigate = { navController.popBackStack() }
                )
            }
        }
    }
}