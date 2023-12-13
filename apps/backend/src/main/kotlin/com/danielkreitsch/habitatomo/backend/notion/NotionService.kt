package com.danielkreitsch.habitatomo.backend.notion

import org.jraf.klibnotion.client.Authentication
import org.jraf.klibnotion.client.ClientConfiguration
import org.jraf.klibnotion.client.NotionClient
import org.jraf.klibnotion.model.page.Page
import org.springframework.stereotype.Service

@Service
class NotionService(private val notionConfig: NotionConfig) {
  val notion =
      NotionClient.newInstance(ClientConfiguration(Authentication(this.notionConfig.accessToken)))

  suspend fun getPages(databaseId: String): List<Page> {
    val pages = ArrayList<Page>()

    // First page
    var query = this.notion.databases.queryDatabase(databaseId)
    pages.addAll(query.results)

    // Other pages
    while (query.nextPagination != null) {
      query = this.notion.databases.queryDatabase(databaseId, null, null, query.nextPagination!!)
      pages.addAll(query.results)
    }

    return pages
  }

  suspend fun getPage(id: String): Page {
    return this.notion.pages.getPage(id)
  }
}
