def call() {
    node {
        git branch: 'main', url: "https://github.com/hidayathms/${COMPONENT}.git"
        common.lintchecks()
        env.ARGS="-Dsonar.sources=."
        common.sonarChecks()
        common.testcases()
        if(env.TAG_NAME != null) {
            common.artifacts()
        }
    }
}


/* Uncomment this to run it over Declarative approach */

// def call() {
//   pipeline { 
//     agent any
//     environment {
//        SONAR_CRED=credentials('SONAR_CRED')
//        NEXUS_CRED=credentials('NEXUS_CRED')
//        SONAR_URL="172.31.41.5"
//        NEXUS_URL="172.31.45.41"
//     }
//     stages {
//         stage('Lint Checks') {
//             steps {
//                 script {
//                     common.lintchecks()
//                 }
//             }
//         }
//         stage('Static Code Analysis') {
//             steps {
//                 script {
//                     env.ARGS="-Dsonar.sources=."
//                     common.sonarChecks()
//                 }
//             }
//         }
//         stage('Get the sonar results') {
//             steps {
//                 sh "curl https://gitlab.com/thecloudcareers/opensource/-/raw/master/lab-tools/sonar-scanner/quality-gate >gates.sh"
//                 sh "bash gates.sh admin pass ${SONAR_URL} ${COMPONENT}"                }
//             }
//         stage ('Test Cases'){
//         parallel {
//                 stage('Unit Testing') {
//                      steps {
//                         sh "echo Unit Testing In Progress"
//                         // sh "pip test"
//                         sh "echo Unit Testing Completed"
//                     }
//                 }
//                 stage('Integration Testing') {
//                     steps {
//                        sh "echo Integration Testing In Progres"
//                     //    sh "pip verify"
//                        sh "echo Integration Testing Completed"
//                     }
//                 }
//                 stage('Functional Testing') {
//                     steps {
//                        sh "echo Functional Testing in progress"
//                     //    sh "pip function xxx"
//                        sh "echo Functional Testing completed"
//                     }
//                 }
//                 stage('Checking Arifacts availibility on NEXUS repo') {    
//                     when { expression { env.TAG_NAME != null } }
//                     steps {
//                         script {
//                           env.UPLOAD_STATUS=sh(returnStdout: true, script: "curl http://${NEXUS_URL}:8081/service/rest/repository/browse/${COMPONENT}/ | grep ${COMPONENT}-${TAG_NAME}.zip || true") 
                               
//                         }
//                     }
//                 }
//                 stage('Prepare Artifact') {  // Runs only when you run this job from tag and from branches it should run
//                     when { 
//                         expression {env.TAG_NAME != null}
//                         expression {env.UPLOAD_STATUS == ""} 
//                         }
//                     steps {
//                        sh "echo Prparing Artifacts in progress"
//                        sh "echo Artifact build  completed"
//                     }
//                 }
//                 stage('Uploding the Artifacts') {       // Runs only when you run this job from tag and from branches it should run
//                     when { 
//                         expression {env.TAG_NAME != null}
//                         expression {env.UPLOAD_STATUS == ""} 
//                         }
//                     steps {
//                        sh "echo Uploading the Artifacts in progress"
//                        sh "echo Artifacts Uload completed"
//                     }
//                 }
//             }
//         }
        
//     }
//   }

// }