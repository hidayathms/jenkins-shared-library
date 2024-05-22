# jenkins-shared-library

This is a repository to host all the common patterns what arise during the CI/CD of roboshop components development:
https://www.jenkins.io/doc/book/pipeline/shared-libraries/
vars/ is going to host all the common stages 


## Why shared libararies in Jenkins?
As Pipeline is adopted for more and more projects in an organization, common patterns are likely to emerge. Oftentimes it is useful to share parts of Pipelines between various projects to reduce redundancies and keep code "DRY" [1].

Pipeline has support for creating "Shared Libraries" which can be defined in external source control repositories and loaded into existing Pipelines.

