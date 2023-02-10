pipeline {

    agent any

    stages {
        stage('Clean') {
            steps {
                echo 'Cleaning workspace'
                cleanWs()
            }
        }
        stage ('Clone') {
            steps {
                git branch: 'main', url: "https://github.com/LoPan455/ktor-sample.git"
            }
        }
        stage('Artifactory Configuration') {
            steps {
                rtServer(
                        id: 'ARTIFACTORY_SERVER',
                        url: "${params.SERVER_URL}",
                        credentialsId: CREDENTIALS,
                        timeout: 20
                )
                rtGradleResolver(
                        id: "${params.GRADLE_RESOLVER}",
                        serverId: ARTIFACTORY_SERVER,
                        repo: "${params.PROJECT_VIRTUAL_REPO}",
                )
                rtGradleDeployer(
                        id: "${params.GRADLE_DEPLOYER}",
                        serverId: ARTIFACTORY_SERVER,
                        repo: "${params.PROJECT_VIRTUAL_REPO}",
                        properties: ['foo=bar', 'fizz=buzz'],
                        publications: ["mavenJava", "ivyJava"]
                )
            }
        }
        stage('Config Build Info') {
            steps {
                rtBuildInfo(
                        captureEnv: true,
                        includeEnvPatterns: ["*"],
                        excludeEnvPatterns: ["DONT_COLLECT*"]
                )
            }
        }
        stage('Exec Gradle') {
            steps {
                rtGradleRun(
                        usesPlugin: true, // Artifactory plugin already defined in build script
                        useWrapper: true,
                        rootDir: "./",
                        tasks: 'clean artifactoryPublish',
                        deployerId: "${params.GRADLE_DEPLOYER}",
                        resolverId: "${params.GRADLE_RESOLVER}"
                )
            }
        }
        stage ('Publish build info') {
            steps {
                echo 'Publishing build info'
                rtBuildInfo (
                        buildName: "SomeBuildName",
                        captureEnv: true
                )
                rtPublishBuildInfo (
                        serverId: ARTIFACTORY_SERVER
                )
            }
        }
    }
}