package ba.klika

import ba.klika.tasks.UploadXmlReportTask
import org.gradle.api.Plugin
import org.gradle.api.Project

class XrayExtension {
    String clientId
    String clientSecret
}

class XrayUploadPlugin implements Plugin<Project>{

    @Override
    void apply(Project project) {

        XrayExtension extension = project.extensions.create('xrayextension', XrayExtension)

        project.getTasks().register("uploadxmlreport", UploadXmlReportTask) { t ->
        }
    }
}
