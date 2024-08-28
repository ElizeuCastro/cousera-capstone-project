package com.example.littlelemon.ui.screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.littlelemon.R
import com.example.littlelemon.data.local.Menu
import com.example.littlelemon.ui.helper.FilterHelper
import com.example.littlelemon.ui.navigation.Destinations
import com.example.littlelemon.ui.viewmodel.HomeViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory)
) {
    val menu = viewModel.menu.collectAsStateWithLifecycle().value
    val categories = menu.map { it.category }.distinct()
    var query by rememberSaveable { mutableStateOf("") }
    var selectedCategory by rememberSaveable { mutableStateOf("") }
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        item(
            key = "header"
        ) {
            Column {
                NavBar(navController)
                HeroSection {
                    query = it
                }
            }
        }
        stickyHeader {
            MenuBreakDown(categories, selectedCategory) {
                selectedCategory = it
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        val filteredItem = FilterHelper.doSearch(menu, query, selectedCategory)

        items(
            items = filteredItem,
            key = { item -> item.id }
        ) {
            MenuItem(item = it)
        }
    }
}


@Composable
fun NavBar(navController: NavController) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "little lemon logo",
            modifier = Modifier
                .padding(top = 16.dp, bottom = 16.dp, start = 50.dp)
                .height(50.dp)
                .weight(1F, true)
        )

        Image(
            painter = painterResource(id = R.drawable.profile_icon),
            contentDescription = "profile",
            modifier = Modifier
                .padding(vertical = 16.dp, horizontal = 16.dp)
                .size(50.dp)
                .clickable {
                    navController.navigate(Destinations.Profile.route)
                }
        )
    }
}

@Composable
fun HeroSection(onSearch: (String) -> Unit) {
    var query by rememberSaveable { mutableStateOf("") }
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .background(MaterialTheme.colors.primary),
    ) {
        Text(
            text = "Little Lemon",
            style = TextStyle(
                color = MaterialTheme.colors.primaryVariant,
                fontWeight = FontWeight.Medium,
                fontSize = 64.sp
            ),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(start = 16.dp, end = 16.dp)
        )
        Text(
            text = "Chicago",
            modifier = Modifier
                .offset(y = (-15).dp)
                .padding(start = 16.dp, end = 16.dp),
            style = TextStyle(
                color = Color.White,
                fontWeight = FontWeight.Normal,
                fontSize = 40.sp
            )
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(start = 16.dp, end = 16.dp)
        ) {
            Text(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .weight(1f)
                    .width(0.dp)
                    .align(Alignment.CenterVertically),
                text = "We are a family owned Mediterranean restaurant, focused on traditional recipes served with a modern twist.",
                style = TextStyle(
                    color = Color.White
                )
            )
            Image(
                modifier = Modifier
                    .width(100.dp)
                    .height(120.dp)
                    .clip(MaterialTheme.shapes.medium),
                painter = painterResource(id = R.drawable.banner_icon),
                contentDescription = "hero image",
                contentScale = ContentScale.Crop,
            )
        }
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .background(color = Color.Gray),
            value = query,
            onValueChange = {
                query = it
                onSearch(query)
            },
            placeholder = {
                Text(
                    text = "Search Food Items",
                )
            },
            singleLine = true,
            leadingIcon = {
                Icon(
                    modifier = Modifier.size(24.dp),
                    imageVector = Icons.Default.Search,
                    contentDescription = "",
                    tint = Color.Black
                )
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search,
            ),
            keyboardActions = KeyboardActions(
                onSearch = { onSearch(query) }
            ),
            shape = MaterialTheme.shapes.medium,
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
    }
}


@Composable
fun MenuBreakDown(
    categories: List<String>,
    selectedCategory: String,
    onCategorySelectionChange: (selectedCategory: String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colors.background)
            .padding(top = 16.dp)
    ) {
        Text(
            text = "ORDER FOR DELIVERY!",
            modifier = Modifier.padding(start = 16.dp)
        )
        Spacer(
            modifier = Modifier
                .height(8.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            categories.forEach {
                CategoryItem(
                    categoryName = it,
                    selectedCategory,
                    onCategorySelectionChange
                )
            }
        }
        Divider(
            modifier = Modifier
                .padding(top = 4.dp)
                .height(1.dp)
        )
    }
}


@Composable
fun CategoryItem(
    categoryName: String,
    selectedCategory: String,
    onCheckedChange: (category: String) -> Unit
) {
    Text(
        text = categoryName,
        modifier = Modifier
            .background(
                color = if (categoryName == selectedCategory) MaterialTheme.colors.primary else MaterialTheme.colors.primary.copy(
                    alpha = 0.2F
                ),
                shape = MaterialTheme.shapes.small
            )
            .clickable {
                onCheckedChange(
                    if (selectedCategory == categoryName)
                        ""
                    else categoryName
                )
            }
            .padding(8.dp),
        style = TextStyle(
            fontWeight = FontWeight.ExtraBold,
            fontSize = 16.sp
        )
    )
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MenuItem(item: Menu) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = item.title,
        )
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .width(0.dp)
            ) {
                Text(
                    text = item.description,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .padding(top = 8.dp, end = 8.dp, bottom = 8.dp)
                )
                Text(
                    text = stringResource(id = R.string.menu_item_price, item.price)
                )
            }
            GlideImage(
                model = item.image,
                contentDescription = "Food Image",
                modifier = Modifier
                    .size(80.dp)
                    .clip(MaterialTheme.shapes.medium),
                contentScale = ContentScale.Crop
            )
        }
        Divider(
            modifier = Modifier
                .padding(vertical = 24.dp)
                .height(1.dp),
            color = Color.LightGray
        )
    }
}