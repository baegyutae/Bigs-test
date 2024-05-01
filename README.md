
## 설정 방법

1. **데이터베이스 설정**: `application.properties` 파일에서 데이터베이스 연결 정보를 설정하세요.
2. **API 키 설정**: 공공데이터포털에서 발급받은 API 키를 `application.properties` 파일에 추가하세요.

## API 사용법

- **단기 예보 데이터 동기화**
    - **Method**: POST
    - **URL**: `/api/weather/sync`
    - **Parameters**:
      - `baseDate`: 요청할 기준 날짜 (예: `20240430`)
      - `baseTime`: 요청할 기준 시간 (예: `0500`)
    - **Description**: 공공데이터 포털에서 최신 단기 예보 데이터를 가져와 데이터베이스에 저장합니다.

- **단기 예보 데이터 조회**
    - **Method**: GET
    - **URL**: `/api/weather/forecasts`
    - **Description**: 데이터베이스에 저장된 모든 단기 예보 데이터를 조회합니다. 데이터가 없는 경우 HTTP 상태 코드 204를 반환합니다.

## 개발자 가이드

이 프로젝트는 기상 정보의 정확한 동기화와 빠른 조회를 목적으로 합니다. 코드의 가독성과 유지보수가 중요하며, 멀티모듈 구조를 통해 각 기능의 응집도를 높이고 있습니다.

## 문제
대부분의 경우 API는 정상적으로 작동하지만, 간혹 JSON 형식이 아닌 XML 형식으로 응답이 반환되어 파싱 오류가 발생합니다. 이는 500 오류로 이어지며, 오류 메시지는 다음과 같습니다:

```agsl
Error parsing JSON: Unexpected character ('<' (code 60)): expected a valid value (JSON String, Number, Array, Object or token 'null', 'true' or 'false')
at [Source: (String)"<OpenAPI_ServiceResponse>
\u0009<cmmMsgHeader>
\u0009\u0009<errMsg>SERVICE ERROR</errMsg>
\u0009\u0009<returnAuthMsg>SERVICE_KEY_IS_NOT_REGISTERED_ERROR</returnAuthMsg>
\u0009\u0009<returnReasonCode>30</returnReasonCode>
\u0009</cmmMsgHeader>
</OpenAPI_ServiceResponse>"; line: 1, column: 2]

```

### 해결 방안

현재 이 문제에 대한 정확한 원인을 찾는 중이며, 해결 방안을 모색하고 있습니다. 만약 이 오류가 발생하면, 요청을 다시 시도하거나 애플리케이션을 재시작한 후 다시 시도해 주세요. 문제의 원인과 해결 방안이 명확해지는 대로 정보를 업데이트할 예정입니다.
