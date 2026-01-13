def call(Map cfg) {

    withCredentials([[
        $class: 'AmazonWebServicesCredentialsBinding',
        credentialsId: 'my-aws-cred'
    ]]) {

        dir(cfg.CODE_BASE_PATH) {
            sh """
              ansible-playbook ${cfg.ANSIBLE.PLAYBOOK} \
              -i ${cfg.ANSIBLE.INVENTORY} \
              -f ${cfg.ANSIBLE.FORKS}
            """
        }
    }
}
