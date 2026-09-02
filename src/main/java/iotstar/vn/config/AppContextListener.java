package iotstar.vn.config;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

/**
 * Đảm bảo EntityManagerFactory được đóng đúng cách khi context bị dừng/reload
 * (ví dụ khi Eclipse tự publish lại app, hoặc Tomcat undeploy).
 * Nếu không có listener này, mỗi lần redeploy sẽ để lại 1 connection pool "mồ côi"
 * gây leak thread/JDBC driver (như warning bạn thấy trong log).
 */
@WebListener
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // Không cần làm gì - JpaConfig tự khởi tạo factory khi lần đầu được gọi (lazy static init)
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        JpaConfig.close();
    }
}
