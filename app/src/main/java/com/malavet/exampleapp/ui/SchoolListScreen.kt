package com.malavet.exampleapp.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.malavet.exampleapp.data.models.SAT


//two screens for this app
sealed class Screen(val route: String) {
    object MessageList : Screen("list")
    data class DetailScreen(val item: String) : Screen("detail/{itemId}")
}

/*
* Nav Graph used for navigation between components
* */
@Composable
fun NavGraph() {
    val navController = rememberNavController()
    val mainViewModel: MainViewModel = viewModel()

    NavHost(navController = navController, startDestination = "list") {
        // ListScreen shows the lists of schools
        composable(Screen.MessageList.route) {
            ListScreen(navController = navController, mainViewModel)
        }
        // DetailScreen shows the SAT for a school
        composable(
            route = Screen.DetailScreen("").route,
            arguments = listOf(navArgument("itemId") { type = NavType.StringType })
        ) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getString("itemId")
            itemId?.let { SchoolDetailScreen(dbn = it, mainViewModel) }
        }
    }
}

/*
* List of Schools, shows circular loading until list has values
* Improvement would be different states handled in ViewModel
* When row is clicked, viewmodel function is called to get details
* */
@Composable
fun ListScreen(navController: NavController, mainViewModel: MainViewModel) {

    val schoolList by mainViewModel.schoolList.collectAsState(emptyList())

    if(schoolList.isEmpty()) {
        CircularProgressIndicator(
            modifier = Modifier
                .size(width = 40.dp, height = 40.dp),
            color = Color.Gray, //progress color
        )
    } else {
        LazyColumn {
            items(schoolList) { school ->
                Row(
                    modifier = Modifier.clickable {
                        mainViewModel.getSATData(school.dbn)
                        navController.navigate("detail/${school.dbn}")
                    }
                ) {
                    // Row content
                    Text(text = school.school_name)
                }
                Divider()
            }
        }
    }
}
/*
* SAT Data
* If API cannot be called or if no data returned, message "No data available for this school" displayed
* */
@Composable
fun SchoolDetailScreen(dbn: String, mainViewModel: MainViewModel) {
    val satData: List<SAT?> by mainViewModel.satData.collectAsState(emptyList())
    if(satData.isEmpty()) {
        Text(text = "No data available for this school.")
    } else {
        LazyColumn {
            items(satData) { item ->
                // Display row item
                val one: String = item?.sat_math_avg_score ?: "SAT MATH AVERAGE SCORE N/A"
                val two: String = item?.sat_critical_reading_avg_score ?: "SAT CRITICAL READING AVERAGE SCORE N/A"
                val three: String = item?.sat_writing_avg_score ?: "SAT WRITING AVERAGE SCORE N/A"
                val four: String = item?.num_of_sat_test_takers ?: "SAT NUMBER OF TEST TAKERS N/A"

                Row() {
                    Text("SAT MATH AVG SCORE: $one")
                }
                Divider()
                Row() {

                    Text("SAT CRITICAL READING AVERAGE SCORE: $two")
                }
                Divider()
                Row() {
                    Text("SAT WRITING AVERAGE SCORE: $three")
                }
                Divider()
                Row() {
                    Text("SAT NUMBER OF TEST TAKERS: $four")
                }
            }
        }
    }
}











