package ba.klika.tasks

import groovyx.net.http.HttpResponseDecorator
import groovyx.net.http.HttpResponseException
import groovyx.net.http.RESTClient
import org.gradle.api.DefaultTask

abstract class UploadXmlReportBaseTask extends DefaultTask {

    protected RESTClient getRestClient() {
        def url = 'https://xray.cloud.xpand-it.com'
        RESTClient restClient = new RESTClient(url)
        return restClient
    }

    protected HttpResponseDecorator catchHttpExceptions(Closure callClosure) {
        try {
            return callClosure.call()
        } catch (HttpResponseException responseException) {
            return responseException.response
        }
    }
}
