def call() {

    def cfg = loadConfig()

    pipeline {
        agent any

        environment {
            ANSIBLE_FORCE_COLOR = 'true'
            ANSIBLE_HOST_KEY_CHECKING = 'False'
            ENVIRONMENT = cfg.ENVIRONMENT
        }

        stages {

            stage('Checkout Code') {
                steps {
                    checkoutCode(cfg.GIT)
                }
            }

            stage('User Approval') {
                steps {
                    approvalGate(cfg.ENVIRONMENT, cfg.KEEP_APPROVAL_STAGE)
                }
            }

            stage('Apply Playbook') {
                steps {
                    runPlaybook(cfg)
                }
            }
        }

        post {
            success {
                notifySlack(
                    cfg.SLACK_CHANNEL_NAME,
                    cfg.ACTION_MESSAGE,
                    "SUCCESS"
                )
            }
            failure {
                notifySlack(
                    cfg.SLACK_CHANNEL_NAME,
                    cfg.ACTION_MESSAGE,
                    "FAILED"
                )
            }
            always {
                cleanWs()
            }
        }
    }
}
