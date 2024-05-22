
def nodejs() {
                sh "echo ***** Starting Style checks *******"
                sh "npm i jslint"
                sh "ls -ltr node_modules/jslint/bin"
                sh "/home/centos/node_modules/jslint/bin/jslint.js server.js || true"
                sh "echo **** Starting Style checks *******"
}