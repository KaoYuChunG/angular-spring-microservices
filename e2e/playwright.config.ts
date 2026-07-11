import { defineConfig, devices } from '@playwright/test';

export default defineConfig({
  testDir: '.',                  // 測試檔案目錄為當前目錄
  timeout: 30 * 1000,            // 單一測試案例超時時間：30 秒 (Keycloak 啟動較慢時容錯)
  expect: {
    timeout: 5000                // 斷言超時時間：5 秒
  },
  fullyParallel: true,           // 啟用平行測試，最大化測試速度
  forbidOnly: !!process.env.CI,  // 在 Jenkins CI 中，若代碼裡殘留 test.only 則強制報錯
  retries: process.env.CI ? 2 : 0, // 在 CI 中若失敗，自動重試 2 次 (避免網絡偶發性不穩定)
  workers: process.env.CI ? 1 : undefined, // CI 中使用單線程，避免伺服器內存爆炸
  reporter: 'html',              // 產生漂亮的 HTML 測試報告

  use: {
    /* 測試使用的基礎 URL (當使用 page.goto('/login') 時會自動拼接) */
    baseURL: 'http://localhost:3000',
    
    /* 收集 Traces 以便在 Jenkins 失敗時，能下載 Trace 檔案還原當下畫面 */
    trace: 'on-first-retry',
  },

  /* 定義要測試的瀏覽器環境 */
  projects: [
    {
      name: 'chromium',
      use: { ...devices['Desktop Chrome'] },
    },
  ],
});
