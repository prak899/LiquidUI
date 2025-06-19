package com.example.liquidui.model

data class UIModel(
    val type: String, // "vertical_stack", "banner", "label", "label_with_action", "grid", "horizontal_list", "carousel", etc.

    // Common properties
    val padding: Int = 0,
    val columns: Int? = null, // for grid
    val children: List<UIModel>? = null, // for stack, grid, horizontal list, etc.

    // For image-related items like banner or carousel
    val imageUrl: String? = null,

    // For icon buttons and grid items
    val iconUrl: String? = null,

    // Text properties
    val title: String? = null,
    val subtitle: String? = null,
    val text: String? = null, // For label
    val textSize: Int? = null,
    val textColor: String? = null,

    // For label with action
    val actionText: String? = null,

    // For toolbar (if you want to extend)
    val backgroundColor: String? = null,
    val titleColor: String? = null,

    // For carousel
    val autoScroll: Boolean? = false,

    // Action object
    val action: ActionModel? = null
)


