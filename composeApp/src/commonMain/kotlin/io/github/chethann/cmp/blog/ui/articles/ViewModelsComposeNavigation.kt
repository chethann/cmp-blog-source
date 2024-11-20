package io.github.chethann.cmp.blog.ui.articles

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import io.github.chethann.cmp.blog.ui.component.CodeSnippetView
import io.github.chethann.cmp.blog.utils.AnnotationStringUtils.addBold
import io.github.chethann.cmp.blog.utils.AnnotationStringUtils.addLink

@Composable
fun ViewModelsComposeNavigation() {
    Column(modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
    ) {

        // Header Text
        Text(
            text = "Multi-Section collapsable List in Jetpack Compose\n",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            buildAnnotatedString {
                append("As per the official documentation, hilt is the recommended solution for dependency injection in Android apps, and works seamlessly with Compose. Hilt also integrates with the Navigation Compose library and gives a developer-friendly API to create ViewModels in Compose projects. One can also check ")
                addLink("Compose Hilt and Navigation", "https://developer.android.com/jetpack/compose/libraries#hilt-navigation")
                append("We can use hiltViewModel() function to get an instance of a ViewModel which is annotated with @HiltViewModel. Ex:")
            }
        )

        CodeSnippetView(code1)

        Text(
            buildAnnotatedString {
                append("This piece of code gives a ViewModel scoped to the current backStackEntry of the navigation. NavBackStackEntry implements ViewModelStoreOwner and the hiltViewModel method accepts the ViewModelStoreOwner as an optional param. The default value is set to LocalViewModelStoreOwner’s (a CompositionLocal) current value, which points to the current NavBackStackEntry.\n" +
                        "\n" +
                        "In most cases just calling hiltViewModel method suffices our development needs. However, there are obvious exceptions when we want a ViewModel to be scoped to something else apart from the current backStackEntry of the navigation graph.")
            }
        )

        Text("\nScopping ViewModel to the entire route / graph\n", style = MaterialTheme.typography.titleLarge)

        Text(
            buildAnnotatedString {
                append("One of the simplest such use cases is to have a ViewModel scoped to the entire route (or sub-graph), not just the current destination, in such cases, we can pass the backStack entry of the route instead of the current destination’s backStackEntry. Below is a sample code to do so.")
            }
        )

        CodeSnippetView(code2)

        Text("In the above code, all three screens (Mobile Number collection, OTP submission and error) in the loginGraph share a common ViewModel. This can be convenient when we have to share the UI states / fetched data with all the screens within a graph.\n" +
                "\n" +
                "One can pass any navBackStack entry to get a viewModel scoped to it. One of the use cases can be sharing a ViewModel between a screen and its next screen. This one is convenient, especially in cases where we have a screen and a bottom sheet in that screen and we model bottom sheets as bottomSheet (We found this to be a more scalable way instead of using ModelBottomSheet) which can be navigated to just like any other destinations. We can pass previousBackStackEntry in this case in bottomSheet to get the same ViewModel instance at that of the main screen.")


        CodeSnippetView(code3)

        Text(buildAnnotatedString {
            addBold("Note: ")
            append("We need to ensure we navigate to this destination (bottomsheet) only from a host screen.")
        })

        Text("\nMultiple ViewModels of the the same type within a scope\n", style = MaterialTheme.typography.titleLarge)

        Text(
            buildAnnotatedString {
                append("hiltViewModel method gives the same instance of the ViewModel (of a particular type) if it is called multiple times within a screen, for obvious reasons already discussed. There can be cases where we want to have multiple ViewModels of the same type within a screen. One of the cases where this comes in handy is when we have different tabs that user can select and we want to have the logic of fetching data for each of the tabs in a separate ViewModel. Also, if the user switches back to a tab for which the data is fetched previously, we want to show that immediately.\n" +
                        "\n" +
                        "This can be achieved by introducing a unique key for each instance of ViewModel we want to create. Unfortunately, hilt doesn’t expose such a method to developers right now. However, we can quickly write our own version of hiltViewModel method that accepts a key.\n" +
                        "\n" +
                        "Code for the same:")
            }
        )

        CodeSnippetView(codeFour)

        Text("Here we take advantage of the fact that ViewModel method already supports this way of passing a key to get different instances of ViewModels within a scope based on the key.\n" +
                "\n" +
                "One can even combine both the techniques of passing a key and passing previousBackStackEntry in case the UI has multiple tabs and we need to show a bottom sheet on click of some element in that particular tab. In this case, we need to pass the key to the bottom sheet as a navigation parameter.\n" +
                "\n" +
                "Code sample:" +
                "\n")

        CodeSnippetView(codeFive)

        Text("You can access a ViewModel from any backStackEntry this was, but accessing a backStackEntry deep inside the current navigation stack is not recommended. Also, we need to make sure BackStackEntry we are trying to access is not empty. Only then we can use this approach.\n" +
                "\n" +
                "In some rare cases, if we need a ViewModel scoped at the activity level (can be useful if we want to share the ViewModel for activity and some usecase inside of the compose destinations) then we can create it at the activity level and this can be passed to NavHost down to all the destinations where it is needed.\n" +
                "\n" +
                "If we need a ViewModel scoped to the entire NavHost, we can create it in NavHost and pass it down to all the sub-graphs or destinations.")

        CodeSnippetView(codeSix)

        Text(buildAnnotatedString {
            addBold("Conclusion:")
            append(" We can use different ways like the above to create ViewModels when using hilt and compose navigation to fit our use case.")
        })

    }
}

val code1 = """
    val viewModel = hiltViewModel<MyViewModel>()
""".trimIndent()

val code2 = """
    fun NavGraphBuilder.loginGraph(navController: NavController) {
        navigation(
            startDestination = "login/phoneNumber",
            route = "login"
        ) {

            composable("login/phoneNumber") {
                val parentEntry = remember(it) {
                    navController.getBackStackEntry("login")
                }
                val loginViewModel: LoginViewModel = hiltViewModel(parentEntry)
                LoginMobileNumberScreen(navController = navController, loginViewModel)
            }
            composable("login/otp") {
                val parentEntry = remember(it) {
                    navController.getBackStackEntry("login")
                }
                val loginViewModel: LoginViewModel = hiltViewModel(parentEntry)
                LoginOTPScreen(navController = navController, loginViewModel)
            }
            composable("login/error") {
                val parentEntry = remember(it) {
                    navController.getBackStackEntry("login")
                }
                val loginViewModel: LoginViewModel = hiltViewModel(parentEntry)
                LoginErrorScreen(navController = navController, loginViewModel = loginViewModel)
            }

        }
    }
""".trimIndent()

private val code3 = """
    fun NavGraphBuilder.bottomSheetGraph(navController: NavController) {
        bottomSheet("bottomsheet/qwerty") {
            val sharedViewModel: SharedViewModel = hiltViewModel(navController.previousBackStackEntry!!)
            BottomSheetView(sharedViewModel, navController)
        }
    }
""".trimIndent()

private val codeFour = """
    /**
     * A wrapper to get hilt VMs by key. This can be used when we need different instance of the same ViewModel within the same NavBackStackEntry
     * Most of the logic is copied from [androidx.hilt.navigation.compose.hiltViewModel]
     * and [androidx.lifecycle.viewmodel.compose.viewModel]
     */

    @Composable
    inline fun <reified VM : ViewModel> hiltViewModelForKey(key: String, navBackStackEntry: NavBackStackEntry): VM {
        return viewModel(
            key = key,
            viewModelStoreOwner = navBackStackEntry,
            modelClass = VM::class.java,
            factory = HiltViewModelFactory(
                context = LocalContext.current,
                navBackStackEntry = navBackStackEntry
            )
        )
    }
""".trimIndent()

private val codeFive = """
    bottomSheet("bottomsheet/qwerty") {
        val key = it.arguments?.getString("key") ?: ""
        val sharedViewModel: SharedViewModel = hiltViewModelForKey(
            key = key,
            navBackStackEntry = navController.previousBackStackEntry!!
        )
        BottomSheetView(sharedViewModel, navController)
    }
""".trimIndent()

private val codeSix = """
    NavHost(
            navController = rememberNavController(),
            startDestination = "startDestination"
        ) {
            val commonViewModel: CommonViewModel = hiltViewModel()
            myGraph(navController, commonViewModel)
        }
""".trimIndent()