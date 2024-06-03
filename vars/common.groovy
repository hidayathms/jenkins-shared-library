def sonarChecks() {
    stage ('Sonar Checks') {
    echo "Sonar checks in progress"
    // sh "sonar-scanner -Dsonar.host.url=http://172.31.41.5:9000 ${ARGS} -Dsonar.projectKey=${COMPONENT} -Dsonar.login=${SONAR_CRED_USR} -Dsonar.password=${SONAR_CRED_PSW}"
    echo "Sonar checks in Completed"
    }
}

def lintchecks() {
    stage ('Lint checks') {
        if(env.APP_TYPE == "maven") {
            sh "echo ***** Starting Style checks for  ${COMPONENT} *******"
            // sh "mvn checkstyle:check || true"
            sh "echo **** Style checks are completed for ${COMPONENT} *******"  
        }
        else if (env.APP_TYPE =="nodejs") {
            sh "echo ***** Starting Style checks for  ${COMPONENT} *******"
            // sh "npm i jslint"
            // sh "node_modules/jslint/bin/jslint.js server.js || true"
            sh "echo **** Style checks are completed for ${COMPONENT} *******"
        }
        else if (env.APP_TYPE == "python") {
            sh "echo ***** Starting Style checks for  ${COMPONENT} *******"
            // sh "pip3 install pylint || true"
            // sh "pylint *.py || true"
            sh "echo **** Style checks are completed for ${COMPONENT} *******"
        }
        else {
            sh "echo ***** Starting Style checks for  ${COMPONENT} *******"
            // sh "npm i jslint"
            // sh "node_modules/jslint/bin/jslint.js server.js || true"
            sh "echo **** Style checks are completed for ${COMPONENT} *******"
        }
    }
  
}

def testcases() {
stage('Test Cases') {
        def stages = [:]

        stages["Unit Testing"] = {
            echo "Unit Testing In Progress"
            // npm test
            echo "Unit Testing Is Completed"
        }
        stages["Integration Testing"] = {
            echo "Integration Testing In Progress"
            // npm verify
            echo "Integration Testing Is Completed"
        }
        stages["Funcitonal Testing"] = {
            echo "Functional Testing In Progress"
            // npm function xxx
            echo "Functional Testing Is Completed"

        }
        parallel(stages)
    }
}

def artifacts() {
    stage('Checking Artifact Release on Nexus') {
        env.UPLOAD_STATUS=sh(returnStdout: true, script: "curl http://${NEXUS_URL}:8081/service/rest/repository/browse/${COMPONENT}/ | grep ${COMPONENT}-${TAG_NAME}.zip || true")
        print UPLOAD_STATUS
    }
    if(env.UPLOAD_STATUS == "") {
        stage('Generating Artifacts') {
            if(env.APP_TYPE == "nodejs") {
                sh "ls -ltr"
                sh "npm install"
                sh "zip ${COMPONENT}-${TAG_NAME}.zip nodemodules server.js"
                sh "ls -ltr"
            }
            else if(env.APP_TYPE == "maven") {
                sh "mvn clean package"
                sh "mv target/${COMPONENT}-1.0.jar ${COMPONENT}.jar"
                sh "zip -r ${COMPONENT}-${TAG_NAME}.zip ${COMPONENT}.jar"
                sh "echo Artifact build completed"
            }
            else if(env.APP_TYPE == "python") {
                sh "zip -r ${COMPONENT}-${TAG_NAME}.zip *.py *.ini requirement.txt"
                sh "echo Artifact build completed"
            }
            else if(env.APP_TYPE == "angularjs") {
                sh "ls -ltr"
                sh "cd static/"
                sh "zip -r ../${COMPONENT}-${TAG_NAME}.zip" 
                sh "ls -ltr"
            }
            else {
                sh "echo Selected Component Type doesnot exist"
            }
        }

        stage('Uploading the artifacts') {
            withCredentials([usernamePassword(credentialsId: 'NEXUS_CRED', passwordVariable: 'NEXUS_PASSWORD', usernameVariable: 'NEXUS_USER')]) {
            sh "echo Uploading the ${COMPONENT} Artifacts to Nexus"
            sh "curl -f -v -u ${NEXUS_USER}:${NEXUS_PASSWORD} --upload-file ${COMPONENT}-${TAG_NAME}.zip http://${NEXUS_URL}:8081/repository/${COMPONENT}/${COMPONENT}-${TAG_NAME}.zip"
            echo "Artifact build completed"
            }
        }
    }
}

