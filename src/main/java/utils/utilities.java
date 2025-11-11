package utils;

import com.aventstack.extentreports.ExtentTest;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class utilities {


    public void removeAds(WebDriver driver) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        try {
            String script =
                    "(() => {" +
                            "  function removeAdsNow() {" +
                            "    const selectors = [" +
                            "      'iframe[src*=\"ads\"]', " +
                            "      'iframe[id*=\"ad\"]', " +
                            "      '[id*=\"google_ads\"]', " +
                            "      '[class*=\"ad-\" i]', " +
                            "      '[class*=\"banner\" i]', " +
                            "      '.ad-container', '.ad-slot', '.sponsor', " +
                            "      'video[autoplay]', 'video[src*=\"ads\"]'" +
                            "    ];" +
                            "    selectors.forEach(sel => {" +
                            "      document.querySelectorAll(sel).forEach(el => {" +
                            "        if (el.tagName.toLowerCase() === 'video') {" +
                            "          try { el.pause(); el.src=''; el.remove(); } catch(e){}" +
                            "        } else {" +
                            "          el.remove();" +
                            "        }" +
                            "      });" +
                            "    });" +
                            "  }" +
                            "  removeAdsNow();" +
                            "  const observer = new MutationObserver(() => removeAdsNow());" +
                            "  observer.observe(document.body, { childList: true, subtree: true });" +
                            "  setTimeout(() => observer.disconnect(), 10000);" +
                            "})();";

            // 🔁 Run multiple passes to catch dynamically reloaded ads
            for (int i = 0; i < 2; i++) {
                js.executeScript(script);
                System.out.println("🧹 Ad removal pass " + (i + 1) + " completed.");
                Thread.sleep(2000); // wait 2s before next cleanup
            }

            System.out.println("Ads and videos removed successfully.");
        } catch (Exception e) {
            System.out.println("Ad removal failed: " + e.getMessage());
        }
    }
}
