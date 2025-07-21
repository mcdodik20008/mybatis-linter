#Модуль по организации работы с ExternalTask в системе

###Описание модуля
Модуль предоставляет API, конфигурацию, модели для организации процесса обработки ExternalTask в Worker-ах

###Состав модуля

* **ExternalTaskApplicationConfig** - содержит минимальный набор конфигураций для обработки ExternalTask

---

* **ExternalTaskExecutor** - базовый Executor для всех воркеров по обработке ExternalTask

---

* **ExternalTaskProcessor** - интерфейс который необходимо реализовать воркеру для обработки ExternalTask

###Параметры конфигурации

```yaml
camunda:
  bpm:
    client:
      base-url: ${CAMUNDA_BASE_URL:http://camunda:8080/engine-rest}
      max-tasks: ${CAMUNDA_MAX_TASKS:10}
      lock-duration: ${CAMUNDA_LOCK_DURATION:20000}
      worker-id: ${WORKER_ID:my-example-worker-1}
      use-priority: ${USE_PRIORITY:true}
      async-response-timeout: ${CAMUNDA_ASYNC_RESPONSE_TIMEOUT:60000}
    rrclient:
      watchdog-repeat-num: ${CAMUNDA_WATCHDOG_REPEAT_NUM:5}
      incident-num-retries: ${CAMUNDA_INCIDENT_NUM_RETRIES:5}
      exception-retry-timeout: ${CAMUNDA_EXCEPTION_RETRY_TIMEOUT:60000}
      engine-rest-uri: ${CAMUNDA_BASE_URL:http://localhost:8080/engine-rest}
      client-registration-id: ${CAMUNDA_CLIENT_REGISTRATION_ID:avanpost}
```

* CAMUNDA_BASE_URL - URL-адрес, указывающий на REST API Camunda.
* CAMUNDA_MAX_TASKS - максимальное кол-во задач, которое может захватить воркер за раз.
* CAMUNDA_LOCK_DURATION - определяет на сколько миллисекунд externalTask заблокированы, пока их нельзя будет снова взять
  в работу.
* WORKER_ID - Идентификатор воркера, от имени которого извлекаются задачи. Возвращенные задачи закрепляются за этим
  воркером и могут быть выполнены только при предоставлении того же идентификатора. Если параметр не указан, то
  worker-id сгенерируется автоматически (пример: Worker-45c37f701bb7c3eaa7a3-8ffe-4aa8-a242-3bcf5289cb3f).
* USE_PRIORITY - Следует ли извлекать задачу на основе ее приоритета
* CAMUNDA_ASYNC_RESPONSE_TIMEOUT - (Long Polling) Запрос от клиента будет удерживаться сервером до появления новых
  задач. Но не более указанного интервала времени. Если указанный интервал времени прошёл, а новых задач так и не
  появилось, то сервер ответит клиенту с пустым списком задач. Далее клиент сможет снова сделать новый запрос. Если
  данный параметр не указан, то Long Polling будет не активен и на каждый запрос будет получен немедленный ответ.
* Более подробное описание на https://docs.camunda.org/manual/latest/reference/rest/external-task/fetch/
* CAMUNDA_WATCHDOG_REPEAT_NUM - Количество повторов в watchdog, чтобы продлить через extendLock. На случай, если таска
  выполняется дольше, чем планировали.
* CAMUNDA_INCIDENT_NUM_RETRIES - Количество повторов retry при регистрации инцидентов, если это значение не пришло из
  Camunda
* CAMUNDA_EXCEPTION_RETRY_TIMEOUT - Через сколько external task будет доступен к повторной обработке после ошибки. На
  случай если выбросили не RetryException, а общий Exception
