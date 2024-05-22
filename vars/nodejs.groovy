
def lintchecks() {
    sh "echo ***** Starting Style checks *******"
    sh "npm install jslint"
    sh "ls -ltr node_modules/jslint/bin"
    sh "/node_modules/jslint/bin/jslint.js server.js || true"
    sh "echo **** Starting Style checks *******"
}