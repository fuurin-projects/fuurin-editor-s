package fuurineditor.service.data.event

data class Event(
    var name: String,
    var name1: String = "",
    val nodeList: List<EventNode> = arrayListOf()
) {
}