package fuurineditor.service.data.event

data class Event(
    val name: String,
    val nodeList: List<EventNode> = arrayListOf()
) {
}