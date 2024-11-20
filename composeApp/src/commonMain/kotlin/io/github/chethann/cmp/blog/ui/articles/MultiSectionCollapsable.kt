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
import io.github.chethann.cmp.blog.ui.component.BulletPoints
import io.github.chethann.cmp.blog.ui.component.CodeSnippetView
import io.github.chethann.cmp.blog.utils.AnnotationStringUtils.addLink

@Composable
fun MultiSectionCollapsable() {
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

        Text("Our application is built using Jetpack Compose and this article explains how we can build a performant multi-section list.")

        Text(
            buildAnnotatedString {
                append("LazyList has a limitation that it can’t contain other lists of undefined height which can scroll in the same direction. Read more about the same here: ")
                addLink("", "")
                append(" Basically, nested scrolling is not supported (except when you fix the height for the inner lists).\n" +
                        "\n" +
                        "This article explains the design choices we had to make to build a complex UI with LazyColumn.\n" +
                        "\n" +
                        "Below are the basic requirements for the screen:")

            }
        )

        BulletPoints(
            listOf(
                "We wanted to build a screen for a restaurant menu listing where users could easily browse through the items of a restaurant.",
                "The items are grouped into sections, each section can have sub-sections or items directly inside them.",
                "Any Section or subsection can be collapsed and expanded by the user.",
                "Each subsection has items.",
                "If there are out-of-stock items in a section or a sub-section, they are collapsed into a separate section at the end.",
                "There is a menu bottom sheet, from which the user can click on a category and jump to that position in the UI."
            )
        )

        Text(
            buildAnnotatedString {
                append("We wanted to build a view with these requirements using LazyColumn. We also wanted to keep the code easy and intuitive to understand for all the developers.\n" +
                        "\n" +
                        "As you can see in the image above, we display a list of Categories on the screen, each category can have items and/or SubCategories. SubSections contain Items.\n" +
                        "\n" +
                        "Defining each section as a Composable and using these inside the LazyColumn is not a performant solution as there can be hundreds of items within a category and there would be no recycling as all of these items need to be rendered and need to be in memory when any portion of the category needs to rendered. This approach is not performant in terms of memory as well. The only performant approach we could think of was to have all of the items as the immediate children of the LazyList rather than the whole category. That way, there is no need to render the entire section, nor the limitation of not recycling the views would be there.\n" +
                        "\n" +
                        "Keeping these requirements in mind, these would be the interactable components:")
            }
        )

        BulletPoints(
            listOf("Category", "SubCategory", "Item")
        )

        Text(
            buildAnnotatedString {
                append("We display a list of Categories on the screen, each category can have items and/or SubCategories. SubSections contain Items.\n" +
                        "\n" +
                        "Other UI elements are:")
            }
        )

        BulletPoints(
            listOf("CategoryHeader (Name of the section and count is displayed here)",
                    "SubCategoryHeader",
                    "InterCategorySpace",
                    "SeparatorView (A thin line between sub-categories / Items)")
        )

        Text("Jumping to the code:\n")



        CodeSnippetView(
            code
        )

        CodeSnippetView(
            codeTwo
        )

        CodeSnippetView(
            codeThree
        )

        Text(
            buildAnnotatedString {
                append("As you can see from the code, CategoryListView uses a single LazyColumn (In fact, in the actual implementation of full screen with other things on the top of the sections like search, store info etc, the entire screen is a single LazyColumn). We just call the Categories extension function for each of the Categories we need to render. These are not Composble functions which emit UI however.\n" +
                        "\n" +
                        "We define extension functions for different components, some of the components like Categories (LazyListScope.Categories) and SubCategories don’t directly emit any UI, these call other functions like SectionHeader and ListofItems that emit UI (only the functions using item/items function of LazyList are emitting UI).\n" +
                        "\n" +
                        "By defining the above constructs we were able to control the order in which the different components are rendered. If a category is collapsed, there will not be any items/subcategories rendered for that section. On expansion, all the content inside a category is rendered.\n" +
                        "\n" +
                        "This also makes sure that there is granular recycling of items as, everything from items, category separators, and lines between the items/subCategories are all immediate children of the LazyList.\n" +
                        "\n" +
                        "Another important bit it to handle the toggling of categories/subCategories or out-of-stock sections. This is achieved by having a SnapshotStateMap of category/subCategory ids and **StateFlow**. The map can be in the ViewModel of the Screen as well. Any user action to collapse or expand calls the toggle method that changes the state flow value for the category/subCategory. This change in state flow value triggers the rerender of a category/subCategory with requested items being toggled. ***isExpanded: (id: String) -> StateFlow?*** is a lambda that returns a StateFlow? to achieve this. You can initialise expandedSectionIdsMap to control what all sections need to be in the expanded state in the initial render and keep modifying the map as and when the user interacts.\n" +
                        "\n" +
                        "We needed similar expand and collapse functionality for the out-of-stock item section at the end. An exactly similar pattern is used to accomplish that.\n" +
                        "\n" +
                        "As you can from the code, what items/subCategories need to be rendered in which order and toggling of the categories/subCategories can be achieved easily with the above approach. It also takes care of performance as each small item in the LazyList can be reused as all of them are immediate children of the LazyList.\n" +
                        "\n")
            }
        )
    }
}

val code = """
                // LazyListScopeExtentions.kt

                fun LazyListScope.Categories(
                    categories: List<CategoryRenderData>,
                    isExpanded: (id: String) -> StateFlow<Boolean>?,
                    onToggle: (id: String) -> Unit,
                    isOOSExpanded: (id: String) -> StateFlow<Boolean>?,
                    onOOSToggle: (id: String) -> Unit,
                ) {
                    categories.forEach { menuCategoryRenderData ->
                        val hasSubSections = menuCategoryRenderData.subCategoryList.isNotEmpty()
                        val collapseAble = !hasSubSections
                        val isExpandedValue = isExpanded(menuCategoryRenderData.id)?.value ?: false
                        SectionHeader(name = "$ {menuCategoryRenderData.displayName} ($ {menuCategoryRenderData.totalItemsCount})",
                            id = menuCategoryRenderData.id,
                            isExpanded = isExpandedValue,
                            collapseAble = collapseAble,
                            onToggle = {
                                if (collapseAble) {
                                    onToggle(menuCategoryRenderData.id)
                                }
                            })
                        // Show the contents only if it is expanded or if it has sub sections
                        if (isExpandedValue || hasSubSections) {
                            if (hasSubSections) {
                                menuCategoryRenderData.subCategoryList.forEachIndexed { index, subCategoryRenderData ->
                                    SubCategories(
                                        menuSubCategoryRenderData = subCategoryRenderData,
                                        isExpanded = isExpanded(subCategoryRenderData.id),
                                        showSeparator = index != menuCategoryRenderData.subCategoryList.size - 1,
                                        onToggle = {
                                            onToggle(subCategoryRenderData.id)
                                        },
                                        showTopPadding = index != 0,
                                        onShowOOSToggle = onOOSToggle,
                                        isOOSExpanded = isOOSExpanded(subCategoryRenderData.id),
                                    )
                                }
                            } else {
                                if (menuCategoryRenderData.inStockItems.isNotEmpty()) {
                                    ListofItems(
                                        menuCategoryRenderData.inStockItems,
                                        menuCategoryRenderData.id
                                    )
                                }
                                // Display OOS items in a section, similar logic as above
                            }
                        }
                        InterCategorySpace(menuCategoryRenderData.id)
                    }
                }

                fun LazyListScope.SubCategories(
                    menuSubCategoryRenderData: SubCategoryRenderData,
                    isExpanded: StateFlow<Boolean>?,
                    showSeparator: Boolean,
                    showTopPadding: Boolean,
                    isOOSExpanded: StateFlow<Boolean>?,
                    onToggle: () -> Unit,
                    onShowOOSToggle: (id: String) -> Unit,
                ) {
                    val isExpandedValue = isExpanded?.value ?: false
                    val isOOSExpandedValue = isOOSExpanded?.value ?: false
                    SubSectionHeader(
                        "$ {menuSubCategoryRenderData.displayName} ($ {menuSubCategoryRenderData.totalItemsCount})",
                        menuSubCategoryRenderData.id,
                        isExpandedValue,
                        showTopPadding = showTopPadding,
                        onToggle = onToggle
                    )

                    if (isExpandedValue) {
                        ListofItems(
                            menuSubCategoryRenderData.inStockItems,
                            menuSubCategoryRenderData.id
                        )
                        // Display OOS items in a section, similar logic as above
                    }
                    if (showSeparator) {
                        SeparatorView(menuSubCategoryRenderData.id)
                    }
                }

                fun LazyListScope.ListofItems(
                    items: List<Item>,
                    sectionId: String
                ) {
                    items.forEachIndexed { index, item ->
                        item(key = item.id + sectionId, contentType = "Item") {
                            // Display actual item here
                            /*Item(
                                ...
                            )*/
                        }
                    }
                }

                fun LazyListScope.SeparatorView(id: String) {
                    item("SeparatorView$ id", contentType = "SeparatorView") {
                        Spacer(modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(Color.Gray)
                        )
                    }
                }

                fun LazyListScope.InterCategorySpace(id: String) {
                    item(key = "SectionSpace$ id", contentType = "InterCategorySpace") {
                        Spacer(modifier = Modifier
                            .height(8.dp)
                            .fillMaxWidth()
                            .background(Color.Gray)
                        )
                    }
                }

                fun LazyListScope.SectionHeader(name: String, id: String, isExpanded: Boolean, collapseAble: Boolean, onToggle: () -> Unit) {
                    item(key = "SectionHeader$ id", contentType = "SectionHeader") {
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .noRippleClickable { onToggle() },
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                modifier = Modifier.fillMaxWidth(0.85f),
                                text = name,
                                maxLines = 2
                            )
                            if (collapseAble) {
                                val rotationAngle = animateFloatAsState(if (isExpanded) 180f else 0f)
                                Icon(
                                    painter = painterResource(com.phonepe.app.address.R.drawable.ic_chevron_down),
                                    contentDescription = "arrow",
                                    modifier = Modifier
                                        .rotate(rotationAngle.value)
                                )
                            }
                        }
                    }
                }

                fun LazyListScope.SubSectionHeader(name: String, id: String, isExpanded: Boolean, showTopPadding: Boolean, onToggle: () -> Unit) {
                    item(key = "SubSectionHeader$ id", contentType = "SubSectionHeader") {
                        // Similar to SectionHeader with a lesser font weightage
                    }
                }
""".trimIndent()

private val codeTwo = """
    // MultiSectionListView.kt 

    @Composable
    fun CategoryListView(categories: List<CategoryRenderData> = listOf()) {

        // These maps should ideally be in View model
        val expandedSectionIdsMap: SnapshotStateMap<String, StateFlow<Boolean>> = remember {
            mutableStateMapOf<String, StateFlow<Boolean>>()
        }
        val expandedOOSSectionIdsMap: SnapshotStateMap<String, StateFlow<Boolean>> = remember {
            mutableStateMapOf<String, StateFlow<Boolean>>()
        }

        LazyColumn(content = {
            Categories(
                categories = categories,
                isExpanded = {
                    expandedSectionIdsMap[it]
                },
                onToggle = {
                    expandedSectionIdsMap[it] = MutableStateFlow(
                        expandedSectionIdsMap[it]?.value?.not() ?: true
                    )
                },
                isOOSExpanded = {
                    expandedOOSSectionIdsMap[it]
                },
                onOOSToggle = {
                    expandedOOSSectionIdsMap[it] = MutableStateFlow(
                        expandedOOSSectionIdsMap[it]?.value?.not() ?: true
                    )
                }

            )
        })
    }

""".trimIndent()

val codeThree = """
    // SampleRenderDataModels.kt 

    data class CategoryRenderData(
        val id: String,
        val displayName: String,
        val subCategoryList: List<SubCategoryRenderData>,
        val inStockItems: List<Item>,
        val oosItems: List<Item>,
        val totalItemsCount: Int?,
        val hasOnlyOutOfStockItems: Boolean = false
    )

    data class SubCategoryRenderData(
        val id: String,
        val displayName: String,
        val inStockItems: List<Item>,
        val oosItems: List<Item>,
        val totalItemsCount: Int?,
        val hasOnlyOutOfStockItems: Boolean = false
    )

    data class Item(
        val id: String
    )

""".trimIndent()
