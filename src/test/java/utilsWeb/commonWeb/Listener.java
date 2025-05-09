package utilsWeb.commonWeb;

import org.testng.ITestListener;
import org.testng.ITestResult;

public class Listener implements ITestListener {

    @Override
    public void onTestStart(ITestResult result) {
        String className = result.getTestClass().getRealClass().getSimpleName();
        System.out.println(className + " - Start");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        String className = result.getTestClass().getRealClass().getSimpleName();
        System.out.println(className + " - Pass");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String className = result.getTestClass().getRealClass().getSimpleName();
        System.out.println(className + " - Fail");
    }
}
