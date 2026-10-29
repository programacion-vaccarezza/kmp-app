package org.example.project.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen

class MainScreen : Screen {

    @Composable
    override fun Content() {

        var selectedTab by rememberSaveable { mutableStateOf(MainTab.Catalog) }

        val catalogScreen = remember { CatalogScreen() }
        val myAppointmentsScreen = remember { MyAppointmentsScreen() }
        val profileScreen = remember { ProfileScreen() }

        Scaffold(
            bottomBar = {
                NavigationBar {
                    MainTab.entries.forEach { tab ->
                        NavigationBarItem(
                            selected = selectedTab == tab,
                            onClick = { selectedTab = tab },
                            icon = { Text(tab.label) }
                        )
                    }
                }
            }
        ) { innerPadding ->
            when (selectedTab) {
                MainTab.Catalog -> catalogScreen.Content(Modifier.padding(innerPadding))
                MainTab.MyAppointments -> myAppointmentsScreen.Content(Modifier.padding(innerPadding))
                MainTab.Profile -> profileScreen.Content(Modifier.padding(innerPadding))
            }
        }
    }
}

enum class MainTab(val label: String) {
    Catalog("Catálogo"),
    MyAppointments("Mis turnos"),
    Profile("Perfil")
}
