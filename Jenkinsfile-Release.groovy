pipeline {
    agent {
        dockerfile {
            filename 'docker/dockerfile-java'
            additionalBuildArgs '--build-arg JENKINS_USER_ID=`id -u jenkins` --build-arg JENKINS_GROUP_ID=`id -g jenkins`'
        }
    }

    parameters {
        string(name: 'TAG', defaultValue: '1.0.0', description: 'Tag')
        gitParameter name: 'BRANCH', branchFilter: 'origin/(.*)', defaultValue: 'master', type: 'PT_BRANCH'
    }

    environment {
        S3_REPO_ID='maven-repo.opendatahub.bz.it'
        S3_REPO_USERNAME=credentials('s3_repo_username')
        S3_REPO_PASSWORD=credentials('s3_repo_password')
    }

    stages {
        stage('Configure') {
            steps {
                sh 'sed -i -e "s/<\\/settings>$//g\" ~/.m2/settings.xml'
                sh 'echo "    <servers>" >> ~/.m2/settings.xml'
                sh 'echo "        <server>" >> ~/.m2/settings.xml'
                sh 'echo "            <id>${S3_REPO_ID}</id>" >> ~/.m2/settings.xml'
                sh 'echo "            <username>${S3_REPO_USERNAME}</username>" >> ~/.m2/settings.xml'
                sh 'echo "            <password>${S3_REPO_PASSWORD}</password>" >> ~/.m2/settings.xml'
                sh 'echo "        </server>" >> ~/.m2/settings.xml'
                sh 'echo "    </servers>" >> ~/.m2/settings.xml'
                sh 'echo "</settings>" >> ~/.m2/settings.xml'

                sh 'xmlstarlet ed -P -L -N pom="http://maven.apache.org/POM/4.0.0" -u "/pom:project/pom:version" -v $TAG pom.xml'
                sh 'xmlstarlet ed -P -L -N pom="http://maven.apache.org/POM/4.0.0" -u "/pom:project/pom:dependencies/pom:dependency[pom:groupId=\'it.bz.opendatahub.alpinebits\']/pom:version" -v $TAG pom.xml'
                
                sh 'xmlstarlet ed -P -L -N pom="http://maven.apache.org/POM/4.0.0" -u "/pom:project/pom:version" -v $TAG alpinebits-common/pom.xml'
                sh 'xmlstarlet ed -P -L -N pom="http://maven.apache.org/POM/4.0.0" -u "/pom:project/pom:parent/pom:version" -v $TAG alpinebits-common/pom.xml'
                sh 'xmlstarlet ed -P -L -N pom="http://maven.apache.org/POM/4.0.0" -u "/pom:project/pom:version" -v $TAG alpinebits-common/api/pom.xml'
                sh 'xmlstarlet ed -P -L -N pom="http://maven.apache.org/POM/4.0.0" -u "/pom:project/pom:parent/pom:version" -v $TAG alpinebits-common/api/pom.xml'
                sh 'xmlstarlet ed -P -L -N pom="http://maven.apache.org/POM/4.0.0" -u "/pom:project/pom:version" -v $TAG alpinebits-common/utils/pom.xml'
                sh 'xmlstarlet ed -P -L -N pom="http://maven.apache.org/POM/4.0.0" -u "/pom:project/pom:parent/pom:version" -v $TAG alpinebits-common/utils/pom.xml'

                sh 'xmlstarlet ed -P -L -N pom="http://maven.apache.org/POM/4.0.0" -u "/pom:project/pom:version" -v $TAG alpinebits-db/pom.xml'
                sh 'xmlstarlet ed -P -L -N pom="http://maven.apache.org/POM/4.0.0" -u "/pom:project/pom:parent/pom:version" -v $TAG alpinebits-db/pom.xml'
                sh 'xmlstarlet ed -P -L -N pom="http://maven.apache.org/POM/4.0.0" -u "/pom:project/pom:version" -v $TAG alpinebits-db/api/pom.xml'
                sh 'xmlstarlet ed -P -L -N pom="http://maven.apache.org/POM/4.0.0" -u "/pom:project/pom:parent/pom:version" -v $TAG alpinebits-db/api/pom.xml'
                sh 'xmlstarlet ed -P -L -N pom="http://maven.apache.org/POM/4.0.0" -u "/pom:project/pom:version" -v $TAG alpinebits-db/impl/pom.xml'
                sh 'xmlstarlet ed -P -L -N pom="http://maven.apache.org/POM/4.0.0" -u "/pom:project/pom:parent/pom:version" -v $TAG alpinebits-db/impl/pom.xml'

                sh 'xmlstarlet ed -P -L -N pom="http://maven.apache.org/POM/4.0.0" -u "/pom:project/pom:version" -v $TAG alpinebits-housekeeping/pom.xml'
                sh 'xmlstarlet ed -P -L -N pom="http://maven.apache.org/POM/4.0.0" -u "/pom:project/pom:parent/pom:version" -v $TAG alpinebits-housekeeping/pom.xml'
                sh 'xmlstarlet ed -P -L -N pom="http://maven.apache.org/POM/4.0.0" -u "/pom:project/pom:version" -v $TAG alpinebits-housekeeping/api/pom.xml'
                sh 'xmlstarlet ed -P -L -N pom="http://maven.apache.org/POM/4.0.0" -u "/pom:project/pom:parent/pom:version" -v $TAG alpinebits-housekeeping/api/pom.xml'
                sh 'xmlstarlet ed -P -L -N pom="http://maven.apache.org/POM/4.0.0" -u "/pom:project/pom:version" -v $TAG alpinebits-housekeeping/impl/pom.xml'
                sh 'xmlstarlet ed -P -L -N pom="http://maven.apache.org/POM/4.0.0" -u "/pom:project/pom:parent/pom:version" -v $TAG alpinebits-housekeeping/impl/pom.xml'

                sh 'xmlstarlet ed -P -L -N pom="http://maven.apache.org/POM/4.0.0" -u "/pom:project/pom:version" -v $TAG alpinebits-mapping/pom.xml'
                sh 'xmlstarlet ed -P -L -N pom="http://maven.apache.org/POM/4.0.0" -u "/pom:project/pom:parent/pom:version" -v $TAG alpinebits-mapping/pom.xml'
                sh 'xmlstarlet ed -P -L -N pom="http://maven.apache.org/POM/4.0.0" -u "/pom:project/pom:version" -v $TAG alpinebits-mapping/api/pom.xml'
                sh 'xmlstarlet ed -P -L -N pom="http://maven.apache.org/POM/4.0.0" -u "/pom:project/pom:parent/pom:version" -v $TAG alpinebits-mapping/api/pom.xml'
                sh 'xmlstarlet ed -P -L -N pom="http://maven.apache.org/POM/4.0.0" -u "/pom:project/pom:version" -v $TAG alpinebits-mapping/impl/pom.xml'
                sh 'xmlstarlet ed -P -L -N pom="http://maven.apache.org/POM/4.0.0" -u "/pom:project/pom:parent/pom:version" -v $TAG alpinebits-mapping/impl/pom.xml'

                sh 'xmlstarlet ed -P -L -N pom="http://maven.apache.org/POM/4.0.0" -u "/pom:project/pom:version" -v $TAG alpinebits-middleware/pom.xml'
                sh 'xmlstarlet ed -P -L -N pom="http://maven.apache.org/POM/4.0.0" -u "/pom:project/pom:parent/pom:version" -v $TAG alpinebits-middleware/pom.xml'
                sh 'xmlstarlet ed -P -L -N pom="http://maven.apache.org/POM/4.0.0" -u "/pom:project/pom:version" -v $TAG alpinebits-middleware/api/pom.xml'
                sh 'xmlstarlet ed -P -L -N pom="http://maven.apache.org/POM/4.0.0" -u "/pom:project/pom:parent/pom:version" -v $TAG alpinebits-middleware/api/pom.xml'
                sh 'xmlstarlet ed -P -L -N pom="http://maven.apache.org/POM/4.0.0" -u "/pom:project/pom:version" -v $TAG alpinebits-middleware/impl/pom.xml'
                sh 'xmlstarlet ed -P -L -N pom="http://maven.apache.org/POM/4.0.0" -u "/pom:project/pom:parent/pom:version" -v $TAG alpinebits-middleware/impl/pom.xml'

                sh 'xmlstarlet ed -P -L -N pom="http://maven.apache.org/POM/4.0.0" -u "/pom:project/pom:version" -v $TAG alpinebits-routing/pom.xml'
                sh 'xmlstarlet ed -P -L -N pom="http://maven.apache.org/POM/4.0.0" -u "/pom:project/pom:parent/pom:version" -v $TAG alpinebits-routing/pom.xml'
                sh 'xmlstarlet ed -P -L -N pom="http://maven.apache.org/POM/4.0.0" -u "/pom:project/pom:version" -v $TAG alpinebits-routing/api/pom.xml'
                sh 'xmlstarlet ed -P -L -N pom="http://maven.apache.org/POM/4.0.0" -u "/pom:project/pom:parent/pom:version" -v $TAG alpinebits-routing/api/pom.xml'
                sh 'xmlstarlet ed -P -L -N pom="http://maven.apache.org/POM/4.0.0" -u "/pom:project/pom:version" -v $TAG alpinebits-routing/impl/pom.xml'
                sh 'xmlstarlet ed -P -L -N pom="http://maven.apache.org/POM/4.0.0" -u "/pom:project/pom:parent/pom:version" -v $TAG alpinebits-routing/impl/pom.xml'

                sh 'xmlstarlet ed -P -L -N pom="http://maven.apache.org/POM/4.0.0" -u "/pom:project/pom:version" -v $TAG alpinebits-servlet/pom.xml'
                sh 'xmlstarlet ed -P -L -N pom="http://maven.apache.org/POM/4.0.0" -u "/pom:project/pom:parent/pom:version" -v $TAG alpinebits-servlet/pom.xml'
                sh 'xmlstarlet ed -P -L -N pom="http://maven.apache.org/POM/4.0.0" -u "/pom:project/pom:version" -v $TAG alpinebits-servlet/api/pom.xml'
                sh 'xmlstarlet ed -P -L -N pom="http://maven.apache.org/POM/4.0.0" -u "/pom:project/pom:parent/pom:version" -v $TAG alpinebits-servlet/api/pom.xml'
                sh 'xmlstarlet ed -P -L -N pom="http://maven.apache.org/POM/4.0.0" -u "/pom:project/pom:version" -v $TAG alpinebits-servlet/impl/pom.xml'
                sh 'xmlstarlet ed -P -L -N pom="http://maven.apache.org/POM/4.0.0" -u "/pom:project/pom:parent/pom:version" -v $TAG alpinebits-servlet/impl/pom.xml'

                sh 'xmlstarlet ed -P -L -N pom="http://maven.apache.org/POM/4.0.0" -u "/pom:project/pom:version" -v $TAG alpinebits-xml/pom.xml'
                sh 'xmlstarlet ed -P -L -N pom="http://maven.apache.org/POM/4.0.0" -u "/pom:project/pom:parent/pom:version" -v $TAG alpinebits-xml/pom.xml'
                sh 'xmlstarlet ed -P -L -N pom="http://maven.apache.org/POM/4.0.0" -u "/pom:project/pom:version" -v $TAG alpinebits-xml/api/pom.xml'
                sh 'xmlstarlet ed -P -L -N pom="http://maven.apache.org/POM/4.0.0" -u "/pom:project/pom:parent/pom:version" -v $TAG alpinebits-xml/api/pom.xml'
                sh 'xmlstarlet ed -P -L -N pom="http://maven.apache.org/POM/4.0.0" -u "/pom:project/pom:version" -v $TAG alpinebits-xml/impl/pom.xml'
                sh 'xmlstarlet ed -P -L -N pom="http://maven.apache.org/POM/4.0.0" -u "/pom:project/pom:parent/pom:version" -v $TAG alpinebits-xml/impl/pom.xml'

                sh 'xmlstarlet ed -P -L -N pom="http://maven.apache.org/POM/4.0.0" -u "/pom:project/pom:version" -v $TAG ota-extension/pom.xml'
                sh 'xmlstarlet ed -P -L -N pom="http://maven.apache.org/POM/4.0.0" -u "/pom:project/pom:parent/pom:version" -v $TAG ota-extension/pom.xml'
                sh 'xmlstarlet ed -P -L -N pom="http://maven.apache.org/POM/4.0.0" -u "/pom:project/pom:version" -v $TAG ota-extension/api/pom.xml'
                sh 'xmlstarlet ed -P -L -N pom="http://maven.apache.org/POM/4.0.0" -u "/pom:project/pom:parent/pom:version" -v $TAG ota-extension/api/pom.xml'
                sh 'xmlstarlet ed -P -L -N pom="http://maven.apache.org/POM/4.0.0" -u "/pom:project/pom:version" -v $TAG ota-extension/impl/pom.xml'
                sh 'xmlstarlet ed -P -L -N pom="http://maven.apache.org/POM/4.0.0" -u "/pom:project/pom:parent/pom:version" -v $TAG ota-extension/impl/pom.xml'

                sh 'xmlstarlet ed -P -L -N pom="http://maven.apache.org/POM/4.0.0" -u "/pom:project/pom:parent/pom:version" -v $TAG buildtools/pom.xml'

                sh 'xmlstarlet ed -P -L -N pom="http://maven.apache.org/POM/4.0.0" -u "/pom:project/pom:version" -v $TAG examples/pom.xml'
                sh 'xmlstarlet ed -P -L -N pom="http://maven.apache.org/POM/4.0.0" -u "/pom:project/pom:parent/pom:version" -v $TAG examples/pom.xml'
                sh 'xmlstarlet ed -P -L -N pom="http://maven.apache.org/POM/4.0.0" -u "/pom:project/pom:version" -v $TAG examples/freerooms/pom.xml'
                sh 'xmlstarlet ed -P -L -N pom="http://maven.apache.org/POM/4.0.0" -u "/pom:project/pom:parent/pom:version" -v $TAG examples/freerooms/pom.xml'
                sh 'xmlstarlet ed -P -L -N pom="http://maven.apache.org/POM/4.0.0" -u "/pom:project/pom:version" -v $TAG examples/guestrequests/pom.xml'
                sh 'xmlstarlet ed -P -L -N pom="http://maven.apache.org/POM/4.0.0" -u "/pom:project/pom:parent/pom:version" -v $TAG examples/guestrequests/pom.xml'
                sh 'xmlstarlet ed -P -L -N pom="http://maven.apache.org/POM/4.0.0" -u "/pom:project/pom:version" -v $TAG examples/housekeeping/pom.xml'
                sh 'xmlstarlet ed -P -L -N pom="http://maven.apache.org/POM/4.0.0" -u "/pom:project/pom:parent/pom:version" -v $TAG examples/housekeeping/pom.xml'
                sh 'xmlstarlet ed -P -L -N pom="http://maven.apache.org/POM/4.0.0" -u "/pom:project/pom:version" -v $TAG examples/inventory/pom.xml'
                sh 'xmlstarlet ed -P -L -N pom="http://maven.apache.org/POM/4.0.0" -u "/pom:project/pom:parent/pom:version" -v $TAG examples/inventory/pom.xml'
            }
        }
        stage('Test') {
            steps {
                sh 'mvn -B -U clean verify -P it'
            }
        }
        stage('Release') {
            steps {
		        sh 'cd alpinebits-common && mvn -B -U clean deploy'
                sh 'cd alpinebits-db && mvn -B -U clean deploy'
                sh 'cd alpinebits-housekeeping && mvn -B -U clean deploy'
                sh 'cd alpinebits-mapping && mvn -B -U clean deploy'
                sh 'cd alpinebits-middleware && mvn -B -U clean deploy'
                sh 'cd alpinebits-routing && mvn -B -U clean deploy'
                sh 'cd alpinebits-servlet && mvn -B -U clean deploy'
                sh 'cd alpinebits-xml && mvn -B -U clean deploy'
                sh 'cd ota-extension && mvn -B -U clean deploy'
            }
        }
        stage('Tag') {
            steps {
                sshagent (credentials: ['jenkins_github_ssh_key']) {
                    sh "git config --global user.email 'info@opendatahub.bz.it'"
                    sh "git config --global user.name 'Jenkins'"
                    sh "git commit -a -m 'Version ${params.TAG}' --allow-empty"
                    sh "git tag -d ${params.TAG} || true"
                    sh "git tag -a ${params.TAG} -m ${params.TAG}"
                    sh "mkdir -p ~/.ssh"
                    sh "ssh-keyscan -H github.com >> ~/.ssh/known_hosts"
                    sh "git push origin HEAD:${params.BRANCH} --follow-tags"
                }
            }
        }
    }
}
