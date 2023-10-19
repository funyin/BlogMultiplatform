package com.example.android.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.android.R
import org.example.blogmultiplatform.models.Category

@Composable
fun NavigationDrawer(
    drawerState: DrawerState,
    onCategorySelect: (Category) -> Unit,
    content: @Composable () -> Unit
) {
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 100.dp)
                        .padding(horizontal = 16.dp)
                ) {
                    Image(painter = painterResource(id = R.drawable.logo), contentDescription = "Logo Image")
                }
                Text(
                    text = "Categories", modifier = Modifier
                        .fillMaxWidth()
                        .alpha(0.5f)
                        .padding(
                            top = 20.dp, bottom = 12.dp)
                        .padding(horizontal = 16.dp)
                )
                Category.entries.forEach {
                    NavigationDrawerItem(label = {
                        Text(text = it.name, color = MaterialTheme.colorScheme.onSurface)
                    }, selected = false, onClick = {
                        onCategorySelect(it)
                    })
                }
            }
        }) {
        content()
    }
}