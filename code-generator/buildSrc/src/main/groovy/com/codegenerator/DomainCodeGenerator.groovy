package com.codegenerator;

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class DomainCodeGenerator extends DefaultTask {
    private final String SYSTEM_PROPERTY_NAME = "name"
    private final String TEMPLATE_FILE_PATH = 'templates.list'

    @TaskAction
    def action() {
        def domainName = System.getProperty(SYSTEM_PROPERTY_NAME)
        if(domainName==null || domainName.isEmpty()){
            System.err.println("system parameter 'name' should be specified")
            return
        }

        processTemplate(domainName)
    }

    private void processTemplate(String domainName) {
        def templates = readTemplates(TEMPLATE_FILE_PATH)
        def binding = [
                nameLowCase: domainName.toLowerCase(),
                nameReal   : domainName,
                nameCamelCase: domainName.charAt(0).toLowerCase().toString() + domainName.substring(1),
                splitName : splitNameWithLowerCase(domainName)
        ]
        templates.each {
            if(!it){
                return
            }
            def templateFileName = it.substring(0, it.indexOf("=")).trim()
            try{
                def content = generateFileFromTemplate(binding, templateFileName)
                def destinationPath = buildPath(binding, it.substring(it.indexOf("=") + 1).trim())
                createFileWithText(destinationPath, content)
            }catch(all){
                println(">>> can't generate file from template "+templateFileName)
                println(">>> Exception: "+all)
            }
        }
    }

    def buildPath(binding, templateString) {
        def engine = new groovy.text.GStringTemplateEngine()
        return engine.createTemplate(templateString).make(binding)
    }

    def createFileWithText(relativePath, content) {
        File outputFile = new File(project.getProjectDir().getPath() + "/" + relativePath)
        outputFile.parentFile.mkdirs()
        outputFile.createNewFile()
        outputFile.text =  content
        println(relativePath)
    }

    def generateFileFromTemplate(binding, fileName){
        def engine = new groovy.text.GStringTemplateEngine()
        return engine.createTemplate(getClass().classLoader.getResourceAsStream(fileName).text).make(binding)
    }

    def readTemplates(String fileName) {
        def textFromFile = getClass().classLoader.getResourceAsStream(fileName).text
        textFromFile.split("\n")
    }

    def splitNameWithLowerCase(realValue){
        def isFirst = true
        def returnValue = ""
        realValue.each{
            if(it.capitalize()==it){
                if(isFirst){
                    isFirst = false
                    returnValue += it.toLowerCase()
                } else{
                    returnValue += "-" + it.toLowerCase()
                }
            }else{
                returnValue += it
            }
        }
        return returnValue
    }

}
