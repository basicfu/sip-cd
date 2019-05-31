pipeline:
  build:
    image: gradle
    commands:
      - go get
      - go build
      - go test
  deploy:
    image: kubernetes
    environment:
      GOOS: linux
      GOARCH: amd64
      CGO_ENABLED: 0
    commands:
      - npm install
      - npm test

  publish:
    image: plugins/docker
    repo: octocat/hello-world
    tags: [ 1, 1.1, latest ]
    registry: index.docker.io

  notify:
    image: plugins/slack
    channel: developers
    username: drone



steps:
- name: test
  image: golang
  commands:
  - go test
  - go build

- name: deploy
  image: vallard/drone-kube
  settings:
    template: deployment.yaml
  when:
    event: [ push ]


prefix: jdcloud
suffix: prod
redis:
  image: redis
  tag: 4.0.11
  port:
    - 6379:6379
  command: ["redis-server"]
  args: ["--requirepass", "czkjroot"]
ui:
  image: registry.cn-beijing.aliyuncs.com/czkj/jdcloud-ui
  tag: latest
  command:
    - /bin/sh
    - -c
    - envsubst '\$API_HOST' < /etc/nginx/mysite.template > /etc/nginx/nginx.conf && nginx -g 'daemon off;'
  port:
    - 80:80
  env:
    API_HOST: sip-getway-prod.prod.svc.cluster.local
  host:
    80: jdcloud.czkj.cn
  livenessProbe:
    httpGet:
      path: /
      port: http-80
    initialDelaySeconds: 30
  readinessProbe:
    httpGet:
      path: /
      port: http-80
    initialDelaySeconds: 10
  configMap:
    nginx.conf: |-
      user nginx;
      worker_processes 1;
      error_log /var/log/nginx/error.log;
  volumes:
    nfs:
      server: 26fa04b11c-icq81.cn-shenzhen.nas.aliyuncs.com
      path:
        - /logs:/var/log/nginx:jdcloud/prod/nginx
    configMap:
      jdcloud-ui-prod:
        path:
          - /etc/nginx/mysite.template:nginx.conf
base:
  image: registry.cn-beijing.aliyuncs.com/czkj/jdcloud
  tag: latest
  count: 1
  port:
    - 80
  env:
    ENV: prod
    JDCLOUD_SERVER_PORT: 80
    JDCLOUD_EUREKA_HOST: sip-eureka-prod
    JDCLOUD_EUREKA_PORT: 80
    JDCLOUD_MYSQL_HOST: rm-wz9d8w2d884ucd7x82o.mysql.rds.aliyuncs.com
    JDCLOUD_MYSQL_PORT: 3306
    JDCLOUD_MYSQL_USERNAME: root
    JDCLOUD_MYSQL_PASSWORD: Root1234
    JDCLOUD_REDIS_HOST: jdcloud-redis-prod
    JDCLOUD_REDIS_PORT: 6379
    JDCLOUD_REDIS_PASSWORD: czkjroot
    JDCLOUD_ALIPAY_APPID: "2018071960718556"
    JDCLOUD_ALIPAY_PRIVATE_KEY: MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCvrzsKL9ykL299/YIuh053hxkNwQk1m6uJoPrJ8CM1EHLBoITyxsD3PUdpxwygJKeIpMdTbqE1UIqpFXvgIA4d67AKvAvJpt+o1Gzg+Y25HQO5IChgwEtoLKYaTpwDSi6K/a7EVxCQiyWWoPJyxZf4XBBTm+QvXyjKN0hVCYcD9cNE6REA6mUBXwKWWnzGV/faiTO3Rw+adfUxN7WTWLPuNpEiKm7siAbPjJNENysuM0NOO4iydwN0qWgRXz8eC9RhC/RNv5rxUXsuXPB7xpBx7RsfexN9uCxFfpoLZReKcyXnG2R71dZ4WK/5qXEu3l8wV9FaalxendGcyKi9PmsPAgMBAAECggEAI5dvcY/itkx53N9n6T82MVboR3YNBJRF+zrms9R3fX+YkAuSA5EqdKwwdIiD0Um7ux4O16M4+5EWlkyo7O3xjjQ6VOJlzPOGqjBm4mkMQi1TnMScCYXLgQ5SlE/KoYv8w+HC7znoPDBEbDCtDX1kXUdhc17IqqQvok/y0m98r9taTBkpak4asX7A9HvjkTpM6PWo4I9ObGgDsh2j0+QTCg52eUTuo/+MxMToNDAcP5x3HIIytHchViT1IO/PYNRvmxSojqIFbQo7e+0KEJ37AVIM5BO1OFcpFoNzCSnYFWD2AqSmktulPdTWqF3xveogKp7SV7dE57NmLiy1pTwzcQKBgQD2oocuEEtN8lSW9sBpAXoDMr7mmxolfzCUgI7OZlXuBkPoLjVU+jHsJU7rG38L53wMrL7FCXbygbasSv4zxBjaic723Jq8eTY25jcbwGWY8PfPl8Rd9QDZwL9sHiPQQygSnWYGOK9nfH4yUZmpyNbdAen/DFJQugpTCSPEvhk0lwKBgQC2WwNIHruRnpUkUOXfJcwn/zh/eBE+81Sliqs3yAkjJhAmf+7bHBO4+py7+oPIq1T13Wh+0AKEzlimacCKE3qc1hh1Z9bV9wEhVzwtqijYgzt5ud9eNRXGIvDbbcmc5zZ+mM69CkjtGDv374HToC9NzGvp0W1uXVNFKi02ytZ0SQKBgQDy3yARa3hMnNtSAlF91jdp2NoGyywXSsXzJxn6BOqPx9mtVXutX+gzbJXnjh8xANa2wJHoBKb+rl/OENd88Jms81I85Qb3nMGXlGMmT/N8haby5bg48iDRTi7y8EdCOi/HDPrAG6gXV5AKSzLv8FguMUlVoLOazWmHVZPElZLKpwKBgQCykCCAiIeHKlBEhWryvaJSMBp0x2+rwr5Kc6UBNEGuR2FdMb0ElVPCuLStZbuRziOdtbbTJoCw5xXrrnY5PqW72fGNVacLUZmrui5VUOA6Uxb0NUxChZzSrfhIBhN8LvQvtxYKCn4qnKZsO/1W6u4nMlshVE/h2ETn30ry8mBY8QKBgGYdab4BvR4mT5BvaYfjuEitnG5/QUqEF9eiByQ1jkG3aipzBKdknSx/y9OZTzdECW6ZBM9Luos0IzF4ClUXONjFf4ywaNhLG51pK2DmA2U+0S1YHbQ73jDWDBhzXRTRVw8kzAbcv2uvoi9MoQfPMmNF27ScSVzY5RhJfOupngpN
    JDCLOUD_ALIPAY_PUBLIC_KEY: MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnxEbNoPNp4Iun6i7pn+M7pU/3/x3aJS0aqX8UiakmRvmjZoTUUCsLSAwivLJa5kBzw26N9QKqq3MgdyQSHmzzkcP2EQS8RFztwa8cSBWUzQXe82yQHwTLtlXQPj4u6PY4DJZuSnvjRkL87607jWUD+fcHzzL45foyl0gUWN4eVwJInXSlJuLR7lt/yKIiaqQsvrGX17ICwkpJvzpMC2a7/oUDorBniM50PX/IL1nK7M9oIz9m37YCTXHn+mEV9RYiAk9bngFnJgr+AEhL81Qbyw/Ol/UfXotEb1RSuXRexcJBUChvk8F1UQQ/NHZRkLMCYwr2T1B4oMPs+zeSzhr6wIDAQAB
    JDCLOUD_ALIPAY_BALANCE_URL: http://sip-api.czkj.cn/jdcloud/jdcloud/alipay/balance
    JDCLOUD_ALIPAY_ORDER_URL: http://sip-api.czkj.cn/jdcloud/jdcloud/alipay/order
  livenessProbe:
    httpGet:
      path: /actuator/health
      port: http-80
    initialDelaySeconds: 120
  readinessProbe:
    httpGet:
      path: /actuator/health
      port: http-80
    initialDelaySeconds: 30
  resources:
    limits:
      cpu: 1
      memory: 1Gi
    requests:
      cpu: 10m
      memory: 200Mi
  volumes:
    nfs:
      server: 26fa04b11c-icq81.cn-shenzhen.nas.aliyuncs.com
      path:
        - /logs:/var/log/jdcloud:jdcloud/prod/java
