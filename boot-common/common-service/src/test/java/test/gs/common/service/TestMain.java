package test.gs.common.service;

/**
 * description:
 *
 * @auth guoshuai
 * @since 2018/8/30
 */
public class TestMain {

    protected void aaa(){
        this.getMethodName();
    }

    protected String getMethodName(){
        String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();
        System.out.println(methodName);
        return methodName;
    }

    public static void main(String[] args) {

        TestMain testMain = new TestMain();

        testMain.aaa();
    }
}
