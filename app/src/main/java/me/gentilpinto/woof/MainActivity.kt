package me.gentilpinto.woof

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.gentilpinto.woof.data.Dog
import me.gentilpinto.woof.data.dogs
import me.gentilpinto.woof.ui.theme.WoofTheme

@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WoofTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    WoffApp()
                }
            }
        }
    }
}

@ExperimentalMaterial3Api
@Preview(
    name = "Light Theme", showBackground = true, showSystemUi = true, apiLevel = 23
)
@Preview(
    name = "Dark Theme",
    showBackground = true,
    showSystemUi = true,
    apiLevel = 23,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun AppPreview() {
    WoofTheme {
        Surface(
            modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
        ) {
            WoffApp()
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun WoffApp() {
    Scaffold(topBar = {
        WoofTopAppBar()
    }) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding)
        ) {
            items(dogs) {
                DogItem(it)
            }
        }
    }
}

@Composable
fun WoofTopAppBar(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.ic_woof_logo),
            contentDescription = null,
            modifier = Modifier
                .size(64.dp)
                .padding(8.dp)
        )
        Text(
            text = stringResource(R.string.app_name), style = MaterialTheme.typography.headlineLarge
        )
    }
}

@Composable
fun DogItem(
    dog: Dog, modifier: Modifier = Modifier
) {
    var expanded by remember {
        mutableStateOf(false)
    }
    Card(
        modifier = modifier.padding(8.dp), colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ), elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessLow
                )
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                DogIcon(
                    image = dog.imageResourceId
                )
                DogInformation(
                    name = dog.name, age = dog.age
                )
                Spacer(modifier = Modifier.weight(1f))
                DogItemButton(expanded = expanded, onClick = { expanded = !expanded })
            }
            if (expanded) DogHobby(dogHobby = dog.hobbies)
        }
    }
}

@Composable
fun DogIcon(
    @DrawableRes image: Int, modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(image),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .size(64.dp)
            .padding(8.dp)
            .clip(RoundedCornerShape(50))
    )
}

@Composable
fun DogInformation(
    @StringRes name: Int, age: Int, modifier: Modifier = Modifier
) {
    Column {
        Text(
            text = stringResource(name),
            style = MaterialTheme.typography.headlineMedium,
            modifier = modifier.padding(top = 8.dp)
        )
        Text(
            text = stringResource(R.string.years_old, age),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun DogHobby(
    @StringRes dogHobby: Int, modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(
            start = 16.dp, top = 8.dp, bottom = 16.dp, end = 16.dp
        )
    ) {
        Text(
            text = stringResource(R.string.about), style = MaterialTheme.typography.headlineSmall
        )
        Text(
            text = stringResource(dogHobby), style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun DogItemButton(
    expanded: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier
) {
    IconButton(onClick = onClick, modifier = modifier) {
        @DrawableRes val icon: Int = when (expanded) {
            true -> R.drawable.expand_less_icon_24
            false -> R.drawable.expand_more_icon_24
        }
        Icon(
            painter = painterResource(icon),
            tint = MaterialTheme.colorScheme.secondary,
            contentDescription = stringResource(
                R.string.expand_button_content_description
            )
        )
    }
}
