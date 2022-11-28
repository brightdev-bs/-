### Thread
#### Step1
다음과 같은 상황에서는 메인 쓰레드가 동작한다.

    public void start() throws IOException {
        try(ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("[CustomWebApplicationServer] started {} port ", port);

            Socket clientScoket;
            logger.info("[CustomWebapplicationServer] waiting for client");

            while((clientScoket = serverSocket.accept()) != null) {
                logger.info("[CustomWebapplicationServer] client connected");

                // step1

                try(InputStream in = clientScoket.getInputStream(); OutputStream out = clientScoket.getOutputStream()) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));
                    DataOutputStream dos = new DataOutputStream(out);

                    HttpRequest httpRequest = new HttpRequest(br);

                    if (httpRequest.isGetRequest() && httpRequest.matchPath("/calculate")) {
                        QueryStrings queryStrings = httpRequest.getQueryStrings();

                        int operand1 = Integer.parseInt(queryStrings.getValue("operand1"));
                        String operator = queryStrings.getValue("operator");
                        int operand2 = Integer.parseInt(queryStrings.getValue("operand2"));

                        System.out.println(operand1);
                        System.out.println(operand2);
                        System.out.println(operator);

                        int result = Calculator.calculate(new PositiveNumber(operand1), operator, new PositiveNumber(operand2));
                        byte[] body = String.valueOf(result).getBytes();

                        HttpResponse response = new HttpResponse(dos);
                        response.response200Header("application/json", body.length);
                        response.responseBody(body);
                    }
                }
            }
        }
    }

다음과 같은 코드의 문제점은 이 동작을 처리하는 쓰레드가 main쓰레드이기 때문에, 처리 과정에서 쓰레드가 특정 이유에 의해 처리가 지연되고 있다면, 다른 쓰레드도 동작을 할 수 없게 된다.
따라서 다음과 같이 새로운 쓰레드를 실행하도록 개선할 수 있다.
<br>

#### step2
다음과 같은 코드에서는 새로운 쓰레드를 할당한다.

        public void start() throws IOException {
        try(ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("[CustomWebApplicationServer] started {} port ", port);

            Socket clientScoket;
            logger.info("[CustomWebapplicationServer] waiting for client");

            while((clientScoket = serverSocket.accept()) != null) {
                logger.info("[CustomWebapplicationServer] client connected");
                new Thread(new ClientRequestHandler(clientScoket)).start();

            }
        }
    }

이 코드에서는 client의 연결 요청이 들어올 때 마다 새로운 쓰레드를 할당하고, 요청이 끝나면 이를 반환한다. 이렇게 자원을 할당하고 반환하는 과정은 
비효율적이다. 이를 개선하기 위해 쓰레드 풀을 상요할 수 있다.

<br>

#### step3
다음과 같이 쓰레드 풀에 쓰레드를 미리 생성하고 이를 사용할 수 있다.

        private final ExecutorService executorService = Executors.newFixedThreadPool(10);
        
        public void start() throws IOException {
        try(ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("[CustomWebApplicationServer] started {} port ", port);

            Socket clientScoket;
            logger.info("[CustomWebapplicationServer] waiting for client");

            while((clientScoket = serverSocket.accept()) != null) {
                logger.info("[CustomWebapplicationServer] client connected");
                executorService.execute(new ClientRequestHandler(clientScoket));

            }
        }
    }