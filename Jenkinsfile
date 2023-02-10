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
//                rtServer(
//                        serverId: 'ARTIFACTORY_SERVER',
//                        url: 'SERVER_URL',
//                        credentialsId: CREDENTIALS,
//                        timeout: 20
//                )
                rtGradleResolver(
                        id: "GRADLE_RESOLVER",
                        serverId: ${params.ARTIFACTORY_SERVER},
                        repo: PROJECT_VIRTUAL_REPO
                )
                rtGradleDeployer(
                        id: "GRADLE_DEPLOYER",
                        serverId: ${params.ARTIFACTORY_SERVER},
                        repo: PROJECT_VIRTUAL_REPO,
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
                        deployerId: "GRADLE_DEPLOYER",
                        resolverId: "GRADLE_RESOLVER"
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
                        serverId: $params.ARTIFACTORY_SERVER
                )
            }
        }
    }
}