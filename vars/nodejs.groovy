def call() {
    node {
        // git branch: 'main', url: "https://github.com/hidayathms/${COMPONENT}.git"
        common.lintchecks()
        env.ARGS="-Dsonar.sources=."
        common.sonarChecks()
        common.testcases()
        env.NEXUS_URL="172.31.45.41"
        tool = NPM_NODEJS: 'nodejs'
        // env.NPM_HOME = "${tool)"
        if(env.TAG_NAME != null) {
            common.artifacts()
        }
    }        
}


/* Uncomment this to run it over Declarative approach */

// def call() {
//   pipeline {  
//     agent any
//     tools {
//         nodejs 'NPM_NODEJS' 
//     }
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
//                 sh "echo Sonar scan is good"
//             //     sh "curl https://gitlab.com/thecloudcareers/opensource/-/raw/master/lab-tools/sonar-scanner/quality-gate >gates.sh"
//             //     sh "bash gates.sh admin pass ${SONAR_URL} ${COMPONENT}"                }
//             }   
//         }
//         // stage ('Test Cases'){
//         // parallel {
//                 stage('Unit Testing') {
//                      steps {
//                         sh "env"
//                         sh "echo Unit Testing In Progress"
//                         // sh "npm test"
//                         sh "echo Unit Testing Completed"
//                     }
//                 }
//                 stage('Integration Testing') {
//                     steps {
//                        sh "echo Integration Testing In Progres"
//                     //    sh "npm verify"
//                        sh "echo Integration Testing Completed"
//                     }
//                 }
//                 stage('Functional Testing') {
//                     steps {
//                        sh "echo Functional Testing in progress"
//                     //    sh "npm function xxx"
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
//                 stage('Prepare Artifact') {    
//                     when { 
//                         expression {env.TAG_NAME != null}
//                         expression {env.UPLOAD_STATUS == ""} 
//                     }
//                     steps {
//                        sh "npm install"
//                        sh "ls -ltr"
//                        sh "zip ${COMPONENT}-${TAG_NAME}.zip nodemodules server.js"
//                        sh "ls -ltr"
                   
//                     }
//                 }
//                 stage('Uploding the Artifacts') {       
//                     when { 
//                         expression {env.TAG_NAME != null} 
//                         expression {env.UPLOAD_STATUS == ""} 
//                     }
//                     steps {
//                        sh "echo Uploading the Artifacts in progress"
//                        sh "curl -f -v -u ${NEXUS_CRED_USR}:${NEXUS_CRED_PSW} --upload-file ${COMPONENT}-${TAG_NAME}.zip http://172.31.45.41:8081/repository/${COMPONENT}/${COMPONENT}-${TAG_NAME}.zip"
//                     }
//                 }
//             }
//         }
        
//     }
// //   }

// // }



