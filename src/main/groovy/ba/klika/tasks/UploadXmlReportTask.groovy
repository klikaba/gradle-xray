package ba.klika.tasks

import groovyx.net.http.ContentType
import groovyx.net.http.RESTClient
import org.gradle.api.GradleException
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

class UploadXmlReportTask extends UploadXmlReportBaseTask {

    @Input
    final Property<String> testExecution = project.objects.property(String)

    @Input
    final Property<String> filePath = project.objects.property(String)

    @TaskAction
    def upload() {

        def clientId = project.xrayextension.clientId
        def clientSecret = project.xrayextension.clientSecret

        if (!testExecution.get()) {
            throw new GradleException("testExecution field must not be empty!")
        }
        if (!filePath.get()) {
            throw new GradleException("filePath field must not be empty!")
        }

        if(clientId == null){
            throw new GradleException("clientId field must not be empty!")
        }

        if(clientSecret == null){
            throw new GradleException("clientSecret field must not be empty!")
        }

        project.logger.lifecycle("Authenticating Xray...")
        RESTClient client = getRestClient()
        def callResponse = catchHttpExceptions {
            client.post(
                    path: "/api/v1/authenticate",
                    body:
                            [
                                    client_id    : clientId,
                                    client_secret: clientSecret
                            ],
                    contentType: ContentType.JSON,
                    requestContentType: ContentType.JSON)
        }

        if (callResponse.status == 200) {
            def token = callResponse.getData()
            uploadXML(token, testExecution.get(), filePath.get())
        } else {
            throw new GradleException("Error trying to authenticate. Status code " + callResponse.status + '\n' + callResponse.getData().toString())
        }
    }

    def uploadXML(String token, String testExecution, String filePath) {
        project.logger.lifecycle("Uploading XML...")
        RESTClient client = getRestClient()
        def callResponse = catchHttpExceptions {
            client.post(
                    requestContentType: ContentType.XML,
                    path: '/api/v1/import/execution/testng',
                    query: [testExecKey: testExecution],
                    body: new File(filePath).text,
                    headers: [
                            Authorization: "Bearer " + token,
                    ],
            )
        }

        if (callResponse.status == 200) {
            project.logger.lifecycle("XML Upload Successful")
        } else {
            throw new GradleException("Error trying to upload XML. Status code " + callResponse.status + '\n' + callResponse.getData().toString())
        }
    }
}
