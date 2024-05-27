def sonarChecks() {
    sh "sonar-scanner -Dsonar.host.url=http://172.31.41.5:9000 -Dsonar.sources=. -Dsonar.projectKey=${COMPONENT} -Dsonar.login=admin -Dsonar.password=pass"
    }