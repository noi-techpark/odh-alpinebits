pipeline {
    agent {
        dockerfile {
            filename 'docker/dockerfile-java'
            additionalBuildArgs '--build-arg JENKINS_USER_ID=`id -u jenkins` --build-arg JENKINS_GROUP_ID=`id -g jenkins`'
        }
    }

    parameters {
        string(name: 'TAG', defaultValue: '1.0.0', description: 'Tag')
    }

    environment {
        ODH_OSSRH_USERNAME=credentials('odh-ossrh-username')
        ODH_OSSRH_PASSWORD=credentials('odh-ossrh-password')
        ODH_OSSRH_GPG_KEY=credentials('odh-ossrh-gpg-key-renewed')
    }

    stages {
        stage('Configure') {
            steps {
                sh 'gpg --import ${ODH_OSSRH_GPG_KEY}'		
		    
                sh 'sed -i -e "s/<\\/settings>$//g\" ~/.m2/settings.xml'
                sh 'echo "    <servers>" >> ~/.m2/settings.xml'
                sh 'echo "        <server>" >> ~/.m2/settings.xml'
                sh 'echo "            <id>ossrh</id>" >> ~/.m2/settings.xml'
                sh 'echo "            <username>${ODH_OSSRH_USERNAME}</username>" >> ~/.m2/settings.xml'
                sh 'echo "            <password>${ODH_OSSRH_PASSWORD}</password>" >> ~/.m2/settings.xml'
                sh 'echo "        </server>" >> ~/.m2/settings.xml'
                sh 'echo "    </servers>" >> ~/.m2/settings.xml'

                sh 'echo "        <profiles>" >> ~/.m2/settings.xml'
                sh 'echo "            <profile>" >> ~/.m2/settings.xml'
                sh 'echo "                <id>ossrh</id>" >> ~/.m2/settings.xml'
                sh 'echo "                <activation>" >> ~/.m2/settings.xml'
                sh 'echo "                    <activeByDefault>true</activeByDefault>" >> ~/.m2/settings.xml'
                sh 'echo "                </activation>" >> ~/.m2/settings.xml'
                sh 'echo "                <properties>" >> ~/.m2/settings.xml'
                sh 'echo "                    <gpg.executable>gpg2</gpg.executable>" >> ~/.m2/settings.xml'
                sh 'echo "                </properties>" >> ~/.m2/settings.xml'
                sh 'echo "            </profile>" >> ~/.m2/settings.xml'
                sh 'echo "        </profiles>" >> ~/.m2/settings.xml'

                sh 'echo "</settings>" >> ~/.m2/settings.xml'

                sh "mvn versions:set -DnewVersion=${params.TAG}"
            }
        }
        stage('Test') {
            steps {
                sh 'mvn -B -U clean verify -P it,sources'
            }
        }
        stage('Release') {
            steps {
		        sh 'mvn -B -U clean deploy -P release,sources'
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
                    sh "git push origin HEAD:master --follow-tags"
                }
            }
        }
    }
}
