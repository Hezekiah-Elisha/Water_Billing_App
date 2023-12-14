package com.olefaent.waterbilling.ui.screens


import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.olefaent.waterbilling.model.Customer
import com.olefaent.waterbilling.ui.screens.components.ErrorScreen
import com.olefaent.waterbilling.ui.screens.components.LoadingScreen

@Composable
fun CustomersScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    uiState: BillingState,
){
    when (uiState){
        is BillingState.Loading -> LoadingScreen(modifier = modifier)
        is BillingState.Success -> CustomersList(customers = uiState.customers, modifier = modifier)
        is BillingState.Error -> ErrorScreen(retryAction = {}, modifier = modifier)
    }
}

@Composable
fun CustomersList(
    customers: List<Customer>,
    modifier: Modifier = Modifier
){
    LazyColumn(modifier = modifier){
        items(customers.size){ index ->
            CustomerCard(customer = customers[index])
        }
    }
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomerCard(customer: Customer,modifier: Modifier = Modifier){
    ListItem(
        headlineText = { Text(text = customer.name) },
        leadingContent = {
            Icon(
                Icons.Filled.Person,
                contentDescription = "Localized description",
            )
        },
        supportingText = { Text(text = customer.location) },
        tonalElevation = 10.dp,
    )
}





@Preview(showBackground = true)
@Composable
fun CustomersScreenPreview(){
    val customer = Customer(
        1,
        "Rufus Olefa",
        "0722560780",
        "elishahezekiah903@gmail.com",
        "Ruai",
        "09:00hrs")
    CustomerCard(customer = customer)
}