package cat.jai.innertube.entities.domain.browse

open class DomainGenericBrowse<T> {
    open val items: List<T> = emptyList()
    open val continuation: String? = null
}