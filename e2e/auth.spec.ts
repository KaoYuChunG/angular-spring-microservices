import { test, expect } from '@playwright/test';

test.describe('SSO Login and Gateway Header Propagation Flow', () => {
  test('should successfully authenticate via Keycloak and forward requests with headers', async ({ request }) => {
    // 1. 模擬使用者透過帳密向 Keycloak SSO 取得 Access Token
    const tokenResponse = await request.post('http://keycloak:8080/realms/study-platform/protocol/openid-connect/token', {
      form: {
        grant_type: 'password',
        client_id: 'gateway',
        client_secret: 'gateway-secret',
        username: 'student',
        password: 'password'
      }
    });

    expect(tokenResponse.ok()).toBeTruthy();
    const tokenData = await tokenResponse.json();
    const accessToken = tokenData.access_token;
    expect(accessToken).toBeDefined();

    // 2. 攜帶 Bearer Token 存取 Java Gateway 的保護端點
    // 該端點會轉發給 Downstream Auth 服務的 TestController
    const apiResponse = await request.get('http://localhost:8081/api/auth/test-headers', {
      headers: {
        'Authorization': `Bearer ${accessToken}`
      }
    });

    expect(apiResponse.ok()).toBeTruthy();
    const headersData = await apiResponse.json();
    
    // 3. 核心斷言：驗證 Downstream 是否正確接收到由 Gateway 解析並寫入的 X-Headers
    console.log('Received downstream headers:', headersData);
    expect(headersData['X-User-Id']).toBeDefined();
    expect(headersData['X-User-Id']).not.toBe('null');
    expect(headersData['X-User-Roles']).toContain('STUDENT');
  });
});
