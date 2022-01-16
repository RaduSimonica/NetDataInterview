package jenkins

pipeline {
    agent any
    stages {
        stage("Cleanup") {
            steps {
                cleanWs()
                catchError(buildResult: 'SUCCESS', stageResult: 'SUCCESS') {
                    sh "sudo docker ps"
                    sh "sudo docker stop netdata"
                    sh "sudo docker rm netdata"
                    sh "sudo docker ps"
                }
            }
        }
        stage("Deploy Netdata in docker") {
            steps {
                catchError(buildResult: 'FAILURE', stageResult: 'FAILURE') {
                    sh "sudo docker run " +
                            "-d " +
                            "--name=netdata " +
                            "-p 19999:19999 " +
                            "-v netdataconfig:/etc/netdata " +
                            "-v netdatalib:/var/lib/netdata " +
                            "-v netdatacache:/var/cache/netdata " +
                            "-v /etc/passwd:/host/etc/passwd:ro " +
                            "-v /etc/group:/host/etc/group:ro " +
                            "-v /proc:/host/proc:ro " +
                            "-v /sys:/host/sys:ro " +
                            "-v /etc/os-release:/host/etc/os-release:ro " +
                            "--restart unless-stopped " +
                            "--cap-add SYS_PTRACE " +
                            "--security-opt apparmor=unconfined " +
                            "netdata/netdata"
                }
            }
        }
    }
}