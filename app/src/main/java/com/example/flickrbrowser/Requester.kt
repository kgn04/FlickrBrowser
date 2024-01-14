import android.os.AsyncTask
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import org.xml.sax.InputSource
import java.io.StringReader
import javax.xml.parsers.DocumentBuilderFactory

class Requester(
    private val tags: String,
    private val tagmode: String,
    private val lang: String
) {

    fun getResult(): String {
        val endpoint = "https://www.flickr.com/services/feeds/photos_public.gne"
        val params = mapOf(
            "tags" to tags,
            "tagmode" to tagmode,
            //"lang" to lang
        )

        val url = StringBuilder(endpoint)
        url.append("?")
        for ((key, value) in params) {
            url.append("$key=$value&")
        }

        // Remove the last "&" from the URL
        url.setLength(url.length - 1)

        val result = FetchDataAsync().execute(url.toString()).get()

        return result ?: "Error fetching data"
    }

    private class FetchDataAsync : AsyncTask<String, Void, String>() {
        @Deprecated("Deprecated in Java")
        override fun doInBackground(vararg params: String): String {
            val url = URL(params[0])
            val connection: HttpURLConnection = url.openConnection() as HttpURLConnection

            try {
                val inputStream = BufferedReader(InputStreamReader(connection.inputStream))
                val result = StringBuilder()
                var line: String?

                while (inputStream.readLine().also { line = it } != null) {
                    result.append(line)
                }

                return result.toString()
            } finally {
                connection.disconnect()
            }
        }
    }

    fun getImagesURLs(): List<String> {
        val result = getResult()
        val document = parseXml(result)

        val urls = mutableListOf<String>()

        if (document != null) {
            val nodeList: NodeList = document.getElementsByTagName("link")

            for (i in 0 until nodeList.length) {
                val node: Node = nodeList.item(i)

                if (node.nodeType == Node.ELEMENT_NODE) {
                    val element = node as Element

                    if (element.getAttribute("rel") == "enclosure") {
                        val imageUrl = element.getAttribute("href")
                        urls.add(imageUrl)
                    }
                }
            }
        }

        return urls
    }

    private fun parseXml(xmlString: String): Document? {
        val factory = DocumentBuilderFactory.newInstance()
        val builder = factory.newDocumentBuilder()
        return builder.parse(InputSource(StringReader(xmlString)))
    }
}
